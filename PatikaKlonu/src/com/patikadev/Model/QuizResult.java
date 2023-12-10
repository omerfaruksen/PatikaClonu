package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuizResult {
    private int id;
    private int quiz_id;
    private Quiz quiz;
    private int user_id;
    private User user;
    private String result;

    public QuizResult(int id, int quiz_id, int user_id, String result) {
        this.id = id;
        this.quiz_id = quiz_id;
        this.user_id = user_id;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public static boolean add(int quiz_id, int user_id,String result) {
        String query = "INSERT INTO quiz_result (quiz_id,user_id,result) VALUES (?,?,?)";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);

            pr.setInt(1, quiz_id);
            pr.setInt(2, user_id);
            pr.setString(3, result);


            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

}