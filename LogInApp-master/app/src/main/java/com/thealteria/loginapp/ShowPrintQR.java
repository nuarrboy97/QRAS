package com.thealteria.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowPrintQR extends AppCompatActivity {

    private Bitmap qRBit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_print_qr);
        qRBit = getIntent().getParcelableExtra("bitmap");
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageBitmap(qRBit);
    }

    public void print(View view) {
        doPhotoPrint();
    }

    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);

        //print
        photoPrinter.printBitmap("image.png_test_print", qRBit, new PrintHelper.OnPrintFinishCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(ShowPrintQR.this, "Thank you!", Toast.LENGTH_SHORT).show();
            }
        });
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
                Intent intent = new Intent(ShowPrintQR.this,About.class);
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
