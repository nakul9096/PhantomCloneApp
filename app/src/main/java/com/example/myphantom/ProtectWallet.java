package com.example.myphantom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executor;
public class ProtectWallet extends AppCompatActivity {
    private Switch deviceAuthSwitch;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_wallet);
        deviceAuthSwitch = findViewById(R.id.deviceAuthSwitch);
        Executor executor = ContextCompat.getMainExecutor(this);
        Button nextButon = findViewById(R.id.nextButton);
        ViewUtils.addPressEffect(nextButon);

        biometricPrompt = new BiometricPrompt(ProtectWallet.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication successful", Toast.LENGTH_SHORT).show();
                deviceAuthSwitch.setChecked(true); // Keep it ON
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                deviceAuthSwitch.setChecked(false); // Revert switch
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Error: " + errString, Toast.LENGTH_SHORT).show();
                deviceAuthSwitch.setChecked(false); // Revert switch
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Use your fingerprint or face to continue")
                .setNegativeButtonText("Cancel")
                .build();

        deviceAuthSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                BiometricManager biometricManager = BiometricManager.from(this);
                if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
                    biometricPrompt.authenticate(promptInfo);
                } else {
                    Toast.makeText(this, "Biometric not available/enrolled", Toast.LENGTH_SHORT).show();
                    deviceAuthSwitch.setChecked(false);
                }
            }
        });
        nextButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProtectWallet.this, RecoveryPhrase.class);
                startActivity(intent);
            }
        });
    }
}