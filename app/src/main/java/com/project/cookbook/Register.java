package com.project.cookbook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {

    Button next;
    EditText user, pass, confirmPass, email;
    TextView userErr, passErr, confirmPassErr, emailErr;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.register);

        bridgeValues();

        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user.getText().toString();
                String password = pass.getText().toString();
                String emailAdd = email.getText().toString();

                if(name.length() <= 0) {
                    userErr.setText("Please Enter Username");
                    user.requestFocus();
                }
                else if(password.length() <= 0) {
                    passErr.setText("Please Enter Password");
                    pass.requestFocus();
                } else if (!confirmPass.getText().toString().equals(pass.getText().toString())) {
                    confirmPassErr.setText("Passwords Do Not Match");
                    confirmPass.requestFocus();
                }
                else {
                    editor.putString("Name", name);
                    editor.putString("Password", password);
                    editor.putString("Email", emailAdd);
                    editor.commit();

                    Intent log = new Intent(Register.this, Login.class);
                    log.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(log);
                    finish();
                }


            }
        });
    }

    private void bridgeValues() {
        next = (Button) findViewById(R.id.bNext);
        user = (EditText) findViewById(R.id.regUser);
        pass = (EditText) findViewById(R.id.regPass);
        confirmPass = (EditText) findViewById(R.id.regConfirmPass);
        email = (EditText) findViewById(R.id.regEmail);
        userErr = (TextView) findViewById(R.id.regErr1);
        passErr = (TextView) findViewById(R.id.regErr2);
        confirmPassErr = (TextView) findViewById(R.id.regErr3);
        emailErr = (TextView) findViewById(R.id.regErr4);
        pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                passErr.setText(null);
                return false;
            }
        });
        user.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                userErr.setText(null);
                return false;
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(count > 0 && !b) {
                    if(!confirmPass.getText().toString().equals(pass.getText().toString()))
                        confirmPassErr.setText("Passwords Do Not Match!");
                    else confirmPassErr.setText(null);
                }
                count ++;
            }
        });
        confirmPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    if(!confirmPass.getText().toString().equals(pass.getText().toString()))
                        confirmPassErr.setText("Passwords Do Not Match!");
                    else confirmPassErr.setText(null);
                }
            }
        });
    }
}
