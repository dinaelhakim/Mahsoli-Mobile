package activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.testslidenerd.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import listeners.GetOffersListenerClass;
import adapters.OffersAdapterClass;
import pojos.OfferClass;
import reguest_parameters_pojos.JsonParams;
import web_services_connections_handlers.JsonStringGenerator;
import web_services_connections_handlers.WebServicesUrl;

public class GetOffersActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton fab;
    String parameters,categoryName;
    ProgressDialog dialog;
    ArrayList<OfferClass> offersList=new ArrayList<>();
    OffersAdapterClass getMyOffersListAdapterClass;
    int categoryId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //to display back button

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action, productId=" +categoryId, Snackbar.LENGTH_LONG).setAction("Action", null).show();
         //       Intent intent=new Intent(GetOffersActivity.this,);//
         //       intent.putExtra("productId",categoryId);
          //      startActivity(intent);
            }
        });




        listView= (ListView) findViewById(R.id.listView);
        Log.i("++", "GetOffersActivity");
        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);
        categoryName=intent.getStringExtra("categoryName");
        Log.i("++offerid= ",((Integer)categoryId).toString());



        parameters= JsonStringGenerator.getGetOffersParameters(categoryId);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, WebServicesUrl.GET_OFFERS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String x=response.getString("entity");
                            Log.i("++",x);

                            Log.i("++",x);
                            JSONObject jsonObjectEntity = new JSONObject(x);
                            JSONArray jsonArrayOffers=jsonObjectEntity.getJSONArray("offers");
                            for (int i=0; i< jsonArrayOffers.length() ;i++ ){
                                JSONObject jsonObjectOffer = jsonArrayOffers.getJSONObject(i);
                                OfferClass offerObj=new OfferClass();
                                offerObj.setProducctId(categoryId);
                                offerObj.setPrice(jsonObjectOffer.getInt("price"));
                                offerObj.setQuntity(jsonObjectOffer.getInt("quantity"));
                                offerObj.setStart_date(jsonObjectOffer.getString("startDate"));
                                offerObj.setUserPhone(jsonObjectOffer.getString("userPhone"));
                                offerObj.setUserLocation(jsonObjectOffer.getString("userLocation"));
                                offerObj.setDescription(jsonObjectOffer.getString("description"));
                                offerObj.setImageUrl(WebServicesUrl.Image_URL + jsonObjectOffer.getString("imageUrl"));
                                offersList.add(offerObj);
                            }
                            getMyOffersListAdapterClass.notifyDataSetChanged();
                            listView.setOnItemClickListener(new GetOffersListenerClass(GetOffersActivity.this,offersList));

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


        getMyOffersListAdapterClass=new OffersAdapterClass(getBaseContext(),offersList);
        listView.setAdapter(getMyOffersListAdapterClass);
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



    //To be back when you click back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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
