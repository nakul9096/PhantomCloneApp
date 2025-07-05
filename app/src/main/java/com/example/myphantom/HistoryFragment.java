package com.example.myphantom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HistoryFragment extends Fragment {

    public HistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        Button refreshButton = view.findViewById(R.id.refreshButton);
        ViewUtils.addPressEffect(refreshButton);
        return view;
    }

    // You can add this method to handle refresh functionality
    private void fetchRecentActivity() {
        // Implement your data fetching logic here
        // Update UI when data is received
    }
}