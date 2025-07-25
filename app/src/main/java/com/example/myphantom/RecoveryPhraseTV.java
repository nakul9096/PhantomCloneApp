package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecoveryPhraseTV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_phrase_tv);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
        ImageView backBtn = findViewById(R.id.backIV);
        backBtn.setOnClickListener(v -> onBackPressed());
        EditText editRecoveryPhrase = findViewById(R.id.editRecoveryPhrase);
        Button btnImportRecovery = findViewById(R.id.btnImportRecovery);
        btnImportRecovery.setEnabled(false);
        ViewUtils.addPressEffect(btnImportRecovery);
        editRecoveryPhrase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnImportRecovery.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}