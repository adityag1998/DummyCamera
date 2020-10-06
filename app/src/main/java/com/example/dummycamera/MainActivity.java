package com.example.dummycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // Class functions
    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            return packageManager.getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    // Class Members
    protected static final String PARSE_BUNDLE = "com.samsung.navicam.parse_bundle";
    protected static final String KEY1 = "com.samsung.navicam.ObjectList";
    protected static final String KEY2 = "com.samsung.navicam.text";
    protected static final String BENEFICIARY = "com.samsung.smartnotes";

    // Data Members
    protected EditText intentInputText;
    protected EditText intentInputObjectList;
    protected Button sendButton;

    // Helper Functions
    protected void showToast(ArrayList<String> objectList, String text) {
        String toastText = "Broadcast is Fired: " + "\n" +
                "Object List Size: " + objectList.size() + "\n" +
                "Text String Length: " + text.length();
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

    protected void showFailToast(){
        Toast.makeText(this, "Companion App: com.samsung.smartnotes Not Found", Toast.LENGTH_SHORT).show();
    }

    protected Bundle createBundle(ArrayList<String> objectList, String text){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY1, objectList);
        bundle.putSerializable(KEY2, text);
        return bundle;
    }

    protected String getText(EditText intentInputText) {
        return intentInputText.getText().toString();
    }

    protected ArrayList<String> getObjectList(EditText intentInputObjectList) {
        String processableString = intentInputObjectList.getText().toString();
        String[] processedString = processableString.split(",");
        return new ArrayList<>(Arrays.asList(processedString));
    }

    protected void sendBroadcastIntent (Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(PARSE_BUNDLE);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage("com.samsung.smartnotes");
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentInputObjectList = (EditText) findViewById(R.id.intentInputObjectList);
        intentInputText = findViewById(R.id.intentInputText);
        sendButton = (Button) findViewById(R.id.sendButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logic to publish Broadcast Intent
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.isPackageInstalled(BENEFICIARY, getPackageManager())) {
                    ArrayList<String> objectList = getObjectList(intentInputObjectList);
                    String text = getText(intentInputText);
                    sendBroadcastIntent(createBundle(objectList, text));
                    showToast(objectList, text);
                }
                else {
                    showFailToast();
                }
            }
        });
    }
}