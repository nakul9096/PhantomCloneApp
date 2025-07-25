package com.example.myphantom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
        ImageView backIV = findViewById(R.id.backIV);
        EditText usernameInput = findViewById(R.id.usernameInput);
        ImageView clear_text = findViewById(R.id.clear_text);
        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameInput.setText("");
            }
        });
        backIV.setOnClickListener(v -> onBackPressed());
        Button continueButton = findViewById(R.id.continueButton);
        ViewUtils.addPressEffect(continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateUsername.this, GetStarted.class);
                startActivity(intent);
            }
        });
    }
}