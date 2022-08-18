package com.swing.View;

import com.swing.Helper.Helper;
import com.swing.Model.Operator;
import com.swing.Model.User;

import javax.swing.*;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_uname;
    private JPasswordField fld_user_pass;
    private JButton btn_login;
    private JButton KAYITOLButton;
    private JButton btn_exit;

    public LoginGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Giriş Ekranı");
        setResizable(false);
        setVisible(true);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            } else {
                Operator u = (Operator) User.getFetch(fld_user_uname.getText(), fld_user_pass.getText());
                if (u == null){
                    Helper.showMsg("Kullanıcı Bulunamadı ");
                } else {
                    switch (u.getType()){
                        case "operator":
                            OperatorGUI opGUI = new OperatorGUI(u);
                            break;
                        case "educator":
                            EducatorGUI edGUI = new EducatorGUI(fld_user_uname.getText());
                            break;
                        case "student":
                            StudentGUI stGUI = new StudentGUI();
                            break;
                    }
                    dispose();
                }

            }
        });
        KAYITOLButton.addActionListener(e -> {
            SignUpGUI signUpGUI = new SignUpGUI();
            dispose();
        });
        btn_exit.addActionListener(e -> {
            dispose();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
