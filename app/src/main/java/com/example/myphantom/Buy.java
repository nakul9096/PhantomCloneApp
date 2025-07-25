package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Buy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
        ImageView back_button_select_token = findViewById(R.id.back_button_select_token);
        back_button_select_token.setOnClickListener(v -> onBackPressed());
    }
}