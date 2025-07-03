package com.example.myphantom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConnectWallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_wallet);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
        CardView createAccountCard = findViewById(R.id.createAccountCard);
        CardView connectHardwareWallet = findViewById(R.id.connectHardwareCard);
        CardView importRecoveryPhrase = findViewById(R.id.importRecoveryCard);
        CardView importPrivateKey = findViewById(R.id.importPrivateKeyCard);
        CardView watchAddress = findViewById(R.id.watchAddressCard);
        ViewUtils.addPressEffect(createAccountCard);
        ViewUtils.addPressEffect(importPrivateKey);
        ViewUtils.addPressEffect(importRecoveryPhrase);
        ViewUtils.addPressEffect(connectHardwareWallet);
        ViewUtils.addPressEffect(watchAddress);
        createAccountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConnectWallet.this, CreateAccount.class);
                startActivity(intent);
            }
        });
        connectHardwareWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConnectWallet.this, ConnectHardwareWallet.class);
                startActivity(intent);
            }
        });
        importRecoveryPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConnectWallet.this, RecoveryPhraseTV.class);
                startActivity(intent);
            }
        });
        watchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConnectWallet.this, WatchAddress.class);
                startActivity(intent);
            }
        });
        importPrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConnectWallet.this, PrivateKey.class);
                startActivity(intent);
            }
        });
    }
}