package com.example.mobilepro.recyclerview;

import android.app.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.mobilepro.recyclerview.Adapter.CustomRecyclerAdatper;
import com.example.mobilepro.R;
import com.example.mobilepro.databinding.ActivityEmjBinding;
import com.example.mobilepro.recyclerview.Model.ListItemModel;
import com.example.mobilepro.recyclerview.Model.MyListItem;

import java.util.ArrayList;

public class EmjActivity extends Activity {

    private ActivityEmjBinding binding;
    private ArrayList<MyListItem> myListItems;
    private CustomRecyclerAdatper adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emj);

        initBinding();

        initialize();

        listAdd();

        recyclerViewConnection();

    }
    private void initBinding(){
        binding = ActivityEmjBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    /**
     * @DESC: 초기화
     */
    private void initialize(){
        myListItems = new ArrayList<>();
        binding.recyclerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * @DESC: 리스트 추가
     */
    private void listAdd(){
        String imageUri = "drawable://";
        addItem(getResources().getString(R.string.emj_01),getResources().getString(R.string.emj_01_describe),imageUri + R.drawable.emj_01);
        addItem(getResources().getString(R.string.emj_02),getResources().getString(R.string.emj_02_describe),imageUri + R.drawable.emj_02);
        addItem(getResources().getString(R.string.emj_03),getResources().getString(R.string.emj_03_describe),imageUri + R.drawable.emj_03);
        addItem(getResources().getString(R.string.emj_04),getResources().getString(R.string.emj_04_describe),imageUri + R.drawable.emj_04);
        addItem(getResources().getString(R.string.emj_05),getResources().getString(R.string.emj_05_describe),imageUri + R.drawable.emj_05);
    }

    /**
     * @DESC: 아이템 추가
     * @param title
     * @param describe
     * @param path
     */
    private void addItem(String title, String describe, String path){
        ListItemModel listItemModel = new ListItemModel();
        listItemModel.setTitle(title);
        listItemModel.setDescribe(describe);
        listItemModel.setUri(path);

        MyListItem myListItem = new MyListItem();

        ArrayList<ListItemModel> items = new ArrayList<>();

        items.add(listItemModel);

        myListItem.setList(items);

        myListItems.add(myListItem);
    }

    /**
     * @DESC: RecyclerView에 어댑터 연결
     */
    private void recyclerViewConnection(){
        adapter = new CustomRecyclerAdatper(this,this,myListItems);
        binding.recyclerList.setAdapter(adapter);
    }

}


