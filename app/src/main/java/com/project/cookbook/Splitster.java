package com.project.cookbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;

public class Splitster extends Activity implements View.OnClickListener {

    Bundle basket;
    private double sum = 0.0, avg = 0.0;
    TableLayout table;
    EditText name, paid;
    public int counter;
    public UserSession session;
    Button add_row_button, view_button, refresh_button;
    ImageButton del;
    LinearLayout layout, baseLayout, horizon, title, bottom, top;
    ScrollView scrollView;
    ImageView splitster, instruct;
    ImageButton logout, exit;
    LinearLayout.LayoutParams layoutDetails, topDetails, topButton, bottomDetails, splitsterDetails, genericLayoutDetails, titleLayoutDetails, buttonDetails, tableDetails, hzbtm;
    ScrollView.LayoutParams scrollViewDetails;
    public static Activity fa;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cool_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                session.logOutUser();
                finish();
                break;
            case R.id.exit: finish();
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa = this;

        // Full-Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Start New User Session
        session = new UserSession(getApplicationContext());


        // Refresh Database
        //new Refresh().execute("run");
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

        // Refresh Counter
        counter = 0;

        // Create layouts, views etc.
            layouts();
            backgrounds();
            layoutParams();
            buttons();
        // Create New Table
            table = new TableLayout(this);

        // Create New Rows and add them to the Table
            tables();

