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
        ImageView myprofilebtn = findViewById(R.id.myprofilebtn);
        Button buysolwithcashbtn = findViewById(R.id.buysolwithcashbtn);
        Button transfercryptobtn = findViewById(R.id.transfercryptobtn);
        LinearLayout swapbtn = findViewById(R.id.swapbtn);
        LinearLayout recievebtn = findViewById(R.id.receivebtn);
        LinearLayout sendbtn = findViewById(R.id.sendbtn);
        LinearLayout buybtn = findViewById(R.id.buybtn);
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