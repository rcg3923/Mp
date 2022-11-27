package com.example.mobilepro.Account;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    // Create object of DatabaseReference class to access firebase's Realtime Database
    private FirebaseAuth mFirebaseAuth; // Firebase 인증

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proj1-b1917-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        final EditText Email = findViewById(R.id.Email_Input);
        final EditText Name = findViewById(R.id.Name_Input);
        final EditText Phone = findViewById(R.id.Phone_Input);
        final EditText PassWord = findViewById(R.id.Password_Input);
        final EditText CheckPassWord = findViewById(R.id.Password_Check);

        final TextView RegisterBtn = findViewById(R.id.Register_Controller);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get data from EditTexts into String Variables
                final String EmailTxt = Email.getText().toString();
                final String NameTxt = Name.getText().toString();
                final String PhoneTxt = Phone.getText().toString();
                final String PassWordTxt = PassWord.getText().toString();
                final String CheckPassWordTxt = CheckPassWord.getText().toString();


                // Check if user fill all the fields before sending data to firebase

                if (EmailTxt.isEmpty() || NameTxt.isEmpty() || PhoneTxt.isEmpty() || PassWordTxt.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "모두 입력해주세요 ", Toast.LENGTH_SHORT).show();
                }

                // Check if passwords are matching with each other

                else if (!PassWordTxt.equals(CheckPassWordTxt)) {
                    Toast.makeText(RegisterActivity.this, "패스워드가 일차하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {

                    mFirebaseAuth.createUserWithEmailAndPassword(EmailTxt, PassWordTxt).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(PassWordTxt);
                                account.setPhoneNumber(PhoneTxt);
                                account.setName(NameTxt);
                                databaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                                databaseReference.child("UserPoint").child(firebaseUser.getUid()).child("point").setValue(0);

                                Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });


                }

            }

        });


    }
}