            genericLayoutDetails.weight = 18f;
            hzbtm.weight = 33f;
            topDetails.weight = 37f;
            bottomDetails.weight = 36f;
            titleLayoutDetails.weight = 35f;
            integrate();
    }

    private void integrate() {
        // Add layouts and views to the Base Layout
        layout.addView(table, tableDetails);
        scrollView.addView(layout, layoutDetails);
        horizon.addView(add_row_button, buttonDetails);
        horizon.addView(view_button, buttonDetails);
        horizon.addView(refresh_button, buttonDetails);
        baseLayout.addView(top, topDetails);
        baseLayout.addView(horizon, hzbtm);
        baseLayout.addView(scrollView, genericLayoutDetails);
        baseLayout.addView(title, titleLayoutDetails);
        baseLayout.addView(bottom, bottomDetails);

        setContentView(baseLayout);
    }

    public void tables() {

        // Create New Row
        final TableRow new_row = new TableRow(this);
        // Row Entity - 1: 'Name' EditText Field
        name = new EditText(this);
        name.setHint("Enter Name");
        name.setTextColor(Color.rgb(135, 170, 255));
        name.setHintTextColor(Color.rgb(60, 15, 240));
        name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        // Row Entity - 2: 'Amount Paid' EditText Field
        paid = new EditText(this);
        paid.setHint("Amount Paid");
        paid.setTextColor(Color.rgb(240, 15, 20));
        paid.setHintTextColor(Color.rgb(60, 15, 240));
        paid.setInputType(InputType.TYPE_CLASS_NUMBER); // Input Type set to Only Numeric Values

        // Row Item - 3: 'Delete Entity' Button
        del = new ImageButton(this);
        del.setBackground(getDrawable(R.drawable.del_button_props));
        /*del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TableRow delRow = (TableRow) findViewById(view.getId());
                ((ViewGroup) delRow.getParent()).removeView(delRow);

                //if(counter > 0) counter --;
                try {
                    GiveOrGet gog = new GiveOrGet(Splitster.this);
                    gog.open();
                    gog.deleteEntry(view.getId()+1);
                    gog.close();
                } catch(Exception e) {
                    String error = e.toString();
                    Dialog d = new Dialog(Splitster.this);
                    d.setTitle("Oops!");
                    TextView tv = new TextView(Splitster.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }

            }
        });
        del.setId(counter);
        new_row.setId(counter);
        paid.setId(counter);*/

        // Row Layout
        TableLayout.LayoutParams rowDetails = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        rowDetails.weight = 100f;
        rowDetails.setMargins(0,20,0,20);

        // Layout to hold 'Name' Field
        TableRow.LayoutParams nameDetails = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        nameDetails.weight = 90f;

        // Layout to hold 'Amount Paid' Field
        TableRow.LayoutParams paidDetails = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        paidDetails.weight = 5f;
        paidDetails.setMargins(30, 0, 30, 0);

        // Layout to hold 'Delete Entity' Button
        TableRow.LayoutParams delDetails = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        delDetails.weight = 5f;

        // Add 'Name', 'Amount Paid, 'Delete' entities to row
        new_row.addView(name, nameDetails);
        new_row.addView(paid, paidDetails);
        new_row.addView(del, delDetails);

        // Add row to the table
        table.addView(new_row, rowDetails);

        // Request focus on 'Name' Field and Hide Keyboard
        name.requestFocus();
        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(baseLayout.getWindowToken(), 0);*/

    }

    private void buttons() {
        Resources r = getResources();

        // Layout Params Buttons
        buttonDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonDetails.setMargins(25, 30, 25, 30);
        buttonDetails.gravity = Gravity.CENTER;

        // Unit Conversion using Resources

        // ADD Button
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());

        add_row_button = new Button(this);
        add_row_button.setOnClickListener(this);
        add_row_button.setWidth(px);
        add_row_button.setBackground(getDrawable(R.drawable.add_button_props));

        // VIEW Button
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, r.getDisplayMetrics());

        view_button = new Button(this);
        view_button.setOnClickListener(this);
        view_button.setWidth(px);
        view_button.setBackground(getDrawable(R.drawable.view_button_props));

        // REFRESH Button
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, r.getDisplayMetrics());

        refresh_button = new Button(this);
        refresh_button.setOnClickListener(this);
        refresh_button.setWidth(px);
        refresh_button.setBackground(getDrawable(R.drawable.refresh_button_props));


        // Setting Button IDs
        add_row_button.setId(R.id.addId);
        view_button.setId(R.id.viewId);
        refresh_button.setId(R.id.refreshId);

    }

    private void layoutParams() {
        Resources r = getResources();

        // ScrollView holding the Layout
        scrollViewDetails = new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT
        );

        // Layout holding the Table
        layoutDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        int pad = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, r.getDisplayMetrics());
        layout.setPadding(pad, pad, pad, pad);
        layout.setMinimumHeight(1000);

        // Final Layout holding All Components
        genericLayoutDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        // Layout Holding the Title
        titleLayoutDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        splitsterDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        splitsterDetails.setMargins(0, 50, 0, 50);
        splitsterDetails.gravity = Gravity.CENTER_HORIZONTAL;
        title.addView(splitster, splitsterDetails);

        hzbtm = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        topDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        topButton = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        topButton.gravity = Gravity.RIGHT;

        TableLayout extraTable = new TableLayout(this);

        TableLayout.LayoutParams extraRow = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
        );
        TableRow.LayoutParams ibLogout = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        TableRow.LayoutParams ibExit = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        TableRow.LayoutParams ibInstruct = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        TableRow extra = new TableRow(this);
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, r.getDisplayMetrics());
        ibLogout.width = px; ibExit.width = px; ibInstruct.width = 2 * px;
        extra.addView(instruct, ibInstruct);
        extra.addView(exit, ibExit);
        extra.addView(logout, ibLogout);
        ibLogout.column = 1; ibLogout.span = 1;

        ibExit.column = 0; ibExit.span = 1;
        extraTable.addView(extra, extraRow);
        top.addView(extraTable, topButton);

        bottomDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        // Layout to hold the Table
        tableDetails = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private void backgrounds() {
        // Title
        splitster.setBackground(getDrawable(R.drawable.splitster));
        logout.setBackground(getDrawable(R.drawable.logout_props));
        instruct.setBackground(getDrawable(R.drawable.instruct_props));
        exit.setBackground(getDrawable(R.drawable.exit_props));

        // Layout Backgrounds
        layout.setBackgroundColor(Color.rgb(6, 11, 110));
        baseLayout.setBackgroundColor(Color.rgb(6, 11, 90));
        bottom.setBackgroundColor(Color.rgb(6, 11, 70));
        top.setBackgroundColor(Color.rgb(6, 11, 70));
        horizon.setBackgroundColor(Color.rgb(6, 11, 60));
        title.setBackgroundColor(Color.rgb(6, 11, 60));
    }

    private void layouts() {

        // Declarations
        scrollView = new ScrollView(this);
        splitster = new ImageView(this);
        logout = new ImageButton(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logOutUser();
                finish();
            }
        });
        instruct = new ImageView(this);
        instruct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instruct.setActivated(true);
                Intent i = new Intent(Splitster.this, Instructions.class);
                startActivityForResult(i, 0);
            }
        });
        exit = new ImageButton(this);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit.setActivated(true);
                Intent i = new Intent(Splitster.this, CustomDialog.class);
                startActivityForResult(i, 0);
            }
        });

        layout = new LinearLayout(this);
        baseLayout = new LinearLayout(this);
        horizon = new LinearLayout(this);
        title = new LinearLayout(this);
        bottom = new LinearLayout(this);
        top = new LinearLayout(this);

        // Orientations
        layout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        horizon.setOrientation(LinearLayout.HORIZONTAL);
        title.setOrientation(LinearLayout.VERTICAL);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        top.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            exit.setActivated(false);
            instruct.setActivated(false);
        }
    }

    @Override
    public void onClick(View v) {
        v.setPressed(true);
        switch(v.getId()) {
            case R.id.addId:

                    String db_name = name.getText().toString();
                    String db_amt = paid.getText().toString();


                    if( !db_name.isEmpty() ) {
                        if ( db_amt.isEmpty()) {
                            db_amt = "0.0";
                            paid.setText("0");
                        }

                        sum += Double.parseDouble(db_amt);
                        counter ++;
                    }

                    if (db_name.isEmpty()) {
                        name.requestFocus();
                        Toast.makeText(Splitster.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    paid.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    if(counter % 2 == 0) {
                        name.setBackgroundColor(Color.rgb(10, 20, 130));
                        paid.setBackgroundColor(Color.rgb(10, 20, 130));
                    } else {
                        name.setBackgroundColor(Color.rgb(10, 10, 90));
                        paid.setBackgroundColor(Color.rgb(10, 10, 90));
                    }

                    name.setEnabled(false);
                    paid.setEnabled(false);
                try {
                    GiveOrGet entry = new GiveOrGet(this);
                    entry.open();
                    entry.createEntry(db_name, db_amt, (counter) );
                    entry.close();
                } catch (Exception e) {
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("Oops!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }


                tables();
                break;

            case R.id.viewId:

                db_name = name.getText().toString();
                db_amt = paid.getText().toString();
                if(!db_name.isEmpty() || !db_amt.isEmpty()) {
                    Toast.makeText(Splitster.this, "Please Press ADD to Add Entry", Toast.LENGTH_SHORT).show();
                    add_row_button.setPressed(true);
                    break;
                }
                //Toast.makeText(this, "" + counter, Toast.LENGTH_SHORT).show();
                getCount();
                Intent i = new Intent(Splitster.this, SQLView.class);
                i.putExtras(basket);
                try{
                    startActivity(i);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.refreshId:
                counter = 0;
                new Refresh().execute("Run");
                break;
            /*    try {
                    GiveOrGet gog = new GiveOrGet(this);
                    gog.open();
                    //gog.deleteEntry(rowId);
                    gog.close();
                } catch(Exception e) {
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("Oops!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }*/

        }
    }

    public void getCount() {
        int k = counter;
        basket = new Bundle();
        basket.putInt("COUNTER", counter);
        basket.putDouble("SUM", sum);
        basket.putInt("YES", 0);
    }

    public class Refresh extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            Intent ref = new Intent(Splitster.this, Loading.class);
            ref.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ref.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ref);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                GiveOrGet gog = new GiveOrGet(Splitster.this);
                gog.open();
                gog.deleteAllEntry();
                gog.close();
            } catch (Exception e) {
                String error = e.toString();
                Dialog d = new Dialog(Splitster.this);
                d.setTitle("Oops!");
                TextView tv = new TextView(Splitster.this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            }
            return null;
        }

    }
}

