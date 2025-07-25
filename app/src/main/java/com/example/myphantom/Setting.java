package com.example.myphantom;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {
    private EditText searchEditText;
    private ImageView searchIcon;
    private ImageView cancelButton;
    private List<LinearLayout> settingItems;
    private List<String> settingTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        searchEditText = findViewById(R.id.search_edit_text);
        searchIcon = findViewById(R.id.search_icon);
        cancelButton = findViewById(R.id.cancel_button);
        ImageView backButton = findViewById(R.id.back_button_settings);

        settingItems = new ArrayList<>();
        settingTitles = new ArrayList<>();

        initializeSettingItems();

        cancelButton.setVisibility(View.GONE);

        backButton.setOnClickListener(v -> finish());

        searchEditText.setOnClickListener(v -> {
            searchEditText.setCursorVisible(true);
            cancelButton.setVisibility(View.VISIBLE);
            searchIcon.setVisibility(View.GONE);
        });

        cancelButton.setOnClickListener(v -> {
            searchEditText.setText("");
            searchEditText.setCursorVisible(false);
            cancelButton.setVisibility(View.GONE);
            searchIcon.setVisibility(View.VISIBLE);
            showAllSettings();
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterSettings(s.toString());
                if (s.length() > 0) {
                    cancelButton.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.GONE);
                } else {
                    cancelButton.setVisibility(View.GONE);
                    searchIcon.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initializeSettingItems() {
        int[] settingLayoutIds = {
                R.id.manage_accounts_layout,
                R.id.preferences_layout,
                R.id.security_privacy_layout,
                R.id.active_networks_layout,
                R.id.address_book_layout,
                R.id.connected_apps_layout,
                R.id.developer_settings_layout,
                R.id.help_support_layout,
                R.id.invite_friends_layout,
                R.id.about_phantom_layout
        };

        String[] titles = {
                "Manage Accounts",
                "Preferences",
                "Security & Privacy",
                "Active Networks",
                "Address Book",
                "Connected Apps",
                "Developer Settings",
                "Help & Support",
                "Invite your friends",
                "About Phantom"
        };

        for (int i = 0; i < settingLayoutIds.length; i++) {
            LinearLayout layout = findViewById(settingLayoutIds[i]);
            if (layout != null) {
                settingItems.add(layout);
                settingTitles.add(titles[i]);
            }
        }
    }

    private void filterSettings(String query) {
        query = query.toLowerCase().trim();

        for (int i = 0; i < settingItems.size(); i++) {
            LinearLayout layout = settingItems.get(i);
            String title = settingTitles.get(i).toLowerCase();

            if (query.isEmpty() || title.contains(query)) {
                layout.setVisibility(View.VISIBLE);
            } else {
                layout.setVisibility(View.GONE);
            }
        }
    }

    private void showAllSettings() {
        for (LinearLayout layout : settingItems) {
            layout.setVisibility(View.VISIBLE);
        }
    }
}