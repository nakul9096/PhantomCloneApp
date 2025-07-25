package com.example.myphantom;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.CaptureActivity;

public class ScanQr extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(ScanQr.this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                    initiateQrScan();
                } else {
                    Toast.makeText(ScanQr.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<ScanOptions> qrScanLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(ScanQr.this, "Scan Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ScanQr.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
        Button grantCameraPermissions = findViewById(R.id.btn_grant_permissions);
        Button openSettings = findViewById(R.id.btn_open_settings);

        grantCameraPermissions.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(ScanQr.this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                initiateQrScan();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        openSettings.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });
    }

    private void initiateQrScan() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureActivity.class);
        qrScanLauncher.launch(options);
    }
}