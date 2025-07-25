package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowRecoveryPhrase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recovery_phrase);
        Button continue_button_recovery_phrase = findViewById(R.id.continue_button_recovery_phrase);
        ViewUtils.addPressEffect(continue_button_recovery_phrase);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#121212"));
    }
}