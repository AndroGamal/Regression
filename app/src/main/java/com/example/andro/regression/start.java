package com.example.andro.regression;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class start extends AppCompatActivity {
    Button c, drow;
    EditText t, nu, b;
    FolderPath file;
    String s;
    double alpha;
    int ilter;
    ReadDataSet n;
    static Bitmap o;
    ArrayList<Double> xt, y, theta, one;
    static ArrayList<ArrayList<Double>> x, y_ans, theta_ans;
    static double coust;
    ProgressBar progressBar;
    LinearLayout layout;
    TextView textView;
    AlertDialog.Builder builder;
    AlertDialog cansel;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //  progressBar.setProgress(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // progressBar.setProgress(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        c = findViewById(R.id.button);
        t = findViewById(R.id.editText);
        nu = findViewById(R.id.editText2);
        b = findViewById(R.id.editText1);
        drow = findViewById(R.id.button2);
        textView = new TextView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        textView.setText("please wait...");
        layout.addView(textView);
        layout.addView(progressBar);
        builder = new AlertDialog.Builder(start.this).setNegativeButton("Cansel", null).setTitle("wait").setView(layout).setCancelable(false);
        cansel = builder.create();
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file = new FolderPath(start.this) {
                    @Override
                    void OK() {
                        t.setText(s = file.getpath());
                    }
                };
                file.create();
            }
        });
        drow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alpha = Double.parseDouble(nu.getText().toString());
                ilter = Integer.parseInt(b.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        e();
                    }
                }).start();
                cansel.show();
            }
        });

    }

    public void lineX() {
        for (int x = 0; x < o.getWidth(); x++) {

            o.setPixel(x, o.getHeight() - 2, Color.BLACK);

        }

    }

    public void lineY() {
        for (int y = 0; y < o.getHeight(); y++) {

            o.setPixel(2, y, Color.BLACK);

        }
    }

    public void setpoint(ArrayList<Double> x, ArrayList<Double> y) {
        for (int i = 0; i < x.size(); i++) {
            o.setPixel(Math.round(x.get(i).floatValue() * 10), (o.getHeight() - Math.abs(Math.round(y.get(i).floatValue() * 10))), Color.RED);
        }
    }

    public void line() {
    }

    public void e() {
        o = Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_4444);
        try {
            n = new ReadDataSet(s) {
                @Override
                public void ok() {
                    startActivity(new Intent(start.this, MainActivity.class));
                    cansel.cancel();
                }
            };
            n.read();
        } catch (FileNotFoundException e) {
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        lineX();
        lineY();
        x = new ArrayList<>(n.get_all_without_y());
        xt = new ArrayList<>(n.getcolum(0));
        y = new ArrayList<>(n.getcolum(x.get(0).size()));
        one = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            one.add(1.0);
        }
        x = n.convert(x, one);
        theta = new ArrayList<>();
        for (int i = 0; i < x.get(0).size(); i++) {
            theta.add(0.0);
        }



            coust = n.computeCost(x, y_ans = n.T(n.convert(y)), theta_ans = n.gradientDescent(x, n.T(n.convert(y)), n.convert(theta), alpha, ilter, progressBar));
            setpoint(xt, y);

    }

}
