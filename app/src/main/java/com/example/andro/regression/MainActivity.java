package com.example.andro.regression;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(start.o);
        textView = findViewById(R.id.data);
        try {
         textView.setText("X = \n" + loop(start.x) + "\n" + "Y = \n" + loop(start.y_ans) + "\nTheta = " + loop(start.theta_ans) + "\nCoust = " + start.coust);
     }catch (Exception e){
         Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();}
     }

    public String loop(ArrayList<ArrayList<Double>> b) {
        String d = "[";
        for (int i = 0; i < b.size(); i++) {
            for (int r = 0; r < b.get(0).size(); r++) {
                d += b.get(i).get(r) + "  ";
            }
            d+="\n";
        }
        d += "]";
        return d;
    }

}
