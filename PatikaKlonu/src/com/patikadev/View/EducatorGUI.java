package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_exits;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrl_content_list;
    private JTable tbl_content_list;
    private JLabel fld_wlcm;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private  Educator educator;

    public EducatorGUI(Educator educator){
        this.educator=educator;
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_wlcm.setText("Hoşgeldin : " +educator.getName());


        //***********************Model Content List**************************
        mdl_content_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_content_list={"ID","Patika","İçerik Başlığı","İçerik Açıklaması","İçerik Linki","Eğitmen"};

        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list=new Object[col_content_list.length];
        loadContentModel();
        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);


        btn_exits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI login=new LoginGUI();
            }
        });
    }
    public void loadContentModel(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        for (Content obj: Content.getContentList()){
            int i=0;
            row_content_list[i++]=obj.getId();
            row_content_list[i++]=obj.getPatika();
            row_content_list[i++]=obj.getCont_name();
            row_content_list[i++]=obj.getCont_inf();
            row_content_list[i++]=obj.getCont_link();
            row_content_list[i++]=obj.getEducator();
            mdl_content_list.addRow(row_content_list);


        }
    }


}
