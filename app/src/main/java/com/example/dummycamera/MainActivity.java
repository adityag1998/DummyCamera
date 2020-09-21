package com.example.dummycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String intentName;
    EditText intentInput;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentInput = (EditText) findViewById(R.id.intentInput);
        sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentName = intentInput.getText().toString();

                if (intentName.length() == 0) {
                    showToast("Please Enter Intent Name to send");
                } else {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.samsung.smartnotes");
                    if (launchIntent != null) {
                        showToast("Sending Intent : " + intentName);
                        launchIntent.putExtra("com.samsung.smartnotes.MainActivity.valueReceived", intentName);
                        startActivity(launchIntent);
                    } else {
                        showToast("Smart Notes not found");
                    }
                }
            }
        });
    }

    public void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}