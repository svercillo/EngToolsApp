package com.example.matrixapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.*;

public class HomePage extends AppCompatActivity {

    private Button rrefBtn;
    private Button transposeBtn;
    private Button mulBtn;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        rrefBtn = (Button) findViewById(R.id.rrefBtn);
        rrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        transposeBtn = (Button) findViewById(R.id.transposeBtn);
        transposeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        mulBtn = findViewById(R.id.mulBtn);
        mulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, MatrixSizer.class);
        startActivity(intent);
    }

    public void openActivity3() {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);

    }

    public void openActivity4() {
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);

    }

    public void openActivity5() {
        Intent intent = new Intent(this, Main5Activity.class);
        startActivity(intent);

    }



    public static boolean isInvalid = false;

    private static List<double[][]> rrefList = new ArrayList<>();


/*    =====================================================
      MATRIX MULTIPLICATION
 */

    private static double[][] matrixMultiplier(double[][] A, double[][] B) {
        if (A.length != B[0].length || A[0].length != B.length) {

            return null;
        }
        double[][] C = new double[A.length][B[0].length];

        List<double[]> arrBList = new ArrayList<>();
        for (int t = 0; t < B[0].length; t++) {
            double[] arrB = new double[B.length];
            for (int s = 0; s < B.length; s++) {

                arrB[s] = B[s][t];

            }
            arrBList.add(arrB);
        }

        for (int t = 0; t < B[0].length; t++) {
            for (int s = 0; s < A.length; s++) {
                double[] arrA = A[s];
//                double[] arrB = new double[B.length];
                C[s][t] = dotProduct(arrA, arrBList.get(t));
            }
        }
        return C;
    }

    //      Make this a lambda function
    private static double dotProduct(double[] arrA, double[] arrB) {
        int dotProduct = 0;
        List<Double> doubleList = new ArrayList<>();
        for (int i = 0; i < arrA.length; i++) {
            doubleList.add(arrA[i] * arrB[i]);
        }
        for (double d : doubleList) {
            dotProduct += d;
        }
        return dotProduct;
    }


/*    =====================================================
      MATRIX ADDITION AND SUBTRACTION
 */

    private static double[][] matrixAddSub(double[][] A, double[][] B, char operation) {
//        Both A and B must be the same size
        if (A.length != B.length || A[0].length != B[0].length) {
            isInvalid = true;
            return null;
        }
        double[][] C = new double[A.length][A[0].length];
        for (int t = 0; t < C[0].length; t++) {
            for (int s = 0; s < C.length; s++) {
                if (operation == '+')
                    C[s][t] = A[s][t] + B[s][t];
                else if (operation == '-')
                    C[s][t] = A[s][t] - B[s][t];
            }
        }
        return C;
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
}
