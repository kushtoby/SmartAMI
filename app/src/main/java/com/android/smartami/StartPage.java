package com.android.smartami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {
        public Button paymentButton, monitorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        paymentButton = findViewById(R.id.paymentBtn);
        monitorButton = findViewById(R.id.monitorBtn);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customPay = new Intent(StartPage.this, PaymentPage.class);
                startActivity(customPay);
            }
        });
        monitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customMonitor = new Intent(StartPage.this, ViewData.class);
                startActivity(customMonitor);
            }
        });
    }
}
