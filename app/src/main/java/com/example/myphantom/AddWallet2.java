package com.example.myphantom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AddWallet2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet2);
        Button continueWithEmail = findViewById(R.id.continueWithEmail);
        Button otherOptions = findViewById(R.id.otherOptions);
        ViewUtils.addPressEffect(continueWithEmail);
        ViewUtils.addPressEffect(otherOptions);
        otherOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view2 = LayoutInflater.from(AddWallet2.this).inflate(R.layout.activity_import_options, null);
                BottomSheetDialog dialog = new BottomSheetDialog(AddWallet2.this);
                dialog.setContentView(view2);
                dialog.show();
                Button importseedbtn = view2.findViewById(R.id.importseedphrasebtn);
                Button importprivatebtn = view2.findViewById(R.id.btnimportprivatekey);
                Button connecthardbtn = view2.findViewById(R.id.btnconnecthardwarewallets);
                ViewUtils.addPressEffect(importseedbtn);
                ViewUtils.addPressEffect(importprivatebtn);
                ViewUtils.addPressEffect(connecthardbtn);
            }
        });
        continueWithEmail.setOnClickListener(v -> {
            // Inflate bottom sheet layout
            View view = LayoutInflater.from(this).inflate(R.layout.email_options_bottom_sheet, null);
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            dialog.setContentView(view);
            dialog.show();
            LinearLayout btnGoogle = view.findViewById(R.id.btnGoogle);
            LinearLayout btnApple = view.findViewById(R.id.btnApple);
            ViewUtils.addPressEffect(btnApple);
            ViewUtils.addPressEffect(btnGoogle);
            btnGoogle.setOnClickListener(btn -> {
                Uri uri = Uri.parse("https://mrdoob.com/projects/google/chrome/collapse/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });
            btnApple.setOnClickListener(btn -> {
                Uri uri = Uri.parse("https://apple.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });
        });
    }
}