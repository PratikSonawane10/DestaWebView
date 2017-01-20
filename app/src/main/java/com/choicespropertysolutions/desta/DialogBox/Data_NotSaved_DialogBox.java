package com.choicespropertysolutions.desta.DialogBox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.choicespropertysolutions.desta.R;


public class Data_NotSaved_DialogBox extends AppCompatActivity implements View.OnClickListener {

    private static Button dataNotSaved_btnOk;
    private static TextView dataNotSaved_EroorLabel;
    private static View dataNotSavedErrorDividerLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_notsaved_dialogbox);

        dataNotSaved_btnOk = (Button) findViewById(R.id.btnDataNotSavedOk);
        dataNotSavedErrorDividerLine = findViewById(R.id.dataNotSavedErrorDividerLine);
        dataNotSavedErrorDividerLine.setBackgroundResource(R.color.list_internal_divider);
        dataNotSaved_btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDataNotSavedOk) {
            this.finish();
        }
    }
}