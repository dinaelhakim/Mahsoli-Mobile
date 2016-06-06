package activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.testslidenerd.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import DB.SqlDB;
import pojos.User;
import reguest_parameters_pojos.JsonParams;
import web_services_connections_handlers.JsonStringGenerator;
import web_services_connections_handlers.WebServicesUrl;

public class LocationActivity extends AppCompatActivity {

    Button next;
    Spinner locationSpinner;
    TextView textviewGpsAddress;
    ImageView imageviewVoice;

    String latitude,longitude,phone,email,userName, imageUrl;

    SqlDB mydb;
    User user;

    public byte[] imageBteArray;
    boolean isGpsEnabled;
    static int once=1,isOnce=1;
    public static final String shareFileName="SharedFile";



    //AddUserWebService Variables------
    private ProgressDialog dialog;
    String parameters ;
    //---------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //to display back button
        initUI();
        getUserDataFromIntent();

//Get LocationActivity By GPS ------------------------------------------
        setLatAndLongByGPS();
//------------------------------------------------------------------
        String[] locations=getResources().getStringArray(R.array.locations);
        ArrayAdapter<String> locationAdapter=new ArrayAdapter(LocationActivity.this, android.R.layout.simple_spinner_item,locations);
        locationSpinner.setAdapter(locationAdapter);

        imageviewVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mPlayer = MediaPlayer.create(LocationActivity.this, R.raw.hello);
                mPlayer.start();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveMobileNumberInSharedPreferences();
                parameters = JsonStringGenerator.getAddUserParameters(userName,locationSpinner.getSelectedItem().toString(),email,phone,imageBteArray);
                connectToAddUserWebService();
                Intent intent = new Intent(LocationActivity.this, MainCategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void connectToAddUserWebService() {

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, WebServicesUrl.ADD_USER, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        Log.i("++",response.toString());
                        try {
                            String responseString=response.getString("entity");
                            JSONObject jsonObjectEntity = new JSONObject(responseString);
                            Integer userId=jsonObjectEntity.getInt("id");
                            Log.i("+", userId.toString());
                            Toast.makeText(LocationActivity.this,userId.toString(),Toast.LENGTH_LONG).show();
                            saveUserIdOnSharedPreferences(userId);
                            saveUserInSqlDB(userId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("++",error.getMessage());
                    }
                }
        ){
            // this is the relevant method
            @Override
            public byte[] getBody() {
                return getRequestBody(parameters).getBytes();
            }
        };


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

    private void saveUserIdOnSharedPreferences(Integer userId) {
        SharedPreferences sharedPref = getSharedPreferences(shareFileName, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", userId);
        editor.commit();
    }


    public void hideDialog(){
        if(dialog !=null){
            dialog.dismiss();
            dialog=null;
        }
    }


    private String getRequestBody(String parameters){
        JsonParams param = new JsonParams();
        param.setJsonObject(parameters);
        param.setUserService("user");
        param.setKeyService("encrypted");
        param.setCompressLength(3);

        Gson gson = new GsonBuilder().create();
        String body = gson.toJson(param);

        return body;
    }

    private void saveUserInSqlDB(int userId){
        mydb=new SqlDB(getApplicationContext());
        user=new User();
        user.setMobile(phone);
        if(!isGpsEnabled){
            user.setLocation(locationSpinner.getSelectedItem().toString());
        }else {
            user.setLocation(textviewGpsAddress.getText().toString());
        }
        user.setId(userId);
        user.setName(userName);
        user.setEmail(email);
        user.setImage(imageBteArray);
        user.setImageUrl(imageUrl);
        mydb.signUp(user);
    }
    private void saveMobileNumberInSharedPreferences(){
        SharedPreferences sharedPref = getSharedPreferences(shareFileName, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("phone", phone);
        editor.putString("email", email);
        editor.commit();
    }

    private void setLatAndLongByGPS(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGpsEnabled) {
            textviewGpsAddress.setText(R.string.deactivatedGps);
            locationSpinner.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            latitude=String.valueOf(location.getLatitude());
            longitude=String.valueOf(location.getLongitude());
            convertLatAndLogToAddress();
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };

    private void convertLatAndLogToAddress(){
        Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude),Double.parseDouble(longitude), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!addresses.isEmpty()&&(once==1)) {
            textviewGpsAddress.setText(addresses.get(0).getAddressLine(2));
            next.setVisibility(View.VISIBLE);
            once=0;
        }else if(isOnce==1) {
            textviewGpsAddress.setText(R.string.weakGpsSignal);
            locationSpinner.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            isOnce=0;
        }
    }


    private void getUserDataFromIntent(){
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
        userName=intent.getStringExtra("userName");
        email=intent.getStringExtra("userEmail");
        imageBteArray=intent.getByteArrayExtra("userImageByteArray");
        imageUrl=intent.getStringExtra("userImageUrl");

        Log.i("++location",phone+userName+imageUrl+imageBteArray+email);

    }

    private void initUI(){
        next=(Button)findViewById(R.id.next);
        imageviewVoice=(ImageView)findViewById(R.id.voice);
        locationSpinner=(Spinner)findViewById(R.id.locationSpinner);
        textviewGpsAddress=(TextView)findViewById(R.id.textview_gps_address);
    }

    //To be back when you click back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  android.R.id.home:
                Intent intent=new Intent(LocationActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LocationActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

}
