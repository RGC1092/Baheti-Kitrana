package com.shiva.bahetikirana.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.shiva.bahetikirana.Adapter.AllBillAdapter;
import com.shiva.bahetikirana.Database.DatabaseHelper;
import com.shiva.bahetikirana.Pojo.ProductList;
import com.shiva.bahetikirana.R;
import com.shiva.bahetikirana.Utils.EnglishNumberToWords;
import com.shiva.bahetikirana.Utils.Preferences;
import com.shiva.bahetikirana.Utils.ScreenshotUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class UpdateInovice extends AppCompatActivity {

    Context context = this;
    EditText etQuantity, etRate;
    //RecyclerView lvProduct;
    ArrayList<ProductList> productLists;
    Preferences pref;
    AllBillAdapter productAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtBillNo, txtDate, txtName, txtMobile, txtAddress,
            txtAmountsWords, txtPayBy, txtPaidAmount, txtBalanceAmount,
            txtSubTotal, txtWages, txtCutting, txtTransport, txtTotal, txtSendInvoice;
    private TextView txtproduct, txtquantity, txtrate, txttotal, txtsrNo;
    String BillNo, BillDate = "", CustName = "", Address = "", Mobile = "", Product = "", PayBy = "";
    float Wages, Transport, Cutting, WagesRate, TransportRate, CuttingRate, WagesValue, TransportValue, CuttingValue, TotalPay, paidAmount, balanceAmount, GrossTotal, Quantity, Rate;
    private String sharePath = "no";
    private LinearLayout llInvoice;
    // Database Helper
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_inovice);
        pref = Preferences.getSharedPrefInstance(context);
        db = new DatabaseHelper(getApplicationContext());

        hideSoftKeyboard();
        initUI();
        loadInvoiceData();
        SetInvoiceData();

    }

    private void SetInvoiceData() {

        txtBillNo.setText(BillNo);
        txtDate.setText(BillDate);
        txtName.setText(CustName);
        txtMobile.setText(Mobile);
        txtAddress.setText(Address);
        txtsrNo.setText("1");
        txtproduct.setText(Product);
        txtquantity.setText(String.valueOf(Quantity));
        txtrate.setText(String.valueOf(Rate));
        txttotal.setText(String.valueOf(Quantity * Rate));
        txtAmountsWords.setText(ConvertIntoWords());
        txtPayBy.setText(PayBy);
        txtPaidAmount.setText("₹ " + String.valueOf(paidAmount));
        txtBalanceAmount.setText("₹ " + String.valueOf(calcBalanceAmount()));
        txtSubTotal.setText("₹ " + String.valueOf(Math.round(GrossTotal)));
        txtWages.setText("₹ " + String.valueOf(Math.round(Wages)));
        txtCutting.setText("₹ " + String.valueOf(Math.round(Cutting)));
        txtTransport.setText("₹ " + String.valueOf(Math.round(Transport)));
        txtTotal.setText("₹ " + String.valueOf(Math.round(totalPay())));
    }

    private float calcBalanceAmount() {

        balanceAmount = (totalPay() - (paidAmount));
        balanceAmount = (Math.round(balanceAmount));
        return balanceAmount;
    }

    private String ConvertIntoWords() {
        String inWords = EnglishNumberToWords.convert(Math.round(totalPay()));
        return inWords+" Only";
    }

    private float totalPay() {

        float Total = ((GrossTotal) - (Cutting) - (Wages) - (Transport));
        Total = (Math.round(Total));

        return Total;
    }

    private void loadInvoiceData() {
        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            BillNo = intent.getExtras().getString("billNo");
            BillDate = intent.getExtras().getString("billDate");
            CustName = intent.getExtras().getString("custName");
            Address = intent.getExtras().getString("custAddress");
            Mobile = intent.getExtras().getString("MobileNo");
            Cutting = intent.getFloatExtra("cutting", 0);
            Wages = intent.getFloatExtra("wages", 0);
            Transport = intent.getFloatExtra("transport", 0);
            CuttingRate = intent.getFloatExtra("cutting_rate", 0);
            WagesRate = intent.getFloatExtra("wages_rate", 0);
            TransportRate = intent.getFloatExtra("transport_rate", 0);
            CuttingValue = intent.getFloatExtra("cutting_value", 0);
            WagesValue = intent.getFloatExtra("wages_value", 0);
            TransportValue = intent.getFloatExtra("transport_value", 0);
            Product = intent.getExtras().getString("product");
            Quantity = intent.getFloatExtra("quantity", 0);
            Rate = intent.getFloatExtra("rate", 0);
            GrossTotal = intent.getFloatExtra("gross_total", 0);
            PayBy = intent.getExtras().getString("payBy");
            paidAmount = intent.getFloatExtra("paidAmount", 0);


            Log.i("Rahul", "\nBillNo: " + BillNo + "\n" +
                    "BillDate: " + BillDate + "\n" +
                    "CustName: " + CustName + "\n" +
                    "Address: " + Address + "\n" +
                    "Mobile: " + Mobile + "\n" +
                    "Cutting: " + Cutting + "\n" +
                    "Wages: " + Wages + "\n" +
                    "Transport: " + Transport + "\n" +
                    "Pay By: " + PayBy + "\n" +
                    "Paid Amount: " + paidAmount);

        }

    }

    //hide keyboard once focus is moved from the screen
    public void hideSoftKeyboard() {
        if (UpdateInovice.this.getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) UpdateInovice.this.getSystemService(INPUT_METHOD_SERVICE);
            assert inm != null;
            inm.hideSoftInputFromWindow(UpdateInovice.this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void initUI() {


        //  lvProduct = findViewById(R.id.lvProduct);
        txtBillNo = findViewById(R.id.txtBillNo);
        txtDate = findViewById(R.id.txtDate);
        txtName = findViewById(R.id.txtName);
        txtMobile = findViewById(R.id.txtMobile);
        txtAddress = findViewById(R.id.txtAddress);
        txtAmountsWords = findViewById(R.id.txtAmountsWords);
        txtPayBy = findViewById(R.id.txtPayBy);
        txtPaidAmount = findViewById(R.id.txtPaidAmount);
        txtBalanceAmount = findViewById(R.id.txtBalanceAmount);
        txtSubTotal = findViewById(R.id.txtSubTotal);
        txtWages = findViewById(R.id.txtWages);
        txtCutting = findViewById(R.id.txtCutting);
        txtTransport = findViewById(R.id.txtTransport);
        txtTotal = findViewById(R.id.txtTotal);
        txtSendInvoice = findViewById(R.id.txtSendInvoice);
        llInvoice = findViewById(R.id.llInvoice);

        txtsrNo = findViewById(R.id.txtSrNo);
        txtproduct = findViewById(R.id.txtProduct);
        txtquantity = findViewById(R.id.txtQuantity);
        txttotal = findViewById(R.id.txtProductTotal);
        txtrate = findViewById(R.id.txtRate);


    }


    public void onClickSendInvoice(View view) {

        db.updateBill(BillNo, CustName, Address, Mobile, Product, Quantity, Rate, GrossTotal, CuttingRate, WagesRate, TransportRate, CuttingValue, WagesValue, TransportValue, totalPay(), PayBy, paidAmount, calcBalanceAmount());

        Bitmap b = null;
        b = ScreenshotUtils.getScreenShot(llInvoice);
        //If bitmap is not null
        if (b != null) {
            //showScreenShotImage(b);//show bitmap over imageview
            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + now + ".jpg", saveFile);//save the screenshot to selected path
            shareScreenshot(file);//finally share screenshot
        } else {
            //If bitmap is null show toast message
            Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
        }
    }


    /*  Share Screenshot  */
    private void shareScreenshot(File file) {
        // Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Uri uri = FileProvider.getUriForFile(
                UpdateInovice.this,
                "com.shiva.bahetikirana.fileprovider", //(use your app signature + ".provider" )
                file);

        Intent sendIntent = new Intent("android.intent.action.SEND");
        sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.ContactPicker"));
        sendIntent.setType("image");
        sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+Mobile)+"@s.whatsapp.net");
        sendIntent.putExtra(Intent.EXTRA_TEXT,"");
        startActivity(sendIntent);
        finish();
    }
}

