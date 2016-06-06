package activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.lenovo.testslidenerd.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import web_services_connections_handlers.GetUserImageAsynchTask;



public class SignUpActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private GoogleApiClient mPlusClient;
    private int REQUEST_CODE_RESOLVE_ERR=301;
    ImageView imageViewVoice;
    TextInputEditText editTextPhoneNumber;
    SignInButton btnSignInWithGooglePlus;
    Boolean isVaildstart=true;
    Boolean isValidNumber=false;
    Person person;
    String email,imageUrl,userName,phone;
    public byte[] imageByteArray;


    TextView logIn;




    //facebook
    String facebook_id , profile_image;
    CallbackManager callbackManager;
    Button btnSignInWithFaceBook;
    GetUserImageAsynchTask getUserImageAsynchTask;
    boolean callbackFlag=true;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (callbackFlag){
           super.onActivityResult(requestCode, resultCode, data);
           callbackManager.onActivityResult(requestCode, resultCode, data);
       }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        getUserImageAsynchTask=new GetUserImageAsynchTask(SignUpActivity.this);
        initUI();
//To change color of line under phone number
        editTextPhoneNumber.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        setListenerForVoiceImage();
        // Initializing google plus api client
        mPlusClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        btnSignInWithGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             callbackFlag=false;
                if (isNetworkConnected()) {
                    if (editTextPhoneNumber.getText().toString().isEmpty()) {
                        editTextPhoneNumber.setError("phone number is required");
                    } else {
                        if (isValidNumber) {
                            if (mPlusClient.isConnected()) {
                                showMsg("user is connected");
                            } else {
                                mPlusClient.connect();
                            }
                        }
                    }
                }
            }
        });

        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());

        btnSignInWithFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()){
                    if (editTextPhoneNumber.getText().toString().isEmpty()) {
                        editTextPhoneNumber.setError("phone number is required");
                    } else {
                        if (isValidNumber) {

                            LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
                            callbackManager = CallbackManager.Factory.create();

                            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                                @Override
                                public void onSuccess(LoginResult loginResult) {
                                    Profile profile = Profile.getCurrentProfile();
                                    if (profile != null) {
                                        facebook_id=profile.getId();
                                        userName =profile.getName();
                                        profile_image=profile.getProfilePictureUri(400, 400).toString();
                                        URL img_value = null;
                                        try {
                                            img_value = new URL("http://graph.facebook.com/"+facebook_id+"/picture?type=large");
                                            imageUrl=img_value.toString();
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
//                                        try {
//                                            Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
//
//                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                                            mIcon1.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
//                                            imageByteArray = baos.toByteArray();
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }



                                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                                new GraphRequest.GraphJSONObjectCallback() {
                                                    @Override
                                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                                        email= object.optString("email");

                                                    }

                                                });

                                        Bundle params = new Bundle();
                                        params.putString("fields", "id,email,gender,cover,picture.type(large)");
                                        request.setParameters(params);
                                        request.executeAsync();

                                        if(email!=null){
//                                            getUserImageAsynchTask.execute(imageUrl, null, null);
                                            imageByteArray=null;
                                            email="hello";
                                            goToNextStepInLogInWithSomeOfUserData();
                                        }else {
                                            showMsg("@string/errorMessage");
                                        }

                                    }

                                }
                                @Override
                                public void onCancel() {
                                    Log.i("++","cancelFB");

                                }

                                @Override
                                public void onError(FacebookException error) {
                                    Log.i("++","errorFB");
                                }
                            });

                        }
                    }
                }
            }
        });




        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    editTextPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                    editTextPhoneNumber.setError("phone number is required");
                    isValidNumber=false;
                }
                if(s.length()==3){
                    String phoneNumber=editTextPhoneNumber.getText().toString();
                    String firstThreeDigits=phoneNumber.substring(0,3);

                    if(!(firstThreeDigits.equals("011"))&&!(firstThreeDigits.equals("012"))&&!(firstThreeDigits.equals("010"))){
                        editTextPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                        editTextPhoneNumber.setError("Invalid number, number should start with 010 or 011 or 012");
                        isVaildstart=false;
                        isValidNumber=false;
                    }else {
                        isVaildstart=true;
                    }
                }
                if(s.length()>3 && s.length()<11&&isVaildstart){
                    editTextPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                    editTextPhoneNumber.setError("phone Number must be 11 digits");
                    isValidNumber=false;
                }
                if(s.length()==11&& isVaildstart==true){
                    editTextPhoneNumber.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    isValidNumber=true;
                }
                if(s.length()>11){
                    editTextPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                    editTextPhoneNumber.setError("phone Number must be 11 digits");
                    isValidNumber=false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {

        if (result.hasResolution()) {

            try {
                result.startResolutionForResult(SignUpActivity.this, REQUEST_CODE_RESOLVE_ERR);
            } catch (SendIntentException e) {
                mPlusClient.disconnect();
                mPlusClient.connect();
            }
        }
    }


    @Override
    public void onConnected(Bundle arg0) {
        getUserData();
        toastUserData();
        getUserImageAsynchTask.execute(imageUrl, null, null);
    }

    public void goToNextStepInLogInWithSomeOfUserData(){
        Log.i("++","7asbi allah w n3m el wakil");
        Intent intent=addUserDataToIntent();
        startActivity(intent);
    }

    private Intent addUserDataToIntent(){
        Intent intent = new Intent(SignUpActivity.this, LocationActivity.class);
        phone=editTextPhoneNumber.getText().toString();

        intent.putExtra("phone", phone);
        intent.putExtra("userName", userName);
        intent.putExtra("userImageUrl",imageUrl);
        intent.putExtra("userImageByteArray", imageByteArray);
        intent.putExtra("userEmail", email);
        Log.i("++login", phone + userName + imageUrl + imageByteArray + email);
        return intent;
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlusClient.connect();
    }


    private void getUserData(){
        person=Plus.PeopleApi.getCurrentPerson(mPlusClient);
        imageUrl=person.getImage().getUrl();
        userName=person.getDisplayName();
        email = Plus.AccountApi.getAccountName(mPlusClient);
    }


    void showMsg(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    private void toastUserData(){
        showMsg("signed in google+");
        showMsg(userName + "\n" + editTextPhoneNumber.getText().toString() + "\n" + "+++" + imageUrl.toString() + "\n" + email);

    }


    private void setListenerForVoiceImage(){
        imageViewVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(SignUpActivity.this, R.raw.hello);
                mediaPlayer.start();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, UserCheckActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initUI(){
        imageViewVoice=(ImageView)findViewById(R.id.voice);
        editTextPhoneNumber=(TextInputEditText)findViewById(R.id.edit_text_phone_number);
        btnSignInWithGooglePlus=(SignInButton)findViewById(R.id.btn_sign_in_with_google_plus);
        btnSignInWithFaceBook=(Button)findViewById(R.id.btn_sign_in_with_facebook);
        logIn=(TextView)findViewById(R.id.go_to_login_in);
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



    //TODO
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() == null){
            showMsg("no network");
            return false;
        }
        return true;
    }
}