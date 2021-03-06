package solstice.DAO;


import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import challenge.solstice.myapp.R;
import solstice.model.Contacts_Mems;
import solstice.appsback.StoreDetails;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private static final String TAG = "Item position";    // Logging to a file
    private List<Contacts_Mems> lforCast;                       //BackEnd Java DB Object list
    Context contextweatherFore;
    public RvAdapter(List<Contacts_Mems> forecastData, Context contextweatherFore) {
        this.lforCast = forecastData;
        this.contextweatherFore = contextweatherFore;
    }

    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vH = LayoutInflater.from(parent.getContext())                          // Attaching backEnd Data to View
                .inflate(R.layout.conactsgenlist,parent,false);
        ViewHolder vCont = new ViewHolder(vH, contextweatherFore, lforCast);
        return vCont;
    }

    @Override
    public void onBindViewHolder(RvAdapter.ViewHolder holder, int position) {
        Contacts_Mems foreData = lforCast.get(position);
        holder.tvAddr.setText(foreData.getDb_Addr());
        holder.tvPh.setText(foreData.getDb_Tue());
        foreData.setDb_imgUrl(foreData.getDb_imgUrl(),holder.imgView);
    }
    @Override
    public int getItemCount() {
        return lforCast.size();            // return the size in the list
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView tvPh, tvAddr;
            ImageView imgView;
            List<Contacts_Mems> lAdapterItems;
            Context cntextDisp;

    /**
     * @param itemView
     * @param cntxt
     * @param fItemData
     */

ViewHolder(View itemView, Context cntxt, List<Contacts_Mems> fItemData) {
            super(itemView);
            this.lAdapterItems = fItemData;
            this.cntextDisp = cntxt;
            itemView.setOnClickListener(this);      //Handle Clicks
            tvAddr = itemView.findViewById(R.id.tvAddr);
            tvPh = itemView.findViewById(R.id.tvPhone);
            imgView = itemView.findViewById(R.id.imgURL);
        }

/**
   recyclerView onClick() method provides all available Resources available in that recyclerView Adapter
   like in this case 03 parameters Address, Name, PhoneNumber

**/
    @Override
        public void onClick(View view) {
        int pos = getAdapterPosition();                     //get Individual Item-position clicked
        Contacts_Mems lItems = lAdapterItems.get(pos);         //get data-Object from List
        String vLink = lItems.getDb_Addr();
        String vPh = lItems.getDb_Tue();
        String iImageRemote = lItems.getDb_imgUrl();
        double[] lLatLong = lItems.getPojoLat_Long();

        Intent iActWDetail = new Intent(this.cntextDisp, StoreDetails.class);
        iActWDetail.putExtra("dArrayLatLong",lLatLong);
        iActWDetail.putExtra("addrStrr",vLink);
        iActWDetail.putExtra("phStrr",vPh);
        iActWDetail.putExtra("imgStrr",iImageRemote);
        iActWDetail.putExtra("dArrayLat_",lLatLong);

        Context contextDetail = view.getContext();
        contextDetail.startActivity(iActWDetail);
    }
    }

}


