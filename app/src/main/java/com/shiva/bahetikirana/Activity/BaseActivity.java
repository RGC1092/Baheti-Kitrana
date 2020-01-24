package com.shiva.bahetikirana.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shiva.bahetikirana.Database.DatabaseHelper;
import com.shiva.bahetikirana.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class BaseActivity extends AppCompatActivity {

    private TextView txtCreate, txtMyBills, txtExportBills;
    private Context context = this;
    private DatabaseHelper dbHelper;
    private Date currentDateTime;
    // Database Name
    private static final String DATABASE_NAME = "ProductManager";
    // Table Names
    private static final String TABLE_BILL = "bill";

    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        dbHelper = new DatabaseHelper(getApplicationContext());
        currentDateTime = new Date();
        android.text.format.DateFormat.format("yyyy_MM_dd_hh:mm:ss", currentDateTime);

        checkPermissions();
        initUI();
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }

    private void initUI() {
        txtCreate = findViewById(R.id.txtCreate);
        txtMyBills = findViewById(R.id.txtMyBills);
        txtExportBills = findViewById(R.id.txtExportBills);

        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCreate = new Intent(context, CreateNewBill.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                intentCreate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentCreate);
            }
        });

        txtMyBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCreate = new Intent(context, BillsActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                intentCreate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentCreate);
            }
        });
        txtExportBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup_MSB/";
                File file = new File(directory_path);
                if (!file.exists()) {
                    file.mkdirs();
                }*/

                final Cursor cursor = dbHelper.getExportBill();

                File sd = Environment.getExternalStorageDirectory();
                String csvFile = "BahetiMarchands.xls";

                File directory = new File(sd.getAbsolutePath());


                //create directory if not exist
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                    Log.i("Rahul","Directory Created: "+directory);
                }
                try {

                    //file path
                    File file = new File(directory, csvFile);
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    WritableWorkbook workbook;
                    workbook = Workbook.createWorkbook(file, wbSettings);
                    //Excel sheet name. 0 represents first sheet
                    WritableSheet sheet = workbook.createSheet("BillingDetails", 0);
                    // column and row

                    sheet.addCell(new Label(0, 0, "bill_no"));
                    sheet.addCell(new Label(1, 0, "bill_date"));
                    sheet.addCell(new Label(2, 0, "name"));
                    sheet.addCell(new Label(3, 0, "address"));
                    sheet.addCell(new Label(4, 0, "mobile"));
                    sheet.addCell(new Label(5, 0, "product"));
                    sheet.addCell(new Label(6, 0, "quantity"));
                    sheet.addCell(new Label(7, 0, "rate"));
                    sheet.addCell(new Label(8, 0, "subtotal"));
                    sheet.addCell(new Label(9, 0, "cutting_value"));
                    sheet.addCell(new Label(10, 0, "cutting_rate"));
                    sheet.addCell(new Label(11, 0, "wages_value"));
                    sheet.addCell(new Label(12, 0, "wages_rate"));
                    sheet.addCell(new Label(13, 0, "transport_value"));
                    sheet.addCell(new Label(14, 0, "transport_rate"));
                    sheet.addCell(new Label(15, 0, "total_payable"));
                    sheet.addCell(new Label(16, 0, "pay_by"));
                    sheet.addCell(new Label(17, 0, " paid"));
                    sheet.addCell(new Label(18, 0, "  balance"));
                    sheet.addCell(new Label(19, 0, "created_at"));
                    sheet.addCell(new Label(20, 0, "updated_at"));

                    if (cursor.moveToFirst()) {
                        do {



                            String bill_no = cursor.getString(cursor.getColumnIndex("bill_no"));
                            String bill_date = cursor.getString(cursor.getColumnIndex("bill_date"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String address = cursor.getString(cursor.getColumnIndex("address"));
                            String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                            String product = cursor.getString(cursor.getColumnIndex("product"));
                            String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
                            String rate = cursor.getString(cursor.getColumnIndex("rate"));
                            String subtotal = cursor.getString(cursor.getColumnIndex("subtotal"));
                            String cutting_value = cursor.getString(cursor.getColumnIndex("cutting_value"));
                            String cutting_rate = cursor.getString(cursor.getColumnIndex("cutting_rate"));
                            String wages_value = cursor.getString(cursor.getColumnIndex("wages_value"));
                            String wages_rate = cursor.getString(cursor.getColumnIndex("wages_rate"));
                            String transport_value = cursor.getString(cursor.getColumnIndex("transport_value"));
                            String transport_rate = cursor.getString(cursor.getColumnIndex("transport_rate"));
                            String total_payable = cursor.getString(cursor.getColumnIndex("total_payable"));
                            String pay_by = cursor.getString(cursor.getColumnIndex("pay_by"));
                            String paid = cursor.getString(cursor.getColumnIndex("paid"));
                            String balance = cursor.getString(cursor.getColumnIndex("balance"));
                            String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                            String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));




                            int i = cursor.getPosition() + 1;
                            sheet.addCell(new Label(0, i, bill_no));
                            sheet.addCell(new Label(1, i, bill_date));
                            sheet.addCell(new Label(2, i, name));
                            sheet.addCell(new Label(3, i, address));
                            sheet.addCell(new Label(4, i, mobile));
                            sheet.addCell(new Label(5, i, product));
                            sheet.addCell(new Label(6, i, quantity));
                            sheet.addCell(new Label(7, i, rate));
                            sheet.addCell(new Label(8, i, subtotal));
                            sheet.addCell(new Label(9, i, cutting_value));
                            sheet.addCell(new Label(10, i, cutting_rate));
                            sheet.addCell(new Label(11, i, wages_value));
                            sheet.addCell(new Label(12, i, wages_rate));
                            sheet.addCell(new Label(13, i, transport_value));
                            sheet.addCell(new Label(14, i, transport_rate));
                            sheet.addCell(new Label(15, i, total_payable));
                            sheet.addCell(new Label(16, i, pay_by));
                            sheet.addCell(new Label(17, i, paid));
                            sheet.addCell(new Label(18, i, balance));
                            sheet.addCell(new Label(19, i, created_at));
                            sheet.addCell(new Label(20, i, updated_at));
                        } while (cursor.moveToNext());
                    }

                    //closing cursor
                    cursor.close();
                    workbook.write();
                    workbook.close();
                    Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Rahul","Exception: "+e);
                }


            }
        });

    }


}
