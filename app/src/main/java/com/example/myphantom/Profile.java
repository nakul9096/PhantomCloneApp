package com.example.myphantom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView settingsIcon = findViewById(R.id.settingsIcon);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
        ImageView editIcon = findViewById(R.id.editIcon);
        Button viewProfileButton = findViewById(R.id.viewProfileButton);
        ViewUtils.addPressEffect(viewProfileButton);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, ViewProfile.class);
                startActivity(intent);
            }
        });
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Setting.class);
                startActivity(intent);
            }
        });
        Button addWalletButton = findViewById(R.id.addWalletButton);
        ViewUtils.addPressEffect(addWalletButton);
        addWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, ConnectWallet.class);
                startActivity(intent);
            }
        });
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, EditAccount.class);
                startActivity(intent);
            }
        });
    }
}