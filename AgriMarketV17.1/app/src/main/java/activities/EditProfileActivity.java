package activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.testslidenerd.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import DB.SqlDB;
import de.hdodenhof.circleimageview.CircleImageView;
import pojos.User;
import reguest_parameters_pojos.JsonParams;
import web_services_connections_handlers.JsonStringGenerator;
import web_services_connections_handlers.WebServicesUrl;

public class EditProfileActivity extends AppCompatActivity {

    SqlDB sqlDB=new SqlDB(this);
    CircleImageView userImage;
    EditText name;
    EditText email;
    EditText location;
    EditText mobile;
    Button save;
    private static final int CAMERA_REQUEST = 1888;
    public static final String fileName="SharedFile";
    String requestParemeters;


    Intent getIntent;
    int isFromNavDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getIntent=getIntent();
        isFromNavDrawer=getIntent.getIntExtra("navDrawer",-1);
        initUI();
        User user=sqlDB.getUserData();
        setUIData(user);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO create New User
                User newUser=new User();
                newUser.setName(name.getText().toString());
                newUser.setEmail(email.getText().toString());
                newUser.setLocation(location.getText().toString());

                //Image
                Bitmap bmp = ((BitmapDrawable)userImage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                newUser.setImage(byteArray);


                //Id
                SharedPreferences sharedPref = getSharedPreferences(fileName, 0);
                int userId=sharedPref.getInt("id", -1);
                newUser.setId(userId);


                //TODO update UserData in DB


                //TODO request updateUser
                requestParemeters = JsonStringGenerator.getUpdateUserParameters(newUser.getId(),newUser.getName(),newUser.getLocation(),newUser.getEmail(),newUser.getMobile(),newUser.getImage());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, WebServicesUrl.UPDAT_USER, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("++", response.toString());
                                try {
                                    String x=response.getString("entity");
                                    Log.i("++",x);
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
                        return getRequestBody(requestParemeters).getBytes();
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(EditProfileActivity.this);
                queue.add(request);



                //TODO back To ProfileActivity Activity & finish update User
                Intent intent;
                if(isFromNavDrawer==1){
                    intent=new Intent(EditProfileActivity.this, MainCategoriesActivity.class);
                }else {
                    intent=new Intent(EditProfileActivity.this, ProfileActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //to display back button
    }


    private void initUI(){
        userImage=(CircleImageView)findViewById(R.id.image_view_update_user_image);
        name=(EditText)findViewById(R.id.edit_text_user_name);
        mobile=(EditText)findViewById(R.id.edit_text_user_mobile);
        location=(EditText)findViewById(R.id.edit_text_user_location);
        email=(EditText)findViewById(R.id.edit_text_user_email);
        save=(Button)findViewById(R.id.btn_edit_user);
    }

    private void setUIData(User user){

        ImageLoader.getInstance().displayImage(user.getImageUrl(), userImage);
        name.setText(user.getName());
        mobile.setText(user.getMobile());
        email.setText(user.getEmail());
        location.setText(user.getLocation());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap userImageBitMap = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(userImageBitMap);
        }
    }

    //To be back when you click back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  android.R.id.home:
               Intent intent;
                if(isFromNavDrawer==1){
                    intent=new Intent(this, MainCategoriesActivity.class);
                }else {
                    intent=new Intent(this, ProfileActivity.class);
                }
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        if(isFromNavDrawer==1){
            intent=new Intent(this, MainCategoriesActivity.class);
        }else {
            intent=new Intent(this, ProfileActivity.class);
        }
        startActivity(intent);
        finish();
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
}
