package com.example.mobilepro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobilepro.CalendarFragment;
import com.example.mobilepro.ChatFragment;
import com.example.mobilepro.MainFragment;
import com.example.mobilepro.R;
import com.example.mobilepro.SetFragment;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    ChatFragment chatFragment;
    CalendarFragment calendarFragment;
    SetFragment setFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.baseFragment);
        chatFragment = new ChatFragment();
        calendarFragment = new CalendarFragment();
        setFragment = new SetFragment();

    }

    public void onFragmentChanged(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, mainFragment).commit();
        }
        else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, chatFragment).commit();
        }
        else if(index == 2){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, calendarFragment).commit();
        }
        else if(index == 3){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, setFragment).commit();
        }
    }

}