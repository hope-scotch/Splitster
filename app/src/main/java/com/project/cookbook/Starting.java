package com.project.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Starting extends Activity {

    private UserSession session;
    private SharedPreferences pref;
    private static final String PREFER_NAME = "Reg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.start);
        pref = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);

        session = new UserSession(getApplicationContext());
        Thread thread = new Thread(){
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    String uName = null, uPass = null;
                    if(pref.contains("Name")) uName = pref.getString("Name", "");
                    if(pref.contains("Password")) uPass = pref.getString("Password", "");

                    if(session.isUserLoggedIn()) {
                        session.createUserLoginSession(uName, uPass);
                        Intent i = new Intent(Starting.this, Splitster.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(Starting.this, Login.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        };
        thread.start();

    }
}
