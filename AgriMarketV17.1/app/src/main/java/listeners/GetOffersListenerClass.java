package listeners;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import java.util.ArrayList;

import activities.GetOffersActivity;
import activities.OfferDetailsActivity;
import pojos.OfferClass;

public class GetOffersListenerClass implements AdapterView.OnItemClickListener{


    GetOffersActivity getOffersActivity;
    ArrayList<OfferClass> arrayListOfOffers;
    public GetOffersListenerClass(GetOffersActivity getOffersActivity,ArrayList<OfferClass> arrayListOfOffers) {
        this.getOffersActivity=getOffersActivity;
        this.arrayListOfOffers=arrayListOfOffers;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        OfferClass offerObj=arrayListOfOffers.get(position);
        Intent intent=new Intent(getOffersActivity, OfferDetailsActivity.class);
        intent.putExtra("offer", offerObj);
        intent.putExtra("from","GetOffersActivity");
        getOffersActivity.startActivity(intent);
    }
}
