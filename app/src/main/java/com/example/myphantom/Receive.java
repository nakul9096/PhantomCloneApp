package com.example.myphantom;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Receive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        LinearLayout solanacard = findViewById(R.id.solanacard);
        LinearLayout ethereumcard = findViewById(R.id.ethereumcard);
        LinearLayout basecard = findViewById(R.id.basecard);
        LinearLayout suicard = findViewById(R.id.suicard);
        LinearLayout polygoncard = findViewById(R.id.polygoncard);
        LinearLayout bitcoincard = findViewById(R.id.bitcoincard);
        ImageView solanaqr = findViewById(R.id.solanaqr);
        ImageView ethereumqr = findViewById(R.id.ethereumqr);
        ImageView baseqr = findViewById(R.id.baseqr);
        ImageView suiqr = findViewById(R.id.suiqr);
        ImageView polygonqr = findViewById(R.id.polygonqr);
        ImageView bitcoinqr = findViewById(R.id.bitcoinqr);
        ImageView solanacopy = findViewById(R.id.solanacopy);
        ImageView ethereumcopy = findViewById(R.id.ethereumcopy);
        ImageView basecopy = findViewById(R.id.basecopy);
        ImageView suicopy = findViewById(R.id.suicopy);
        ImageView polygoncopy = findViewById(R.id.polygoncopy);
        ImageView bitcoincopy = findViewById(R.id.bitcoincopy);
        ViewUtils.addPressEffect(solanacard);
        ViewUtils.addPressEffect(ethereumcard);
        ViewUtils.addPressEffect(basecard);
        ViewUtils.addPressEffect(suicard);
        ViewUtils.addPressEffect(polygoncard);
        ViewUtils.addPressEffect(bitcoincard);
        ViewUtils.addPressEffect(solanaqr);
        ViewUtils.addPressEffect(ethereumqr);
        ViewUtils.addPressEffect(baseqr);
        ViewUtils.addPressEffect(suiqr);
        ViewUtils.addPressEffect(polygonqr);
        ViewUtils.addPressEffect(bitcoinqr);
        ViewUtils.addPressEffect(solanacopy);
        ViewUtils.addPressEffect(ethereumcopy);
        ViewUtils.addPressEffect(basecopy);
        ViewUtils.addPressEffect(suicopy);
        ViewUtils.addPressEffect(polygoncopy);
        ViewUtils.addPressEffect(bitcoincopy);
        baseqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Receive.this, GenerateQR.class);
                startActivity(intent);
            }
        });
        ethereumqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Receive.this, GenerateQR.class);
                startActivity(intent);
            }
        });
        bitcoinqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Receive.this, GenerateQR.class);
                startActivity(intent);
            }
        });
        polygonqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Receive.this, GenerateQR.class);
                startActivity(intent);
            }
        });
        suiqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Receive.this, GenerateQR.class);
                startActivity(intent);
            }
        });
        solanaqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Receive.this, GenerateQR.class);
                startActivity(intent);
            }
        });
    }
}