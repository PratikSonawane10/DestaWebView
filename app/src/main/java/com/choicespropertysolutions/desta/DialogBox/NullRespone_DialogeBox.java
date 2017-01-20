package com.choicespropertysolutions.desta.DialogBox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.choicespropertysolutions.desta.R;

public class NullRespone_DialogeBox extends AppCompatActivity implements View.OnClickListener{

    private static Button null_btnOk;
    private static TextView null_EroorLabel;
    private static View nullErrorDividerLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.null_response_dialogebox);

        null_btnOk = (Button) findViewById(R.id.btnNullOk);
        nullErrorDividerLine = findViewById(R.id.nullErrorDividerLine);
        nullErrorDividerLine.setBackgroundResource(R.color.list_internal_divider);
        null_btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNullOk) {
            this.finish();
        }
    }
}
