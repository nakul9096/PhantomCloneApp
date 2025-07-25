package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private EditText searchEditText;
    private TextView cancelText;
    private ImageView backArrow;
    private RecyclerView tokenRecyclerView;
    private TokenAdapter tokenAdapter;
    private List<Token> tokenList;
    private List<Token> filteredTokenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize views
        searchEditText = findViewById(R.id.search_edit_text);
        cancelText = findViewById(R.id.cancel_text);
        backArrow = findViewById(R.id.back_arrow);
        tokenRecyclerView = findViewById(R.id.token_recycler_view);

        // Setup RecyclerView
        tokenRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tokenRecyclerView.setHasFixedSize(true);

        // Initialize static data
        initializeData();

        // Setup adapter
        tokenAdapter = new TokenAdapter(filteredTokenList);
        tokenRecyclerView.setAdapter(tokenAdapter);

        // Search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTokens(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle click on search bar to remove hint
        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchEditText.setHint("");
                cancelText.setVisibility(View.VISIBLE);
            } else if (searchEditText.getText().toString().isEmpty()) {
                searchEditText.setHint("Sites, tokens, URL");
                cancelText.setVisibility(View.GONE);
            }
        });

        // Handle back arrow click
        backArrow.setOnClickListener(v -> onBackPressed());

        // Handle cancel click
        cancelText.setOnClickListener(v -> {
            searchEditText.setText("");
            searchEditText.clearFocus();
            cancelText.setVisibility(View.GONE);
            filteredTokenList.clear();
            filteredTokenList.addAll(tokenList);
            tokenAdapter.notifyDataSetChanged();
        });
    }

    private void initializeData() {
        tokenList = new ArrayList<>();
        filteredTokenList = new ArrayList<>();

        // Static token data
        tokenList.add(new Token("AiOShi Apple Companion", "AiOShi", "$0.00080606", "+14,323.74%"));
        tokenList.add(new Token("DegeCoin", "Dege", "$0.0274", "+214.49%"));
        tokenList.add(new Token("Pump", "PUMP", "$0.00530083", "-16.1%"));
        tokenList.add(new Token("Anime Neural Intelligence", "ANI", "$0.00019997", "+2,094.59%"));
        tokenList.add(new Token("rudi", "rudi", "$0.0101", "-36.59%"));
        tokenList.add(new Token("USEFUL COIN", "USEFUL", "$0.00318516", "-39.61%"));

        filteredTokenList.addAll(tokenList);
    }

    private void filterTokens(String query) {
        filteredTokenList.clear();
        if (query.isEmpty()) {
            filteredTokenList.addAll(tokenList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Token token : tokenList) {
                if (token.getName().toLowerCase().contains(lowerCaseQuery) ||
                        token.getSymbol().toLowerCase().contains(lowerCaseQuery)) {
                    filteredTokenList.add(token);
                }
            }
        }
        tokenAdapter.notifyDataSetChanged();
    }

    public static class Token {
        private String name;
        private String symbol;
        private String price;
        private String change;

        public Token(String name, String symbol, String price, String change) {
            this.name = name;
            this.symbol = symbol;
            this.price = price;
            this.change = change;
        }

        public String getName() { return name; }
        public String getSymbol() { return symbol; }
        public String getPrice() { return price; }
        public String getChange() { return change; }
    }
    public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenViewHolder> {

        private List<Search.Token> tokens;

        public TokenAdapter(List<Search.Token> tokens) {
            this.tokens = tokens;
        }

        @NonNull
        @Override
        public TokenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_token, parent, false);
            return new TokenViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TokenViewHolder holder, int position) {
            Search.Token token = tokens.get(position);
            holder.nameTextView.setText(token.getName());
            holder.detailsTextView.setText(token.getSymbol() + " • " + token.getPrice() + " • " + token.getChange());
            // Set color for change based on value
            if (token.getChange().contains("-")) {
                holder.detailsTextView.append(" "); // Add space for alignment
                holder.detailsTextView.setTextColor(Color.parseColor("#FF5722"));
            } else {
                holder.detailsTextView.append(" "); // Add space for alignment
                holder.detailsTextView.setTextColor(Color.parseColor("#7ED957"));
            }
        }

        @Override
        public int getItemCount() {
            return tokens.size();
        }

        public class TokenViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView, detailsTextView;

            public TokenViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.token_name);
                detailsTextView = itemView.findViewById(R.id.token_details);
            }
        }
    }
}