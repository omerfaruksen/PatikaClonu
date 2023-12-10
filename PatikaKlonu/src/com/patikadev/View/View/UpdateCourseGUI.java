package com.patikadev.View.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;

import javax.swing.*;
    public class UpdateCourseGUI extends JFrame {
        private JPanel wrapper;
        private JTextField fld_course_name;
        private JTextField fld_course_lang;
        private JButton btn_update;
        private JButton btn_back;
        private Course course;
        private Operator operator;

        public UpdateCourseGUI(Course course,Operator operator) {
            this.operator=operator;
            this.course = course;
            add(wrapper);
            setSize(600, 600);
            setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
            setTitle(Config.PROJECT_TITLE);
            setVisible(true);
            Helper.setLayout();
            fld_course_name.setText(course.getName());
            fld_course_lang.setText(course.getLang());

            btn_update.addActionListener(e -> {
                if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_lang)) {
                    Helper.showMsg("fill");
                } else {
                    if (Course.update(fld_course_name.getText(), fld_course_lang.getText(),this.course.getId())) {
                        Helper.showMsg("done");
                    }
                    dispose();
                }
            });
            btn_back.addActionListener(e -> {
                OperatorGUI operatorGUI= new OperatorGUI(this.operator);
                dispose();
            });
        }
    }