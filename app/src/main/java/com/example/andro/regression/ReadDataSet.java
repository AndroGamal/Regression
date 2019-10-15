package com.example.andro.regression;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Andro on 10/10/2019.
 */

public abstract class ReadDataSet {
    private Scanner scanner;
    private File fileread;
    private ArrayList<ArrayList<Double>> list;

    ReadDataSet(String file) throws FileNotFoundException {
        fileread = new File(file);
        scanner = new Scanner(fileread);
        list = new ArrayList<>();
    }

    public void setmatrex(ArrayList<ArrayList<Double>> v) {
        list = v;
    }

    public ArrayList<ArrayList<Double>> read() {
        String s;
        ArrayList<Double> x = new ArrayList<>();
        while (scanner.hasNext()) {
            s = scanner.nextLine();
            int y = s.indexOf(",");
            while (y != -1) {
                x.add(Double.parseDouble(s.substring(0, y)));
                s = s.substring(y + 1);
                y = s.indexOf(",");
            }
            x.add(Double.parseDouble(s.substring(0)));
            list.add(new ArrayList<>(x));
            x.clear();
        }
        return list;
    }

    public double computeCost(ArrayList<ArrayList<Double>> X, ArrayList<ArrayList<Double>> y, ArrayList<ArrayList<Double>> theta) {

        return sum(power(sub(multyplay(X, T(theta)), y), 2)) / (2 * X.size());
    }

    public ArrayList<ArrayList<Double>> gradientDescent(ArrayList<ArrayList<Double>> X, ArrayList<ArrayList<Double>> y, ArrayList<ArrayList<Double>> theta, double alpha, int iters, ProgressBar progressBar) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>(theta);
        ArrayList<ArrayList<Double>> m;
        progressBar.setMax(iters - 1);
        for (int i = 0; i < iters; i++) {
            progressBar.setProgress(i);
            if (progressBar.getProgress() == progressBar.getMax()) {
                ok();
            }
            m = new ArrayList<>(sub(multyplay(X, T(theta)), y));
            for (int r = 0; r < X.get(0).size(); r++) {
                ans.get(0).set(r, theta.get(0).get(r) - alpha / X.size() * sum(multyplay(convert(getcolumx(X, r)), m)));
            }
            theta = new ArrayList<>(ans);
        }
        return theta;
    }

    public abstract void ok();

    public ArrayList<Double> getcolum(int colum) {
        ArrayList<Double> row = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            row.add(list.get(i).get(colum));
        }
        return row;
    }

    public ArrayList<ArrayList<Double>> get_all_without_y() {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();
        for (int i = 0; i < list.get(0).size() - 1; i++) {
            ans.add(getcolumx(list, i));
        }
        return T(ans);
    }

    private ArrayList<Double> getcolumx(ArrayList<ArrayList<Double>> x, int colum) {
        ArrayList<Double> row = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            row.add(x.get(i).get(colum));
        }
        return row;
    }

    public ArrayList<ArrayList<Double>> sub(ArrayList<ArrayList<Double>> x, ArrayList<ArrayList<Double>> y) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();
        ArrayList<Double> row_ans = new ArrayList<>();
        if (x.get(0).size() == y.get(0).size() && y.size() == x.size()) {
            for (int r = 0; r < x.size(); r++) {
                for (int i = 0; i < y.get(0).size(); i++) {
                    row_ans.add(x.get(r).get(i) - y.get(r).get(i));
                }
                ans.add(new ArrayList<>(row_ans));
                row_ans.clear();
            }
            return ans;
        } else {

            return null;
        }
    }

    public ArrayList<Double> getrow(int row) {
        return list.get(row);
    }

    public ArrayList<ArrayList<Double>> multyplay(ArrayList<ArrayList<Double>> x) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();
        ArrayList<Double> row_ans = new ArrayList<>();
        if (list.get(0).size() == x.size()) {
            for (int r = 0; r < list.size(); r++) {
                for (int i = 0; i < x.get(0).size(); i++) {
                    row_ans.add(x(list.get(r), getcolumx(x, i)));
                }
                ans.add(new ArrayList<>(row_ans));
                row_ans.clear();
            }
            return ans;
        } else {

            return null;
        }

    }

    public ArrayList<ArrayList<Double>> multyplay(ArrayList<ArrayList<Double>> x, ArrayList<ArrayList<Double>> theta) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>();
        ArrayList<Double> row_ans = new ArrayList<>();
        if (x.get(0).size() == theta.size()) {
            for (int r = 0; r < x.size(); r++) {
                for (int i = 0; i < theta.get(0).size(); i++) {
                    row_ans.add(x(x.get(r), getcolumx(theta, i)));
                }
                ans.add(new ArrayList<>(row_ans));
                row_ans.clear();
            }
            return ans;
        } else {

            return null;
        }

    }

    public void multyplay(double x) {
        for (int r = 0; r < list.size(); r++) {
            for (int i = 0; i < list.get(r).size(); i++) {
                list.get(r).set(i, list.get(r).get(i) * x);
            }
        }
    }

    public ArrayList<ArrayList<Double>> multyplay(ArrayList<ArrayList<Double>> t, double x) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>(t);
        for (int r = 0; r < ans.size(); r++) {
            for (int i = 0; i < ans.get(r).size(); i++) {
                ans.get(r).set(i, ans.get(r).get(i) * x);
            }
        }
        return ans;
    }

    private double x(ArrayList<Double> row, ArrayList<Double> colum) {
        double ans = 0;
        for (int i = 0; i < row.size(); i++) {
            ans += row.get(i) * colum.get(i);
        }
        return ans;
    }

    public ArrayList<ArrayList<Double>> T() {
        ArrayList<ArrayList<Double>> T = new ArrayList<>();
        for (int i = 0; i < list.get(0).size(); i++) {
            T.add(new ArrayList<>(getcolum(i)));
        }
        return T;
    }

    public ArrayList<ArrayList<Double>> T(ArrayList<ArrayList<Double>> t) {
        ArrayList<ArrayList<Double>> T = new ArrayList<>();
        for (int i = 0; i < t.get(0).size(); i++) {
            T.add(new ArrayList<>(getcolumx(t, i)));
        }
        return T;
    }

    public ArrayList<ArrayList<Double>> power(ArrayList<ArrayList<Double>> t, double x) {
        ArrayList<ArrayList<Double>> ans = new ArrayList<>(t);
        for (int r = 0; r < ans.size(); r++) {
            for (int i = 0; i < ans.get(r).size(); i++) {
                ans.get(r).set(i, Math.pow(ans.get(r).get(i), x));
            }
        }
        return ans;
    }

    public double sum(ArrayList<ArrayList<Double>> c) {
        double ans = 0.0;
        for (int r = 0; r < c.size(); r++) {
            for (int i = 0; i < c.get(r).size(); i++) {
                ans += c.get(r).get(i);
            }
        }
        return ans;
    }

    public ArrayList<ArrayList<Double>> convert(ArrayList<ArrayList<Double>> l, ArrayList<Double>... v) {
        l = T(l);
        for (int i = 0; i < v.length; i++) {
            l.add(0, new ArrayList<>(v[i]));
        }
        l = T(l);
        return l;
    }

    public ArrayList<ArrayList<Double>> convert(ArrayList<Double>... v) {
        ArrayList<ArrayList<Double>> l = new ArrayList<>();
        for (int i = 0; i < v.length; i++) {
            l.add(0, new ArrayList<>(v[i]));
        }
        return l;
    }
}
