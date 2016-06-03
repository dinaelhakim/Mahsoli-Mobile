package listeners;


import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import activities.UserCheckActivity;
import activities.ContactUsActivity;
import activities.MainCategoriesActivity;
import activities.ProfileActivity;
import reguest_parameters_pojos.JsonParams;
import web_services_connections_handlers.JsonStringGenerator;
import web_services_connections_handlers.WebServicesUrl;

public class SideMenuListener implements AdapterView.OnItemClickListener{

    MainCategoriesActivity mainActivity;
    ListView listView;
    String[] setting;
    public static final String shareFileName="SharedFile";
    public SideMenuListener(MainCategoriesActivity mainActivity,ListView listView,String[] setting) {
        this.mainActivity = mainActivity;
        this.listView=listView;
        this.setting=setting;
    }

   @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            listView.setItemChecked(position, true);
            mainActivity.setTitle(setting[position]);
            switch (position){
                case 0:{
                    Intent homeIntent=new Intent(mainActivity,MainCategoriesActivity.class);
                    mainActivity.startActivity(homeIntent);
                    mainActivity.finish();
                    break;
                }
                case 1:{
                    Intent signUpIntent=new Intent(mainActivity, ProfileActivity.class);
                    mainActivity.startActivity(signUpIntent);
                    mainActivity.finish();
                    break;
                }
                case 2:{
                    Intent signUpIntent=new Intent(mainActivity,ContactUsActivity.class);
                    mainActivity.startActivity(signUpIntent);
                    mainActivity.finish();
                    break;
                }
                case 3:{
                    SharedPreferences preferences = mainActivity.getSharedPreferences(shareFileName, 0);
                    preferences.edit().remove("id").commit();
                    String parameters = JsonStringGenerator.getLogOutParameters(preferences.getInt("id",-1));
                    requestLogOutService(parameters);

                    Intent userCheckIntent=new Intent(mainActivity, UserCheckActivity.class);
                    mainActivity.startActivity(userCheckIntent);
                    mainActivity.finish();
                    Toast.makeText(mainActivity,"Log out successfully",Toast.LENGTH_LONG).show();
                    break;
                }

            }
        }

    private void requestLogOutService(final String parameters) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, WebServicesUrl.LOG_OUT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("++", response.toString());
                        try {
                            String x=response.getString("entity");
                            Log.i("++",x);
                        }catch (JSONException e) {
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
            @Override
            public byte[] getBody() {
                return getRequestBody(parameters).getBytes();
            }
        };


        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        queue.add(request);
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
