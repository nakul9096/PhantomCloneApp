package com.example.myphantom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onNavigateToFragment(int menuItemId);
    }

    private OnFragmentInteractionListener listener;
    private TextView tokensTab;
    private TextView collectionTab;
    private LinearLayout tokensContentContainer;
    private LinearLayout collectionsContentContainer;
    private boolean isBalanceHidden = false; // Track balance visibility state

    public HomeFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView searchBtn = view.findViewById(R.id.searchbtn);
        ImageView scanQr = view.findViewById(R.id.scanqr);
        ImageView myProfileBtn = view.findViewById(R.id.myprofilebtn);
        Button buySolWithCashBtn = view.findViewById(R.id.buysolwithcashbtn);
        Button transferCryptoBtn = view.findViewById(R.id.transfercryptobtn);
        LinearLayout swapBtn = view.findViewById(R.id.swapbtn);
        LinearLayout receiveBtn = view.findViewById(R.id.receivebtn);
        LinearLayout sendBtn = view.findViewById(R.id.sendbtn);
        LinearLayout buyBtn = view.findViewById(R.id.buybtn);

        LinearLayout assetBitcoin = view.findViewById(R.id.assestBitcoin);
        LinearLayout assetEthereum = view.findViewById(R.id.assestEthereum);
        LinearLayout assetSolana = view.findViewById(R.id.assestSolana);
        LinearLayout assetPolygon = view.findViewById(R.id.assestPolygon);
        LinearLayout assetSui = view.findViewById(R.id.assestSui);

        tokensTab = view.findViewById(R.id.tokens_tab);
        collectionTab = view.findViewById(R.id.collection_tab);
        tokensContentContainer = view.findViewById(R.id.tokens_content_container);
        collectionsContentContainer = view.findViewById(R.id.collections_content_container);

        // Apply press effects to buttons and tabs
        ViewUtils.addPressEffect(swapBtn);
        ViewUtils.addPressEffect(sendBtn);
        ViewUtils.addPressEffect(buyBtn);
        ViewUtils.addPressEffect(receiveBtn);
        ViewUtils.addPressEffect(buySolWithCashBtn);
        ViewUtils.addPressEffect(transferCryptoBtn);
        ViewUtils.addPressEffect(tokensTab);
        ViewUtils.addPressEffect(collectionTab);

        selectTab(tokensTab);

        tokensTab.setOnClickListener(v -> selectTab(tokensTab));
        collectionTab.setOnClickListener(v -> selectTab(collectionTab));

        searchBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), Search.class)));
        scanQr.setOnClickListener(v -> startActivity(new Intent(requireActivity(), ScanQr.class)));
        myProfileBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), Profile.class)));
        buySolWithCashBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), BuyWithSol.class)));
        transferCryptoBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), Receive.class)));
        receiveBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), Receive.class)));
        sendBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), Send.class)));
        buyBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), Buy.class)));

        swapBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNavigateToFragment(R.id.nav_trending);
            }
        });

        ImageView collectionOptions = view.findViewById(R.id.collection_options);
        collectionOptions.setOnClickListener(v -> showCollectionOptionsMenu(v));

        return view;
    }

    private void selectTab(TextView selectedTab) {
        TextView previouslySelectedTab = (tokensContentContainer.getVisibility() == View.VISIBLE) ? tokensTab : collectionTab;
        LinearLayout previouslyVisibleContent = (tokensContentContainer.getVisibility() == View.VISIBLE) ? tokensContentContainer : collectionsContentContainer;
        LinearLayout newlyVisibleContent = (selectedTab.getId() == R.id.tokens_tab) ? tokensContentContainer : collectionsContentContainer;

        if (previouslySelectedTab == selectedTab) {
            return; // Do nothing if the same tab is clicked
        }

        // Apply fade-out animation to previously visible content
        if (previouslyVisibleContent.getVisibility() == View.VISIBLE) {
            Animation fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    previouslyVisibleContent.setVisibility(View.GONE);
                    // After fade-out, update tab states and show new content
                    updateTabStatesAndContent(selectedTab, newlyVisibleContent);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            previouslyVisibleContent.startAnimation(fadeOut);
        } else {
            updateTabStatesAndContent(selectedTab, newlyVisibleContent);
        }
    }

    private void updateTabStatesAndContent(TextView selectedTab, LinearLayout newlyVisibleContent) {
        // Reset all tabs to unselected state
        tokensTab.setBackgroundResource(R.drawable.tab_unselected_background);
        tokensTab.setTextColor(Color.parseColor("#AAAAAA"));

        collectionTab.setBackgroundResource(R.drawable.tab_unselected_background);
        collectionTab.setTextColor(Color.parseColor("#AAAAAA"));

        // Set the selected tab's background and text color
        selectedTab.setBackgroundResource(R.drawable.tab_selected_background);
        selectedTab.setTextColor(Color.WHITE);

        // Show newly visible content with fade-in animation
        newlyVisibleContent.setVisibility(View.VISIBLE);
        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
        newlyVisibleContent.startAnimation(fadeIn);

        // Update balance visibility based on isBalanceHidden
        updateBalanceVisibility();
    }

    private void updateBalanceVisibility() {
        // Array of asset layouts
        LinearLayout[] assetLayouts = {
                tokensContentContainer.findViewById(R.id.assestBitcoin),
                tokensContentContainer.findViewById(R.id.assestEthereum),
                tokensContentContainer.findViewById(R.id.assestPolygon),
                tokensContentContainer.findViewById(R.id.assestSolana),
                tokensContentContainer.findViewById(R.id.assestSui)
        };

        for (LinearLayout assetLayout : assetLayouts) {
            // Find the balance and value TextViews within each asset layout
            LinearLayout rightColumn = (LinearLayout) assetLayout.getChildAt(2); // The rightmost LinearLayout
            TextView balanceTextView = (TextView) rightColumn.getChildAt(0); // $0.00 (balance)
            TextView valueTextView = (TextView) rightColumn.getChildAt(1); // $0.00 (value)

            if (isBalanceHidden) {
                balanceTextView.setText("****");
                valueTextView.setText("****");
            } else {
                balanceTextView.setText("$0.00");
                valueTextView.setText("$0.00");
            }
        }
        String[] defaultBalances = {"0 BTC", "0 ETH", "0 POL", "0 SOL", "0 SUI"};
        for (int i = 0; i < assetLayouts.length; i++) {
            LinearLayout middleColumn = (LinearLayout) assetLayouts[i].getChildAt(1); // Middle LinearLayout
            TextView tokenBalanceTextView = (TextView) middleColumn.getChildAt(1); // e.g., "0 BTC"
            tokenBalanceTextView.setText(isBalanceHidden ? "****" : defaultBalances[i]);
        }
    }

    private void showCollectionOptionsMenu(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.custom_collection_menu, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout manageTokens = popupView.findViewById(R.id.menu_manage_tokens);
        LinearLayout hideBalances = popupView.findViewById(R.id.menu_hide_balances);
        LinearLayout walletSettings = popupView.findViewById(R.id.menu_wallet_settings);
        ImageView hideBalancesIcon = hideBalances.findViewById(R.id.menu_icon);
        TextView hideBalancesText = hideBalances.findViewById(R.id.menu_text);

        if (isBalanceHidden) {
            hideBalancesText.setText("Show Balances");
            hideBalancesIcon.setImageResource(R.drawable.ic_public);
        } else {
            hideBalancesText.setText("Hide Balances");
            hideBalancesIcon.setImageResource(R.drawable.ic_hide);
        }

        manageTokens.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ManageTokens.class);
            startActivity(intent);
            popupWindow.dismiss();
        });

        hideBalances.setOnClickListener(v -> {
            isBalanceHidden = !isBalanceHidden; // Toggle the state
            // Update menu text and icon
            hideBalancesText.setText(isBalanceHidden ? "Show Balances" : "Hide Balances");
            hideBalancesIcon.setImageResource(isBalanceHidden ? R.drawable.ic_public : R.drawable.ic_hide);
            // Update balance visibility
            updateBalanceVisibility();
            Toast.makeText(requireContext(), isBalanceHidden ? "Balances Hidden" : "Balances Shown", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        walletSettings.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Setting.class);
            startActivity(intent);
            popupWindow.dismiss();
        });

        int xOffset = anchorView.getWidth();
        int yOffset = anchorView.getHeight() / 2;

        popupWindow.showAsDropDown(anchorView, -xOffset, -yOffset, Gravity.END);
    }
}