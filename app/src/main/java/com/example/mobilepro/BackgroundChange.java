package com.example.mobilepro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class BackgroundChange extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_set_background, container, false);

        TextView colorLightBlue = rootView.findViewById(R.id.colorLightBlue);
        TextView colorWhite = rootView.findViewById(R.id.colorWhite);
        TextView colorBlack = rootView.findViewById(R.id.colorBlack);
        TextView colorMint = rootView.findViewById(R.id.colorMint);
        TextView colorLightYellow = rootView.findViewById(R.id.colorLightYellow);
        TextView colorLightPurple = rootView.findViewById(R.id.colorLightPurple);
        TextView colorLightRed = rootView.findViewById(R.id.colorLightRed);
        TextView colorLightGreen = rootView.findViewById(R.id.colorLightGreen);

        colorLightBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MessageActivity.changeBackground(colorLightBlue.getBackground());
            }
        });

        return rootView;
    }
}
