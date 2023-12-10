package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContentEvaluation {
    int id;
    int content_id;
    int point;
    int user_id;
    String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ContentEvaluation(int id, int content_id, int point, int user_id, String comment) {
        this.id = id;
        this.content_id = content_id;
        this.point = point;
        this.user_id = user_id;
        this.comment = comment;
    }
    public static boolean add(int content_id,int user_id,int point,String comment){
        String query = "INSERT INTO content_evaluation (content_id,user_id,point,comment) VALUES (?,?,?,?)";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);

            pr.setInt(1,content_id);
            pr.setInt(2,user_id);
            pr.setInt(3,point);
            pr.setString(4,comment);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return true;
    }
}
