package com.splendidsky.bronzeragent;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.text);

        try {
//            if (AppTool.isAllowBackup("com.splendidsky.bronzeragent"))
//                textView.setText("true");
//            else
//                textView.setText("false");
            textView.setText(RemoteCmdHandler.handle("assess"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
