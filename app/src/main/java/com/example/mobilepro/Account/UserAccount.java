package com.example.mobilepro.Account;

/*

    사용자 계정 정보 모델 클래스
 */
public class UserAccount
{
    // 후에 작업 시 닉네임, 프로필 이미지 url, token, usermessage 등등 이 곳에서 확장관리
    private String idToken; // Firebase Uid(고유 토큰정보)
    private String emailId; // 이메일 아이디
    private String password; // 비밀번호
    private String phoneNumber; // 폰번호
    private String name; // 이름

    // realtimebase 사용할때 꼭 해줘야함
    public UserAccount() { }


    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }

    public String getPhoneNumber() { return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


}
