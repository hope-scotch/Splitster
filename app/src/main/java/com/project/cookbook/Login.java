package com.project.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends Activity implements View.OnClickListener {

    EditText username, password;
    Button login, register;
    ImageView passTog;
    private static final String PREFER_NAME = "Reg";
    private SharedPreferences sharedPreferences;
    UserSession session;
    TextView err;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Full-Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        bridgeValues();
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
    }

    private void bridgeValues() {
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        passTog = (ImageView) findViewById(R.id.bShow);
        passTog.setActivated(true);
        login = (Button) findViewById(R.id.bLogin);
        register = (Button) findViewById(R.id.bReg);
        err = (TextView) findViewById(R.id.logErr2);
        login.setOnClickListener(this);
        passTog.setOnClickListener(this);
        register.setOnClickListener(this);
        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                err.setText(null);
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                err.setText(null);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bShow:
                if (passTog.isActivated()){
                    passTog.setActivated(false);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            else {
                passTog.setActivated(true);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
                break;
            case R.id.bLogin:

                // User Session Manager
                session = new UserSession(getApplicationContext());

                String name = username.getText().toString();
                String pass = password.getText().toString();

                if(name.trim().length() > 0 && pass.trim().length() > 0) {
                    String uName = null;
                    String uPass = null;

                    if(sharedPreferences.contains("Name")) uName = sharedPreferences.getString("Name", "");
                    if(sharedPreferences.contains("Password")) uPass = sharedPreferences.getString("Password", "");

                    if(name.equals(uName) && pass.equals(uPass)) {
                        session.createUserLoginSession(uName, uPass);

                        Intent mainAct = new Intent(Login.this, Splitster.class);
                        mainAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mainAct.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
                        startActivity(mainAct);
                        finish();
                    }
                    else err.setText("Username/Password is incorrect!");
                }
                else err.setText("Please enter username and password");

                break;
            case R.id.bReg:
                Intent reg = new Intent(Login.this, Register.class);
                reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(reg);
                break;
        }
    }

}
