package bbitb.com.donateplus;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Avraj Singh on 17/12/2017.
 */

public class DonationListAdapter extends ArrayAdapter<donation>{
    private List<donation> donationList;
    private Context donationContext;

    public DonationListAdapter(List<donation> donationList, Context donationContext){
        super(donationContext, R.layout.donationitem, donationList);
        this.donationList = donationList;
        this.donationContext = donationContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(donationContext);
        View donationListItem = inflater.inflate(R.layout.donationitem, null, true);

        TextView TVdonationID = donationListItem.findViewById(R.id.D_ID);
        TextView TVprofileName = donationListItem.findViewById(R.id.P_Name);
        TextView TVdonationType = donationListItem.findViewById(R.id.D_Type);
        TextView TVdonationAmount = donationListItem.findViewById(R.id.D_Amount);
        TextView TVdropOffLoc = donationListItem.findViewById(R.id.D_DropOff);
        TextView TVdonationStatus = donationListItem.findViewById(R.id.D_Status);

        donation donation = donationList.get(position);

        TVdonationID.setText("#"+donation.getDonationID());
        TVprofileName.setText(donation.getProfileName());
        TVdonationType.setText(donation.getDonationType());
        if(donation.getDonationType().equals("Food")){
            TVdonationAmount.setText(donation.getDonationAmount()+" kgs");
        }else if(donation.getDonationType().equals("Money")){
            TVdonationAmount.setText("Kshs. "+donation.getDonationAmount());
        }else if(donation.getDonationType().equals("Clothes")){
            TVdonationAmount.setText(donation.getDonationAmount()+" articles of clothing");
        }
        TVdropOffLoc.setText(donation.getDropOffLoc());
        TVdonationStatus.setText(donation.getDonationStatus());

        return donationListItem;
    }
}
