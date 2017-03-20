package com.example.cyder.aktivaturbo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;
    UsbMessage usbMessage;

    private final PCCommunicate pcCommunicate = new PCCommunicate(8888) {
        @Override
        protected void onReceivedData(final String data) {
            Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
            Log.d("MainActivity", "received:" + data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usbMessage = new UsbMessage(8888);

        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("送信しました");
                usbMessage.sendData("Hello,world");
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        pcCommunicate.setRunning(false);
    }
}
