package com.example.david.lang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView numbers=(TextView)findViewById(R.id.no);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Numbers.class);
                startActivity(i);

            }
        });

    }

    public void openFamily(View view){
        Intent i=new Intent(this,Family.class);
        startActivity(i);
    }
    public void openColors(View view){
        Intent i=new Intent(this,Colors.class);
        startActivity(i);
    }
    public  void openPhrases(View view){
        Intent i=new Intent(this,Phrases.class);
        startActivity(i);
    }
}
