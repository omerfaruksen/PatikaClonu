package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private int patika_id;
    private String cont_name;
    private String cont_inf;
    private String cont_link;
    private int user_id;
    private Patika patika;
    private User educator;
    public Content() {}

    public Content(int id, int patika_id, String cont_name, String cont_inf, String cont_link, int user_id) {
        this.id = id;
        this.patika_id = patika_id;
        this.cont_name = cont_name;
        this.cont_inf = cont_inf;
        this.cont_link = cont_link;
        this.user_id = user_id;
        this.educator=User.getFetch(user_id);
        this.patika=Patika.getFetch(patika_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getCont_name() {
        return cont_name;
    }

    public void setCont_name(String cont_name) {
        this.cont_name = cont_name;
    }

    public String getCont_inf() {
        return cont_inf;
    }

    public void setCont_inf(String cont_inf) {
        this.cont_inf = cont_inf;
    }

    public String getCont_link() {
        return cont_link;
    }

    public void setCont_link(String cont_link) {
        this.cont_link = cont_link;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Content>getContentList(){
        ArrayList<Content>contentList=new ArrayList<>();
        String query="SELECT*FROM content";
        Content obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                obj=new Content();
                obj.setId(rs.getInt("id"));
                obj.setPatika_id(rs.getInt("patika_id"));
                obj.setCont_name(rs.getString("cont_name"));
                obj.setCont_inf(rs.getString("cont_inf"));
                obj.setCont_link(rs.getString("cont_link"));
                obj.setUser_id(rs.getInt("user_id"));
                contentList.add(obj);

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return contentList;
    }
}
