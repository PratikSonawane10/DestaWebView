package com.choicespropertysolutions.desta.DialogBox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.choicespropertysolutions.desta.R;

public class TimeOut_DialogeBox extends AppCompatActivity implements View.OnClickListener {

    private static Button timeOut_btnOk;
    private static TextView timeout_EroorLabel;
    private static View timeoutErrorDividerLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeout_error_dialogebox);

        timeOut_btnOk = (Button) findViewById(R.id.btnTimeOutOk);
        timeoutErrorDividerLine = findViewById(R.id.timeoutErrorDividerLine);
        timeoutErrorDividerLine.setBackgroundResource(R.color.list_internal_divider);
        timeOut_btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTimeOutOk) {
            this.finish();
           }
    }
}
