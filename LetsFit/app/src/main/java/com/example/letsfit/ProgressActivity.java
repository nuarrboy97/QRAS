package com.example.letsfit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProgressActivity extends AppCompatActivity {

    TextView points;
    Button logout, claim1, claim2, claim3, claim4, claim5;
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    final Context context = this;
    int point = 0;
    String g,e;
    SharedPreferences mytarikh;

    public static final String mypreference = "mypref";
    public static final String DateKey = "keydate";

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.qr:
                Intent intent = new Intent(ProgressActivity.this, ScanActivity.class);
                startActivity(intent);
                return true;
            case R.id.progress:
                Intent intent1 = new Intent(ProgressActivity.this, ScanActivity.class);
                startActivity(intent1);
                return true;
            case R.id.logout:
                Intent intent2 = new Intent(ProgressActivity.this, LoginActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        TextView mydate = findViewById(R.id.datetv);
        chronometer = findViewById(R.id.textView15);
        points = findViewById(R.id.textView12);
        claim1 = findViewById(R.id.claim1);
        claim2 = findViewById(R.id.claim2);
        claim3 = findViewById(R.id.claim3);
        claim4 = findViewById(R.id.claim4);
        claim5 = findViewById(R.id.claim5);

        claim1.setEnabled(false);
        claim2.setEnabled(false);
        claim3.setEnabled(false);
        claim4.setEnabled(false);
        claim5.setEnabled(false);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
        alertDialogBuilder
                .setTitle("Welcome to LetsFit!")
                .setMessage("\n50 Points will be awarded for every 10 seconds you spend in the gym!\n\nYour accumulated points can be used to claim items displayed below.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        mytarikh = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if(mytarikh.contains(DateKey)){
            mydate.setText(mytarikh.getString(DateKey, "---"));
        }

        startChronometer();

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((SystemClock.elapsedRealtime() - chronometer.getBase() > 10000) && (SystemClock.elapsedRealtime() - chronometer.getBase() < 11000)) {
                    point += 50;
                    g = Integer.toString(point);
                    points.setText(g);

                    claim1.setEnabled(true);
                    claim2.setEnabled(false);
                    claim3.setEnabled(false);
                    claim4.setEnabled(false);
                    claim5.setEnabled(false);

                    claim1.setOnClickListener(new View.OnClickListener() {
                        int counter = 0;

                        @Override
                        public void onClick(View v) {
                            counter++;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    if(counter == 1){
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have successfully claimed 2 MINERAL WATER!\n\nShow this message to the customer service to claim your item.")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                    else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have already claimed 2 MINERAL WATER!")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                }
                            }, 300);
                        }
                    });
                }

                else if((SystemClock.elapsedRealtime() - chronometer.getBase() > 20000) && (SystemClock.elapsedRealtime() - chronometer.getBase() < 21000)) {
                    point += 50;
                    g = Integer.toString(point);
                    points.setText(g);

                    claim1.setEnabled(false);
                    claim2.setEnabled(true);
                    claim3.setEnabled(false);
                    claim4.setEnabled(false);
                    claim5.setEnabled(false);

                    claim2.setOnClickListener(new View.OnClickListener() {
                        int counter = 0;

                        @Override
                        public void onClick(View v) {
                            counter++;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    if(counter == 1) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have successfully claimed 3 PROTEIN BAR!\n\nShow this message to the customer service to claim your item.")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }

                                    else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have already claimed 3 PROTEIN BAR!")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                }
                            }, 300);
                        }
                    });

                }

                else if((SystemClock.elapsedRealtime() - chronometer.getBase() > 30000) && (SystemClock.elapsedRealtime() - chronometer.getBase() < 31000)) {
                    point += 50;
                    g = Integer.toString(point);
                    points.setText(g);

                    claim1.setEnabled(false);
                    claim2.setEnabled(false);
                    claim3.setEnabled(true);
                    claim4.setEnabled(false);
                    claim5.setEnabled(false);

                    claim3.setOnClickListener(new View.OnClickListener() {
                        int counter = 0;

                        @Override
                        public void onClick(View v) {
                            counter++;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    if(counter == 1) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have successfully claimed 1 FREE ENTRY!\n\nShow this message to the customer service to claim your item.")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }

                                    else{
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have already claimed 1 FREE ENTRY!")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                }
                            }, 300);
                        }
                    });
                }

                else if((SystemClock.elapsedRealtime() - chronometer.getBase() > 40000) && (SystemClock.elapsedRealtime() - chronometer.getBase() < 41000)) {
                    point += 50;
                    g = Integer.toString(point);
                    points.setText(g);

                    claim1.setEnabled(false);
                    claim2.setEnabled(false);
                    claim3.setEnabled(false);
                    claim4.setEnabled(true);
                    claim5.setEnabled(false);

                    claim4.setOnClickListener(new View.OnClickListener() {
                        int counter = 0;

                        @Override
                        public void onClick(View v) {
                            counter++;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    if(counter == 1) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have successfully claimed 1 MINERAL WATER + 1 PROTEIN BAR!\n\nShow this message to the customer service to claim your item.")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }

                                    else{
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have already claimed 1 MINERAL WATER + 1 PROTEIN BAR!")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                }
                            }, 300);
                        }
                    });
                }

                else if((SystemClock.elapsedRealtime() - chronometer.getBase() > 50000) && (SystemClock.elapsedRealtime() - chronometer.getBase() < 51000)) {
                    point += 50;
                    g = Integer.toString(point);
                    points.setText(g);

                    claim1.setEnabled(false);
                    claim2.setEnabled(false);
                    claim3.setEnabled(false);
                    claim4.setEnabled(false);
                    claim5.setEnabled(true);

                    claim5.setOnClickListener(new View.OnClickListener() {
                        int counter = 0;

                        @Override
                        public void onClick(View v) {
                            counter++;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    if(counter == 1) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have successfully borrowed 1 PAIR OF GLOVE for the day!\n\nShow this message to the customer service to claim your item.")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }

                                    else{
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this);
                                        alertDialogBuilder
                                                .setMessage("You have already borrowed 1 PAIR OF GLOVE!")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                }
                            }, 300);
                        }
                    });
                }

                else if((SystemClock.elapsedRealtime() - chronometer.getBase() > 60000) && (SystemClock.elapsedRealtime() - chronometer.getBase() < 61000)) {
                    point += 50;
                    g = Integer.toString(point);
                    points.setText(g);

                    claim1.setEnabled(false);
                    claim2.setEnabled(false);
                    claim3.setEnabled(false);
                    claim4.setEnabled(false);
                    claim5.setEnabled(false);
                }

                else{
                    g = Integer.toString(point);
                    points.setText(g);
                }
            }
        });
    }

    public void startChronometer(){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void endChronometer (View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy\nHH:mm:ss");
        String tarikh = df.format(Calendar.getInstance().getTime());
        SharedPreferences.Editor editor = mytarikh.edit();
        editor.remove(DateKey);
        editor.putString(DateKey, tarikh);
        editor.commit();
    }
}
