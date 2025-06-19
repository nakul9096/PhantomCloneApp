package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecoveryPhrase extends AppCompatActivity {
    String[] words = {"amateur", "slice", "margin", "rose", "bicycle", "bacon", "reveal", "rally", "visa", "enroll", "elegant", "foam"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_phrase);
        LinearLayout copy = findViewById(R.id.copytoclipboard);
        ViewUtils.addPressEffect(copy);
        Button btnSaved = findViewById(R.id.btnSaved);
        ViewUtils.addPressEffect(btnSaved);
        GridLayout phraseGrid = findViewById(R.id.phraseGrid);

        for (int i = 0; i < words.length; i++) {
            LinearLayout chip = new LinearLayout(this);
            chip.setOrientation(LinearLayout.HORIZONTAL);
            chip.setBackgroundResource(R.drawable.recovery_chip);
            chip.setPadding(24, 24, 24, 24);
            chip.setGravity(android.view.Gravity.CENTER_VERTICAL);

            // Number
            TextView numberView = new TextView(this);
            numberView.setText(String.valueOf(i + 1));
            numberView.setTextColor(Color.WHITE);
            numberView.setTextSize(16f);

            // Divider
            View divider = new View(this);
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.MATCH_PARENT);
            dividerParams.setMargins(16, 0, 16, 0);
            divider.setLayoutParams(dividerParams);
            divider.setBackgroundColor(Color.WHITE);

            // Word
            TextView wordView = new TextView(this);
            wordView.setText(words[i]);
            wordView.setTextColor(Color.WHITE);
            wordView.setTextSize(16f);

            // Add to chip
            chip.addView(numberView);
            chip.addView(divider);
            chip.addView(wordView);

            // Layout params for GridLayout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Equal width
            params.setMargins(12, 12, 12, 12);
            chip.setLayoutParams(params);

            phraseGrid.addView(chip);
        }


        btnSaved.setOnClickListener(v -> {
            // Proceed to next activity
            Toast.makeText(this, "Phrase saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
