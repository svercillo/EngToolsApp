package com.example.matrixapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/*    =====================================================
      RREF OF MATRIX OR TRANSPOSE
 */
public class Main2Activity extends AppCompatActivity {

    private Button submitDimBtn;
    private TextView input;

    private static String rowsAndCols;
    public static boolean isInvalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        submitDimBtn = findViewById(R.id.submitDimsBtn);
        input = findViewById(R.id.rowsAndCol_input);

        submitDimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowsAndCols = input.getText().toString().trim();
                openActivity6();
            }
        });
    }


    public void openActivity6() {
        Intent intent = new Intent(this, Main6Activity.class);
        Bundle b = new Bundle();
        b.putIntegerArrayList("key", matrixSize());
        intent.putExtras(b);
        startActivity(intent);
    }

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
