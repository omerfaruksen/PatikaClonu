package com.patikadev.View.View;
import com.patikadev.Helper.*;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JScrollPane scrl_userlist;
    private JTable table_userlist;
    private JPanel pnl_user_form;
    private JTextField field_user_name;
    private JTextField fld_uname;
    private JLabel field_password;
    private JLabel field_type;
    private JComboBox cmb_usertypr;
    private JButton button_useradd;
    private JPasswordField field_pass;
    private JLabel field;
    private JLabel field_name;
    private JTextField field_user_ıd;
    private JButton button_user_delete;
    private JTextField fld_search_name;
    private JLabel ad_soyad;
    private JLabel kullanıcı_adı;
    private JTextField field_search_uname;
    private JLabel üyelil_tipi;
    private JComboBox cmb_search_type;
    private JButton button_user_search;
    private JScrollPane scroll_patika_list;
    private JTable tbl_patila_list;
    private JPanel panel_patika_add;
    private JTextField field_patika_name;
    private JButton btn_patika_add;
    private JPanel panel_user_list;
    private JPanel panel_courses_list;
    private JScrollPane scrl_courses;
    private JTable table_course_list;
    private JPanel pnl_course_add;
    private JTextField field_course_name;
    private JTextField field_cours_lang;
    private JComboBox combobox_course_patika;
    private JComboBox comboBox_course_user;
    private JButton bttn_course_add;
    private final Operator operator;
    private DefaultTableModel mdl_user_list;
    private Object[]row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[]row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[]row_course_list;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin : " + operator.getName());

        //model Userlist
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };


        Object[] col_user_List = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_List);
        row_user_list = new Object[col_user_List.length];
        loadUserModel();

        table_userlist.setModel(mdl_user_list);
        //table oynamasını engelleme

        table_userlist.getTableHeader().setReorderingAllowed(false);

        //ıd silmedebc numara dışında veri girilmesini engelleyip tablodan seçmeyi saglama
        table_userlist.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = table_userlist.getValueAt(table_userlist.getSelectedRow(), 0).toString();
                field_user_ıd.setText(select_user_id);
            } catch (Exception exception) {

            }

        });

        table_userlist.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(table_userlist.getValueAt(table_userlist.getSelectedRow(), 0).toString());
                String user_name = table_userlist.getValueAt(table_userlist.getSelectedRow(), 1).toString();
                String user_uname = table_userlist.getValueAt(table_userlist.getSelectedRow(), 2).toString();
                String user_pass = table_userlist.getValueAt(table_userlist.getSelectedRow(), 3).toString();
                String user_type = table_userlist.getValueAt(table_userlist.getSelectedRow(), 4).toString();

                if (User.update(user_id, user_name, user_uname, user_pass, user_type)) {
                    Helper.showMsg("done");

                }
                loadUserModel();
                loadEducatorCombo();
                //Kullanıcı adı güncellendiğinde eğitmen adı güncellenecek
                loadCourseModel();
            }
        });
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);


        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patila_list.getValueAt(tbl_patila_list.getSelectedRow(), 0).toString());
            UpdatePatikaGUI updateGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();

                }
            });
        });
        //delete dinleme
        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(tbl_patila_list.getValueAt(tbl_patila_list.getSelectedRow(), 0).toString());
                if (Patika.delete(select_id)) {
                    Helper.showMsg("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();


        tbl_patila_list.setModel(mdl_patika_list);
        tbl_patila_list.setComponentPopupMenu(patikaMenu);
        tbl_patila_list.getTableHeader().setReorderingAllowed(false);
        tbl_patila_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_patila_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patila_list.rowAtPoint(point);
                tbl_patila_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];
        loadCourseModel();

        table_course_list.setModel(mdl_course_list);
        table_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        table_course_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaCombo();
        loadEducatorCombo();

        button_useradd.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_user_name) || Helper.isFieldEmpty(field_user_name) || Helper.isFieldEmpty(field_pass)) {
                Helper.showMsg("fill");
            } else {
                String name = field_user_name.getText();
                String uname = fld_uname.getText();
                String pass = field_pass.getText();
                String type = cmb_usertypr.getSelectedItem().toString();
                if (User.add(name, uname, pass, type)) {
                    Helper.showMsg("done");
                    loadUserModel();
                    loadEducatorCombo();
                    field_user_name.setText(null);
                    fld_uname.setText(null);
                    field_pass.setText(null);
                }
            }
        });
        button_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(field_user_ıd)) {
                    Helper.showMsg("fill");
                } else {
                    if (Helper.confirm("sure")) {
                        int user_ıd = Integer.parseInt(field_user_ıd.getText());
                        if (User.delete(user_ıd)) {
                            Helper.showMsg("done");
                            loadUserModel();
                            loadEducatorCombo();
                            loadCourseModel();
                            field_user_ıd.setText(null);
                        } else {
                            Helper.showMsg("error");
                        }

                    }
                }
            }
        });

        button_user_search.addActionListener(e -> {
            String name = fld_search_name.getText();
            String uname = field_search_uname.getText();
            String type = cmb_search_type.getSelectedItem().toString();
            String query = User.searchQuery(name, uname, type);
            loadUserModel(User.searchUserList(query));

        });

        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_patika_name)) {
                Helper.showMsg("fill");
            }else {
                if (Patika.add(field_patika_name.getText())){
                    Helper.showMsg("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    field_patika_name.setText(null);
                }else{
                    Helper.showMsg("error");
                }

            }

        });
        bttn_course_add.addActionListener(e -> {
            Item patikaItem =(Item) combobox_course_patika.getSelectedItem();
            Item userItem=(Item) comboBox_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(field_course_name)||Helper.isFieldEmpty(field_cours_lang)){
                Helper.showMsg("fill");
            }else{
                if (Course.add(userItem.getKey(),patikaItem.getKey(),field_course_name.getText(),field_cours_lang.getText())){
                    Helper.showMsg("done");
                    loadCourseModel();
                    field_cours_lang.setText(null);
                    field_course_name.setText(null);
                }else{
                    Helper.showMsg("error");
                }
            }

        });
        btn_logout.addActionListener(new ActionListener() {

            //çıkış yap tuşunu aktif etme
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI login =new LoginGUI();
            }
        });
    }
    private void loadCourseModel() {
        DefaultTableModel clearModel=(DefaultTableModel) table_course_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Course obj: Course.getList()){
            i=0;
            row_course_list[i++]=obj.getId();
            row_course_list[i++]=obj.getName();
            row_course_list[i++]=obj.getLang();
            row_course_list[i++]=obj.getPatika().getName();
            row_course_list[i++]=obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_patila_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Patika obj: Patika.getList()){
            i=0;
            row_patika_list[i++]=obj.getId();
            row_patika_list[i++]=obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void loadUserModel(){
        DefaultTableModel clearModel=(DefaultTableModel) table_userlist.getModel();
        clearModel.setRowCount(0);

        for (User obj: User.getList()){
           int i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUname();
            row_user_list[i++]=obj.getPass();
            row_user_list[i++]=obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }

    public void loadUserModel(ArrayList<User>list){

        DefaultTableModel clearModel=(DefaultTableModel) table_userlist.getModel();
        clearModel.setRowCount(0);

        for (User obj: list){
            int i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUname();
            row_user_list[i++]=obj.getPass();
            row_user_list[i++]=obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }
    public void loadPatikaCombo(){
        combobox_course_patika.removeAllItems();
        for (Patika obj:Patika.getList()){
            combobox_course_patika.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
    public void loadEducatorCombo(){
        comboBox_course_user.removeAllItems();
        for (User obj:User.getListOnlyEducator()){
                comboBox_course_user.addItem(new Item(obj.getId(),obj.getName()));
            }
        }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op=new Operator();
        op.setId(1);
        op.setName("Haticenur DİNÇEL ŞEN");
        op.setPass("1234");
        op.setType("operator");
        op.setUname("haticenur");

        OperatorGUI opGUI=new OperatorGUI(op);
    }
}
