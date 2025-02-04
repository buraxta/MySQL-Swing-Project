package com.swing.Model;

import com.swing.Helper.DBConnector;
import com.swing.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User  {
    private int id;
    private String name;
    private String username;
    private String pass;
    private String type;

    public User() {}

    public User(int id, String name, String username, String pass, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.pass = pass;
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException throwables){
            System.out.println(throwables.getMessage());
        }
        return userList;
    }

    public static boolean add(String name, String uname, String pass, String type){
        String query = "INSERT INTO user (name, username, pass, type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(uname);
        if (findUser != null){
            Helper.showMsg("Bu kullanıcı adı daha önce eklenmiş. Lütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);

            int response = pr.executeUpdate();
            if (response == -1){
                Helper.showMsg("error");
            }
            return response != -1; //burası false dönerse false return edicek, aksi halde true dönecek

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static User getFetch(String uname){
        User obj = null;
        String query = "SELECT * FROM user WHERE username = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static Operator getFetch(String uname, String pass){
        Operator obj = null;
        String query = "SELECT * FROM user WHERE username = ? AND pass = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            pr.setString(2,pass);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                switch (rs.getString("type")){
                    case "operator":
                        obj = new Operator();
                        break;
                    default:
                        obj = new Operator();
                }
                obj = new Operator();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(int id){
        User obj = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static boolean delete(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        ArrayList<Course> courseList = Course.getListByUser(id);
        for (Course c : courseList){
            Course.delete(c.getId());
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name, String uname, String pass, String type){
        String query = "UPDATE user SET name=?, username=?, pass=?, type=? WHERE id=?";
        User findUser = User.getFetch(uname);
        if (findUser != null && findUser.getId() != id){
            Helper.showMsg("Bu kullanıcı adı daha önce eklenmiş. Lütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("hata hata hata hata hata");
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException throwables){
            System.out.println(throwables.getMessage());
        }
        return userList;
    }

    public static String searchQuery(String name, String uname, String type){
        String query = "SELECT * FROM user WHERE username LIKE '%{{username}}%' AND name LIKE '%{{name}}%'";
        query = query.replace("{{username}}", uname);
        query = query.replace("{{name}}", name);
        if (!type.isEmpty()){
            query += " AND type='{{type}}'";
            query = query.replace("{{type}}",type);
        }
        System.out.println(query);
        return query;
    }

    public static ArrayList<User> getListOnlyEducator(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user WHERE type = 'educator'";
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException throwables){
            System.out.println(throwables.getMessage());
        }
        return userList;
    }


    @Override
    public String toString() {
        return getName();
    }
}
