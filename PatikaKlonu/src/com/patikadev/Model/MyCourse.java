package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MyCourse {
    private int id;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    private int course_id;
    private Course course;

    public MyCourse(int id, int user_id, int course_id) {
        this.id = id;
        this.user_id = user_id;
        this.course_id = course_id;
        this.course = Course.getFetch(course_id);
    }

    public MyCourse(int id, int course_id) {
        this.id = id;
        this.course_id = course_id;
        this.course = Course.getFetch(course_id);
    }

    public static boolean add(int user_id, int course_id) {
        String query = "INSERT INTO mycourse (user_id,course_id) VALUES (?,?)";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, user_id);
            pr.setInt(2, course_id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return true;
    }

    public static ArrayList<MyCourse> getMyCourseListByUser(int user_id) {
        ArrayList<MyCourse> myCourseList = new ArrayList<>();

        MyCourse obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM mycourse WHERE user_id=" + user_id);
            while (rs.next()) {

                int id = rs.getInt("id");
                int courseid = rs.getInt("course_id");

                obj = new MyCourse(id, courseid);
                myCourseList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myCourseList;
    }

    public static MyCourse checkMyCourseByUserID(int user_id, int course_id) {
        MyCourse obj = null;
        String query = "SELECT * FROM mycourse WHERE user_id = ? AND course_id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, user_id);
            pr.setInt(2, course_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int courseid = rs.getInt("course_id");

                obj = new MyCourse(id, courseid);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
}

