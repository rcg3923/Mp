package com.example.mobilepro;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilepro.Account.LoginActivity;


public class BackgroundChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_background);

        View layout2 = getLayoutInflater().inflate(R.layout.activity_message, null, false);

        TextView colorLightBlue = findViewById(R.id.colorLightBlue);
        TextView colorWhite = findViewById(R.id.colorWhite);
        TextView colorBlack = findViewById(R.id.colorBlack);
        TextView colorMint = findViewById(R.id.colorMint);
        TextView colorLightYellow = findViewById(R.id.colorLightYellow);
        TextView colorLightPurple = findViewById(R.id.colorLightPurple);
        TextView colorLightRed = findViewById(R.id.colorLightRed);
        TextView colorLightGreen = findViewById(R.id.colorLightGreen);


        colorLightBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#5858FA";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });

        colorWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#ffffff";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });

        colorBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#000000";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });


        colorMint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#81F7F3";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });

        colorLightYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#F2F5A9";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });

        colorLightPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#BE81F7";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });

        colorLightRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#F78181";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });

        colorLightGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.color = "#A9F5A9";
                Toast.makeText(getApplicationContext(), "설정 완료",Toast.LENGTH_LONG).show();
            }
        });

    }
}
