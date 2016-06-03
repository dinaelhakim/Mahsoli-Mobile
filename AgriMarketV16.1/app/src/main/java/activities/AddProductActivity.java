package activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lenovo.testslidenerd.R;

public class AddProductActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    Bitmap photo;
    ImageView productImage;
    Button saveProductButton;
    Spinner priceUnitsSpinner;
    ImageView imageViewVoice;
    EditText editTextProductName;
    EditText editTextProductDescription;
    EditText editTextPrice;
    EditText editTextPhoneNumber;
    String stringProductName;
    String stringProductDescription;
    String stringPrice;
    String stringPhoneNumber;
    TextView textViewErrorProductName;
    TextView textViewErrorProductDescription;
    TextView textViewErrorPrice;
    TextView textViewErrorPhoneNumber;



    boolean saveFlag;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initUI();
        setListeners();
        setAdapterForUnitSpinner();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo  = (Bitmap) data.getExtras().get("data");
            productImage.setImageBitmap(photo);
        }
    }

    private void initUI(){
        saveProductButton=(Button)findViewById(R.id.saveButton);
        priceUnitsSpinner=(Spinner)findViewById(R.id.priceUnitsSpinner);
        imageViewVoice=(ImageView)findViewById(R.id.voice);
        productImage=(ImageView)findViewById(R.id.productImage);
        editTextProductName=(EditText)findViewById(R.id.edit_text_product_name);
        editTextProductDescription=(EditText)findViewById(R.id.edit_text_product_description);
        editTextPrice=(EditText)findViewById(R.id.edit_text_price);
        editTextPhoneNumber=(EditText)findViewById(R.id.edit_text_phone_number);
        textViewErrorProductName=(TextView)findViewById(R.id.text_view_error_product_name);
        textViewErrorProductDescription=(TextView)findViewById(R.id.edit_text_product_description);
        textViewErrorPrice=(TextView)findViewById(R.id.text_view_error_price);
        textViewErrorPhoneNumber=(TextView)findViewById(R.id.text_view_error_phone_number);
    }

    private void setListeners(){

        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromEditTexts();
                saveFlag=true;

                if(stringProductName.isEmpty()){
                    textViewErrorProductName.setVisibility(View.VISIBLE);
                    saveFlag=false;
                }
                if(stringProductDescription.isEmpty()){
                    textViewErrorProductDescription.setVisibility(View.VISIBLE);
                    saveFlag=false;
                }
                if(stringPrice.isEmpty()){
                    textViewErrorPrice.setVisibility(View.VISIBLE);
                    saveFlag=false;
                }
                if((stringPhoneNumber.isEmpty())||(stringPhoneNumber.length()!=11)){
                    textViewErrorPhoneNumber.setVisibility(View.VISIBLE);
                    saveFlag=false;
                }
                if(saveFlag){
                   //insert in DB Should be Here
                    finish();
                }
            }
        });

        imageViewVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mPlayer = MediaPlayer.create(AddProductActivity.this, R.raw.hello);
                mPlayer.start();
            }
        });

        productImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }
    private void setAdapterForUnitSpinner(){
        String[] priceUnite={"Kilo","Ton","Fdan"};
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,priceUnite);
        priceUnitsSpinner.setAdapter(arrayAdapter);
    }
    private void getDataFromEditTexts(){
        stringProductName=editTextProductName.getText().toString();
        stringProductDescription=editTextProductDescription.getText().toString();
        stringPrice=editTextPrice.getText().toString();
        stringPhoneNumber=editTextPhoneNumber.getText().toString();
    }
}
