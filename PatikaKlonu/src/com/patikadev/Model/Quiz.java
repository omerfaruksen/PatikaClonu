package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Model.Content;
import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private int content_id;
    private String question;
    private Content content;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Quiz(int id, int content_id, String question) {
        this.id = id;
        this.content_id = content_id;
        this.question = question;
        this.content = Content.getFetch(content_id);
    }

    public static ArrayList<Quiz> getListQuizByContent(int content_id) {
        ArrayList<Quiz> quizList = new ArrayList<>();

        Quiz obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM quiz WHERE content_id=" + content_id);
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                int contentId = rs.getInt("content_id");
                obj = new Quiz(id, contentId, question);
                quizList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return quizList;
    }

    public static boolean add(String question, int content_id) {
        String query = "INSERT INTO quiz (question,content_id) VALUES (?,?)";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);

            pr.setString(1, question);
            pr.setInt(2, content_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM quiz WHERE id = ? ";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean update(String question, int id) {
        String query = "UPDATE quiz SET question =?  WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, question);
            pr.setInt(2, id);


            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public static Quiz getFetch(int id) {
        Quiz obj = null;
        String query = "SELECT * FROM quiz WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Quiz(rs.getInt("id"),rs.getInt("content_id"),rs.getString("question"));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
    public static ArrayList<Quiz> getList() {
        ArrayList<Quiz> quizList = new ArrayList<>();

        Quiz obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM quiz");
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                int contentId = rs.getInt("content_id");
                obj = new Quiz(id, contentId, question);
                quizList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return quizList;
    }
}