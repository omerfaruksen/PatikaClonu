package com.patikadev.View.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.MyCourse;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentGUI extends JFrame {
        private JPanel wrapper;
        private JButton btn_logout;
        private JTabbedPane tabbed;
        private JPanel pnl_patika_list;
        private JPanel pnl_course_list;
        private JScrollPane scrl_patika_list;
        private JTable tbl_patika_list;
        private JTable tbl_mycourse_list;
        private JScrollPane scrl_course_list;

        private DefaultTableModel mdl_patika_list;
        private DefaultTableModel mdl_mycourse_list;

        private Object[] row_patika_list;
        private Object[] row_mycourseList;
        private JPopupMenu patikaMenu;
        private JPopupMenu CourseMenu;
        private Student student;

        public StudentGUI(Student student) {
            this.student=student;
            add(wrapper);
            setSize(600, 600);
            setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle(Config.PROJECT_TITLE);
            setVisible(true);
            setResizable(false);

            btn_logout.addActionListener(e -> {
                dispose();
                LoginGUI login = new LoginGUI();
            });

            mdl_patika_list = new DefaultTableModel();
            Object[] col_patikaList = {"ID", "name"};
            mdl_patika_list.setColumnIdentifiers(col_patikaList);
            row_patika_list = new Object[col_patikaList.length];
            loadPatikaModel();
            tbl_patika_list.setModel(mdl_patika_list);
            tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
            tbl_patika_list.getTableHeader().setReorderingAllowed(false);

            mdl_mycourse_list = new DefaultTableModel();
            Object[] col_mypatikaList = {"ID", "course_id", "course_name"};
            mdl_mycourse_list.setColumnIdentifiers(col_mypatikaList);
            row_mycourseList = new Object[col_mypatikaList.length];
            loadMyCourseModel();
            tbl_mycourse_list.setModel(mdl_mycourse_list);
            tbl_mycourse_list.getColumnModel().getColumn(0).setMaxWidth(75);
            tbl_mycourse_list.getTableHeader().setReorderingAllowed(false);
            tbl_patika_list.addMouseListener(new MouseAdapter() {
            });

            patikaMenu = new JPopupMenu();
            JMenuItem addMenu = new JMenuItem("Derse kayıt ol");
            patikaMenu.add(addMenu);

            tbl_patika_list.setComponentPopupMenu(patikaMenu);

            addMenu.addActionListener(e -> {
                int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
                CourseRegisterGUI courseRegisterGUI = new CourseRegisterGUI(Patika.getFetch(select_id),this.student);
                courseRegisterGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadPatikaModel();
                    }
                });
            });
            tbl_patika_list.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point point = e.getPoint();
                    int selected_row = tbl_patika_list.rowAtPoint(point);
                    tbl_patika_list.setRowSelectionInterval(selected_row, selected_row);
                }
            });
            CourseMenu = new JPopupMenu();
            JMenuItem contentmenu = new JMenuItem("Ders içeriklerine git");
            CourseMenu.add(contentmenu);

            tbl_mycourse_list.setComponentPopupMenu(CourseMenu);

            tbl_mycourse_list.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point point = e.getPoint();
                    int selected_row = tbl_mycourse_list.rowAtPoint(point);
                    tbl_mycourse_list.setRowSelectionInterval(selected_row, selected_row);
                }
            });

            contentmenu.addActionListener(e -> {
                int select_id = Integer.parseInt(tbl_mycourse_list.getValueAt(tbl_mycourse_list.getSelectedRow(), 1).toString());
                CourseContentGUI courseContentGUI = new CourseContentGUI(Course.getFetch(select_id),this.student);
                courseContentGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {

                    }
                });

            });
        }
        private void loadPatikaModel() {
            DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
            clearModel.setRowCount(0);
            int i = 0;
            for (Patika obj : Patika.getList()) {
                i = 0;
                row_patika_list[i++] = obj.getId();
                row_patika_list[i++] = obj.getName();
                mdl_patika_list.addRow(row_patika_list);
            }
        }
        private void loadMyCourseModel() {
            DefaultTableModel clearModel = (DefaultTableModel) tbl_mycourse_list.getModel();
            clearModel.setRowCount(0);
            int i = 0;
            for (MyCourse obj : MyCourse.getMyCourseListByUser(this.student.getId())) {
                i = 0;
                row_mycourseList[i++] = obj.getId();
                row_mycourseList[i++] = obj.getCourse_id();
                row_mycourseList[i++] = obj.getCourse().getName();
                mdl_mycourse_list.addRow(row_mycourseList);
            }
        }
    }