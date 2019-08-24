package com.example.matrixapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class InputPage extends AppCompatActivity {

    private static int rowsNum;

    private static int colNum;

    private static double[][] matrix;

    private static List<double[][]> rrefList = new ArrayList<>();

    private Logger Log;

    private Button rrefSubmit;

    private Button transposeSubmit;

//        rrefSubmit = (Button) findViewById(R.id.rrefSubmit );
//        rrefSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openrref();
//            }
//        });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);



        Bundle b = getIntent().getExtras();
        if (b != null) {
            try {
                rowsNum = b.getIntegerArrayList("key").get(0);
                colNum = b.getIntegerArrayList("key").get(1);
            } catch (NullPointerException ex) {
                Log.info("Error retrieving matrix size list");
            }
        }

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("Fill in your Matrix Below!");
        linearLayout.addView(textView);



        for (int s=0; s<rowsNum; s++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for (int t=0; t<colNum; t++) {
                EditText temp = new EditText(this);
                temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s);
                String mid = Integer.toString(999);
                stringBuilder.append(mid);
                stringBuilder.append(t);
                int id = Integer.parseInt(stringBuilder.toString());
                temp.setId(t);
                temp.setHint("0");

                row.addView(temp);
                row.setId(s);
            }

            linearLayout.addView(row);
        }

        Button submitBtn = new Button(this);
        submitBtn.setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        submitBtn.setText("Submit !");
        linearLayout.addView(submitBtn);
        setContentView(linearLayout);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrix = new double[rowsNum][colNum];

                for (int t=0; t<colNum; t++) {
                    for (int s = 0; s < rowsNum; s++) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(s);
                        String mid = Integer.toString(999);
                        stringBuilder.append(mid);
                        stringBuilder.append(t);
                        int id = Integer.parseInt(stringBuilder.toString());
                        EditText temp = findViewById(id);
                        matrix[s][t] = Double.parseDouble(temp.getText().toString().trim());
                    }
                }

                double[][] solved = rref(matrix);
                for (int t=0; t<colNum; t++) {
                    for (int s = 0; s < rowsNum; s++) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(s);
                        String mid = Integer.toString(999);
                        stringBuilder.append(mid);
                        stringBuilder.append(t);
                        int id = Integer.parseInt(stringBuilder.toString());
                        EditText temp = findViewById(id);
                        String text = Double.toString(solved[s][t]);
                        temp.setText(text);
                    }
                }
            }
        });

    }

//    public void nullCheck (){
//        for (int t=0; t<colNum; t++) {
//            for (int s = 0; s < rowsNum; s++) {
//                if (matrix[s][t] == null){
//
//                }
//            }
//        }
//    }
//    public void openrref() {
//        Intent intent = new Intent(this, InputPage.class);
//        Bundle b = new Bundle();
//        b.putIntegerArrayList("key", matrixSize());
//        intent.putExtras(b);
//        startActivity(intent);
//    }


    /*    =====================================================
      TRANSPOSE OF THE MATRIX
 */

    private static double[][] transpose(double[][] matrix) {
        List<double[]> doubList = new ArrayList<>();
        int i = matrix.length;
        int j = matrix[0].length;
        double[][] transpose = new double[j][i];
        for (int t = 0; t < j; t++) {
            double[] doub = new double[i];
            for (int s = 0; s < i; s++) {
                doub[s] = matrix[s][t];
            }
            doubList.add(doub);

        }

        for (int s = 0; s < j; s++) {
            transpose[s] = doubList.get(s);
        }
        return transpose;

    }


