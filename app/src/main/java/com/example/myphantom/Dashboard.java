package com.example.myphantom;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ImageView searchbtn = findViewById(R.id.searchbtn);
        ImageView scanqr = findViewById(R.id.scanqr);
        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ScanQr.class);
                startActivity(intent);
            }
        });
        ViewUtils.addPressEffect(searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Search.class);
                startActivity(intent);
            }
        });
        ImageView myprofilebtn = findViewById(R.id.myprofilebtn);
        Button buysolwithcashbtn = findViewById(R.id.buysolwithcashbtn);
        Button transfercryptobtn = findViewById(R.id.transfercryptobtn);
        LinearLayout swapbtn = findViewById(R.id.swapbtn);
        LinearLayout recievebtn = findViewById(R.id.receivebtn);
        LinearLayout sendbtn = findViewById(R.id.sendbtn);
        LinearLayout buybtn = findViewById(R.id.buybtn);
        LinearLayout assestBitcoin = findViewById(R.id.assestBitcoin);
        LinearLayout assestEthereum = findViewById(R.id.assestEthereum);
        LinearLayout assestSolana = findViewById(R.id.assestSolana);
        LinearLayout assestPolygon = findViewById(R.id.assestPolygon);
        LinearLayout assestSui = findViewById(R.id.assestSui);
        ViewUtils.addPressEffect(assestBitcoin);
        ViewUtils.addPressEffect(assestEthereum);
        ViewUtils.addPressEffect(assestSolana);
        ViewUtils.addPressEffect(assestPolygon);
        ViewUtils.addPressEffect(assestSui);
        ViewUtils.addPressEffect(buysolwithcashbtn);
        ViewUtils.addPressEffect(transfercryptobtn);
        ViewUtils.addPressEffect(swapbtn);
        ViewUtils.addPressEffect(recievebtn);
        ViewUtils.addPressEffect(sendbtn);
        ViewUtils.addPressEffect(buybtn);
        ViewUtils.addPressEffect(myprofilebtn);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Send.class);
                startActivity(intent);
            }
        });
        transfercryptobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Receive.class);
                startActivity(intent);
            }
        });
        recievebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Receive.class);
                startActivity(intent);
            }
        });
        myprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}