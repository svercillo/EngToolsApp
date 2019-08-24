package com.example.matrixapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/*    =====================================================
      RREF OF MATRIX OR TRANSPOSE
 */
public class MatrixSizer extends AppCompatActivity {

    private Button submitDimBtn;
    private TextView rows;
    private TextView col;
    private static String rowsAndCols;
    private static String Cols;
    public static boolean isInvalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_sizer);

        submitDimBtn = findViewById(R.id.submitDimsBtn);

        rows = findViewById(R.id.rowsAndCol_input);

        submitDimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowsAndCols = rows.getText().toString().trim();
                openInputPage();
            }
        });
    }


    public void openInputPage() {
        Intent intent = new Intent(this, InputPage.class);
        Bundle b = new Bundle();
        b.putIntegerArrayList("key", matrixSize());
        intent.putExtras(b);
        startActivity(intent);
    }

//    Takes the user input of the size of the desired matrix
    public static ArrayList<Integer> matrixSize (){
        String string = rowsAndCols;
        ArrayList<Integer> list = new ArrayList<>();
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c)){
                list.add(Character.getNumericValue(c));
            }
            if(list.size() > 2) {
                isInvalid = true;
                break;
            }
        }
        return list;

    }
}
