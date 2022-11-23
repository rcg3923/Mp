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
import com.google.firebase.firestore.auth.User;

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

        String new_friend_email = addFriend_editText.getText().toString();

        addFriend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new_friend의 uid를 가져와서 UserFriend.본인uid 밑에 넣어야 한다.
                databaseReference.child("UserAccount").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Toast.makeText(getActivity().getApplication(), new_friend_email, Toast.LENGTH_SHORT).show();
                            // 서로 친추가 되어야 한다. (왜 작동 안되지...)
                            if(snapshot.getValue(UserAccount.class).getEmailId().equals(new_friend_email)) {
                                //UserFriends userFriends = new UserFriends();
                                //userFriends.setEmailId(snapshot.getValue(UserAccount.class).get);
                                databaseReference.child("UserFriends").child(firebaseUser.getUid()).child(snapshot.getValue(UserAccount.class).getIdToken()).setValue(true);
                                //databaseReference.child("UserFriends").child(snapshot.getValue(UserAccount.class).getIdToken()).setValue(firebaseUser.getUid());
                                Toast.makeText(getActivity().getApplication(), "친구추가 성공!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        // 밑에 있는 버튼
        ImageButton btn_chat = rootView.findViewById(R.id.button_chat);
        ImageButton btn_calendar = rootView.findViewById(R.id.button_calendar);
        ImageButton btn_set = rootView.findViewById(R.id.button_set);


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(1);
            }
        });
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(2);
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(3);
            }
        });



        return rootView;
    }

    // recyclerview 추가
    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<UserAccount> userAccounts;  // 우리 코드엔 useraccount로 적용하면 될 듯
        public PeopleFragmentRecyclerViewAdapter() {
            userAccounts = new ArrayList<>();
            DatabaseReference tmp = FirebaseDatabase.getInstance().getReference().child("UserAccount").child(firebaseUser.getUid());
            FirebaseDatabase.getInstance().getReference().child("UserFriends").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (databaseReference.child("UserFriends").child(firebaseUser.getUid()).child(snapshot.getValue(UserAccount.class).getIdToken()).equals(snapshot.getValue(UserAccount.class).getIdToken())) {
                            userAccounts.add(snapshot.getValue(UserAccount.class));
                        }
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
