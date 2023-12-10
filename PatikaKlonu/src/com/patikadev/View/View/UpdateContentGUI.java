package com.patikadev.View.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;

import javax.swing.*;

public class UpdateContentGUI extends JFrame {
    private Content content;
    private JPanel wrapper;
    private JTextField fld_content_name;
    private JButton btn_update;
    private JTextField fld_content_description;
    private JTextField fld_content_link;

    public UpdateContentGUI(Content content) {
        this.content = content;
        add(wrapper);
        setSize(600, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_content_name.setText(content.getName());
        fld_content_description.setText(content.getDescription());
        fld_content_link.setText(content.getLink());

        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_name) || Helper.isFieldEmpty(fld_content_description) || Helper.isFieldEmpty(fld_content_link))
            {
                Helper.showMsg("fill");
            }
            else{
                if (Content.update(content.getId(), fld_content_name.getText(), fld_content_description.getText(), fld_content_link.getText()))
                {
                    Helper.showMsg("done");
                }
                dispose();
            }
        });
    }
}