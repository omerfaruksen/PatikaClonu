package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;
    private Patika patika;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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

    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika=Patika.getFetch(patika_id); //burayı neden böyle yaptık?
        this.educator= User.getFetch(user_id);
    }

    public Course(){

    }

    public Course(int id,String name){
        this.id=id;
        this.name=name;

    }


    private User educator;

    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList= new ArrayList<>();

        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course");
            while (rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id,user_id,patika_id,name,lang);
                courseList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }
    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList= new ArrayList<>();

        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE user_id=" +user_id);
            while (rs.next()){
                int id = rs.getInt("id");
                int userID = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id,user_id,patika_id,name,lang);
                courseList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }
    public static boolean add(int user_id,int patika_id,String name, String lang){
        String query = "INSERT INTO course (user_id,patika_id,name,lang) VALUES (?,?,?,?)";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            pr.setString(3,name);
            pr.setString(4,lang);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return true;
    }
    public static boolean delete(int id) {
        String query = "DELETE FROM course WHERE id = ? ";


        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);


            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static Course getFetch(int id) {
        Course obj = null;
        String query = "SELECT * FROM course WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Course();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setLang(rs.getString("lang"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
    public static ArrayList<Course> getCourseListByPatikaID(int patika_id){
        ArrayList<Course> courseList= new ArrayList<>();

        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE patika_id=" +patika_id);
            while (rs.next()){

                String name = rs.getString("name");
                int id= rs.getInt("id");

                obj = new Course(id,name);
                courseList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }
    public static boolean update( String name, String lang,int course_id) {
        String query = "UPDATE course SET name =? ,lang=?  WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, lang);
            pr.setInt(3,course_id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }
}