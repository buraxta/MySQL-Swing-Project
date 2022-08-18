package com.swing.Model;

import com.swing.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private String quiz;
    private int icerik;

    public Quiz(int id, String quiz, int icerik_id){
        this.id = id;
        this.quiz = quiz;
        this.icerik = icerik_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public int getIcerik() {
        return icerik;
    }

    public void setIcerik(int icerik) {
        this.icerik = icerik;
    }

    public static int getCount(int id){
        int total = 0;
        String query = "SELECT * FROM quizler WHERE icerik_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                total ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 5;
    }

    public static ArrayList<Quiz> getList(){
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Quiz q ;
        String query = "SELECT * FROM quizler";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                q = new Quiz(rs.getInt("id"),rs.getString("Quiz"),rs.getInt("icerik_id"));
                quizzes.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public static boolean add(String quiz, int id){
        String query = "INSERT INTO quizler (Quiz, icerik_id) VALUES (?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,quiz);
            pr.setInt(2,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM quizler WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean deleteByIcerikID(int id){
        String query = "DELETE FROM quizler WHERE icerik_id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
