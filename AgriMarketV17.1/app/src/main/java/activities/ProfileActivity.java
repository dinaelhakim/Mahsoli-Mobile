package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import DB.SqlDB;
import de.hdodenhof.circleimageview.CircleImageView;
import pojos.User;
import web_services_connections_handlers.UniversalImageLoaderConfiguration;
import com.example.lenovo.testslidenerd.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends AppCompatActivity {

    SqlDB sqlDB=new SqlDB(this);
    User user;
    CircleImageView userImage;
    TextView name,email,location,mobile,getOffers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();
        setListenerForGetOffersTextView();


        user=new User();
        user=sqlDB.getUserData();
        setUIData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListenerForGetOffersTextView() {
        getOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,GetMyOffersActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initUI(){
        userImage=(CircleImageView)findViewById(R.id.image_view_user_image);
        name=(TextView)findViewById(R.id.text_view_user_name);
        mobile=(TextView)findViewById(R.id.text_view_user_mobile);
        location=(TextView)findViewById(R.id.text_view_user_location);
        email=(TextView)findViewById(R.id.text_view_user_email);
        getOffers=(TextView)findViewById(R.id.text_view_get_my_offers);
    }

    private void setUIData(){
        UniversalImageLoaderConfiguration.setConfig(getBaseContext());
        ImageLoader.getInstance().displayImage(user.getImageUrl(), userImage);
        name.setText(user.getName());
        mobile.setText(user.getMobile());
        email.setText(user.getEmail());
        location.setText(user.getLocation());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_profile_menu, menu);
        return true;
    }


    //To be back when you click back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent editProfileIntent=new Intent(this, EditProfileActivity.class);
                startActivity(editProfileIntent);
                finish();
                break;
            case android.R.id.home:
                Intent mainActivityIntenet=new Intent(this,MainCategoriesActivity.class);
                startActivity(mainActivityIntenet);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainCategoriesActivity.class);
        startActivity(intent);
        finish();
    }
}