/*    =====================================================
      RREF OF MATRIX
 */

    private static boolean isUnique(double[][] rref) {
        Set<double[][]> rrefSet = new HashSet<>(rrefList);
        return rrefSet.size() == rrefList.size();
    }

    private static double[][] rref(double[][] matrix) {
        double[][] rref = matrix;
        Set<double[][]> rrefSet = new HashSet<>(rrefList);

        while (isUnique(rref)) {
            rref = rowReducer(rref);
        }
        rref = orderMatrixRows(rref);
        rref = reducedMatrix(rref);
        return rref;
    }


    private static double[][] orderMatrixRows(double[][] matrix) {
        List<double[]> rows = new ArrayList<>();
        List<double[]> nonPivotRows = new ArrayList<>();
        double[][] rref = new double[matrix.length][matrix[0].length];
        for (int t = 0; t < matrix[0].length - 1; t++) {
            for (int s = 0; s < matrix.length; s++) {
                if (matrix[s][t] != 0) {
                    double[] row = matrix[s];
                    rows.add(row);
                }
            }
        }
        for (int i = 0; i < rows.size(); i++) {
            rref[i] = rows.get(i);
        }
        return rref;
    }

    private static double[][] reducedMatrix(double[][] matrix) {
        List<double[]> doubList = new ArrayList<>();
        double[][] rref = matrix;
        List<Double> pivotList = new ArrayList<>();
        for (double[] doub : matrix) {
            for (double d : doub) {
                if (d != 0) {
                    pivotList.add(d);
                    break;

                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            double[] doub = matrix[i];
            double[] row = new double[doub.length];
            double pivot = pivotList.get(i);
            for (int j = 0; j < doub.length; j++) {
                if (doub[j] != 0) {
                    double val = doub[j] / pivot;
                    row[j] = val;
                }
            }
            doubList.add(row);
        }

        for (int i = 0; i < doubList.size(); i++) {
            rref[i] = doubList.get(i);
        }
        return rref;
    }

    private static Map<int[], Double> pivotMapper(double[][] matrix) {
        double[][] rref = matrix;
        List<Integer> pivotRows = new ArrayList<>();

//        key for hashmap will be the column # and the value will be the pivot value
        Map<int[], Double> pivotMap = new HashMap<>();
        for (int t = 0; t < rref[0].length; t++) {
            double pivot = -1;
            int pivotRow = 0;
            for (int s = 0; s < rref.length; s++) {
                double st = rref[s][t];
//                there can only be one pivot per column
                if (st != 0 && pivot == -1 && !pivotRows.contains(s)) {
                    pivot = rref[s][t];
                    pivotRows.add(s);
                    int[] array = new int[]{s, t};
                    pivotMap.put(array, pivot);
                }
            }
        }
        pivotMap = mapSorter(pivotMap);
        return pivotMap;
    }

    private static double[][] rowReducer(double[][] matrix) {
        double[][] rref = matrix;
        Map<int[], Double> pivotMap = (pivotMapper(rref));

        for (Map.Entry<int[], Double> entry : pivotMap.entrySet()) {
            boolean isChanged = false;
            double pivot = entry.getValue();
            int i = entry.getKey()[0];
            int j = entry.getKey()[1];
            double[] pivotRow = rref[i];

            for (int s = 0; s < rref.length; s++) {
                double mul = -(rref[s][j] / pivot);
                if (rref[s][j] != 0 && s != i) {
                    for (int t = 0; t < rref[0].length; t++) {
                        double d = rref[s][j];
                        rref[s][t] = rref[s][t] + mul * rref[i][t];
                        isChanged = true;
                    }
                }
            }
            if (isChanged)
                break;
        }
        rrefList.add(rref);
        return rref;
    }

    private static Map<int[], Double> mapSorter(Map<int[], Double> pivotMap) {
        Map<int[], Double> hashMap = new LinkedHashMap<>();
        List<Map.Entry<int[], Double>> entryList = new ArrayList<>(pivotMap.entrySet());

        Collections.sort(entryList, new Comparator<Map.Entry<int[], Double>>() {
            @Override
            public int compare(Map.Entry<int[], Double> o1, Map.Entry<int[], Double> o2) {
                return o1.getKey()[1] - o2.getKey()[1];
            }
        });

        for (Map.Entry<int[], Double> e : entryList) {
            hashMap.put(e.getKey(), e.getValue());
        }
        return hashMap;
    }
}


