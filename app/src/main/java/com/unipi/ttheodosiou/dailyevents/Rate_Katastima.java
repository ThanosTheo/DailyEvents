package com.unipi.ttheodosiou.dailyevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Rate_Katastima extends AppCompatActivity {

    public static TextView addressbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate__katastima);

        addressbox=(TextView) findViewById(R.id.address);

        final EditText rated=(EditText)findViewById(R.id.rated);
        Intent myintent=getIntent();
        final TextView name=(TextView) findViewById(R.id.name);
        final int number=myintent.getIntExtra("Katastima",0);
        name.setText((CharSequence) katastimata_diaskedasis.katastimataArray[number].getText());
        Katastimata.getAddress(name.getText().toString().trim());

        Button submit=(Button)findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(rated.getText().toString())>10)
                {
                    Toast.makeText(getApplicationContext(),"Please insert a number between 1 and 10.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //change database value
                    Katastimata.setRating(number,Integer.parseInt(rated.getText().toString()));
                    Toast.makeText(getApplicationContext(),"Your Rating has been submited",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(getApplicationContext(),katastimata_diaskedasis.class);
                    startActivity(intent);
                }
            }
        });
    }
}
