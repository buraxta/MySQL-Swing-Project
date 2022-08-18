package com.swing.View;

import com.swing.Helper.Helper;
import com.swing.Model.Course;
import com.swing.Model.Patika;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTable tbl_patikalar;
    private JTable tbl_egitimler;
    private JTable tbl_quizler;
    private JTextField fld_secilen_ders;
    private JButton KATILButton;
    private JButton AYRILButton;
    private JTextField fld_secilen_egitim;
    private JTextArea txt_yorum;
    private JButton btn_yorum_yap;
    private JButton btn_yorumu_guncelle;
    private JButton btn_yorumu_sil;
    private DefaultTableModel mdl_patikalar;
    private Object[] row_patikalar;
    private DefaultTableModel mdl_egitimler;
    private Object[] row_egitimler;

    public StudentGUI(){

        add(wrapper);
        setSize(1000,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Student Ekranı");
        setVisible(true);
        setResizable(false);



        // Patikalar

        mdl_patikalar = new DefaultTableModel();
        Object[] col_patikalar = {"ID", "Ders Adı"};
        mdl_patikalar.setColumnIdentifiers(col_patikalar);
        tbl_patikalar.setModel(mdl_patikalar);
        row_patikalar = new Object[col_patikalar.length];
        tbl_patikalar.getColumnModel().getColumn(0).setMaxWidth(40);
        tbl_patikalar.getTableHeader().setReorderingAllowed(false);

        loadPatikaModel();
        // ## Patikalar

        // Eğitimler

        mdl_egitimler = new DefaultTableModel();
        Object[] col_egitimler = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_egitimler.setColumnIdentifiers(col_egitimler);
        tbl_egitimler.setModel(mdl_egitimler);
        row_egitimler = new Object[col_egitimler.length];
        tbl_egitimler.getColumnModel().getColumn(0).setMaxWidth(40);
        tbl_egitimler.getTableHeader().setReorderingAllowed(false);
        tbl_egitimler.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String secilen_ders = tbl_egitimler.getValueAt(tbl_egitimler.getSelectedRow(),1).toString();
                fld_secilen_egitim.setText(secilen_ders);
                if (Course.getYorum(secilen_ders) != null){
                    txt_yorum.setText(Course.getYorum(secilen_ders));
                } else {
                    txt_yorum.setText(null);
                }
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

        loadEgitimModel();
        // ## Eğitimler

        tbl_patikalar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                String str = tbl_egitimler.getValueAt(tbl_egitimler.getSelectedRow(),1).toString();
                String selected_ders = tbl_patikalar.getValueAt(tbl_patikalar.getSelectedRow(),1).toString();
                fld_secilen_ders.setText(selected_ders);
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
        KATILButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_secilen_ders)){
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm(fld_secilen_ders.getText()+" dersine katılmak istediğinizden emin misiniz?")){
                    int selected_id = Integer.parseInt(tbl_patikalar.getValueAt(tbl_patikalar.getSelectedRow(),0).toString());
                    if (Course.checkDuplicate(selected_id)){
                        Helper.showMsg("Bu derse zaten daha önce kayılmışsınız");
                    } else {
                        Course.katil(selected_id);
                        Helper.showMsg("Ders kaydınız başarıyla gerçekleştirildi!");
                        loadEgitimModel();
                    }
                }
            }
        });
        AYRILButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_secilen_ders)){
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm(fld_secilen_ders.getText()+" dersinden ayrılmak istediğinizden emin misiniz?")){
                    int selected_id = Integer.parseInt(tbl_patikalar.getValueAt(tbl_patikalar.getSelectedRow(),0).toString());
                    if (Course.checkDuplicate(selected_id)){
                        Course.ayril(selected_id);
                        Helper.showMsg("Dersten başarıyla ayrıldınız.");
                        loadEgitimModel();
                    } else {
                        Helper.showMsg("Kayıtlı olmadığınız bir dersten ayrılamazsınız :)");
                    }
                }
            }
        });
        btn_yorum_yap.addActionListener(e -> {
            if (Helper.isFieldEmpty(txt_yorum) || Helper.isFieldEmpty(fld_secilen_egitim)){
                Helper.showMsg("fill");
            } else {
                if (Course.yorumChecker(fld_secilen_egitim.getText())){
                    Helper.showMsg("Ders hakkında zaten yorumunuz bulunmaktadır, dilerseniz güncelleyebilirsiniz.");
                } else {
                    if (Course.addYorum(fld_secilen_egitim.getText(), txt_yorum.getText())){
                        Helper.showMsg("Yorumunuz sisteme başarılı bir şekilde kaydedilmiştir.");
                    }
                }
            }
        });
        btn_yorumu_guncelle.addActionListener(e -> {
            if (Helper.isFieldEmpty(txt_yorum) || Helper.isFieldEmpty(fld_secilen_egitim)){
                Helper.showMsg("fill");
            } else {
                if (!Course.yorumChecker(fld_secilen_egitim.getText())){
                    Helper.showMsg("Güncellemek için önce yorumunuz bulunmalıdır, dilerseniz ekleyebilirsiniz.");
                } else {
                    if (Course.updateYorum(fld_secilen_egitim.getText(),txt_yorum.getText())){
                        Helper.showMsg("Yorumunuz başarılı bir şekilde güncellenmiştir.");
                    }
                }
            }
        });
        btn_yorumu_sil.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_secilen_egitim)){
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")){
                    if (Course.delYorum(fld_secilen_egitim.getText())){
                        Helper.showMsg("done");
                        fld_secilen_ders.setText(null);
                        txt_yorum.setText(null);
                        loadEgitimModel();
                    }
                }

            }
        });
    }

    public void loadPatikaModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patikalar.getModel();
        clearModel.setRowCount(0);

        for (Patika p : Patika.getList()){
            row_patikalar[0] = p.getId();
            row_patikalar[1] = p.getName();
            mdl_patikalar.addRow(row_patikalar);

        }
    }

    // Katıl dedikten sonra ilgili patikanın course'una gidip onları ayrı bir listede toplamam lazım.
    public void loadEgitimModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_egitimler.getModel();
        clearModel.setRowCount(0);
        for (Course c : Course.getListByPatika()){
            row_egitimler[0] = c.getId();
            row_egitimler[1] = c.getName();
            row_egitimler[2] = c.getLang();
            row_egitimler[3] = c.getPatika();
            row_egitimler[4] = c.getEducator();
            mdl_egitimler.addRow(row_egitimler);
        }
    }



    public static void main(String[] args) {
        Helper.setLayout();
        StudentGUI s = new StudentGUI();

    }


}


