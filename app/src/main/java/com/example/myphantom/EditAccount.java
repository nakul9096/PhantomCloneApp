package com.example.myphantom;

import android.os.Bundle;
import android.view.View;
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
        ViewUtils.addPressEffect(accName);
        ViewUtils.addPressEffect(accAddress);
        ViewUtils.addPressEffect(notification);
        ViewUtils.addPressEffect(showRecoveryPhrase);
        ViewUtils.addPressEffect(showPrivateKey);
    }
}