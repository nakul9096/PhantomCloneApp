package com.example.myphantom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize all views
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

        // Set click listeners
        searchBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), Search.class)));
        scanQr.setOnClickListener(v -> startActivity(new Intent(getActivity(), ScanQr.class)));
        myProfileBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), Profile.class)));
        buySolWithCashBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), BuyWithSol.class)));
        transferCryptoBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), Receive.class)));
        receiveBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), Receive.class)));
        sendBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), Send.class)));
        buyBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), Buy.class)));
        swapBtn.setOnClickListener(v -> {
            // Handle swap action
        });

        // Set press effects
        ViewUtils.addPressEffect(searchBtn);
        ViewUtils.addPressEffect(scanQr);
        ViewUtils.addPressEffect(myProfileBtn);
        ViewUtils.addPressEffect(buySolWithCashBtn);
        ViewUtils.addPressEffect(transferCryptoBtn);
        ViewUtils.addPressEffect(swapBtn);
        ViewUtils.addPressEffect(receiveBtn);
        ViewUtils.addPressEffect(sendBtn);
        ViewUtils.addPressEffect(buyBtn);
        ViewUtils.addPressEffect(assetBitcoin);
        ViewUtils.addPressEffect(assetEthereum);
        ViewUtils.addPressEffect(assetSolana);
        ViewUtils.addPressEffect(assetPolygon);
        ViewUtils.addPressEffect(assetSui);

        return view;
    }
}