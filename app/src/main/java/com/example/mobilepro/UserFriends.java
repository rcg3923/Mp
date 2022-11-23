package com.example.mobilepro;

/*
    사용자 친구 목록 정보 클래스
 */
public class UserFriends
{
    // 친구 목록 화면에 필요한 데이터 정보들
    private String emailId; // Email ID
    private String idToken; // Firebase Uid(고유 토큰정보)
    private String name; // 이름

    public UserFriends() { }


    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }


    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


}
