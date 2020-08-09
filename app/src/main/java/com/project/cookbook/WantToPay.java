package com.project.cookbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;


public class WantToPay extends Activity implements View.OnClickListener {

    Button yes_button, no_button;
    TextView pay_dialog, bal_dialog;
    Bundle balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.want2pay);
        bridgeValues();

        balance = new Bundle();
        balance = getIntent().getExtras();
        String bal = balance.getString("AMOUNT");
        bal_dialog.setText(bal);
    }

    private void bridgeValues() {
        yes_button = (Button) findViewById(R.id.bYes);
        no_button = (Button) findViewById(R.id.bNo);
        pay_dialog = (TextView) findViewById(R.id.tvDialog);
        bal_dialog = (TextView) findViewById(R.id.tvBalance);
        yes_button.setOnClickListener(this);
        no_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bYes: //GooglePay.java API
                Intent i = new Intent(WantToPay.this, SQLView.class);
                Bundle response = new Bundle();
                response.putInt("YES", 1);
                i.putExtras(response);
                startActivity(i);
                break;
            case R.id.bNo: finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
