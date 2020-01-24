package com.shiva.bahetikirana.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shiva.bahetikirana.Activity.BillsActivity;
import com.shiva.bahetikirana.Activity.CreateNewBill;
import com.shiva.bahetikirana.Activity.Inovice;
import com.shiva.bahetikirana.Activity.UpdateBill;
import com.shiva.bahetikirana.Pojo.MyBills;
import com.shiva.bahetikirana.R;

import java.util.ArrayList;

/**
 * Created by RAHUL on 20-Jan-2020.

 */
public class AllBillAdapter extends RecyclerView.Adapter<AllBillAdapter.ExampleViewHolder> {
    private ArrayList<MyBills> billArrayList ;
    SharedPreferences preferences;
    Context context ;



    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView txtBillDate;
        public TextView txtBillNo;
        public TextView txtName;
        public TextView txtAddress;
        public TextView txtMobile;
        public TextView txtPaidAmount;
        public TextView txtBalanceAmount;

        public ExampleViewHolder(final View itemView) {
            super(itemView);

            txtBillDate = itemView.findViewById(R.id.txtBillDate);
            txtBillNo = itemView.findViewById(R.id.txtBillNo);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtPaidAmount = itemView.findViewById(R.id.txtPaidAmount);
            txtBalanceAmount = itemView.findViewById(R.id.txtBalanceAmount);



            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        MyBills myBills = billArrayList.get(pos);
                        Toast.makeText(v.getContext(), "You clicked " + myBills.getNAME(), Toast.LENGTH_SHORT).show();


                        Intent intentOTPLogin = new Intent(itemView.getContext(), UpdateBill.class);
                        intentOTPLogin.putExtra("billNo", myBills.getBILL_NO());
                        intentOTPLogin.putExtra("billDate", myBills.getBILL_DATE());
                        intentOTPLogin.putExtra("custName", myBills.getNAME());
                        intentOTPLogin.putExtra("custAddress", myBills.getADDRESS());
                        intentOTPLogin.putExtra("MobileNo", myBills.getMOBILE());
                        intentOTPLogin.putExtra("product", myBills.getPRODUCT());
                        intentOTPLogin.putExtra("quantity", myBills.getQUANTITY());
                        intentOTPLogin.putExtra("rate", myBills.getRATE());
                        intentOTPLogin.putExtra("gross_total", myBills.getSUBTOTAL());
                        intentOTPLogin.putExtra("cutting_value", myBills.getCUTTING_VALUE());
                        intentOTPLogin.putExtra("wages_value", myBills.getWAGES_VALUE());
                        intentOTPLogin.putExtra("transport_value", myBills.getTRANSPORT_VALUE());
                        intentOTPLogin.putExtra("cutting_rate", myBills.getCUTTING_RATE());
                        intentOTPLogin.putExtra("wages_rate", myBills.getWAGES_RATE());
                        intentOTPLogin.putExtra("transport_rate", myBills.getTRANSPORT_RATE());
                        intentOTPLogin.putExtra("pay_by", myBills.getPAYBY());
                        intentOTPLogin.putExtra("paid", myBills.getPAID());
                        intentOTPLogin.putExtra("balance", myBills.getBALANCE());

                        intentOTPLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        itemView.getContext().startActivity(intentOTPLogin);


                        Log.i("Rahul", "\nBillNo: " + myBills.getPAYBY() + "\n" +
                                "Product: " + myBills.getPAID() + "\n" +
                                "Quantity: " + myBills.getBALANCE() + "\n" +
                                "Rate: " + myBills.getRATE() + "\n" );


                    }
                }
            });
        
        }
    }

    public AllBillAdapter(ArrayList<MyBills> productLists ) {
        billArrayList = productLists;
    }

    @Override
    public AllBillAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bill, parent, false);
        AllBillAdapter.ExampleViewHolder evh = new AllBillAdapter.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(AllBillAdapter.ExampleViewHolder holder, int position) {
        final MyBills currentItem = billArrayList.get(position);

        holder.txtBillNo.setText("Bill No : #00" +currentItem.getBILL_NO());
        holder.txtBillDate.setText("Billing Date: " + currentItem.getBILL_DATE());
        holder.txtName.setText("Name : " + currentItem.getNAME());
        holder.txtAddress.setText("Name : " + currentItem.getADDRESS());
        holder.txtMobile.setText("Mobile : " + currentItem.getMOBILE());
        holder.txtPaidAmount.setText("Paid Amount : " + currentItem.getPAID() ) ;
        holder.txtBalanceAmount.setText("Balance Amount : " +currentItem.getBALANCE());
    }

    @Override
    public int getItemCount() {
        return billArrayList.size();
    }
}
