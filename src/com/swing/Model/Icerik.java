package com.swing.Model;

import com.swing.Helper.DBConnector;
import com.swing.Helper.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Icerik {
    private int id;
    private String title;
    private String description;
    private String link;
    private int quiz;
    private Course course;



    public Icerik(int id, String title, String description, String link, String course){
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.quiz = 0;
        this.course = Course.getFetch(course);


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getQuiz() {
        return quiz;
    }

    public void setQuiz(int quiz) {
        this.quiz = quiz;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public static ArrayList<Object[]> getList(){
        ArrayList<Object[]> icerikList = new ArrayList();
        Object[] icerik ;
        String query = "SELECT * FROM icerikler";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                icerik = new String[6];
                icerik[0] = String.valueOf(rs.getInt("id"));
                icerik[1] = rs.getString("title");
                icerik[2] = rs.getString("description");
                icerik[3] = rs.getString("link");
                icerik[4] = String.valueOf(rs.getInt("quiz_sayisi"));
                icerik[5] = rs.getString("dersi");
                icerikList.add(icerik);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return icerikList;
    }

    public static boolean add(String title, String description, String link, Item course_id){
        String query = "INSERT INTO icerikler (title, description, link, dersi ) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            pr.setString(2,description);
            pr.setString(3,link);
            pr.setInt(4,course_id.getKey());


            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM icerikler WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean update(String title, String description, String link, Item course_id, int id){
        String query = "UPDATE icerikler SET title=?, description=?, link=?, dersi=? WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            pr.setString(2,description);
            pr.setString(3,link);
            pr.setInt(4,course_id.getKey());
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static Object[] getFetch(int id){
        Object[] obj = new Object[6];
        String query = "SELECT * FROM icerikler WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Object[6];
                obj[0] = rs.getInt("id");
                obj[1] = rs.getString("title");
                obj[2] = rs.getString("description");
                obj[3] = rs.getString("link");
                obj[4] = rs.getInt("quiz_sayisi");
                obj[5] = rs.getString("dersi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static int getFetchByTitle(String title){
        Object[] obj = new Object[6];
        int result = 0;
        String query = "SELECT * FROM icerikler WHERE title=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Object[6];
                obj[0] = rs.getInt("id");
                obj[1] = rs.getString("title");
                obj[2] = rs.getString("description");
                obj[3] = rs.getString("link");
                obj[4] = rs.getInt("quiz_sayisi");
                obj[5] = rs.getString("dersi");
                result = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int quizNumber(int id){
        String query = "SELECT * FROM icerikler WHERE id=?";
        int result = 0;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                result = rs.getInt("quiz_sayisi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<Object[]> searchResult(String baslik, String dersi){
        if (dersi == null){
            ArrayList<Object[]> icerikList = new ArrayList<>();
            Object[] obj ;
            String query = "SELECT * FROM icerikler WHERE title LIKE '%" + baslik + "%'";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                ResultSet rs = pr.executeQuery();
                while (rs.next()){
                    obj = new Object[6];
                    obj[0] = rs.getInt("id");
                    obj[1] = rs.getString("title");
                    obj[2] = rs.getString("description");
                    obj[3] = rs.getString("link");
                    obj[4] = rs.getInt("quiz_sayisi");
                    obj[5] = rs.getString("dersi");
                    icerikList.add(obj);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } return icerikList;
        }
        else if (baslik == null){
            ArrayList<Course> courses = Icerik.searchCourse(dersi);
            ArrayList<Object[]> icerikList = new ArrayList<>();
            Object[] obj;
            for (int i = 0; i<courses.size(); i++){
                String query = "SELECT * FROM icerikler WHERE dersi ="+ courses.get(i).getId();
                try {
                    PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                    ResultSet rs = pr.executeQuery();
                    while (rs.next()){
                        obj = new Object[6];
                        obj[0] = rs.getInt("id");
                        obj[1] = rs.getString("title");
                        obj[2] = rs.getString("description");
                        obj[3] = rs.getString("link");
                        obj[4] = rs.getInt("quiz_sayisi");
                        obj[5] = rs.getString("dersi");
                        icerikList.add(obj);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return icerikList;
        }
        else {
            ArrayList<Course> courses = Icerik.searchCourse(dersi);
            ArrayList<Object[]> icerikList = new ArrayList<>();
            Object[] obj;
            for (int i = 0; i<courses.size(); i++){
                String query = "SELECT * FROM icerikler WHERE title LIKE '%" + baslik + "%' AND dersi ="+ courses.get(i).getId();
                try {
                    PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                    ResultSet rs = pr.executeQuery();
                    while (rs.next()){
                        obj = new Object[6];
                        obj[0] = rs.getInt("id");
                        obj[1] = rs.getString("title");
                        obj[2] = rs.getString("description");
                        obj[3] = rs.getString("link");
                        obj[4] = rs.getInt("quiz_sayisi");
                        obj[5] = rs.getString("dersi");
                        icerikList.add(obj);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return icerikList;
        }
    }


    public static ArrayList<Course> searchCourse(String dersi){
        ArrayList<Course> courseList = new ArrayList<>();
        Course course;
        String query = "SELECT * FROM course WHERE name LIKE '%" + dersi + "%'";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                course = new Course(rs.getInt("id"),rs.getInt("useR_id"),rs.getInt("patika_id"),
                        rs.getString("name"),rs.getString("lang"));
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static void addOnePoint(int id){
        int quiz = Icerik.quizNumber(id);
        String query = "UPDATE icerikler SET quiz_sayisi=? WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,++quiz);
            pr.setInt(2,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void minusOnePoint(int id){
        int quiz = Icerik.quizNumber(id);
        String query = "UPDATE icerikler SET quiz_sayisi=? WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,--quiz);
            pr.setInt(2,id);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
