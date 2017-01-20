package com.choicespropertysolutions.desta.DialogBox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.choicespropertysolutions.desta.Index;
import com.choicespropertysolutions.desta.R;

public class EmptyListDialoge  extends AppCompatActivity implements View.OnClickListener {

    private static Button errortList_btnOk;
    private static TextView emptyList_EroorLabel;
    private static View emptyListDividerLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptylist_dialoge);
        errortList_btnOk = (Button) findViewById(R.id.btnEmptyListOk);
        emptyListDividerLine = findViewById(R.id.emptylistDividerLine);
        emptyListDividerLine.setBackgroundResource(R.color.list_internal_divider);
        errortList_btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEmptyListOk) {
            this.finish();
//            Intent gotoHome= new Intent(this, Index.class);
//            startActivity(gotoHome);
        }
    }
}
