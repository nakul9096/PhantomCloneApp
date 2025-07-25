package com.example.myphantom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private TextView noActivityText;
    private Button refreshButton;
    private ShimmerFrameLayout shimmerLayout;
    private RecyclerView activityRecyclerView;
    private ActivityAdapter activityAdapter;
    private List<ActivityItem> activityList;
    private Handler handler = new Handler(Looper.getMainLooper());

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        noActivityText = view.findViewById(R.id.no_activity_text);
        refreshButton = view.findViewById(R.id.refreshButton);
        shimmerLayout = view.findViewById(R.id.shimmer_layout);
        activityRecyclerView = view.findViewById(R.id.activity_recycler_view);

        activityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activityRecyclerView.setHasFixedSize(true);

        ViewUtils.addPressEffect(refreshButton);

        refreshButton.setOnClickListener(v -> {
            // When refresh is clicked, hide all content, show shimmer
            noActivityText.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);
            activityRecyclerView.setVisibility(View.GONE); // Make sure RecyclerView is hidden
            shimmerLayout.setVisibility(View.VISIBLE);
            shimmerLayout.startShimmer(); // Start shimmer animation
            simulateLoading();
        });

        // Initial state: show shimmer when fragment is created
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmer();
        simulateLoading(); // Simulate initial load

        return view;
    }

    private void simulateLoading() {
        // Clear previous data if any, and prepare for new data or placeholders
        activityList = new ArrayList<>();

        // No need to add dummy items to activityList if shimmer is showing.
        // The shimmer layout handles its own placeholders.
        // If you were to show an empty RecyclerView first, then you would populate it.

        if (activityAdapter == null) {
            activityAdapter = new ActivityAdapter(activityList, R.layout.item_activity); // Use your actual item layout
            activityRecyclerView.setAdapter(activityAdapter);
        } else {
            activityAdapter.updateItems(new ArrayList<>()); // Clear current items for adapter
            activityAdapter.notifyDataSetChanged();
        }

        final WeakReference<HistoryFragment> weakThis = new WeakReference<>(this);
        handler.postDelayed(() -> {
            HistoryFragment fragment = weakThis.get();
            if (fragment != null) {
                // Stop and hide shimmer
                fragment.shimmerLayout.stopShimmer();
                fragment.shimmerLayout.setVisibility(View.GONE);

                // Simulate data fetch
                fragment.activityList.clear();

                if (!fragment.activityList.isEmpty()) {
                    // If data exists, show RecyclerView
                    if (fragment.activityAdapter != null) {
                        fragment.activityAdapter.updateItems(fragment.activityList);
                        fragment.activityAdapter.notifyDataSetChanged();
                    }
                    fragment.activityRecyclerView.setVisibility(View.VISIBLE);
                    // Hide "No activity" and "Refresh" if data is present
                    fragment.noActivityText.setVisibility(View.GONE);
                    fragment.refreshButton.setVisibility(View.GONE);
                } else {
                    // If no data, show "No activity" and "Refresh"
                    fragment.noActivityText.setVisibility(View.VISIBLE);
                    fragment.refreshButton.setVisibility(View.VISIBLE);
                    fragment.activityRecyclerView.setVisibility(View.GONE); // Ensure RecyclerView is hidden
                }
            }
        }, 2000); // Simulate 2 seconds loading time
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        if (shimmerLayout != null) {
            shimmerLayout.stopShimmer(); // Stop shimmer when view is destroyed
        }
    }

    // Your existing ActivityItem and ActivityAdapter classes remain the same
    public static class ActivityItem {
        private final String title;
        private final String subtitle;
        private final String timestamp;

        public ActivityItem(String title, String subtitle, String timestamp) {
            this.title = title;
            this.subtitle = subtitle;
            this.timestamp = timestamp;
        }

        public String getTitle() { return title; }
        public String getSubtitle() { return subtitle; }
        public String getTimestamp() { return timestamp; }
    }

    public static class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

        private List<ActivityItem> items;
        private final int layoutId;

        public ActivityAdapter(List<ActivityItem> items, int layoutId) {
            this.items = new ArrayList<>(items);
            this.layoutId = layoutId;
        }

        public void updateItems(List<ActivityItem> newItems) {
            this.items.clear();
            this.items.addAll(newItems);
            notifyDataSetChanged(); // Important: Notify adapter of data change
        }

        @NonNull
        @Override
        public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new ActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
            ActivityItem item = items.get(position);
            holder.titleTextView.setText(item.getTitle());
            holder.subtitleTextView.setText(item.getSubtitle());
            holder.timestampTextView.setText(item.getTimestamp());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public static class ActivityViewHolder extends RecyclerView.ViewHolder {
            TextView titleTextView, subtitleTextView, timestampTextView;

            public ActivityViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.activity_title);
                subtitleTextView = itemView.findViewById(R.id.activity_subtitle);
                timestampTextView = itemView.findViewById(R.id.activity_timestamp);
            }
        }
    }
}