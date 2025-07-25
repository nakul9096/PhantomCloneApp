package com.example.myphantom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageTokens extends AppCompatActivity {

    private EditText searchEditText;
    private TextView cancelText;
    private ImageView closeButton;
    private RecyclerView tokenRecyclerView;
    private TokenAdapter tokenAdapter;
    private List<Token> tokenList;
    private List<Token> filteredTokenList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tokens);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);

        // Initialize views
        searchEditText = findViewById(R.id.search_edit_text_manage);
        cancelText = findViewById(R.id.cancel_text);
        closeButton = findViewById(R.id.close_button_manage_tokens);
        tokenRecyclerView = findViewById(R.id.token_recycler_view);

        // Check for null views
        if (searchEditText == null || cancelText == null || closeButton == null || tokenRecyclerView == null) {
            throw new IllegalStateException("Required views not found in layout");
        }

        // Setup RecyclerView
        tokenRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tokenRecyclerView.setHasFixedSize(true);

        // Initialize static data
        initializeData();

        // Setup adapter
        tokenAdapter = new TokenAdapter(this, filteredTokenList);
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

        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            cancelText.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            searchEditText.setHint(hasFocus ? "" : "Search...");
        });

        closeButton.setOnClickListener(v -> finish());
        cancelText.setOnClickListener(v -> {
            searchEditText.setText("");
            searchEditText.clearFocus();
            filterTokens("");
        });
    }

    private void initializeData() {
        tokenList = new ArrayList<>();
        filteredTokenList = new ArrayList<>();
        tokenList.add(new Token("Bitcoin", "BTC", "0 BTC", sharedPreferences.getBoolean("Bitcoin", true), "ic_bitcoin"));
        tokenList.add(new Token("Ethereum", "ETH", "0 ETH", sharedPreferences.getBoolean("Ethereum", true), "ic_eth"));
        tokenList.add(new Token("Ethereum", "ETH", "0 ETH", sharedPreferences.getBoolean("Ethereum", true), "ic_eth"));
        tokenList.add(new Token("Polygon", "POL", "0 POL", sharedPreferences.getBoolean("Polygon", true), "ic_polygon"));
        tokenList.add(new Token("Solana", "SOL", "0 SOL", sharedPreferences.getBoolean("Solana", true), "ic_solana"));
        tokenList.add(new Token("Sui", "SUI", "0 SUI", sharedPreferences.getBoolean("Sui", true), "ic_sui"));
        filteredTokenList.addAll(tokenList);
    }

    private void filterTokens(String query) {
        List<Token> newFilteredList = new ArrayList<>();
        if (query.isEmpty()) {
            newFilteredList.addAll(tokenList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Token token : tokenList) {
                if (token.getName().toLowerCase().contains(lowerCaseQuery) ||
                        token.getSymbol().toLowerCase().contains(lowerCaseQuery)) {
                    newFilteredList.add(token);
                }
            }
        }

        // Use DiffUtil for efficient updates
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TokenDiffCallback(filteredTokenList, newFilteredList));
        filteredTokenList.clear();
        filteredTokenList.addAll(newFilteredList);
        diffResult.dispatchUpdatesTo(tokenAdapter);
    }

    public static class Token {
        private final String name;
        private final String symbol;
        private final String balance;
        private boolean isEnabled;
        private final String iconName; // New field for icon name

        public Token(String name, String symbol, String balance, boolean isEnabled, String iconName) {
            this.name = name;
            this.symbol = symbol;
            this.balance = balance;
            this.isEnabled = isEnabled;
            this.iconName = iconName;
        }

        public String getName() { return name; }
        public String getSymbol() { return symbol; }
        public String getBalance() { return balance; }
        public boolean isEnabled() { return isEnabled; }
        public void setEnabled(boolean enabled) { this.isEnabled = enabled; }
        public String getIconName() { return iconName; }
    }

    public static class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenViewHolder> {

        private final List<Token> tokens;
        private final SharedPreferences sharedPreferences;

        public TokenAdapter(Context context, List<Token> tokens) {
            this.tokens = tokens;
            this.sharedPreferences = context.getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public TokenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_token1, parent, false);
            return new TokenViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TokenViewHolder holder, int position) {
            Token token = tokens.get(position);
            holder.nameTextView.setText(token.getName());
            holder.balanceTextView.setText(token.getBalance());
            holder.tokenSwitch.setChecked(token.isEnabled());

            // Set token icon using the iconName field
            int resId = holder.itemView.getContext().getResources().getIdentifier(
                    token.getIconName(), "drawable", holder.itemView.getContext().getPackageName());
            holder.tokenIcon.setImageResource(resId != 0 ? resId : R.drawable.ic_no_nfts);

            // Handle switch toggle and save state
            holder.tokenSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                token.setEnabled(isChecked);
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putBoolean(token.getName(), isChecked).apply();
                }
            });
        }

        @Override
        public int getItemCount() {
            return tokens.size();
        }

        public static class TokenViewHolder extends RecyclerView.ViewHolder {
            ImageView tokenIcon;
            TextView nameTextView, balanceTextView;
            SwitchCompat tokenSwitch;

            public TokenViewHolder(@NonNull View itemView) {
                super(itemView);
                tokenIcon = itemView.findViewById(R.id.token_icon);
                nameTextView = itemView.findViewById(R.id.token_name);
                balanceTextView = itemView.findViewById(R.id.token_balance);
                tokenSwitch = itemView.findViewById(R.id.token_switch);
            }
        }
    }

    private static class TokenDiffCallback extends DiffUtil.Callback {
        private final List<Token> oldList;
        private final List<Token> newList;

        public TokenDiffCallback(List<Token> oldList, List<Token> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Token oldToken = oldList.get(oldItemPosition);
            Token newToken = newList.get(newItemPosition);
            return oldToken.getName().equals(newToken.getName()) &&
                    oldToken.getSymbol().equals(newToken.getSymbol()) &&
                    oldToken.getBalance().equals(newToken.getBalance()) &&
                    oldToken.isEnabled() == newToken.isEnabled() &&
                    oldToken.getIconName().equals(newToken.getIconName());
        }
    }
}