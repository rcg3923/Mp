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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //Google Login
    private SignInButton btn_google;
    private GoogleApiClient googleApiClient;
    private static final int REQ_SIGN_GOOGLE = 100; // Google Login Result Code



    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proj1-b1917-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Firebase 인증 객체 초기화
        mFirebaseAuth = FirebaseAuth.getInstance();

        // xml 파일에서 입력된 값을 자바에서 사용하기 위해 가져오는 과정
        EditText UserName = findViewById(R.id.Username_Input);
        EditText PassWord = findViewById(R.id.pass);
        TextView LoginBtn = findViewById(R.id.Login_Controller);
        TextView RegisterBtn = findViewById(R.id.register_text);
        // Google 로그인 버튼
        btn_google = findViewById(R.id.Google_LoginController);

        //Google Login
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        // 구글 로그인 버튼을 클릭 했을 때 로직
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 구글에서 제공하는 인증되는 엑티비티 화면
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                // 얻어낸 결과 값을 돌려받음
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });

        // 로그인 버튼을 눌럿을때 동작하는 로직
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login 요청
                String UserNameTxt = UserName.getText().toString();
                String PassWordTxt = PassWord.getText().toString();

                // Login 버튼을 눌렀을 때 예외처리
                // 유저네임이나 패스워드가 비어있을경우
                if (UserNameTxt.isEmpty() || PassWordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "모두 입력 해주세요", Toast.LENGTH_SHORT).show();
                } else {

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

            }
        });
        // 로그인 버튼 동작 끝나는 로직

        // 회원가입 버튼 눌렀을 때
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Register페이지로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    // 화원가입 버튼 로직 끝


    // 구글 로그인 인증을 요청 했을 때 결과 값을 돌려받는 곳
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount(); //account 라는 데이터는 구글로그인 정보를 담고 있다.
                resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드

            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 로그인 성공
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), com.example.mobilepro.MainActivity.class);



                            startActivity(intent);
                        }
                        // 로그인 실패
                        else {

                        }
                    }
                });
    }


    // Google 연결 실패했을 때
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

