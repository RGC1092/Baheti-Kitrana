package com.shiva.bahetikirana.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.shiva.bahetikirana.Adapter.AllBillAdapter;
import com.shiva.bahetikirana.Database.DatabaseHelper;
import com.shiva.bahetikirana.Pojo.MyBills;
import com.shiva.bahetikirana.R;
import com.shiva.bahetikirana.Utils.Preferences;

import java.util.ArrayList;

public class BillsActivity extends AppCompatActivity {
    RecyclerView lvBill;
    Context context;
    ArrayList<MyBills> myBillLists;
    AllBillAdapter BillAdapter;
    Preferences pref;
    private RecyclerView.LayoutManager mLayoutManager;
    // Database Helper
    DatabaseHelper db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        db = new DatabaseHelper(getApplicationContext());


        if (myBillLists == null) {
            myBillLists = new ArrayList<>();
        }

        lvBill=findViewById(R.id.lvBill);
        lvBill.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        BillAdapter = new AllBillAdapter(myBillLists);
        lvBill.setLayoutManager(mLayoutManager);

        myBillLists.clear();
        myBillLists.addAll(db.getAllBills());
        BillAdapter = new AllBillAdapter(myBillLists);
        lvBill.setAdapter(BillAdapter);
        //productLists.add(new ProductList(Product, Quantity, Rate, Total));
        BillAdapter.notifyItemInserted(myBillLists.size());

    }
}
