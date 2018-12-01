package com.example.android.justjava;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void increment(View view){
        if(quantity<100){
            quantity += 1;
        }
        else{
            Toast toast=Toast.makeText(getApplicationContext(),"You cannot have more than 1 coffee",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        display(quantity);
    }

    public void decrement(View view){
        if(quantity>1) {
            quantity -= 1;
        }
        else{
            Toast toast=Toast.makeText(getApplicationContext(),"You cannot have less than 1 coffee",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        display(quantity);
    }

    public void submitOrder(View view) {

        CheckBox checkBox1 = (CheckBox) findViewById(R.id.whipped);
        boolean check1=checkBox1.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.chocolate);
        boolean check2=checkBox2.isChecked();

        int price=calculatePrice(check1,check2);

        EditText editText=(EditText) findViewById(R.id.input);
        String name=editText.getText().toString();

        String p=createOrderSummary(name,price,check1,check2) ;

        String r=getResources().getString(R.string.order_summary_email_subject);

        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT,p);
        intent.putExtra(Intent.EXTRA_SUBJECT,r);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean c1, boolean c2) {
        int price;
        if(c1&&!c2){
            price=quantity*6;
        }else if(c2&&!c1){
            price=quantity*7;
        }else if(c1&&c2){
            price=quantity*8;
        }else{
            price=quantity*5;
        }

        return price;
    }

    private String createOrderSummary(String name,int number, boolean checkBox1, boolean checkBox2){
        String s=getResources().getString(R.string.order_summary_name,name);
        s += "\n"+getResources().getString(R.string.order_summary_whipped_cream,checkBox1);
        s += "\n"+getResources().getString(R.string.order_summary_chocolate,checkBox2);
        s += "\n"+getResources().getString(R.string.order_summary_quantity,quantity);
        s += "\n"+getResources().getString(R.string.order_summary_price,number);
        s += "\n"+getResources().getString(R.string.thank_you);
        return s;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */


}