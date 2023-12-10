package com.patikadev.View.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Quiz;

import javax.swing.*;

    public class UpdateQuizGUI extends JFrame {
        private JTextField fld_quiz_name;
        private JPanel wrapper;
        private JButton btn_save;
        Quiz quiz;

        public UpdateQuizGUI(Quiz quiz) {
            this.quiz = quiz;
            add(wrapper);
            setSize(600, 600);
            setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
            setTitle(Config.PROJECT_TITLE);
            setVisible(true);

            fld_quiz_name.setText(quiz.getQuestion());

            btn_save.addActionListener(e -> {
                if (Helper.isFieldEmpty(fld_quiz_name)) {
                    Helper.showMsg("fill");
                } else {
                    if (Quiz.update(fld_quiz_name.getText(), quiz.getId())) {

                        Helper.showMsg("done");
                    }
                    dispose();
                }
            });
        }
    }