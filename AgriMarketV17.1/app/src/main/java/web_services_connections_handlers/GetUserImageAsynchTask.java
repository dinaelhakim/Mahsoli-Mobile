package web_services_connections_handlers;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import activities.SignUpActivity;

public class GetUserImageAsynchTask extends AsyncTask<String, Void, Bitmap>{

    SignUpActivity logIn;
    byte[] byteArray;

    public GetUserImageAsynchTask(SignUpActivity logIn) {
        this.logIn = logIn;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream in=null;
        try {
            URL url = new URL(params[0]);
            in = url.openStream();
            return BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.i("error", ":(((");

        }
        return BitmapFactory.decodeStream(in);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Log.i("error>>>>>",bitmap.toString());
        boolean flag=bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        if(bitmap==null){
            Log.i("error", "bitmab asln b null");
        }else { Log.i("error", "ana fel asynch task w msh b  null");}
        if(flag){
            Log.i("error","el compession s7");
        }else {
            Log.i("error","elcompression 8lt");
        }


        byteArray = stream.toByteArray();


        if(byteArray==null){
            Log.i("error", "ana fel asynch task byte array asln b null");
        }else { Log.i("error", "ana fel asynch task byte[] w msh b  null");}

        logIn.imageByteArray=byteArray;


        if(logIn.imageByteArray!=null){
            Log.i("error","ana el byt[] bta3 el login ="+logIn.imageByteArray.toString());
        }

        logIn.goToNextStepInLogInWithSomeOfUserData();
    }
}

