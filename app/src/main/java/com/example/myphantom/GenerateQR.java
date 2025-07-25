package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GenerateQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> onBackPressed());
        Button shareButton = findViewById(R.id.shareButton);
        ViewUtils.addPressEffect(shareButton);
        LinearLayout addressDisplayLayout = findViewById(R.id.addressDisplayLayout);
        ViewUtils.addPressEffect(addressDisplayLayout);
        addressDisplayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GenerateQR.this, "Address copied successfully!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}