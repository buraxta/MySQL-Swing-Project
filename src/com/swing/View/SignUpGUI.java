package com.swing.View;

import com.swing.Helper.Helper;
import com.swing.Model.User;

import javax.swing.*;

public class SignUpGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_new_name;
    private JTextField fld_new_username;
    private JPasswordField fld_new_pass;
    private JComboBox comboBox1;
    private JButton btn_new_kaydol;
    private JButton fld_giris_yap;


    public SignUpGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Kayıt Ekranı");
        setResizable(false);
        setVisible(true);

        btn_new_kaydol.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_new_name) || Helper.isFieldEmpty(fld_new_username) || Helper.isFieldEmpty(fld_new_pass)){
                Helper.showMsg("fill");
            } else {
                if(User.getFetch(fld_new_username.getText()) == null){
                    User.add(fld_new_name.getText(),fld_new_username.getText(),fld_new_pass.getText(),"student");
                    Helper.showMsg("done");
                    dispose();
                    LoginGUI loginGUI = new LoginGUI();
                } else {
                    Helper.showMsg("Bu kullanıcı adı daha önce alınmıştır.");
                }

            }
        });
        fld_giris_yap.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });
    }



    public static void main(String[] args) {
        Helper.setLayout();
        SignUpGUI signUpGUI = new SignUpGUI();
    }
}
