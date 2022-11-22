package com.example.mobilepro.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proj1-b1917-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();

        EditText UserName = findViewById(R.id.Username_Input);
        EditText PassWord = findViewById(R.id.pass);
        TextView LoginBtn = findViewById(R.id.Login_Controller);
        TextView RegisterBtn = findViewById(R.id.register_text);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login 요청
                String UserNameTxt = UserName.getText().toString();
                String PassWordTxt = PassWord.getText().toString();

                // 유저네임이나 패스워드가 비어있을경우
                mFirebaseAuth.signInWithEmailAndPassword(UserNameTxt, PassWordTxt).addOnCompleteListener(LoginActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, com.example.mobilepro.MainActivity.class);
                                    startActivity(intent);
                                    finish(); // 현재 액티비티 파괴
                                } else {
                                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Register페이지로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

