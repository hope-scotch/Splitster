package com.project.cookbook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;

public class SQLView  extends Activity implements View.OnClickListener {

    TableLayout table;
    private int rev_count, num = 0;
    private double sum = 0.0, avg = 0.0;
    private int user;
    private boolean start_flag = false;
    private double rtd_amt = 0.0;
    View gpay_layout;
    ImageButton GPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle basket = getIntent().getExtras();
        int resp = basket.getInt("YES");

        setContentView(R.layout.sql_view);

        // GPay Button
        /*gpay_layout = (ConstraintLayout) findViewById(R.id.gpay_dialog);

        if (resp == 1) {
            gpay_layout.setVisibility(View.VISIBLE);
        }
        else
            gpay_layout.setVisibility(View.INVISIBLE);*/

        GPay = (ImageButton) findViewById(R.id.ibGPay);
        GPay.setOnClickListener(this);

        user = 0;

        int count = basket.getInt("COUNTER");
        sum = basket.getDouble("SUM");

        avg = sum / count;

        table = (TableLayout) findViewById(R.id.table);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvAmt = (TextView) findViewById(R.id.tvAmt);

        //FONT NOT CHANGING
        /*font = Typeface.createFromAsset(SQLView.this.getAssets(), "assets/georgia-bol.ttf");

        tvName.setTypeface(font);
        tvAmt.setTypeface(font);*/

        rev_count = 1;



        while(rev_count <= count) {


            String returned_name = "Invalid", returned_data = "Invalid";
            double returned_amt = 0.0;
            try {
                GiveOrGet info = new GiveOrGet(this);
                info.open();
                returned_name = info.getName(rev_count);
                returned_amt = info.getAmount(rev_count);
                returned_data = info.getData();
                /*if( !returned_name.contentEquals("Invalid") && !returned_name.isEmpty()  && returned_amt != 0.0
                        && !String.valueOf(returned_amt).isEmpty()) {
                    num ++; // number of valid entries
                    sum += returned_amt;
                }*/

                info.close();
            } catch (Exception e) {
                String error = e.toString();
                Dialog d = new Dialog(SQLView.this);
                d.setTitle("Oops!");
                TextView tv = new TextView(SQLView.this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            }


            if(returned_name.contentEquals("Invalid")) {
                rev_count ++;
                continue;
            }

            TableRow new_row = new TableRow(this);
            Button name = new Button(this);
            TextView paid = new TextView(this);
            TextView flag = new TextView(this);
            //TextView sum = new TextView(this);
            //name.setText(returned_name);
            //paid.setText("" + returned_amt);

            if( returned_amt > avg) {
                name.setText(returned_name + "");
                flag.setText(" GETS ");
                paid.setText("" + ( returned_amt - avg));
            }
            else if (returned_amt < avg) {
                name.setText(returned_name + "");
                flag.setText(" GIVES ");
                paid.setText("" + ( avg - returned_amt));
            }
            else {
                name.setText(returned_name + "");
                flag.setText(" HAS CLEARED!");
            }

            //status.setText("" + avg );
            /*if(user == 1 && flag.getText().toString().contentEquals(" GIVES ")) {
                            start_flag = true;
                            rtd_amt = returned_amt;
            }*/

            TableLayout.LayoutParams rowDetails = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            rowDetails.weight = 1.0f;

            TableRow.LayoutParams nameDetails = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            nameDetails.weight = 0.2f;
            nameDetails.setMargins(10, 20, 10, 5);
            name.setTextSize(18f);

            TableRow.LayoutParams flagDetails = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            flagDetails.weight = 0.3f;
            flagDetails.setMargins(10, 20, 10, 5);
            flag.setTextSize(18f);

            TableRow.LayoutParams paidDetails = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            paidDetails.weight = 0.1f;
            paidDetails.setMargins(10, 20, 10, 5);
            paid.setTextSize(18f);


            new_row.addView(name, nameDetails);
            new_row.addView(flag, flagDetails);
            new_row.addView(paid, paidDetails);
            table.addView(new_row, rowDetails);

            rev_count ++;
        }

        // Want to pay?
        /*if (start_flag && (resp != 1) ) {
            Intent i = new Intent(SQLView.this, WantToPay.class);
            basket.putString("AMOUNT", String.valueOf(avg - rtd_amt));
            i.putExtras(basket);
            startActivity(i);
            }*/


    }

    @Override
    public void onClick(View v) {

    }
}


