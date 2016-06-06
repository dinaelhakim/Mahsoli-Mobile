package activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import reguest_parameters_pojos.JsonParams;
import web_services_connections_handlers.JsonStringGenerator;
import web_services_connections_handlers.WebServicesUrl;


public class UserCheckActivity extends AppCompatActivity {

    EditText edit_text_email;
    Button btnUserCheck;
    TextView goToSignUp;
    public static final String shareFileName="SharedFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_check_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //to display back button
        initUI();
        setListeners();


    }

    private void setListeners() {
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCheckActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnUserCheck.setOnClickListener(new View.OnClickListener() {
            String parameters;
            @Override
            public void onClick(View v) {
                boolean isValidEmail = false;
                //check if email empty
                if (!isEmptyEditText()) {
                    isValidEmail = isValidEmail(edit_text_email.getText().toString());
                }
                if (isValidEmail) {

                    //connect  To  WEb  Service
                    parameters = JsonStringGenerator.getUserCheckParam(edit_text_email.getText().toString(), 1);

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, WebServicesUrl.USER_CHECK, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            Log.i("++ the response is: ", response.toString());
                            try {
                                String x = response.getString("entity");
                                Log.i("++", x);
                                JSONObject jsonObjectEntity = new JSONObject(x);
                                int id=jsonObjectEntity.getInt("userId");

                                if(id==-1){
                                    Toast.makeText(getBaseContext(),"not registered sigin Up",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(UserCheckActivity.this, SignUpActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    SharedPreferences sharedPref = getSharedPreferences(shareFileName, 0);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("id", id);
                                    editor.commit();
                                    Intent intent=new Intent(UserCheckActivity.this,MainCategoriesActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("++ error ", error.getMessage());
                                }
                            }

                    ) {
                        // this is the relevant method
                        @Override
                        public byte[] getBody() {
                            return getRequestBody(parameters).getBytes();
                        }
                    };


                    RequestQueue queue = Volley.newRequestQueue(UserCheckActivity.this);
                    queue.add(request);

                }
            }
        });
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

    private boolean isEmptyEditText(){

        if(edit_text_email.getText().toString().length()==0){
            return true;
        }else
            return false;
    }

    private boolean isValidEmail(String email) {
        boolean isValidEmail;


        if(  (!((email.contains("."))&&(email.contains("@"))))     && (email.length()>6)  ){
            edit_text_email.setError("Invalid Email");
            isValidEmail=false;
        }else{
            isValidEmail=true;
        }

        return isValidEmail;
    }

    private void initUI() {
        edit_text_email=(EditText)findViewById(R.id.edit_text_email);
        btnUserCheck=(Button)findViewById(R.id.btn_user_check);
        goToSignUp=(TextView)findViewById(R.id.go_to_sign_up);
    }

    //To be back when you click back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
