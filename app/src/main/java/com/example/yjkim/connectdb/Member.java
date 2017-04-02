package com.example.yjkim.connectdb;

/**
 * Created by YJ.KIM on 2017. 4. 2..
 */
public class Member {
    private String name;
    private String tel;
    private String avg;

    Member() {
    }

    void setName(String n){
        this.name = n;
    }

    String getName(){
        return this.name;
    }

    void setTel(String t){
        this.tel = t;
    }

    String getTel(){
        return this.tel;
    }

    void setAvg(String a){
        this.avg = a;
    }

    String getAvg(){
        return this.avg;
    }



}
