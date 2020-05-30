package com.example.android.dictionery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView numbers=(TextView)findViewById(R.id.numbers);
        numbers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NumbersActivity.class);
                //intent.setData(Uri.parse("")); // only email apps should handle this
                startActivity(intent);
            }
        });
        TextView family=(TextView)findViewById(R.id.family);
        family.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Family.class);
                //intent.setData(Uri.parse("")); // only email apps should handle this
                startActivity(intent);
            }
        });
        TextView colors=(TextView)findViewById(R.id.colors);
        colors.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,colors.class);
                //intent.setData(Uri.parse("")); // only email apps should handle this
                startActivity(intent);
            }
        });
        TextView phrases=(TextView)findViewById(R.id.phrases);
        phrases.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Phrases.class);
                //intent.setData(Uri.parse("")); // only email apps should handle this
                startActivity(intent);
            }
        });
    }

}
