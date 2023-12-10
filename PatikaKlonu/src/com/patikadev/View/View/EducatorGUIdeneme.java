package com.patikadev.View.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class EducatorGUIdeneme extends JFrame {
    private JPanel wrapper;
    private JButton btn_logout;
    private JTabbedPane tabbed;
    private JPanel pnl_course_list;
    private JScrollPane scrol_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_content_list;
    private JScrollPane scrl_content_list;
    private JPanel pnl_education_add;
    private JTable
            tbl_content_list;
    private JButton btn_add_content;
    private JComboBox cmb_course_list;
    private JTextField fld_content_title;
    private JTextField fld_content_caption;
    private JTextField fld_youtube_link;
    private JTable tbl_quiz;
    private JButton btn_question_add;
    private JComboBox cmb_questions_content;
    private JTextField fld_quiz_question;
    private JButton btn_delete;
    private JTextField fld_content_id;
    private JPanel pnl_quiz_list;
    private JTextField src_content_title;
    private JComboBox src_cmb_course;
    private JButton src_btn;
    private Object[] row_courseList;
    private DefaultTableModel mdl_course_list;
    private DefaultTableModel mdl_content_list;
    private DefaultTableModel mdl_quiz_list;

    private Educator educator;
    private Object[] row_content_list;
    private Object[] row_quiz_list;
    private JPopupMenu contentMenu;


    public EducatorGUIdeneme(Educator educator) {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }}
        this.educator = educator;
        add(wrapper);
        setSize(600, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle(Config.PROJECT_TITLE);
        Helper.setLayout();

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });


        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Ders Adı", "Programlama Dili", "Dersin Patikası", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_courseList = new Object[col_courseList.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        mdl_content_list = new DefaultTableModel();
        Object[] col_contentList = {"ID", "İçerik Adı", "Açıklama", "Link", "Ders Adı"};
        mdl_content_list.setColumnIdentifiers(col_contentList);
        row_content_list = new Object[col_contentList.length];
        loadContentModel();
        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);


        mdl_quiz_list = new DefaultTableModel();
        Object[] col_quizList = {"ID", "İçerik Adı", "Quiz Sorusu"};
        mdl_quiz_list.setColumnIdentifiers(col_quizList);
        row_quiz_list = new Object[col_quizList.length];
        loadQuestionModel();
        tbl_quiz.setModel(mdl_quiz_list);
        tbl_quiz.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_quiz.getTableHeader().setReorderingAllowed(false);

        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_content_id = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString();
                fld_content_id.setText(selected_content_id);

            } catch (Exception ex) {

            }

        });

        btn_add_content.addActionListener(e -> {
            Item courseItem = (Item) cmb_course_list.getSelectedItem();

            if (Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_content_caption) || Helper.isFieldEmpty(fld_youtube_link)) {
                Helper.showMsg("fill");
            } else {
                if (Content.add(fld_content_title.getText(), fld_content_caption.getText(), fld_youtube_link.getText(), courseItem.getKey())) {
                    Helper.showMsg("done");
                    loadContentModel();
                    fld_content_caption.setText(null);
                    fld_content_title.setText(null);
                    fld_youtube_link.setText(null);

                } else {
                    Helper.showMsg("error");
                }

            }

        });

        btn_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    if (Content.delete(Integer.parseInt(fld_content_id.getText()))) {
                        Helper.showMsg("done");

                        loadContentModel();
                        loadQuestionModel();
                        fld_content_id.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }

        });

        contentMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        contentMenu.add(updateMenu);

        tbl_content_list.setComponentPopupMenu(contentMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            UpdateContentGUI updateGUI = new UpdateContentGUI(Content.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadContentModel();
                }
            });

        });
        tbl_content_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_content_list.rowAtPoint(point);
                tbl_content_list.setRowSelectionInterval(selected_row, selected_row);

            }
        });

        btn_question_add.addActionListener(e -> {

            Item ContentItem = (Item) cmb_questions_content.getSelectedItem();

            if (Helper.isFieldEmpty(fld_quiz_question)) {
                Helper.showMsg("fill");
            } else {
                if (Quiz.add(fld_quiz_question.getText(), ContentItem.getKey())) {
                    Helper.showMsg("done");
                    loadQuestionModel();
                    fld_quiz_question.setText(null);

                } else {
                    Helper.showMsg("error");
                }

            }
        });

        src_btn.addActionListener(e -> {
            ArrayList<Content> searchingContent;
            String name = src_content_title.getText();
            Item CoursetItem = (Item) src_cmb_course.getSelectedItem();
            int courseId = CoursetItem.getKey();
            String query = Content.searchQuery(name, courseId);
            searchingContent = Content.SearchContentList(query);
            loadContentModelforSearch(searchingContent);
        });
    }
    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        cmb_course_list.removeAllItems();
        src_cmb_course.removeAllItems();
        int i = 0;
        for (Course obj : Course.getListByUser(this.educator.getId())) {
            i = 0;
            row_courseList[i++] = obj.getId();
            row_courseList[i++] = obj.getName();
            row_courseList[i++] = obj.getLang();
            row_courseList[i++] = obj.getPatika().getName();
            row_courseList[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_courseList);
            cmb_course_list.addItem(new Item(obj.getId(), obj.getName()));
            src_cmb_course.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
    public void loadContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        cmb_questions_content.removeAllItems();
        int i = 0;
        for (Course course : Course.getListByUser(this.educator.getId())) {
            for (Content obj : Content.getListContentByCourse(course.getId())) {
                i = 0;
                row_content_list[i++] = obj.getId();
                row_content_list[i++] = obj.getName();
                row_content_list[i++] = obj.getDescription();
                row_content_list[i++] = obj.getLink();
                row_content_list[i++] = obj.getCourse().getName();
                mdl_content_list.addRow(row_content_list);
                cmb_questions_content.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }

    public void loadContentModelforSearch(ArrayList<Content> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);

        for (Content obj : list) {
            int i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getName();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = obj.getLink();
            row_content_list[i++] = obj.getCourse().getName();
            mdl_content_list.addRow(row_content_list);
        }
    }

    public void loadQuestionModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Course course : Course.getListByUser(this.educator.getId())) {
            for (Content content : Content.getListContentByCourse(course.getId())) {
                for (Quiz obj : Quiz.getListQuizByContent(content.getId())) {
                    i = 0;
                    row_quiz_list[i++] = obj.getId();
                    row_quiz_list[i++] = obj.getContent().getName();
                    row_quiz_list[i++] = obj.getQuestion();
                    mdl_quiz_list.addRow(row_quiz_list);
                }
            }
        }
    }
}