package activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.lenovo.testslidenerd.R;


public class SplashScreen extends Activity {
    public static final String fileName="SharedFile";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SharedPreferences sharedPref = getSharedPreferences(fileName, 0);
                    int id=sharedPref.getInt("id",0);
                    if(id==0){
                        intent = new Intent(SplashScreen.this, UserCheckActivity.class);
                    }else{
                        intent = new Intent(SplashScreen.this, MainCategoriesActivity.class);
                    }
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}