package com.example.myphantom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageView dot1, dot2, dot3;
    private CheckBox checkbox;
    private Button createWalletButton, alreadyHaveWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        checkbox = findViewById(R.id.checkbox);
        createWalletButton = findViewById(R.id.createWalletButton);
        alreadyHaveWallet = findViewById(R.id.alreadyHaveWallet);
        ViewUtils.addPressEffect(createWalletButton);
        ViewUtils.addPressEffect(alreadyHaveWallet);
        createWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddWallet.class);
                startActivity(intent);
            }
        });
        alreadyHaveWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddWallet2.class);
                startActivity(intent);
            }
        });
        // Set up ViewPager adapter
        List<SlideItem> slides = new ArrayList<>();
        slides.add(new SlideItem(R.raw.anim, "Welcome to Phantom", "To get started, create a new wallet or import an existing one."));
        slides.add(new SlideItem(R.raw.anim2, "Controlled by you", "Your wallet is secured with biometrics access, scam detection and 24/7 support."));
        slides.add(new SlideItem(R.raw.anim3, "The best home for your NFTs", "Manage listings, burn spam, and stay updated \n with helpful push notifications."));

        viewPager.setAdapter(new SlideAdapter(slides));

        // Update dots on page change
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
            }
        });

        // Checkbox enable buttons
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            createWalletButton.setEnabled(isChecked);
            alreadyHaveWallet.setEnabled(isChecked);
        });
    }

    private void updateDots(int position) {
        dot1.setImageResource(position == 0 ? R.drawable.dot_active : R.drawable.dot_inactive);
        dot2.setImageResource(position == 1 ? R.drawable.dot_active : R.drawable.dot_inactive);
        dot3.setImageResource(position == 2 ? R.drawable.dot_active : R.drawable.dot_inactive);
    }

}
