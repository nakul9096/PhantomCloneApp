package com.example.myphantom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private List<SlideItem> items;

    public SlideAdapter(List<SlideItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        SlideItem item = items.get(position);
        holder.lottieView.setAnimation(item.lottieRes);
        holder.title.setText(item.title);
        holder.desc.setText(item.desc);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView lottieView;
        TextView title, desc;

        SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            lottieView = itemView.findViewById(R.id.lottieView);
            title = itemView.findViewById(R.id.titleText);
            desc = itemView.findViewById(R.id.descText);
        }
    }
}
