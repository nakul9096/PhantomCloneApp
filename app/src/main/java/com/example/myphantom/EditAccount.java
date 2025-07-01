package com.example.myphantom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        LinearLayout accName = findViewById(R.id.accName);
        LinearLayout accAddress = findViewById(R.id.accAdrress);
        LinearLayout notification = findViewById(R.id.notification);
        LinearLayout showRecoveryPhrase = findViewById(R.id.showRecoveryPhrase);
        LinearLayout showPrivateKey = findViewById(R.id.showPrivateKey);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
        ViewUtils.addPressEffect(accName);
        ViewUtils.addPressEffect(accAddress);
        ViewUtils.addPressEffect(notification);
        ViewUtils.addPressEffect(showRecoveryPhrase);
        ViewUtils.addPressEffect(showPrivateKey);
        ImageView editIcon = findViewById(R.id.editIcon);
        ViewUtils.addPressEffect(editIcon);
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAccount.this, EditProfile.class);
                startActivity(intent);
            }
        });
        showPrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAccount.this, ShowPrivateKey.class);
                startActivity(intent);
            }
        });
        showRecoveryPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAccount.this, ShowRecoveryPhrase.class);
                startActivity(intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAccount.this, Notifications.class);
                startActivity(intent);
            }
        });
        accAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAccount.this, Receive.class);
                startActivity(intent);
            }
        });
    }
}