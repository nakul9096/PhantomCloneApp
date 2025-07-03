package com.example.myphantom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener( v-> onBackPressed());
        Button create_button = findViewById(R.id.create_button);
        ViewUtils.addPressEffect(create_button);
    }
}