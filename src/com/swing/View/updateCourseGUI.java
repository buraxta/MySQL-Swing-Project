package com.swing.View;

import com.swing.Helper.Config;
import com.swing.Helper.Helper;
import com.swing.Model.Course;

import javax.swing.*;

public class updateCourseGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_ders_adi;
    private JTextField fld_programlama_dili;
    private JTextField fld_patika;
    private JTextField fld_egitmen;
    private JButton btn_guncelle;
    private Course course;

    public updateCourseGUI(Course course){
        this.course = course;
        add(wrapper);
        setSize(300, 320);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_ders_adi.setText(course.getName());
        fld_programlama_dili.setText(course.getLang());
        fld_patika.setText(course.getPatika().getName());
        fld_egitmen.setText(course.getEducator().getName());

        btn_guncelle.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_ders_adi,fld_egitmen,fld_patika,fld_programlama_dili)){
                Helper.showMsg("fill");
            } else {
                if (Course.update(course.getId(),fld_ders_adi.getText(),fld_programlama_dili.getText())){
                    Helper.showMsg("done");
                }
            }
            dispose();
        });
    }

//    public static void main(String[] args) {
//        Helper.setLayout();
//        Course c = new Course(1,2,3,"PHP 101","PHP");
//        updateCourseGUI uc = new updateCourseGUI(c);
//    }
}
