package activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import java.util.Iterator;
import java.util.Locale;

import adapters.GenericAdapter;
import listeners.GenericListener;
import reguest_parameters_pojos.JsonParams;
import web_services_connections_handlers.JsonStringGenerator;
import web_services_connections_handlers.WebServicesUrl;

public class ChildrenActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    GenericAdapter genericAdapter;
    ArrayList<String> categoriesNames;
    ArrayList<String> imagesUrl;
    ArrayList<Integer> categoryIdsList;
    String parameters ;
    String key;
    ListView listView;


    public ChildrenActivity() {
        categoriesNames=new ArrayList();
        imagesUrl=new ArrayList();
        categoryIdsList=new ArrayList<>();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //to display back button
        listView= (ListView) findViewById(R.id.listView);
        Log.i("++","childrenActivity");
        Intent intent=getIntent();
        int categoryId = intent.getIntExtra("categoryId", -1);
        parameters= JsonStringGenerator.getGetChildrenParameters(Locale.getDefault().getLanguage(),categoryId);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, WebServicesUrl.GET_CHILDREN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        Log.i("++", response.toString());
                        try {
                            String x=response.getString("entity");
                            JSONObject jsonObjectEntity = new JSONObject(x);

                            Iterator<String> iterator=jsonObjectEntity.keys();
                            key = iterator.next();
                            Log.i("++Key",key);
                            JSONArray jsonArrayCategories=jsonObjectEntity.getJSONArray(key);

                            for (int i=0; i< jsonArrayCategories.length() ;i++ ){
                                JSONObject jsonObjectName = jsonArrayCategories.getJSONObject(i);
                                //Name
                                String name=null;
                                if(Locale.getDefault().getLanguage().equals("en")){
                                    name=jsonObjectName.getString("nameEn");
                                }else if(Locale.getDefault().getLanguage().equals("ar")){
                                    name=jsonObjectName.getString("nameAr");
                                }
                                Log.i("++",name);
                                categoriesNames.add(i,name);
                                //imageUrl
                                String imageUri=jsonObjectName.getString("imageUrl");
                                Log.i("++",imageUri);
                                imagesUrl.add(i,WebServicesUrl.Image_URL+imageUri);
                                //Id
                                Integer categoryId=jsonObjectName.getInt("id");
                                Log.i("++categoryId",categoryId.toString());
                                categoryIdsList.add(i,categoryId);
                            }
                            genericAdapter.notifyDataSetChanged();

                            if(key.equals("categories")){
                                Log.i("++hello","hello set listener");
                                listView.setOnItemClickListener(new GenericListener(ChildrenActivity.this, categoryIdsList,0));
                            }else if(key.equals("products")){
                                Log.i("++hello else","hello don't set listener");
                                listView.setOnItemClickListener(new GenericListener(ChildrenActivity.this, categoryIdsList, 1));
                            }
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

        genericAdapter=new GenericAdapter(this, categoriesNames,imagesUrl);
        listView.setAdapter(genericAdapter);

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
