package com.example.letsfit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    DatabaseHelper db;
    TextView tx1, tx2;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        b1 = (Button)findViewById(R.id.lgin);
        e1 = (EditText)findViewById(R.id.usnm);
        e2 = (EditText)findViewById(R.id.pswd);
        tx1 = (TextView)findViewById(R.id.textView);
        tx2 = (TextView) findViewById(R.id.textView4);
        tx1.setVisibility(View.GONE);
        final Context context = this;

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               String username = e1.getText().toString();
               String password = e2.getText().toString();
               Boolean chkusernamepassword = db.usernamepassword(username, password);
               if(chkusernamepassword == true){
                   Toast.makeText(getApplicationContext(),"Successfully Login", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(LoginActivity.this, ScanActivity.class);
                   startActivity(intent);
               }
               else
                   Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });

        tx2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
