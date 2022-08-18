package com.swing.Model;

import com.swing.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;
    private Map<Integer,String> quiz;

    private Patika patika; //Bunları kendi içerisindeki getFetch metodları'na id...
    private User educator; //..göndererek alttaki constructor'da oluşturacağım

    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika = Patika.getFetch(patika_id);
        this.educator = User.getFetch(user_id);
        this.quiz = new HashMap<>();
    }

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

    public void addQuiz(int index, String icerik){
        this.quiz.put(index,icerik);
    }
    public void delQuiz(int index){
        this.quiz.remove(index);
    }
    public void getQuiz(int index){
        this.quiz.get(index);
    }
    public int quizCount(){
        return this.quiz.size();
    }

    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList = new ArrayList<>();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static boolean add(int user_id, int patika_id, String name, String lang){
        String query = "INSERT INTO course (user_id, patika_id, name, lang) VALUES (?,?,?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            pr.setString(3,name);
            pr.setString(4,lang);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name, String lang){
        String query = "UPDATE course SET name = ?, lang = ? WHERE id = ?" ;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,lang);
            pr.setInt(3,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static Course getFetch(int id){
        Course obj = null;
        String query = "SELECT * FROM course WHERE id=?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),
                        rs.getString("name"),rs.getString("lang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Course getFetch(String name){
        Course obj = null;
        String query = "SELECT * FROM course WHERE name=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),
                        rs.getString("name"),rs.getString("lang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM course WHERE id = ?";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList = new ArrayList<>();

        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE user_id = " + user_id);
            while (rs.next()){
                int id = rs.getInt("id");
                int userID = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id,userID,patika_id,name,lang);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }
    public static ArrayList<Course> getListByPatika(int selecte_id){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE id=" + selecte_id);
            while (rs.next()){
                int id = rs.getInt("id");
                int userID = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id,userID,patika_id,name,lang);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static ArrayList<Course> getListByPatika(){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        ArrayList<Integer> selectedLessons = getListByStudent();
        for (int i = 0; i<selectedLessons.size(); i++){
            try {
                Statement st = DBConnector.getInstance().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM course WHERE patika_id=" + selectedLessons.get(i));
                while (rs.next()){
                    int id = rs.getInt("id");
                    int userID = rs.getInt("user_id");
                    int patika_id = rs.getInt("patika_id");
                    String name = rs.getString("name");
                    String lang = rs.getString("lang");
                    obj = new Course(id,userID,patika_id,name,lang);
                    courseList.add(obj);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return courseList;
    }
    public static ArrayList<Integer> getListByStudent(){
        ArrayList<Integer> studentsList = new ArrayList<>();
        Course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM secilen_ders");
            while (rs.next()){
                int id = rs.getInt("secilen_ders");
                studentsList.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsList;
    }

    public static boolean katil(int id){
        String query = "INSERT INTO secilen_ders (secilen_ders) VALUES (?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean ayril(int id){
        String query = "DELETE FROM secilen_ders WHERE secilen_ders=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean checkDuplicate(int id){
        String query = "SELECT * FROM secilen_ders WHERE secilen_ders=?";
        int i = -1;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                i = rs.getInt("secilen_ders");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i != -1;
    }

    public static boolean addYorum(String ders_adi, String yorum){
        String query = "INSERT INTO yorumlar (ders_adi,yorum) VALUES (?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,ders_adi);
            pr.setString(2,yorum);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
//            String query = "UPDATE course SET name = ?, lang = ? WHERE id = ?" ;

    public static boolean updateYorum(String ders_adi, String yorum){
        String query = "UPDATE yorumlar SET yorum=? WHERE ders_adi=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,yorum);
            pr.setString(2,ders_adi);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean yorumChecker(String ders_adi){
        String query = "SELECT * FROM yorumlar WHERE ders_adi=?";
        int i = 0;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,ders_adi);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                i = rs.getString("yorum").toString().length();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i != 0;
    }
    public static String getYorum(String ders_adi){
        String query = "SELECT yorum FROM yorumlar WHERE ders_adi = ?";
        String yorum = "";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,ders_adi);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                yorum = rs.getString("yorum");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yorum;
    }

    public static boolean delYorum(String ders_adi){
        String query = "DELETE FROM yorumlar WHERE ders_adi=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,ders_adi);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
