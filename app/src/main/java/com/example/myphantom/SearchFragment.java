package com.example.myphantom;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    // Filter Buttons
    private Button btnFilterTokens;
    private Button btnFilterLists;
    private Button btnFilterPeople;
    private Button btnFilterDapps;

    // Content Sections
    private LinearLayout sectionTokens;
    private LinearLayout sectionLists;
    private LinearLayout sectionPeople;
    private LinearLayout sectionDapps;

    // Static Data (for demonstration)
    private List<TokenItem> trendingTokens;
    private List<ListItem> topLists;
    private List<PersonItem> topPeople;
    private List<DAppItem> popularDapps;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Find common UI elements
        LinearLayout searchLayout = view.findViewById(R.id.searchLayout);
        TextView account_initials = view.findViewById(R.id.account_initials);
        ImageView addbtn = view.findViewById(R.id.addbtn);

        // Find filter buttons
        btnFilterTokens = view.findViewById(R.id.btn_filter_tokens);
        btnFilterLists = view.findViewById(R.id.btn_filter_lists);
        btnFilterPeople = view.findViewById(R.id.btn_filter_people);
        btnFilterDapps = view.findViewById(R.id.btn_filter_dapps);

        // Find content sections
        sectionTokens = view.findViewById(R.id.section_tokens);
        sectionLists = view.findViewById(R.id.section_lists);
        sectionPeople = view.findViewById(R.id.section_people);
        sectionDapps = view.findViewById(R.id.section_dapps);

        // Apply press effects
        ViewUtils.addPressEffect(addbtn);
        ViewUtils.addPressEffect(account_initials);

        // Set up click listeners for header elements
        addbtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Search.class); // Assuming Search.class is for adding/managing items
            startActivity(intent);
        });

        account_initials.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Profile.class);
            startActivity(intent);
        });

        searchLayout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Search.class); // Assuming Search.class is the actual search screen
            startActivity(intent);
        });

        // Initialize static data (for demonstration purposes)
        setupStaticData();

        // Set up click listeners for filter buttons
        btnFilterTokens.setOnClickListener(v -> selectFilter("tokens"));
        btnFilterLists.setOnClickListener(v -> selectFilter("lists"));
        btnFilterPeople.setOnClickListener(v -> selectFilter("people"));
        btnFilterDapps.setOnClickListener(v -> selectFilter("dapps"));

        // Set initial selected filter
        selectFilter("tokens");

        return view;
    }

    //region Static Data Setup (for demonstration)
    private void setupStaticData() {
        // Trending Tokens
        trendingTokens = new ArrayList<>();
        trendingTokens.add(new TokenItem("SuperGrok", "$1.1M MC", "$0.00106262", "+7,955.30%", R.drawable.ic_supergrok, "1"));
        trendingTokens.add(new TokenItem("BARRON", "$863K MC", "$0.00086332", "+817.51%", R.drawable.ic_bitcoin, "2"));
        trendingTokens.add(new TokenItem("memecoin", "$5.9M MC", "$0.00582691", "+137,468.98%", R.drawable.ic_bitcoin, "3"));
        // You'd typically use a RecyclerView.Adapter here to populate these dynamically
        // For static includes, you'd manually set text if you had unique IDs for each TextView within the includes.
        // For this example, we're just setting up the data, not dynamically binding it to the include layouts.

        // Top Lists
        topLists = new ArrayList<>();
        topLists.add(new ListItem("Top Gainers", "50 tokens", R.drawable.ic_profile));
        topLists.add(new ListItem("Top Losers", "50 tokens", R.drawable.ic_profile));
        topLists.add(new ListItem("Meme", "100 tokens", R.drawable.ic_profile));

        // Top People
        topPeople = new ArrayList<>();
        topPeople.add(new PersonItem("Vitalik Buterin", "@vitalik.eth", R.drawable.ic_profile));
        topPeople.add(new PersonItem("SBF", "@sbf_ftx", R.drawable.ic_profile));
        topPeople.add(new PersonItem("CZ Binance", "@cz_binance", R.drawable.ic_profile));

        // Popular DApps
        popularDapps = new ArrayList<>();
        popularDapps.add(new DAppItem("Uniswap", "DeFi", R.drawable.ic_no_nfts));
        popularDapps.add(new DAppItem("OpenSea", "NFT Marketplace", R.drawable.ic_no_nfts));
        popularDapps.add(new DAppItem("Aave", "Lending", R.drawable.ic_no_nfts));
    }
    //endregion

    //region Filter Logic
    private void selectFilter(String filterType) {
        // Reset all button backgrounds to unselected state
        btnFilterTokens.setBackgroundResource(R.drawable.rounded_button_options);
        btnFilterLists.setBackgroundResource(R.drawable.rounded_button_options);
        btnFilterPeople.setBackgroundResource(R.drawable.rounded_button_options);
        btnFilterDapps.setBackgroundResource(R.drawable.rounded_button_options);

        // Hide all content sections
        sectionTokens.setVisibility(View.GONE);
        sectionLists.setVisibility(View.GONE);
        sectionPeople.setVisibility(View.GONE);
        sectionDapps.setVisibility(View.GONE);

        // Set selected button background and show relevant section
        switch (filterType) {
            case "tokens":
                btnFilterTokens.setBackgroundResource(R.drawable.rounded_button_gradient_selected);
                sectionTokens.setVisibility(View.VISIBLE);
                // Optionally populate section_tokens with trendingTokens data here
                break;
            case "lists":
                btnFilterLists.setBackgroundResource(R.drawable.rounded_button_gradient_selected);
                sectionLists.setVisibility(View.VISIBLE);
                // Optionally populate section_lists with topLists data here
                break;
            case "people":
                btnFilterPeople.setBackgroundResource(R.drawable.rounded_button_gradient_selected);
                sectionPeople.setVisibility(View.VISIBLE);
                // Optionally populate section_people with topPeople data here
                break;
            case "dapps":
                btnFilterDapps.setBackgroundResource(R.drawable.rounded_button_gradient_selected);
                sectionDapps.setVisibility(View.VISIBLE);
                // Optionally populate section_dapps with popularDapps data here
                break;
        }
    }
    //endregion

    //region Data Models (Inner Classes)
    public static class TokenItem {
        String name;
        String marketCap;
        String price;
        String change;
        int iconResId;
        String rank;

        public TokenItem(String name, String marketCap, String price, String change, int iconResId, String rank) {
            this.name = name;
            this.marketCap = marketCap;
            this.price = price;
            this.change = change;
            this.iconResId = iconResId;
            this.rank = rank;
        }
        // Add getters if you plan to use this data elsewhere
    }

    public static class ListItem {
        String name;
        String count;
        int iconResId;

        public ListItem(String name, String count, int iconResId) {
            this.name = name;
            this.count = count;
            this.iconResId = iconResId;
        }
    }

    public static class PersonItem {
        String name;
        String handle;
        int avatarResId;

        public PersonItem(String name, String handle, int avatarResId) {
            this.name = name;
            this.handle = handle;
            this.avatarResId = avatarResId;
        }
    }

    public static class DAppItem {
        String name;
        String category;
        int iconResId;

        public DAppItem(String name, String category, int iconResId) {
            this.name = name;
            this.category = category;
            this.iconResId = iconResId;
        }
    }

}