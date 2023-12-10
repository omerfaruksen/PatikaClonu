package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
        private int id;
        private String name;
        private String description;
        private String link;
        private int course_id;
        private Course course;
        ArrayList<Content> contentList;

        public Content(int id, String name, String description, String link, int course_id) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.link = link;
            this.course_id = course_id;
            this.course = Course.getFetch(course_id);
        }

        public Content() {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public static ArrayList<Content> getListContentByCourse(int course_id) {
            ArrayList<Content> contentList = new ArrayList<>();

            Content obj;
            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM content WHERE course_id=" + course_id);
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String link = rs.getString("link");
                    int courseId = rs.getInt("course_id");
                    obj = new Content(id, name, description, link, courseId);
                    contentList.add(obj);

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return contentList;
        }

        public static boolean add(String name, String description, String link, int course_id) {
            String query = "INSERT INTO content (name,description,link,course_id) VALUES (?,?,?,?)";
            PreparedStatement pr = null;
            try {
                pr = DBConnector.getInstance().prepareStatement(query);

                pr.setString(1, name);
                pr.setString(2, description);
                pr.setString(3, link);
                pr.setInt(4, course_id);
                return pr.executeUpdate() != -1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            return true;
        }

        public static boolean delete(int id) {
            String query = "DELETE FROM content WHERE id = ? ";

            ArrayList<Quiz> quizList = Quiz.getListQuizByContent(id);
            for (Quiz q : quizList) {
                Quiz.delete(q.getId());
            }

            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setInt(1, id);

                return pr.executeUpdate() != -1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return true;
        }

        public static boolean update(int id, String name, String description, String link) {
            String query = "UPDATE content SET name =? ,description=?, link=? WHERE id = ?";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setString(1, name);
                pr.setString(2, description);
                pr.setString(3, link);
                pr.setInt(4, id);
                return pr.executeUpdate() != -1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return true;
        }

        public static Content getFetch(int id) {
            Content obj = null;
            String query = "SELECT * FROM content WHERE id=?";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setInt(1, id);
                ResultSet rs = pr.executeQuery();
                if (rs.next()) {
                    obj = new Content(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("link"), rs.getInt("course_id"));


                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return obj;
        }

        public static String searchQuery(String name, int courseId) {

            String query = "SELECT * FROM content WHERE name LIKE '%{{name}}%' AND course_id LIKE '%{{course_id}}%'";

            query = query.replace("{{name}}", name);
            query = query.replace("{{course_id}}", String.valueOf(courseId));

            return query;

        }

        public static ArrayList<Content> SearchContentList(String query) {
            ArrayList<Content> contentList = new ArrayList<>();

            Content obj;
            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    obj = new Content();
                    obj.setId(rs.getInt("id"));
                    obj.setName(rs.getString("name"));
                    obj.setDescription(rs.getString("description"));
                    obj.setLink(rs.getString("link"));
                    obj.setCourse_id(rs.getInt("course_id"));
                    obj.setCourse(Course.getFetch(rs.getInt("course_id")));
                    contentList.add(obj);

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return contentList;
        }

        public static ArrayList<Content> getList() {
            ArrayList<Content> contentList = new ArrayList<>();

            Content obj;
            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM content");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String link = rs.getString("link");
                    int courseId = rs.getInt("course_id");
                    obj = new Content(id, name, description, link, courseId);
                    contentList.add(obj);

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return contentList;
        }

    }
