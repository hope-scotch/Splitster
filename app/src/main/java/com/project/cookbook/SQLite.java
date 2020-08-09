package com.project.cookbook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SQLite extends Activity implements View.OnClickListener {

    Button update, view, modify, getInfo, delete, delete_all;
    EditText name, amt, row ;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_example);
        update = (Button) findViewById(R.id.bUpdate);
        view = (Button) findViewById(R.id.bView);
        modify = (Button) findViewById(R.id.bModify);
        getInfo = (Button) findViewById(R.id.bGetInfo);
        delete = (Button) findViewById(R.id.bDelete);
        delete_all = (Button) findViewById(R.id.bDeleteAll);

        update.setOnClickListener(this);
        view.setOnClickListener(this);
        modify.setOnClickListener(this);
        getInfo.setOnClickListener(this);
        delete.setOnClickListener(this);
        delete_all.setOnClickListener(this);

        name = (EditText) findViewById(R.id.etName);
        amt = (EditText) findViewById(R.id.etAmt);
        row = (EditText) findViewById(R.id.etRow);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bUpdate:
                count ++;

                boolean didItWork = true;

                try {
                    String db_name = name.getText().toString();
                    String db_amt = amt.getText().toString();

                    GiveOrGet entry = new GiveOrGet(this);
                    entry.open();
                    entry.createEntry(db_name, db_amt, count);
                    entry.close();
                } catch (Exception e) {
                    didItWork = false;
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("Man!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                } finally {
                    if (didItWork) {
                        Dialog d = new Dialog(this);
                        d.setTitle("Yo!");
                        TextView tv = new TextView(this);
                        tv.setText("Success");
                        d.setContentView(tv);
                        d.show();
                    }
                }
                break;
            case R.id.bView:
                Intent i = new Intent("com.project.cookbook.SQLVIEW");
                startActivity(i);
                break;
            case R.id.bGetInfo:
                try {
                    String s = row.getText().toString();
                    int l = Integer.parseInt(s);
                    GiveOrGet gog = new GiveOrGet(this);
                    gog.open();
                    String returnedName = gog.getData(l);
                    //String returnedAmount = gog.getAmount(l);
                    gog.close();

                    name.setText(returnedName);
                    //amt.setText(returnedAmount);
                } catch (Exception e) {
                String error = e.toString();
                Dialog d = new Dialog(this);
                d.setTitle("Man!");
                TextView tv = new TextView(this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            }

                break;
            case R.id.bModify:
                try {
                    String sRow = row.getText().toString();
                    long lRow = Long.parseLong(sRow);
                    String db_modify_name = name.getText().toString();
                    String db_modify_amt = amt.getText().toString();

                    GiveOrGet gog = new GiveOrGet(this);
                    gog.open();
                   // gog.updateEntry(lRow, db_modify_name, db_modify_amt);
                    gog.close();
                } catch (Exception e) {
                String error = e.toString();
                Dialog d = new Dialog(this);
                d.setTitle("Man!");
                TextView tv = new TextView(this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            }


                break;
            case R.id.bDelete:
                try {
                    String sRow1 = row.getText().toString();
                    long lRow1 = Long.parseLong(sRow1);

                    GiveOrGet gog = new GiveOrGet(this);
                    gog.open();
                    gog.deleteEntry(lRow1);
                    gog.close();
                } catch (Exception e) {
                String error = e.toString();
                Dialog d = new Dialog(this);
                d.setTitle("Man!");
                TextView tv = new TextView(this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            }
                break;
            case R.id.bDeleteAll:
                try {
                    GiveOrGet gog = new GiveOrGet(this);
                    gog.open();
                    gog.deleteAllEntry();
                    gog.close();
                } catch (Exception e) {
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("Oops!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }
                break;
        }
    }
}
