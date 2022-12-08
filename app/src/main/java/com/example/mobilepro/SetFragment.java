package com.example.mobilepro;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobilepro.Account.LoginActivity;
import com.example.mobilepro.R;
import com.example.mobilepro.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetFragment extends Fragment {

    private ImageButton imageButton;

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_set, container, false);

        imageButton = rootView.findViewById(R.id.img_button);



        TextView user_name = rootView.findViewById(R.id.text_name);
        TextView user_email = rootView.findViewById(R.id.text_email);
        TextView curr_point = rootView.findViewById(R.id.text_point);
        View background_btn = rootView.findViewById(R.id.background_btn);

        databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                user_name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user_email.setText(firebaseUser.getEmail());

        databaseReference.child("UserPoint").child(firebaseUser.getUid()).child("point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(int.class);
                curr_point.setText(value + "포인트");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        background_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BackgroundChange.class);
                startActivity(intent);
            }
        });
        
        // 하단 메뉴바 기능
        ImageButton btn_main = rootView.findViewById(R.id.button_main);
        ImageButton btn_calendar = rootView.findViewById(R.id.button_calendar);
        //ImageButton btn_set = rootView.findViewById(R.id.button_set);


        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(0);
            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(1);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmjActivity.class);
                startActivity(intent);
            }
        });

        Button logout = rootView.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
