package com.example.mobilepro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class BackgroundChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_background);

        TextView colorLightBlue = findViewById(R.id.colorLightBlue);
        TextView colorWhite = findViewById(R.id.colorWhite);
        TextView colorBlack = findViewById(R.id.colorBlack);
        TextView colorMint = findViewById(R.id.colorMint);
        TextView colorLightYellow = findViewById(R.id.colorLightYellow);
        TextView colorLightPurple = findViewById(R.id.colorLightPurple);
        TextView colorLightRed = findViewById(R.id.colorLightRed);
        TextView colorLightGreen = findViewById(R.id.colorLightGreen);

        /*
        colorLightBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MessageActivity.changeBackground(colorLightBlue.getBackground());
            }
        });
         */


    }
}
