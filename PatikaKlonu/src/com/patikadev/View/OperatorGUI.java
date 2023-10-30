package com.patikadev.View;
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
    private  JPanel wrapper;
    private JTabbedPane tabb_patika;
    private JLabel fld_wlcome;
    private JButton btn_exist;
    private JScrollPane scrl_userList;
    private JTable tbl_userList;
    private JLabel fld_name;
    private JTextField fld_Name;
    private JTextField fld_userName;
    private JPasswordField fld_password;
    private JLabel fld_type;
    private JComboBox cmb_usertype;
    private JButton btn_userAdd;
    private JTextField fld_userid;
    private JButton btn_user_delete;
    private JTextField fld_srch;
    private JTextField fld_search_user;
    private JComboBox cmb_src_type;
    private JButton btn_user_search;
    private JScrollPane scrollPane;
    private JTable table_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JScrollPane ScroolPane;
    private JTable tbl_courses_list;
    private JPanel pnl_user_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private DefaultTableModel mdl_userList;
    private Object[] row_user_list;
    private final Operator operator;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object [] row_course_list;
    public OperatorGUI(Operator operator){
        this.operator=operator;
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_wlcome.setText("Hoşgeldin : "+operator.getName());

        //ModelUserList

        mdl_userList=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };


        Object[] col_user_list={"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik Tipi"};
        mdl_userList.setColumnIdentifiers(col_user_list);
        row_user_list=new Object[col_user_list.length];
        loadUserMode();

        tbl_userList.setModel(mdl_userList);
        tbl_userList.getTableHeader().setReorderingAllowed(false);

        tbl_userList.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_user_id=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),0).toString();
                fld_userid.setText(selected_user_id);
            }catch (Exception exception){

            }

        });
        tbl_userList.getModel().addTableModelListener(e -> {
            if (e.getType()==TableModelEvent.UPDATE){
                int user_id=Integer.parseInt(tbl_userList.getValueAt(tbl_userList.getSelectedRow(),0).toString());
                String user_name=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),1).toString();
                String user_username=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),2).toString();
                String user_password=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),3).toString();
                String user_type=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),4).toString();

                if (User.uptade(user_id,user_name,user_username,user_password,user_type)){
                    Helper.showMessage("done");
                }
                loadUserMode();
                loadEducatorCombo();
                loadCourseModel();
            }
        });
        //MODEL PATİKA LİST
        patikaMenu=new JPopupMenu();//PopupMenü ***********************************************************************
        JMenuItem updateMenu=new JMenuItem("Güncelle");
        JMenuItem deleteMenu=new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id=Integer.parseInt(table_patika_list.getValueAt(table_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI updateGUI =new UpdatePatikaGUI(Patika.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });
        //SİLME İŞLEMİ************************************************************************************************
        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure"));
            int select_id=Integer.parseInt(table_patika_list.getValueAt(table_patika_list.getSelectedRow(),0).toString());
            if (Patika.delete(select_id)){
                Helper.showMessage("done");
                loadPatikaModel();
                loadPatikaCombo();
                loadCourseModel();
            }else {
                Helper.showMessage("error");
            }
        });





        mdl_patika_list=new DefaultTableModel();
        Object[] col_patika_list={"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list=new Object[col_patika_list.length];
        loadPatikaModel();

        table_patika_list.setModel(mdl_patika_list);
        table_patika_list.setComponentPopupMenu(patikaMenu);//PopupMenu entegre etme ***********************************
        table_patika_list.getTableHeader().setReorderingAllowed(false);
        table_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);//Sutunları genişliğini ayarlama

        table_patika_list.addMouseListener(new MouseAdapter() {//Popupmenu seçimleri mause listeneer satır seçme
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                Point point=e.getPoint();
                int selected_row=table_patika_list.rowAtPoint(point);
                table_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        //  MODEL PATİKA LİST
//COURSELİST*************************************************************************************************/COURSELİST
        mdl_course_list =new DefaultTableModel();
        Object[] col_courselist={"ID","DERS ADI","PROGRAMLAMA DİLİ","PATİKA","EGİTMEN"};
        mdl_course_list.setColumnIdentifiers(col_courselist);
        row_course_list=new Object[col_courselist.length];
        loadCourseModel();
        tbl_courses_list.setModel(mdl_course_list);
        tbl_courses_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_courses_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaCombo();//****************************************************************************Combo_Box_Patika
        loadEducatorCombo();
        /*cmb_course_patika.addItem(new Item(1,"1.Eleman"));
        cmb_course_patika.addItem(new Item(2,"2.Eleman"));
        cmb_course_patika.addItem(new Item(3,"3.Eleman"));*/

//CORUSELİST**************************************************************************************************COURSELİST
        btn_userAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isfieldEmpty(fld_userName)||Helper.isfieldEmpty(fld_Name)||Helper.isfieldEmpty(fld_password)){
                    //fld_userName.getText().length()==0 || fld_Name.getText().length()==0|| fld_password.getPassword().length==0
                    //JOptionPane.showMessageDialog(null,"Lütfen gerekli alanları doldurunuz.","Erorr :",JOptionPane.INFORMATION_MESSAGE);
                    Helper.showMessage("fill");
                }else{
                    String name=fld_Name.getText();
                    String username=fld_userName.getText();
                    String password=fld_password.getText();
                    String type=cmb_usertype.getSelectedItem().toString();
                    if (User.add(name,username,password,type)){
                        Helper.showMessage("done");
                        loadUserMode();
                        loadEducatorCombo();
                        fld_userName.setText(null);
                        fld_Name.setText(null);
                        fld_password.setText(null);
                    }
                }
            }
        });
        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isfieldEmpty(fld_userid)){
                    Helper.showMessage("fill");
                }else{
                    if (Helper.confirm("sure")){
                        int fld_user_id=Integer.parseInt(fld_userid.getText());
                        if (User.delete(fld_user_id)){
                            Helper.showMessage("done");
                            loadUserMode();
                            loadEducatorCombo();
                            loadCourseModel();
                            fld_userid.setText(null);
                        }else{
                            Helper.showMessage("error");
                        }
                    }
                }
            }
        });
        btn_user_search.addActionListener(e -> {
            String name=fld_srch.getText();
            String username=fld_search_user.getText();
            String type=cmb_src_type.getSelectedItem().toString();
            String query=User.searchQeury(name,username,type);
            loadUserMode(User.searchUserList(query));

        });
        btn_exist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI login=new LoginGUI();
            }
        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isfieldEmpty(fld_patika_name)){
                Helper.showMessage("fill");
            }else{
                if (Patika.add(fld_patika_name.getText())){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_name.setText(null);
                }else{
                    Helper.showMessage("error");
                }
            }
        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem= (Item) cmb_course_patika.getSelectedItem();
            Item userItem=(Item) cmb_course_user.getSelectedItem();
            if (Helper.isfieldEmpty(fld_course_name)|| Helper.isfieldEmpty(fld_course_lang)){
                Helper.showMessage("fill");
            }else {
                if (Course.add(userItem.getKey(),patikaItem.getKey(),fld_course_name.getText(),fld_course_lang.getText())){
                    Helper.showMessage("done");
                    loadCourseModel();
                    fld_course_lang.setText(null);
                    fld_course_name.setText(null);
                }else {
                    Helper.showMessage("error");
                }
            }
        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_courses_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Course obj: Course.getList()){
            i=0;
            row_course_list[i++]=obj.getId();
            row_course_list[i++]=obj.getName();
            row_course_list[i++]=obj.getLanguage();
            row_course_list[i++]=obj.getPatika().getName();
            row_course_list[i++]=obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    //PatikaMODEL
    private void loadPatikaModel() {
        DefaultTableModel clearModel= (DefaultTableModel) table_patika_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Patika obj : Patika.getList()){
            i=0;
            row_patika_list[i++]=obj.getId();
            row_patika_list[i++]=obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void loadUserMode(){

        DefaultTableModel clearModel= (DefaultTableModel) tbl_userList.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getList()){
            int i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUsername();
            row_user_list[i++]=obj.getPassword();
            row_user_list[i++]=obj.getType();
            mdl_userList.addRow( row_user_list);

        }
    }
    public void loadUserMode(ArrayList<User> list){

        DefaultTableModel clearModel= (DefaultTableModel) tbl_userList.getModel();
        clearModel.setRowCount(0);

        for (User obj : list){
            int i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUsername();
            row_user_list[i++]=obj.getPassword();
            row_user_list[i++]=obj.getType();
            mdl_userList.addRow( row_user_list);

        }
    }
    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for (Patika obj:Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(),obj.getName()));
        }
    }

    public void loadEducatorCombo(){
        cmb_course_user.removeAllItems();
        for (User obj : User.getListOnlyEducator()){
            cmb_course_user.addItem(new Item(obj.getId(), obj.getName()));
    //İf bloğunu yorum satırına aldık çünkü; user içersinde getListOnlyEducator() methodu oluşturduk ve for döngüsü içerisinde,
    // User.getList yerine getListOnlyEducator() yazarak aynı işlemin yapılmasını sağladık.
           /* if (obj.getType().equals("educator")){
                cmb_course_user.addItem(new Item(obj.getId(), obj.getName()));
            }*/

        }
    }
    public static void main(String[] args) {
        Helper.setLayout();
        Operator op=new Operator();
        op.setId(1);
        op.setName("Ömer Faruk");
        op.setPassword("123456");
        op.setType("operator");
        op.setUsername("OFS");
        OperatorGUI opGUI=new OperatorGUI(op);


    }

}
