package com.swing.View;

import com.swing.Helper.Helper;
import com.swing.Helper.Item;
import com.swing.Model.Course;
import com.swing.Model.Icerik;
import com.swing.Model.Quiz;
import com.swing.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_egitimler;
    private JTable tbl_egitimler;
    private JPanel pnl_egitimler;
    private JPanel pnl_içerikler;
    private JTable tbl_icerikler;
    private JTextField fld_icerikler_baslik;
    private JTextField fld_icerikler_link;
    private JComboBox cmb_icerik_dersler;
    private JButton EKLEButton;
    private JTextArea txt_icerikler_aciklama;
    private JPanel pnl_quiz;
    private JTable tbl_quiz;
    private JComboBox cmb_quiz_dersler;
    private JButton btn_quiz_ekle;
    private JTextArea txt_quiz;
    private JButton btn_sil_icerikler;
    private JButton btn_sil_quiz;
    private JButton btn_guncelle_icerikler;
    private JTextField fld_baslik_src;
    private JTextField fld_ders_src;
    private JButton btn_icerikler_src;
    private DefaultTableModel mdl_egitimler;
    private Object[] row_egitimler;
    private DefaultTableModel mdl_icerikler;
    private Object[] row_icerikler;
    private User user;
    private DefaultTableModel mdl_quiz;
    private Object[] row_quiz;

    public EducatorGUI(String username){
        add(wrapper);
        setSize(1000,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Educator Ekranı");
        setVisible(true);
        setResizable(false);
        this.user = User.getFetch(username);



        //tbl_icerikler mouse listener
        tbl_icerikler.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fld_icerikler_baslik.setText(tbl_icerikler.getModel().getValueAt(tbl_icerikler.getSelectedRow(),1).toString());
                txt_icerikler_aciklama.setText(tbl_icerikler.getModel().getValueAt(tbl_icerikler.getSelectedRow(),2).toString());
                fld_icerikler_link.setText(tbl_icerikler.getModel().getValueAt(tbl_icerikler.getSelectedRow(),3).toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                fld_icerikler_baslik.setText(tbl_icerikler.getName());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //tbl_quiz mouse listener
        tbl_quiz.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // ??? combobox'ta varsayılan değeri burda seçilen satırın dersine ayarlayamadım
                txt_quiz.setText(tbl_quiz.getModel().getValueAt(tbl_quiz.getSelectedRow(),1).toString());

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        // EĞİTİMLER

        mdl_egitimler = new DefaultTableModel(){
        };
        Object[] col_egitimler = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_egitimler.setColumnIdentifiers(col_egitimler);
        tbl_egitimler.setModel(mdl_egitimler);
        row_egitimler = new Object[col_egitimler.length];

        tbl_egitimler.getColumnModel().getColumn(0).setMaxWidth(40);

        loadEgitimlerModel();


        // ## EĞİTİMLER


        // İÇEİRKLER
        mdl_icerikler = new DefaultTableModel();
        Object[] col_icerikler = {"ID", "Başlık", "Açıklama", "Link", "Quiz Sayısı", "Dersi"};
        mdl_icerikler.setColumnIdentifiers(col_icerikler);
        tbl_icerikler.setModel(mdl_icerikler);
        row_icerikler = new Object[col_icerikler.length];
        tbl_icerikler.getColumnModel().getColumn(0).setPreferredWidth(30);
        tbl_icerikler.getColumnModel().getColumn(1).setPreferredWidth(60);
        tbl_icerikler.getColumnModel().getColumn(2).setPreferredWidth(270);
        tbl_icerikler.getColumnModel().getColumn(3).setPreferredWidth(150);
        tbl_icerikler.getColumnModel().getColumn(4).setPreferredWidth(40);
        loadIcerikModel();

        loadIcerikCombo();
        loadQuizCombo();

        // ## İÇERİKLER

        //içerik ekleme
        EKLEButton.addActionListener(e -> {
            Item icerikItem = (Item) cmb_icerik_dersler.getSelectedItem();
            if (Helper.isFieldEmpty(fld_icerikler_baslik) || Helper.isFieldEmpty(txt_icerikler_aciklama) ||
                    Helper.isFieldEmpty(fld_icerikler_link)){
                Helper.showMsg("fill");
            } else {
                if (Icerik.add(fld_icerikler_baslik.getText(),txt_icerikler_aciklama.getText(),fld_icerikler_link.getText(),
                        icerikItem)){
                    Helper.showMsg("done");
                    loadIcerikModel();
                    loadQuizCombo();
                }
            }
        });

        // Quiz

        mdl_quiz = new DefaultTableModel();
        Object[] col_quiz = {"ID", "Quiz", "Ait Olduğu İçerik"};
        mdl_quiz.setColumnIdentifiers(col_quiz);
        tbl_quiz.setModel(mdl_quiz);
        row_quiz = new Object[col_quiz.length];
        tbl_quiz.getColumnModel().getColumn(0).setPreferredWidth(30);
        tbl_quiz.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbl_quiz.getColumnModel().getColumn(2).setPreferredWidth(50);
        loadQuizModel();

        //## Quiz

        btn_quiz_ekle.addActionListener(e -> {
            Item quizItem = (Item) cmb_quiz_dersler.getSelectedItem();
            if (Helper.isFieldEmpty(txt_quiz)){
                Helper.showMsg("fill");
            }  else {
                if (Quiz.add(txt_quiz.getText(),quizItem.getKey())){
                    Icerik.addOnePoint(quizItem.getKey());
                    Helper.showMsg("done");
                    loadQuizModel();
                    loadIcerikModel();
                    txt_quiz.setText(null);
                }
            }
        });
        btn_sil_icerikler.addActionListener(e -> {
            int check = -1;
            check = tbl_icerikler.getSelectedRow();
            if (check == -1){
                Helper.showMsg("Lütfen listeden bir değer seçiniz");
            }
            else {
                if (Helper.confirm("sure")){
                    int selected_id = Integer.parseInt(tbl_icerikler.getValueAt(tbl_icerikler.getSelectedRow(),0).toString());
                    if (Icerik.delete(selected_id)){
                        // aynı zamanda ilgili quiz'i silmeye çalışıyorum.
                        Quiz.deleteByIcerikID(selected_id);
                        Helper.showMsg("done");
                        loadIcerikModel();
                        loadQuizModel();
                        loadQuizCombo();
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }

        });

        btn_sil_quiz.addActionListener(e -> {
            if (Helper.confirm("sure")){
                if (Quiz.delete(Integer.parseInt(tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),0).toString()))){
                    Helper.showMsg("done");
                    String title = tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(),2).toString();
                    int id = Icerik.getFetchByTitle(title);
                    Icerik.minusOnePoint(id);
                    loadQuizModel();
                    loadIcerikModel();
                    txt_quiz.setText(null);
                }else {
                    Helper.showMsg("error");
                }
            }
        });
        btn_guncelle_icerikler.addActionListener(e -> {

            if (Helper.isFieldEmpty(fld_icerikler_baslik) || Helper.isFieldEmpty(fld_icerikler_link)||
            Helper.isFieldEmpty(txt_icerikler_aciklama)){
                Helper.showMsg("fill");
            } else {
                Item icerikItem = (Item) cmb_icerik_dersler.getSelectedItem();
                int selected_id = Integer.parseInt(tbl_icerikler.getValueAt(tbl_icerikler.getSelectedRow(),0).toString());
                if (Icerik.update(
                        fld_icerikler_baslik.getText(),
                        txt_icerikler_aciklama.getText(),
                        fld_icerikler_link.getText(),
                        icerikItem,
                        selected_id)){
                    Helper.showMsg("done");
                    loadIcerikModel();
                    loadQuizCombo();
                    loadQuizModel();
                    fld_icerikler_baslik.setText(null);
                    txt_icerikler_aciklama.setText(null);
                    fld_icerikler_link.setText(null);

                }else {
                    Helper.showMsg("error");
                }
            }
        });
        btn_icerikler_src.addActionListener(e -> {
            loadIcerikModel(Icerik.searchResult(fld_baslik_src.getText(),fld_ders_src.getText()));
        });
    }



    public static void main(String[] args) {
        Helper.setLayout();
        EducatorGUI e = new EducatorGUI("hoca");
    }

    public void loadEgitimlerModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_egitimler.getModel();
        clearModel.setRowCount(0);

        for (Course c : Course.getListByUser(user.getId())){
            row_egitimler[0] = c.getId();
            row_egitimler[1] = c.getName();
            row_egitimler[2] = c.getLang();
            row_egitimler[3] = c.getPatika().toString();
            row_egitimler[4] = c.getEducator().toString();
            mdl_egitimler.addRow(row_egitimler);
        }
    }

    public void loadIcerikModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_icerikler.getModel();
        clearModel.setRowCount(0);

        for (Object[] obj : Icerik.getList()){
            row_icerikler[0] = obj[0];
            row_icerikler[1] = obj[1];
            row_icerikler[2] = obj[2];
            row_icerikler[3] = obj[3];
            row_icerikler[4] = obj[4];
            row_icerikler[5] = Course.getFetch(Integer.parseInt((String) obj[5])).getName();
            mdl_icerikler.addRow(row_icerikler);
        }

    }
    public void loadIcerikModel(ArrayList<Object[]> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_icerikler.getModel();
        clearModel.setRowCount(0);

        for (Object[] obj : list){
            row_icerikler[0] = obj[0];
            row_icerikler[1] = obj[1];
            row_icerikler[2] = obj[2];
            row_icerikler[3] = obj[3];
            row_icerikler[4] = obj[4];
            row_icerikler[5] = Course.getFetch(Integer.parseInt((String) obj[5])).getName();
            mdl_icerikler.addRow(row_icerikler);
        }

    }

    public void loadQuizModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz.getModel();
        clearModel.setRowCount(0);

        for (Quiz q : Quiz.getList()){
            row_quiz[0] = q.getId();
            row_quiz[1] = q.getQuiz();
            row_quiz[2] = Icerik.getFetch(q.getIcerik())[1];
            mdl_quiz.addRow(row_quiz);
        }
    }

    public void loadIcerikCombo(){
        cmb_icerik_dersler.removeAllItems();
        for (Course c : Course.getListByUser(user.getId())){
            cmb_icerik_dersler.addItem(new Item(c.getId(), c.getName()));
        }
    }

    public void loadQuizCombo(){
        cmb_quiz_dersler.removeAllItems();
        for (Object[] icerik : Icerik.getList()){
            int id = Integer.parseInt((String) icerik[0]);
            String title = (String) icerik[1];
            cmb_quiz_dersler.addItem(new Item(id,title));
        }
    }

}
