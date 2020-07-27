package com.thealteria.loginapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QrcodeActivity extends AppCompatActivity {

    static SQLiteDatabase myDatabase;
    Date currentTime;
    SimpleDateFormat df;
    ListView qrDetailList;
    List<QrBeanModel> QrObject = new ArrayList<>();
    QrDetailAdapter qrDetailAdapter;
    Cursor c;
    SearchView inputSearch;
    List<QrBeanModel> tempList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        intializeViews();
        qrDetailAdapter = new QrDetailAdapter( this ,R.layout.qr_list_items,QrObject);
        qrDetailList.setAdapter(qrDetailAdapter);
        qrDetailAdapter.notifyDataSetChanged();
        performSql();

        //Main code for search view
        tempList.addAll(QrObject);
        inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                QrObject.clear();
                for(QrBeanModel qr: tempList){
                    if(qr.getQrText().contains(s)){
                        //contains
                        QrObject.add(qr);
                    } else if(s.length() == 0) {
                        Toast.makeText(getApplicationContext(),"list is clear",Toast.LENGTH_LONG).show();
                        tempList.addAll(QrObject);
                    }
                }
                qrDetailAdapter.notifyDataSetChanged();
                return true;
            }
        });



        //To perform delete on swipe in the list
        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(qrDetailList),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                qrDetailAdapter.remove(position);
                            }
                        });

        qrDetailList.setOnTouchListener(touchListener);
        qrDetailList.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        qrDetailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    Toast.makeText(QrcodeActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void intializeViews(){
        qrDetailList = (ListView) findViewById(R.id.qrDetailList);
        qrDetailList.setTextFilterEnabled(true);
        inputSearch = (SearchView) findViewById(R.id.inputSearch);
    }

    public void scan(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                String scannedResult = result.getContents();
                currentTime = Calendar.getInstance().getTime();
                df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(currentTime);
                String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                System.out.println(formattedDate);
                String sql = "INSERT INTO lastfourth (name, date , spec) VALUES (? , ?, ?)";
                SQLiteStatement statement = myDatabase.compileStatement(sql);
                QrBeanModel qrBeanModel = new QrBeanModel(scannedResult,formattedDate,d);
                QrObject.add(qrBeanModel);
                qrDetailAdapter.notifyDataSetChanged();
                statement.bindString(1,scannedResult);
                statement.bindString(2,formattedDate);
                statement.bindString(3,d);
                statement.execute();
                Toast.makeText(getApplicationContext(),"ADDED to database",Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.scanQrCode:
                scan();
                break;
            case R.id.sortByDate:
                orderByDate();
                break;
            case R.id.sortByName:
                orderByName();
                break;
            case R.id.item5:
                Intent intent = new Intent(QrcodeActivity.this,About.class);
                startActivity(intent);
                return true;

            case R.id.homeButton:
                Context homeButtonContext = getApplicationContext();
                Intent home = new Intent(this,Login.class);
                startActivity(home);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void orderByDate(){
        Toast.makeText(getApplicationContext(),"order by date working",Toast.LENGTH_LONG).show();
        c = myDatabase.rawQuery("SELECT * FROM lastfourth ORDER BY date Desc",null);
        int nameIndex = c.getColumnIndex("name");
        int dateIndex = c.getColumnIndex("date");
        int dateIDIndex = c.getColumnIndex("spec");


        if(c.moveToFirst()){
            do{
                Log. i ( "user-name" ,c.getString(nameIndex));
                Log.i("date id ",c.getString(dateIDIndex));
                QrBeanModel qrBeanModel = new QrBeanModel(c.getString(nameIndex),c.getString(dateIndex),c.getString(dateIDIndex));
                QrObject.add(qrBeanModel);
                qrDetailAdapter.notifyDataSetChanged();
                Log. i ( "user-age" ,c.getString(dateIndex));
            }while (c.moveToNext());
        }
    }

    public void orderByName(){
        Toast.makeText(getApplicationContext(),"order by date working",Toast.LENGTH_LONG).show();
        c = myDatabase.rawQuery("SELECT * FROM lastfourth ORDER BY name",null);
        int nameIndex = c.getColumnIndex("name");
        int dateIndex = c.getColumnIndex("date");
        int dateIDIndex = c.getColumnIndex("spec");


        if(c.moveToFirst()){
            do{
                Log. i ( "user-name" ,c.getString(nameIndex));
                Log.i("date id ",c.getString(dateIDIndex));
                QrBeanModel qrBeanModel = new QrBeanModel(c.getString(nameIndex),c.getString(dateIndex),c.getString(dateIDIndex));
                QrObject.add(qrBeanModel);
                qrDetailAdapter.notifyDataSetChanged();
                Log. i ( "user-age" ,c.getString(dateIndex));
            }while (c.moveToNext());
        }
    }

    public static void sendUniqueKey(String s){
        String sql = "DELETE FROM lastfourth WHERE spec = ? ";
        SQLiteStatement statement = myDatabase.compileStatement(sql);
        statement.bindString(1,s);
        statement.execute();
        Log.i("tag",s+"Deleted");
        // MainActivity m = new MainActivity();
        // m.performSql();
    }

    public void performSql(){
        myDatabase = this .openOrCreateDatabase( "Users.db" , MODE_PRIVATE , null );

        //Creating the table if not exists
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS lastfourth (name VARCHAR , date VARCHAR , spec VARCHAR)");
        showDatabaseInList();
    }

    public void showDatabaseInList(){

        try {
            c = myDatabase.rawQuery("SELECT * FROM lastfourth",null);

            int nameIndex = c.getColumnIndex("name");
            int dateIndex = c.getColumnIndex("date");
            int dateIDIndex = c.getColumnIndex("spec");

            if(c.moveToFirst()){
                do{
                    Log. i ( "user-name" ,c.getString(nameIndex));
                    Log.i("date id ",c.getString(dateIDIndex));
                    QrBeanModel qrBeanModel = new QrBeanModel(c.getString(nameIndex),c.getString(dateIndex),c.getString(dateIDIndex));
                    QrObject.add(qrBeanModel);
                    qrDetailAdapter.notifyDataSetChanged();
                    Log. i ( "user-age" ,c.getString(dateIndex));
                }while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
