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

public class ShowPrivateKey extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_private_key);
        Button continue_button_private_key = findViewById(R.id.continue_button_private_key);
        ViewUtils.addPressEffect(continue_button_private_key);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
    }
}