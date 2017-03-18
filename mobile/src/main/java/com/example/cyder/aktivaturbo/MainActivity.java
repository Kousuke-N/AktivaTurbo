package com.example.cyder.aktivaturbo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MobileCommunicate mobileCommunicate;

    private final PCCommunicate mMessageListener = new PCCommunicate(8888) {
        @Override
        protected void onReceivedData(final String data) {
            Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        通信用クラスのインスタンス化
        mobileCommunicate = new MobileCommunicate();
        mobileCommunicate.connect(this);

        mMessageListener.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
