package listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.testslidenerd.R;

import java.util.ArrayList;
import activities.ChildrenActivity;
import activities.GetOffersActivity;

public class GenericListener implements AdapterView.OnItemClickListener {

    Context context;
    Intent intent;
    int listenerFlag;
    ArrayList<Integer> categoryIdsList;
    TextView textView;

    public GenericListener(Context context,ArrayList<Integer> categoryIdsList,int listenerFlag ) {
        this.context=context;
        this.categoryIdsList=categoryIdsList;
        this.listenerFlag=listenerFlag;
        textView=(TextView)((Activity)context).findViewById(R.id.textView1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (listenerFlag){
            case 0:{                        //0 means comes from categories  so open childrenActivity
                intent = new Intent(context, ChildrenActivity.class);
                intent.putExtra("categoryId", categoryIdsList.get(position));
                showToast(categoryIdsList.get(position).toString());
                context.startActivity(intent);
                break;
            }
            case 1:{                    //1 means comes from products  so open GetOffersActivity
                intent = new Intent(context, GetOffersActivity.class);
                intent.putExtra("categoryId", categoryIdsList.get(position));
                showToast(categoryIdsList.get(position).toString());
                context.startActivity(intent);
                break;
            }
        }

    }

    private void showToast(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
