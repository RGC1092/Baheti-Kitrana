package com.shiva.bahetikirana.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiva.bahetikirana.Database.DatabaseHelper;
import com.shiva.bahetikirana.Pojo.ProductList;
import com.shiva.bahetikirana.R;
import com.shiva.bahetikirana.Utils.Preferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateNewBill extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    private static final String TAG = "Rahul";
    AutoCompleteTextView etProduct;
    EditText etQuantity, etRate, etBillNo, etDate, etCustName, etAddress, etMobile, etCutting, etWages, etTransport,etCuttingValue, etWagesValue, etTransportValue;
    TextView /*txtAddProduct, txtGrossTotal,*/ txtGenerate;
    //RecyclerView lvProduct;
    ArrayList<ProductList> productLists;
    Preferences pref;
    private RecyclerView.LayoutManager mLayoutManager;
    // Database Helper
    DatabaseHelper db;
    String BillNo = "", BillDate = "", CustName = "", Address = "", Mobile = "";
    String[] ProductList = {"Maize", "Soyabean", "Jawar", "Wheat", "Bajara"};
    String[] PaymentList = {"Cash", "Cheque", "Online"};
    String Product = "", PayBy;
    float GrossTotal,Quantity, Rate, Total,Wages, Transport, Cutting, WagesRate, TransportRate, CuttingRate,WagesValue, TransportValue, CuttingValue, paidAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_bill);
        pref = Preferences.getSharedPrefInstance(context);
        db = new DatabaseHelper(getApplicationContext());

        initUI();
        initListner();


    }

    private void initListner() {
        txtGenerate.setOnClickListener(this);
        etDate.setOnClickListener(this);
    }

    private void initUI() {
        etProduct = findViewById(R.id.etProduct);
        etQuantity = findViewById(R.id.etQuantity);
        etRate = findViewById(R.id.etRate);
        txtGenerate = findViewById(R.id.txtGenerate);

        etBillNo = findViewById(R.id.etBillNo);
        etBillNo.setText(String.valueOf(db.getBillNo()+1));
        Log.i("Rahul","DB.Bill No"+db.getBillNo());
        etDate = findViewById(R.id.etDate);
        setDate();
        etCustName = findViewById(R.id.etCustName);
        etAddress = findViewById(R.id.etAddress);
        etMobile = findViewById(R.id.etMobile);
        etCutting = findViewById(R.id.etCutting);
        etWages = findViewById(R.id.etWages);
        etTransport = findViewById(R.id.etTransport);
        etCuttingValue = findViewById(R.id.etCuttingValue);
        etWagesValue = findViewById(R.id.etWagesValue);
        etTransportValue = findViewById(R.id.etTransportValue);




        //Product List
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ProductList);
        etProduct.setAdapter(adapter);


        etProduct.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                // TODO Auto-generated method stub
                etProduct.showDropDown();
                etProduct.requestFocus();
                return false;
            }
        });


    }

    private boolean validateAdd() {
        if (etProduct.getText().toString().equalsIgnoreCase("")) {
            etProduct.setError("Select Product");
            etProduct.requestFocus();
            Toast.makeText(context, "Select Product", Toast.LENGTH_SHORT).show();

        } else if (etQuantity.getText().toString().equalsIgnoreCase("")) {
            etQuantity.setError("Enter Quantity");
            etQuantity.requestFocus();
            Toast.makeText(context, "Enter Quantity", Toast.LENGTH_SHORT).show();

        } else if (etRate.getText().toString().equalsIgnoreCase("")) {
            etRate.setError("Enter Rate");
            etRate.requestFocus();
            Toast.makeText(context, "Enter Rate", Toast.LENGTH_SHORT).show();

        } else {
            return true;
        }
        return false;
    }

    private void setDate() {
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//formating according to my need
        String date = formatter.format(today);
        etDate.setText(date);
    }




    private void saveData() {
        Gson gson = new Gson();
        String json = gson.toJson(productLists);
        pref.saveProductList(json);
    }


    private float calGrossTotal(float quantity , float rate) {

        GrossTotal = quantity * rate;
        Log.i(TAG, "GrossTotal: " + GrossTotal);


        return GrossTotal;
    }private float calCutting(float value , float cutting) {

        Cutting = value * cutting;
        Log.i(TAG, "Cutting: " + Cutting);


        return Cutting;
    }

    private float calWages(float value ,float wages) {
        Wages = value * wages;
        Log.i(TAG, "Wages: " + Wages);

        return Wages;
    }

    private float calTransport(float value ,float transport) {
        Transport = value * transport;
        Log.i(TAG, "Transport: " + Transport);
        return Transport;
    }


    public void onClickGenerateBill(View view) {


        if (validate()) {

            //saveData();

            BillNo = etBillNo.getText().toString();
            BillDate = etDate.getText().toString();
            CustName = etCustName.getText().toString();
            Address = etAddress.getText().toString();
            Mobile = etMobile.getText().toString();
            Product =etProduct.getText().toString();
            Quantity = Float.parseFloat(etQuantity.getText().toString());
            Rate =Float.parseFloat(etRate.getText().toString());
            CuttingRate =Float.parseFloat(etCutting.getText().toString());
            WagesRate = Float.parseFloat(etWages.getText().toString());
            TransportRate =Float.parseFloat(etTransport.getText().toString());
            CuttingValue = Float.parseFloat(etCuttingValue.getText().toString());
            WagesValue = Float.parseFloat(etWagesValue.getText().toString());
            TransportValue = Float.parseFloat(etTransportValue.getText().toString());
           GrossTotal = calGrossTotal(Quantity,Rate);
            Log.i(TAG, "GrossTotal click: " + GrossTotal);
           Cutting = calCutting(CuttingValue,CuttingRate);
           Wages =calWages(WagesValue,WagesRate);
           Transport = calTransport(TransportValue,TransportRate);

            Log.i(TAG, "\nBillNo: " + BillNo + "\n" +
                    "BillDate: " + BillDate + "\n" +
                    "CustName: " + CustName + "\n" +
                    "Address: " + Address + "\n" +
                    "Mobile: " + Mobile + "\n" +
                    "Product: " + Product + "\n" +
                    "Quantity: " + Quantity + "\n" +
                    "Rate: " + Rate + "\n" +
                    "GrossTotal: " + GrossTotal + "\n" +
                    "Cutting: " + Cutting + "\n" +
                    "Wages: " + Wages + "\n" +
                    "Transport: " + Transport);

            ShowDialogBill();
        }
        /*Intent intentMain = new Intent(this, Inovice.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentMain);*/

    }

    private boolean validate() {

        BillNo = etBillNo.getText().toString();
        BillDate = etDate.getText().toString();
        CustName = etCustName.getText().toString();
        Address = etAddress.getText().toString();
        Mobile = etMobile.getText().toString();

        if (BillNo.equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter Bill No", Toast.LENGTH_SHORT).show();
        } else if (BillDate.equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter Bill Date", Toast.LENGTH_SHORT).show();
        } else if (CustName.equalsIgnoreCase("")) {
            etCustName.setError("Enter Customer Name");
            etCustName.requestFocus();
            Toast.makeText(context, "Enter Customer Name", Toast.LENGTH_SHORT).show();
        } else if (Address.equalsIgnoreCase("")) {
            etAddress.setError("Enter Customer Address");
            etAddress.requestFocus();
            Toast.makeText(context, "Enter Customer Address", Toast.LENGTH_SHORT).show();
        } else if (Mobile.equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter Customer Mobile No", Toast.LENGTH_SHORT).show();
        } else if (Mobile.length() != 0 && Mobile.length() < 10) {
            etMobile.setError("Enter Mobile No");
            etMobile.requestFocus();
            Toast.makeText(context, "Enter Mobile No", Toast.LENGTH_SHORT).show();
        } else if (etProduct.getText().toString().equalsIgnoreCase("")) {
            etProduct.setError("Select Product");
            etProduct.requestFocus();
            Toast.makeText(context, "Select Product", Toast.LENGTH_SHORT).show();

        } else if (etQuantity.getText().toString().equalsIgnoreCase("")) {
            etQuantity.setError("Enter Quantity");
            etQuantity.requestFocus();
            Toast.makeText(context, "Enter Quantity", Toast.LENGTH_SHORT).show();

        } else if (etRate.getText().toString().equalsIgnoreCase("")) {
            etRate.setError("Enter Rate");
            etRate.requestFocus();
            Toast.makeText(context, "Enter Rate", Toast.LENGTH_SHORT).show();

        } else if (etCuttingValue.getText().toString().equalsIgnoreCase("")) {
            etCuttingValue.setError("Enter Cutting Quantity");
            etCuttingValue.requestFocus();
            Toast.makeText(context, "Enter Cutting Quantity", Toast.LENGTH_SHORT).show();
        }else if (etCutting.getText().toString().equalsIgnoreCase("")) {
            etCutting.setError("Enter Cutting Rate");
            etCutting.requestFocus();
            Toast.makeText(context, "Enter Cutting Rate", Toast.LENGTH_SHORT).show();
        } else if (etWagesValue.getText().toString().equalsIgnoreCase("")) {
            etWagesValue.setError("Enter Wages Quantity");
            etWagesValue.requestFocus();
            Toast.makeText(context, "Enter Wages Quantity", Toast.LENGTH_SHORT).show();
        }else if (etWages.getText().toString().equalsIgnoreCase("")) {
            etWages.setError("Enter Wages Rate");
            etWages.requestFocus();
            Toast.makeText(context, "Enter Wages Rate", Toast.LENGTH_SHORT).show();
        } else if (etTransportValue.getText().toString().equalsIgnoreCase("")) {
            etTransportValue.setError("Enter Transport Quantity");
            etTransportValue.requestFocus();
            Toast.makeText(context, "Enter Transport Quantity", Toast.LENGTH_SHORT).show();
        }else if (etTransport.getText().toString().equalsIgnoreCase("")) {
            etTransport.setError("Enter Transport Rate");
            etTransport.requestFocus();
            Toast.makeText(context, "Enter Transport Rate", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    private void clearField() {
        etRate.setText("");
        etProduct.setText("");
        etQuantity.setText("");
    }

    private void ShowDialogBill() {
        final Dialog dialogInvoice = new Dialog(context);
        dialogInvoice.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInvoice.setCancelable(true);
        dialogInvoice.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_payment, null, false);
        dialogInvoice.setContentView(dialogview);
        dialogInvoice.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogInvoice.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogInvoice.getWindow().setAttributes(lp);
        dialogInvoice.show();


        final TextView txtInvoice, txtItems, txtGrossTotal, txtCutting, txtWages, txtTransport, txtTotalAmount;
        final AutoCompleteTextView etPaymentType;
        final EditText etAmount;

        txtInvoice = dialogview.findViewById(R.id.txtInvoice);
        txtGrossTotal = dialogview.findViewById(R.id.txtGrossTotal);
        txtCutting = dialogview.findViewById(R.id.txtCutting);
        txtWages = dialogview.findViewById(R.id.txtWages);
        txtTransport = dialogview.findViewById(R.id.txtTransport);
        txtTotalAmount = dialogview.findViewById(R.id.txtTotalAmount);
        etPaymentType = dialogview.findViewById(R.id.etPaymentType);
        etAmount = dialogview.findViewById(R.id.etAmount);

        txtGrossTotal.setText("" +GrossTotal);
        Log.i(TAG, "GrossTotal dialog: " + GrossTotal);
        txtCutting.setText("" + Cutting);
        txtWages.setText("" + Wages);
        txtTransport.setText("" + Transport);
        txtTotalAmount.setText("" + Math.round(totalPay()));
        etAmount.setText("" + Math.round(totalPay()));

        //Product List
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PaymentList);
        etPaymentType.setAdapter(adapter);

        etPaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPaymentType.showDropDown();
                etPaymentType.requestFocus();
            }
        });


        txtInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDialog()) {


                    dialogInvoice.dismiss();
               /* Intent intentMain = new Intent(CreateNewBill.this, Inovice.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentMain);*/
                    Intent intentOTPLogin = new Intent(CreateNewBill.this, Inovice.class);
                    intentOTPLogin.putExtra("billNo", BillNo);
                    intentOTPLogin.putExtra("billDate", BillDate);
                    intentOTPLogin.putExtra("custName", CustName);
                    intentOTPLogin.putExtra("custAddress", Address);
                    intentOTPLogin.putExtra("MobileNo", Mobile);
                    intentOTPLogin.putExtra("product", Product);
                    intentOTPLogin.putExtra("quantity", Quantity);
                    intentOTPLogin.putExtra("rate", Rate);
                    intentOTPLogin.putExtra("gross_total", GrossTotal);
                    intentOTPLogin.putExtra("cutting_value", CuttingValue);
                    intentOTPLogin.putExtra("wages_value", WagesValue);
                    intentOTPLogin.putExtra("transport_value", TransportValue);
                    intentOTPLogin.putExtra("cutting_rate", CuttingRate);
                    intentOTPLogin.putExtra("wages_rate", WagesRate);
                    intentOTPLogin.putExtra("transport_rate", TransportRate);
                    intentOTPLogin.putExtra("cutting", Cutting);
                    intentOTPLogin.putExtra("wages", Wages);
                    intentOTPLogin.putExtra("transport", Transport);
                    intentOTPLogin.putExtra("payBy", PayBy);
                    intentOTPLogin.putExtra("paidAmount", paidAmount);

                    intentOTPLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    startActivity(intentOTPLogin);
                    finish();
                }

            }

            private boolean validateDialog() {
                PayBy = etPaymentType.getText().toString();
                paidAmount = Float.parseFloat(etAmount.getText().toString());

                if (PayBy.equalsIgnoreCase("")) {
                    etPaymentType.setError("Please Select Payment Type");
                    etPaymentType.requestFocus();
                    Toast.makeText(context, "Select Payment Type", Toast.LENGTH_SHORT).show();
                } else if (paidAmount>totalPay()) {
                    etAmount.setError("Invalid Paid Amount");
                    etAmount.requestFocus();
                    Toast.makeText(context, "Invalid Paid Amount", Toast.LENGTH_SHORT).show();
                }else if (etAmount.getText().toString().equalsIgnoreCase("")) {
                    etAmount.setError("Enter Paid Amount");
                    etAmount.requestFocus();
                    Toast.makeText(context, "Enter Paid Amount", Toast.LENGTH_SHORT).show();
                } else {
                    return true;
                }
                return false;
            }
        });

    }

    private float totalPay() {

        float Total = ((GrossTotal) - (Cutting) - (Wages) - (Transport));


        return Total;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.etDate:
                final Calendar calendarDob = Calendar.getInstance();
                int dobYear = calendarDob.get(Calendar.YEAR);
                int dobMonth = calendarDob.get(Calendar.MONTH);
                int dobDay = calendarDob.get(Calendar.DAY_OF_MONTH);

                int minDobYear = dobYear;
                int minDobMonth = dobMonth;
                int minDobDay = dobDay;
                calendarDob.set(minDobYear, minDobMonth, minDobDay);
                long minDobDateInMilliSeconds = calendarDob.getTimeInMillis();


                final DatePickerDialog dpdDob = new DatePickerDialog(CreateNewBill.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        long selectedMilli = newDate.getTimeInMillis();

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate = new SimpleDateFormat("dd/MM/yyyy").format(datePickerDate);
                        etDate.setText(reportDate);
                    }
                }, dobYear, dobMonth, dobDay);
                dpdDob.getDatePicker().setMaxDate(minDobDateInMilliSeconds);
                dpdDob.show();
                break;


            case R.id.txtGenerate:
                hideSoftKeyboard();
                onClickGenerateBill(v);
                break;


        }
    }

    //hide keyboard once focus is moved from the screen
    public void hideSoftKeyboard() {
        if (CreateNewBill.this.getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) CreateNewBill.this.getSystemService(INPUT_METHOD_SERVICE);
            assert inm != null;
            inm.hideSoftInputFromWindow(CreateNewBill.this.getCurrentFocus().getWindowToken(), 0);
        }
    }

}


