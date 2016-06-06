package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lenovo.testslidenerd.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import pojos.OfferClass;
import web_services_connections_handlers.UniversalImageLoaderConfiguration;



public class OffersAdapterClass extends BaseAdapter{
    Context context;
    ArrayList<OfferClass> userOffersList;

    public OffersAdapterClass(Context context, ArrayList<OfferClass> userOffersList) {
        this.context = context;
        this.userOffersList = userOffersList;
    }

    @Override
    public int getCount() {
        return userOffersList.size();
    }

    @Override
    public Object getItem(int position) {
        return userOffersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.custom_row,parent,false);
        }else {
            row=convertView;
        }
        ImageView imageView1=(ImageView)row.findViewById(R.id.imageView1);
        TextView textView1=(TextView)row.findViewById(R.id.textView1);
        TextView textView2=(TextView)row.findViewById(R.id.textView2);
        UniversalImageLoaderConfiguration.setConfig(context);
        ImageLoader.getInstance().displayImage(userOffersList.get(position).getImageUrl(), imageView1);
        textView1.setText("Product Id : "+userOffersList.get(position).getProducctId());
        textView2.setText(userOffersList.get(position).getDescription());
        ImageView image2=(ImageView)row.findViewById(R.id.voice);
        image2.setVisibility(View.INVISIBLE);
        return row;
    }
}
