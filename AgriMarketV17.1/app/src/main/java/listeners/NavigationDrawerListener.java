package listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
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
import activities.ContactUsActivity;
import activities.EditProfileActivity;
import activities.GetMyOffersActivity;
import activities.MainCategoriesActivity;
import activities.ProfileActivity;
import activities.UserCheckActivity;
import reguest_parameters_pojos.JsonParams;
import web_services_connections_handlers.JsonStringGenerator;
import web_services_connections_handlers.WebServicesUrl;


public class NavigationDrawerListener implements NavigationView.OnNavigationItemSelectedListener  {

    public static final String shareFileName="SharedFile";
    MainCategoriesActivity context;
    Intent intent;
    public NavigationDrawerListener(MainCategoriesActivity context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Toast.makeText(context,"home",Toast.LENGTH_LONG).show();
            intent=new Intent(context, MainCategoriesActivity.class);

        }
        else if (id == R.id.nav_profile) {

            Toast.makeText(context,"profile",Toast.LENGTH_LONG).show();
            intent=new Intent(context, ProfileActivity.class);
        }
        else if (id == R.id.nav_contact_us) {

            Toast.makeText(context,"contact us",Toast.LENGTH_LONG).show();
            intent=new Intent(context, ContactUsActivity.class);
        }
        else if (id == R.id.nav_log_out) {

            SharedPreferences preferences = context.getSharedPreferences(shareFileName, 0);
            preferences.edit().remove("id").commit();
            String parameters = JsonStringGenerator.getLogOutParameters(preferences.getInt("id", -1));
            requestLogOutService(parameters);
            intent=new Intent(context, UserCheckActivity.class);
            Toast.makeText(context,"Log out successfully",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_edit_pofile) {

            Toast.makeText(context,"editProfile",Toast.LENGTH_LONG).show();
            intent=new Intent(context, EditProfileActivity.class);
            intent.putExtra("navDrawer",1);
        }
        else if (id == R.id.nav_my_offers) {

            Toast.makeText(context,"myOffers",Toast.LENGTH_LONG).show();
            intent=new Intent(context, GetMyOffersActivity.class);
            intent.putExtra("navDrawer",1);
        }

        context.startActivity(intent);
        context.finish();
        return true;
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


        RequestQueue queue = Volley.newRequestQueue(context);
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
