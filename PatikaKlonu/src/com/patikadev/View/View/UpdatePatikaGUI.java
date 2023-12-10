package com.patikadev.View.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;

import javax.swing.*;
public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField field_patika_name;
    private JButton button_update;
    private Patika patika;
    public UpdatePatikaGUI(Patika patika){
        this.patika=patika;
        add(wrapper);
        setSize(600,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        field_patika_name.setText(patika.getName());
        button_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(field_patika_name)){
                Helper.showMsg("fill");
            }else{
                if (Patika.update(patika.getId(),field_patika_name.getText())){
                    Helper.showMsg("done");
                }
                dispose();
            }
        });
    }
}
