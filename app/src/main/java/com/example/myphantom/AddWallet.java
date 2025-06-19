package com.example.myphantom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AddWallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        TextView createseedphrasewallett = findViewById(R.id.createseedphrasewallettv);
        ViewUtils.addPressEffect(createseedphrasewallett);
        createseedphrasewallett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddWallet.this, ProtectWallet.class);
                startActivity(intent);
            }
        });
        Button continueWithEmail = findViewById(R.id.continueWithEmail);
        ViewUtils.addPressEffect(continueWithEmail);

        continueWithEmail.setOnClickListener(v -> {
            // Inflate bottom sheet layout
            View view = LayoutInflater.from(this).inflate(R.layout.email_options_bottom_sheet, null);
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            dialog.setContentView(view);
            dialog.show();

            // Use view.findViewById to access BottomSheet views
            LinearLayout btnGoogle = view.findViewById(R.id.btnGoogle);
            LinearLayout btnApple = view.findViewById(R.id.btnApple);
            ViewUtils.addPressEffect(btnApple);
            ViewUtils.addPressEffect(btnGoogle);
            // Set click listeners properly
            btnGoogle.setOnClickListener(btn -> {
                // Example Google intent
                Uri uri = Uri.parse("https://mrdoob.com/projects/google/chrome/collapse/"); // Replace with actual Google sign-in intent
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });

            btnApple.setOnClickListener(btn -> {
                // Example Apple intent
                Uri uri = Uri.parse("https://apple.com"); // Replace with actual Apple sign-in logic
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });
        });
    }
}
