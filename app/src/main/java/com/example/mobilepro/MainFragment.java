package com.example.mobilepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilepro.Account.RegisterActivity;
import com.example.mobilepro.Account.UserAccount;
import com.example.mobilepro.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// 친구창 프래그먼트
public class MainFragment extends Fragment {

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.peoplefragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());

        // 내 프로필에 해당하는 부분의 정보 채우기.
        TextView curr_name = rootView.findViewById(R.id.tv_username);
        TextView curr_email = rootView.findViewById(R.id.tv_useremail);

        databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                curr_name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        curr_email.setText(firebaseUser.getEmail());

        // "친구 추가 = 채팅방 생성"이라고 볼 수 있음.
        // 친구 추가 시, 유일키인 email로 검색하도록 함.
        EditText addFriend_editText = rootView.findViewById(R.id.addFriend_editText);
        Button addFriend_button = rootView.findViewById(R.id.addFriend_button);

        addFriend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_friend_email = addFriend_editText.getText().toString();

                // new_friend의 uid를 가져와서 UserFriend.본인uid 밑에 넣어야 한다.
                FirebaseDatabase.getInstance().getReference().child("UserAccount").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String check = "";
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // 서로 친추가 되어야 한다. (작동은 되는데 데이터베이스 부분 수정해야함.. 일대다 로 만들어야함.)
                            if(snapshot.getValue(UserAccount.class).getEmailId().equals(new_friend_email) && !new_friend_email.equals(firebaseUser.getEmail())) {
                                check = new_friend_email;

                                // 내 친구 목록에 추가
                                databaseReference.child("UserFriends").child(firebaseUser.getUid()).child(snapshot.getValue(UserAccount.class).getIdToken()).setValue(true);

                                // 친구의 목록에 나를 추가
                                databaseReference.child("UserFriends").child(snapshot.getValue(UserAccount.class).getIdToken()).child(firebaseUser.getUid()).setValue(true);

                                // 친구창의 리사이클러뷰 다시 출력해야함.
                                recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
                                recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());

                                Toast.makeText(getActivity().getApplication(), "친구추가 성공!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(check.equals("")) {
                            Toast.makeText(getActivity().getApplication(), "친구추가 실패!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


        // 밑에 있는 버튼
        ImageButton btn_calendar = rootView.findViewById(R.id.button_calendar);
        ImageButton btn_set = rootView.findViewById(R.id.button_set);


        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(1);
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(2);
            }
        });



        return rootView;
    }

    // recyclerview 추가
    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<UserAccount> userAccounts;  // 우리 코드엔 useraccount로 적용하면 될 듯
        public PeopleFragmentRecyclerViewAdapter() {
            userAccounts = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("UserAccount").addValueEventListener(new ValueEventListener() {
                // hasChild() 함수?
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                        FirebaseDatabase.getInstance().getReference().child("UserFriends").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                if (snapshot2.hasChild(snapshot1.getKey()) && !snapshot1.getKey().equals(firebaseUser.getUid())) {
                                    userAccounts.add(snapshot1.getValue(UserAccount.class));
                                }

                                notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder)holder).nameView.setText(userAccounts.get(position).getName());
            ((CustomViewHolder)holder).emailView.setText(userAccounts.get(position).getEmailId());
            int n = position; // 임시방편(친구 추가 기능과 파이어베이스에 userfriends가 추가되면 변경 예정)

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("destinationUid", userAccounts.get(n).getIdToken()); // destinationUid는 상대방 uid 말하는 거임.
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return userAccounts.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView nameView;
            public TextView emailView;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.iv_user);
                nameView = (TextView) view.findViewById(R.id.tv_username);
                emailView = (TextView) view.findViewById(R.id.tv_useremail);
            }
        }


    }
}
