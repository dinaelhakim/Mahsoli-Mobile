package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lenovo.testslidenerd.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import pojos.OfferClass;

public class OfferDetailsActivity extends AppCompatActivity {

    TextView nameTextView,descriptionTextView,priceTextView,quntityTextView,startDateTextView,locationTextView,userPhoneTextView;
    ImageView offerImageView;
    Button okButton;
    String from;
    OfferClass offerObj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_offers_details);
        initUI();
        Intent intent=getIntent();
        from=intent.getStringExtra("from");

        offerObj=(OfferClass)intent.getSerializableExtra("offer");
        nameTextView.setText("Product Id : "+offerObj.getProducctId());
        descriptionTextView.setText(offerObj.getDescription());
        priceTextView.setText(((Integer)offerObj.getPrice()).toString());
        quntityTextView.setText(((Integer)offerObj.getQuntity()).toString());
        startDateTextView.setText(offerObj.getStart_date());
        locationTextView.setText(offerObj.getUserLocation());
        userPhoneTextView.setText(offerObj.getUserPhone());
        ImageLoader.getInstance().displayImage(offerObj.getImageUrl(), offerImageView);
        setButtonListener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //to display back button
    }

    private void setButtonListener() {
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from.equals("GetOffersActivity")){
                    finish();
                }else if(from.equals("GetMyOffersActivity")){
                    Intent intent = new Intent(OfferDetailsActivity.this, GetMyOffersActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initUI() {
        nameTextView=(TextView)findViewById(R.id.id_productName);
        descriptionTextView=(TextView)findViewById(R.id.id_offer_description);
        priceTextView =(TextView)findViewById(R.id.id_price);
        quntityTextView=(TextView)findViewById(R.id.id_quntity);
        startDateTextView =(TextView)findViewById(R.id.id_start_date);
        locationTextView=(TextView)findViewById(R.id.id_location);
        userPhoneTextView=(TextView)findViewById(R.id.id_mobile_number);
        offerImageView=(ImageView)findViewById(R.id.id_offer_image_view);
        okButton=(Button)findViewById(R.id.okButton);
    }

    //To be back when you click back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  android.R.id.home:
                if(from.equals("GetOffersActivity")){
                    finish();
                }else if(from.equals("GetMyOffersActivity")){
                    Intent intent = new Intent(OfferDetailsActivity.this, GetMyOffersActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(from.equals("GetOffersActivity")){
            finish();
        }else if(from.equals("GetMyOffersActivity")){
            Intent intent = new Intent(OfferDetailsActivity.this, GetMyOffersActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
