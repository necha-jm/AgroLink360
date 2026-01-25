package com.app.agroli;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SmsActivity extends AppCompatActivity {
 EditText edit1, edit2;
 Button btn1, btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        String to = edit1.getText().toString().trim();
        String message = edit2.getText().toString().trim();

        // Add this in onCreate() after setContentView()
        EditText messageEditText = findViewById(R.id.edit2);
        TextView charCountTextView = findViewById(R.id.charCountTextView);

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                charCountTextView.setText(length + "/160");

                // Change color if approaching limit
                if (length > 140) {
                    charCountTextView.setTextColor(Color.RED);
                } else {
                    charCountTextView.setTextColor(getResources().getColor(R.color.secondary));
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Uri smsUri = Uri.parse("smsto:" + to); // ✅ colon is REQUIRED
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(smsUri);
                intent.putExtra("sms_body", message);
                if(intent.resolveActivity(getPackageManager())!=  null) {
                    startActivity(intent);
                }// no permission needed
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent call = new Intent(Intent.ACTION_CALL);
                Uri callUri =  Uri.parse("tel:"+  to.replaceAll("[\\s\\-()]", ""));
                call.setData(callUri);
                if(callgit.resolveActivity(getPackageManager())!=  null){
                   startActivity(call);
                }else {
                    Toast.makeText(SmsActivity.this,
                            "No app to handle calls",
                            Toast.LENGTH_SHORT).show();
                }
            }});


    }
}