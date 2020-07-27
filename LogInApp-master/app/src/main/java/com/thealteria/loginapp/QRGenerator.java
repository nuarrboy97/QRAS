package com.thealteria.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRGenerator extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_generator);
    }

    private Bitmap printQRCode(String textToQR){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textToQR, BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onClick(View view) {
        TextView text = (TextView) findViewById(R.id.editText);
        if (text.getText().toString().isEmpty()){
            Toast.makeText(this, "Kindly enter any subject first", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap QRBit = printQRCode(text.getText().toString());
        if (QRBit == null){
            Toast.makeText(this, "Unable to generate code!", Toast.LENGTH_SHORT).show();
        }else {
            Intent qRIntent = new Intent(this, ShowPrintQR.class);
            qRIntent.putExtra("bitmap", QRBit);
            startActivity(qRIntent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item5:
                Intent intent = new Intent(QRGenerator.this,About.class);
                startActivity(intent);
                return true;

            case R.id.homeButton:
                Context homeButtonContext = getApplicationContext();
                Intent home = new Intent(this,Login.class);
                startActivity(home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}