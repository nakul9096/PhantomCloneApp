package com.example.myphantom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Receive extends AppCompatActivity {

    private EditText searchEditText;
    private ImageView closeButton;
    private TextView cancelText;
    private RecyclerView tokenRecyclerView;
    private TokenAdapter tokenAdapter;
    private List<Token> tokenList;
    private List<Token> filteredTokenList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        Window window = getWindow();
        window.setNavigationBarColor(Color.parseColor("#1C1C1C"));
        sharedPreferences = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);

        searchEditText = findViewById(R.id.search_edit_text_manage);
        closeButton = findViewById(R.id.close_button_manage_tokens);
        cancelText = findViewById(R.id.cancel_text);
        tokenRecyclerView = findViewById(R.id.token_recycler_view);

        if (searchEditText == null || closeButton == null || cancelText == null || tokenRecyclerView == null) {
            throw new IllegalStateException("Required views not found in layout");
        }

        tokenRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tokenRecyclerView.setHasFixedSize(true);

        initializeData();

        tokenAdapter = new TokenAdapter(this, filteredTokenList);
        tokenRecyclerView.setAdapter(tokenAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTokens(s.toString());
                cancelText.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            cancelText.setVisibility(hasFocus && searchEditText.getText().length() > 0 ? View.VISIBLE : View.GONE);
        });

        closeButton.setOnClickListener(v -> onBackPressed());

        cancelText.setOnClickListener(v -> {
            searchEditText.setText("");
            searchEditText.clearFocus();
            filterTokens("");
        });
    }

    private void initializeData() {
        tokenList = new ArrayList<>();
        filteredTokenList = new ArrayList<>();

        tokenList.add(new Token("Solana", "SOL", "2GTE...2BYF", "ic_solana"));
        tokenList.add(new Token("Ethereum", "ETH", "0x26b9...9f8A", "ic_eth"));
        tokenList.add(new Token("Base", "BASE", "0x26b9...9f8A", "ic_base"));
        tokenList.add(new Token("Sui", "SUI", "0x4827...c2b3", "ic_sui"));
        tokenList.add(new Token("Polygon", "POL", "0x26b9...9f8A", "ic_polygon"));
        tokenList.add(new Token("Bitcoin", "BTC", "bc1q...uygr", "ic_bitcoin"));

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

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TokenDiffCallback(filteredTokenList, newFilteredList));
        filteredTokenList.clear();
        filteredTokenList.addAll(newFilteredList);
        diffResult.dispatchUpdatesTo(tokenAdapter);
    }

    public static class Token {
        private final String name;
        private final String symbol;
        private final String address;
        private final String iconName;

        public Token(String name, String symbol, String address, String iconName) {
            this.name = name;
            this.symbol = symbol;
            this.address = address;
            this.iconName = iconName;
        }

        public String getName() { return name; }
        public String getSymbol() { return symbol; }
        public String getAddress() { return address; }
        public String getIconName() { return iconName; }
    }

    public static class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenViewHolder> {

        private final List<Token> tokens;
        private final Context context;

        public TokenAdapter(Context context, List<Token> tokens) {
            this.context = context;
            this.tokens = tokens;
        }

        @NonNull
        @Override
        public TokenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_token, parent, false);
            return new TokenViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TokenViewHolder holder, int position) {
            Token token = tokens.get(position);
            holder.nameTextView.setText(token.getName());
            holder.addressTextView.setText(token.getAddress());

            int resId = context.getResources().getIdentifier(token.getIconName(), "drawable", context.getPackageName());
            holder.tokenIcon.setImageResource(resId != 0 ? resId : R.drawable.ic_no_nfts);

            holder.qrCodeImageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GenerateQR.class);
                context.startActivity(intent);
            });
            holder.copyImageView.setOnClickListener(v -> {});
        }

        @Override
        public int getItemCount() {
            return tokens.size();
        }

        public static class TokenViewHolder extends RecyclerView.ViewHolder {
            ImageView tokenIcon, qrCodeImageView, copyImageView;
            TextView nameTextView, addressTextView;

            public TokenViewHolder(@NonNull View itemView) {
                super(itemView);
                tokenIcon = itemView.findViewById(R.id.token_icon);
                nameTextView = itemView.findViewById(R.id.token_name);
                addressTextView = itemView.findViewById(R.id.token_address);
                qrCodeImageView = itemView.findViewById(R.id.token_qr);
                copyImageView = itemView.findViewById(R.id.token_copy);
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
                    oldToken.getAddress().equals(newToken.getAddress()) &&
                    oldToken.getIconName().equals(newToken.getIconName());
        }
    }
}