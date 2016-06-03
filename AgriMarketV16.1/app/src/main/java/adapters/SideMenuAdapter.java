package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.testslidenerd.R;

public class SideMenuAdapter extends BaseAdapter {

    Context context;
    String[] setting;

    int[] images={R.drawable.home,R.drawable.profile,R.drawable.contactus,R.drawable.logout};

    public SideMenuAdapter(Context context, String[] setting) {

        this.context=context;
        this.setting=setting;
    }

    @Override
    public int getCount() {
        return setting.length;
    }

    @Override
    public Object getItem(int position) {
        return setting[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(!(position==0)){
        View row;
        if(convertView==null){
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=inflater.inflate(R.layout.side_menu_custom_row,parent,false);
            }else{
                row=convertView;
        }
        ImageView image=(ImageView)row.findViewById(R.id.imageView1);
        TextView text1=(TextView)row.findViewById(R.id.textView1);
        image.setImageResource(images[position]);
        text1.setText(setting[position]);
        return row;
        }else {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.custom_header_for_side_menu,parent,false);
        }
    }
}
