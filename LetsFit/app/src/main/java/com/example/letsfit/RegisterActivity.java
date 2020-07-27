package com.example.letsfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
DatabaseHelper db;
EditText e1, e2, e3, e4, e5;
Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.textView8);
        e2 = (EditText) findViewById(R.id.textView9);
        e3 = (EditText) findViewById(R.id.textView6);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();

                if(s1.equals("")||s2.equals("")||s3.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }

                else
                    {
                        if(s2.equals(s3))
                        {
                            Boolean chkusername = db.chkusername(s1);
                            if(chkusername == true)
                            {
                                Boolean insert = db.insert(s1,s2);
                                if(insert == true)
                                {
                                    Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                                {
                                Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
                                }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
    }
}
