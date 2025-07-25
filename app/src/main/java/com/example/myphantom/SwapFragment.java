package com.example.myphantom;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SwapFragment extends Fragment {

    private EditText youPayAmountEt;
    private TextView youPayTokenSymbolTv;
    private ImageView youPayTokenIconIv;
    private TextView youPayBalanceTv;
    private LinearLayout youPayTokenSelector;

    private TextView youReceiveAmountTv;
    private TextView youReceiveTokenSymbolTv;
    private ImageView youReceiveTokenIconIv;
    private TextView youReceiveBalanceTv;
    private LinearLayout youReceiveTokenSelector;

    private ImageView settingsIcon;
    private ImageView swapDirectionButton;
    private LinearLayout paymentDetailsContainer;
    private TextView slippageValueTv;
    private TextView priceImpactValueTv;

    private RecyclerView trendingTokensRecyclerView;
    private TrendingTokenAdapter trendingTokenAdapter;
    private List<TrendingToken> trendingTokensList;

    private Token currentPayToken;
    private Token currentReceiveToken;

    private BigDecimal solBalance = new BigDecimal("10.5");
    private BigDecimal usdcBalance = new BigDecimal("500.25");
    private BigDecimal ethBalance = new BigDecimal("0.5");
    private BigDecimal btcBalance = new BigDecimal("0.01");

    public SwapFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swap, container, false);

        youPayAmountEt = view.findViewById(R.id.you_pay_amount_et);
        youPayTokenSymbolTv = view.findViewById(R.id.you_pay_token_symbol);
        youPayTokenIconIv = view.findViewById(R.id.you_pay_token_icon);
        youPayBalanceTv = view.findViewById(R.id.you_pay_balance_tv);
        youPayTokenSelector = view.findViewById(R.id.you_pay_token_selector);

        youReceiveAmountTv = view.findViewById(R.id.you_receive_amount_tv);
        youReceiveTokenSymbolTv = view.findViewById(R.id.you_receive_token_symbol);
        youReceiveTokenIconIv = view.findViewById(R.id.you_receive_token_icon);
        youReceiveBalanceTv = view.findViewById(R.id.you_receive_balance_tv);
        youReceiveTokenSelector = view.findViewById(R.id.you_receive_token_selector);

        settingsIcon = view.findViewById(R.id.settings_icon);
        swapDirectionButton = view.findViewById(R.id.swap_direction_button);
        paymentDetailsContainer = view.findViewById(R.id.payment_details_container);
        slippageValueTv = view.findViewById(R.id.slippage_value_tv);
        priceImpactValueTv = view.findViewById(R.id.price_impact_value_tv);

        trendingTokensRecyclerView = view.findViewById(R.id.trending_tokens_recycler_view);

        currentPayToken = new Token("Solana", "SOL", R.drawable.ic_solana, solBalance, new BigDecimal("177.865"));
        currentReceiveToken = new Token("USDC", "USDC", R.drawable.ic_usdc, usdcBalance, new BigDecimal("1.0"));

        updatePayCardUI(currentPayToken);
        updateReceiveCardUI(currentReceiveToken);
        updatePaymentDetailsVisibility(false);

        initializeTrendingTokens();
        trendingTokensRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trendingTokenAdapter = new TrendingTokenAdapter(trendingTokensList);
        trendingTokensRecyclerView.setAdapter(trendingTokenAdapter);
        trendingTokensRecyclerView.setNestedScrollingEnabled(false);

        settingsIcon.setOnClickListener(v -> Toast.makeText(getContext(), "Settings Clicked", Toast.LENGTH_SHORT).show());

        swapDirectionButton.setOnClickListener(v -> swapTokens());

        youPayTokenSelector.setOnClickListener(v -> selectPayToken());
        youReceiveTokenSelector.setOnClickListener(v -> selectReceiveToken());

        youPayAmountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && !s.toString().equals("0")) {
                    calculateReceiveAmountAndDetails();
                    updatePaymentDetailsVisibility(true);
                } else {
                    youReceiveAmountTv.setText("0");
                    updatePaymentDetailsVisibility(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    public static class Token {
        String name;
        String symbol;
        int iconResId;
        BigDecimal balance;
        BigDecimal usdPrice;

        public Token(String name, String symbol, int iconResId, BigDecimal balance, BigDecimal usdPrice) {
            this.name = name;
            this.symbol = symbol;
            this.iconResId = iconResId;
            this.balance = balance;
            this.usdPrice = usdPrice;
        }

        public String getName() { return name; }
        public String getSymbol() { return symbol; }
        public int getIconResId() { return iconResId; }
        public BigDecimal getBalance() { return balance; }
        public BigDecimal getUsdPrice() { return usdPrice; }
    }

    private void updatePayCardUI(Token token) {
        youPayTokenSymbolTv.setText(token.getSymbol());
        youPayTokenIconIv.setImageResource(token.getIconResId());
        youPayBalanceTv.setText("Balance: " + formatBigDecimal(token.getBalance(), 4) + " " + token.getSymbol());
    }

    private void updateReceiveCardUI(Token token) {
        youReceiveTokenSymbolTv.setText(token.getSymbol());
        youReceiveTokenIconIv.setImageResource(token.getIconResId());
        youReceiveBalanceTv.setText("Balance: " + formatBigDecimal(token.getBalance(), 4) + " " + token.getSymbol());
    }

    private void swapTokens() {
        if (currentPayToken == null || currentReceiveToken == null) {
            return;
        }

        String payAmount = youPayAmountEt.getText().toString();

        Token tempToken = currentPayToken;
        currentPayToken = currentReceiveToken;
        currentReceiveToken = tempToken;

        updatePayCardUI(currentPayToken);
        updateReceiveCardUI(currentReceiveToken);

        youPayAmountEt.setText(payAmount);
        calculateReceiveAmountAndDetails();
    }

    private void calculateReceiveAmountAndDetails() {
        String payAmountStr = youPayAmountEt.getText().toString();
        if (payAmountStr.isEmpty() || currentPayToken == null || currentReceiveToken == null) {
            youReceiveAmountTv.setText("0");
            updatePaymentDetailsVisibility(false);
            return;
        }

        try {
            BigDecimal payAmount = new BigDecimal(payAmountStr);

            BigDecimal exchangeRate = getExchangeRate(currentPayToken.getSymbol(), currentReceiveToken.getSymbol());
            BigDecimal receiveAmount = payAmount.multiply(exchangeRate);

            DecimalFormat amountDf = new DecimalFormat("#,##0.#########");
            youReceiveAmountTv.setText(amountDf.format(receiveAmount));

            updateSlippageAndPriceImpact();

        } catch (NumberFormatException e) {
            youReceiveAmountTv.setText("0");
            updatePaymentDetailsVisibility(false);
        }
    }

    private void updatePaymentDetailsVisibility(boolean visible) {
        paymentDetailsContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void updateSlippageAndPriceImpact() {
        slippageValueTv.setText("0.5%");

        double priceImpact = Math.random() * 0.1;
        String priceImpactStr;
        int priceImpactColor;

        if (priceImpact < 0.01) {
            priceImpactStr = "< 0.01%";
            priceImpactColor = Color.parseColor("#7ED957");
        } else if (priceImpact < 0.5) {
            priceImpactStr = String.format("%.2f%%", priceImpact);
            priceImpactColor = Color.parseColor("#FFFFFF");
        } else {
            priceImpactStr = String.format("%.2f%%", priceImpact);
            priceImpactColor = Color.parseColor("#FF5722");
        }
        priceImpactValueTv.setText(priceImpactStr);
        priceImpactValueTv.setTextColor(priceImpactColor);
    }

    private BigDecimal getExchangeRate(String fromSymbol, String toSymbol) {
        if (fromSymbol.equals("SOL") && toSymbol.equals("USDC")) {
            return new BigDecimal("176.2411795");
        } else if (fromSymbol.equals("USDC") && toSymbol.equals("SOL")) {
            return new BigDecimal("0.0056740");
        } else if (fromSymbol.equals("SOL") && toSymbol.equals("DEGE")) {
            return new BigDecimal("177.865").divide(new BigDecimal("0.0469"), 10, BigDecimal.ROUND_HALF_UP);
        } else if (fromSymbol.equals("DEGE") && toSymbol.equals("SOL")) {
            return new BigDecimal("0.0469").divide(new BigDecimal("177.865"), 10, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ONE;
    }

    private void selectPayToken() {
        Toast.makeText(getContext(), "Select Pay Token Clicked", Toast.LENGTH_SHORT).show();
    }

    private void selectReceiveToken() {
        Toast.makeText(getContext(), "Select Receive Token Clicked", Toast.LENGTH_SHORT).show();
    }

    private String formatBigDecimal(BigDecimal value, int decimalPlaces) {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(decimalPlaces);
        df.setMaximumFractionDigits(decimalPlaces);
        df.setGroupingUsed(false);
        return df.format(value);
    }

    private void initializeTrendingTokens() {
        trendingTokensList = new ArrayList<>();
        trendingTokensList.add(new TrendingToken("Dege", "$25M MC", "$0.0323", "+257.73%", R.drawable.ic_profile, 1));
        trendingTokensList.add(new TrendingToken("MEMELESS", "$2.4M MC", "$0.00241571", "+809.05%", R.drawable.ic_profile, 2));
        trendingTokensList.add(new TrendingToken("SAVIOUR", "$491K MC", "$0.00039268", "+6,433.27%", R.drawable.ic_profile, 3));
        trendingTokensList.add(new TrendingToken("BONKHOUSE", "$915K MC", "$0.00085901", "-29.45%", R.drawable.ic_profile, 4));
    }

    public static class TrendingToken {
        String name;
        String marketCap;
        String price;
        String priceChange;
        int iconResId;
        int rank;

        public TrendingToken(String name, String marketCap, String price, String priceChange, int iconResId, int rank) {
            this.name = name;
            this.marketCap = marketCap;
            this.price = price;
            this.priceChange = priceChange;
            this.iconResId = iconResId;
            this.rank = rank;
        }

        public String getName() { return name; }
        public String getMarketCap() { return marketCap; }
        public String getPrice() { return price; }
        public String getPriceChange() { return priceChange; }
        public int getIconResId() { return iconResId; }
        public int getRank() { return rank; }
    }

    public class TrendingTokenAdapter extends RecyclerView.Adapter<TrendingTokenAdapter.TokenViewHolder> {

        private List<TrendingToken> tokens;

        public TrendingTokenAdapter(List<TrendingToken> tokens) {
            this.tokens = tokens;
        }

        @NonNull
        @Override
        public TokenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_token_swap, parent, false);
            return new TokenViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TokenViewHolder holder, int position) {
            TrendingToken token = tokens.get(position);

            holder.rankTextView.setText(String.valueOf(token.getRank()));
            holder.nameTextView.setText(token.getName());
            holder.marketCapTextView.setText(token.getMarketCap());
            holder.priceTextView.setText(token.getPrice());

            String changeText = token.getPriceChange();
            int changeColor = Color.parseColor("#7ED957");
            if (changeText.contains("-")) {
                changeColor = Color.parseColor("#FF5722");
            }
            SpannableString spannableString = new SpannableString(changeText);
            spannableString.setSpan(new ForegroundColorSpan(changeColor), 0, changeText.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.priceChangeTextView.setText(spannableString);
            holder.tokenIcon.setImageResource(token.getIconResId());
        }

        @Override
        public int getItemCount() {
            return tokens.size();
        }

        public class TokenViewHolder extends RecyclerView.ViewHolder {
            TextView rankTextView;
            ImageView tokenIcon;
            TextView nameTextView, marketCapTextView, priceTextView, priceChangeTextView;

            public TokenViewHolder(@NonNull View itemView) {
                super(itemView);
                rankTextView = itemView.findViewById(R.id.token_rank);
                tokenIcon = itemView.findViewById(R.id.token_icon);
                nameTextView = itemView.findViewById(R.id.token_name);
                marketCapTextView = itemView.findViewById(R.id.token_market_cap);
                priceTextView = itemView.findViewById(R.id.token_price);
                priceChangeTextView = itemView.findViewById(R.id.token_price_change);
            }
        }
    }
}