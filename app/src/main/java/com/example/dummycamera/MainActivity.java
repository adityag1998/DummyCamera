package com.example.dummycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected String objectName;
    protected EditText intentInput;
    protected Button sendButton;
    protected static final String RECEIVE_OBJECT_NAME = "com.example.dummycamera.receive_object_name";
    protected static final String KEY = "com.example.dummycamera.objectName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentInput = (EditText) findViewById(R.id.intentInput);
        sendButton = (Button) findViewById(R.id.sendButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logic to publish Broadcast Intent
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectName = intentInput.getText().toString();
                if (objectName.length() == 0) {
                    showToast("Please Enter Object Name to Send");
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(RECEIVE_OBJECT_NAME); //Custom Action
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setPackage("com.samsung.smartnotes");
                    intent.putExtra(KEY, objectName);
                    sendBroadcast(intent);
                    showToast("Broadcast is Fired with Object Name : " + objectName);
                }
            }
        });
    }

    protected void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}