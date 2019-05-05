package com.example.matrixapp;

import android.graphics.Paint;
import android.sax.RootElement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main6Activity extends AppCompatActivity {

    private static int rowsNum;

    private static int colNum;

    private Logger Log;

    private Button submitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
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
                temp.setId(id);
                temp.setHint("0");

                row.addView(temp);

            }

            linearLayout.addView(row);
        }

        Button btn = new Button(this);
        btn.setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        btn.setText("Submit!");
        linearLayout.addView(btn);
        setContentView(linearLayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }


}


