package com.example.myphantom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecoveryPhrase extends AppCompatActivity {

    private String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_phrase);
        TextView nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Written the recovery phase down?")
                    .setMessage("Without the recovery phrase you will not be able to access your key or any assets associated with it.")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(RecoveryPhrase.this, CreateUsername.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));

        List<String[]> wordSets = new ArrayList<>();
        wordSets.add(new String[]{
                "amateur", "slice", "margin", "rose",
                "bicycle", "bacon", "reveal", "rally",
                "visa", "enroll", "elegant", "foam"
        });
        wordSets.add(new String[]{
                "apple", "banana", "cherry", "date",
                "elder", "fig", "grape", "honey",
                "kiwi", "lemon", "mango", "nut"
        });
        wordSets.add(new String[]{
                "zebra", "yacht", "xray", "wagon",
                "vivid", "union", "tiger", "snake",
                "robot", "queen", "pilot", "ocean"
        });
        wordSets.add(new String[]{
                "bright", "calm", "deep", "east",
                "fancy", "giant", "happy", "idle",
                "jolly", "keen", "lucky", "magic"
        });
        wordSets.add(new String[]{
                "north", "open", "proud", "quick",
                "round", "soft", "truth", "unity",
                "vivid", "warm", "yield", "zero"
        });

        Random random = new Random();
        words = wordSets.get(random.nextInt(wordSets.size()));

        ImageView backBtn = findViewById(R.id.backImageView);
        backBtn.setOnClickListener(v -> onBackPressed());

        LinearLayout copy = findViewById(R.id.copytoclipboard);
        ViewUtils.addPressEffect(copy);
        Button btnSaved = findViewById(R.id.btnSaved);
        ViewUtils.addPressEffect(btnSaved);

        GridLayout phraseGrid = findViewById(R.id.phraseGrid);
        for (int i = 0; i < words.length; i++) {
            final String word = words[i];
            LinearLayout chip = new LinearLayout(this);
            chip.setOrientation(LinearLayout.HORIZONTAL);
            ViewUtils.addPressEffect(chip);
            chip.setBackgroundResource(R.drawable.recovery_chip);
            chip.setPadding(24, 24, 24, 24);
            chip.setGravity(android.view.Gravity.CENTER_VERTICAL);

            TextView numberView = new TextView(this);
            numberView.setText(String.valueOf(i + 1));
            numberView.setTextColor(Color.WHITE);
            numberView.setTextSize(16f);

            View divider = new View(this);
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.MATCH_PARENT);
            dividerParams.setMargins(16, 0, 16, 0);
            divider.setLayoutParams(dividerParams);
            divider.setBackgroundColor(Color.WHITE);

            TextView wordView = new TextView(this);
            wordView.setText(word);
            wordView.setTextColor(Color.WHITE);
            wordView.setTextSize(16f);

            chip.addView(numberView);
            chip.addView(divider);
            chip.addView(wordView);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(12, 12, 12, 12);
            chip.setLayoutParams(params);

            chip.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Word", word);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Copied: " + word, Toast.LENGTH_SHORT).show();
            });

            phraseGrid.addView(chip);
        }

        copy.setOnClickListener(v -> {
            StringBuilder fullPhrase = new StringBuilder();
            for (String word : words) {
                fullPhrase.append(word).append(" ");
            }
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Recovery Phrase", fullPhrase.toString().trim());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Full phrase copied to clipboard!", Toast.LENGTH_SHORT).show();
        });

        btnSaved.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Written the recovery phase down?")
                    .setMessage("Without the recovery phrase you will not be able to access your key or any assets associated with it.")
                    .setCancelable(true)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(RecoveryPhrase.this, CreateUsername.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });
    }
}