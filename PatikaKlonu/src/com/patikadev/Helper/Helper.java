package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }
    public static int screenCenterPoint(String coordinate, Dimension size){
        int point;
        switch (coordinate){
            case "x":
                point=(Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y":
                point=(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point=0;


        }
        return point;

    }
    public static boolean isfieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();

    }
    public static void showMessage(String str){
        optionPageTR();
        String msg;
        String title;
        switch (str){
            case "fill":
                msg="Lütfen tüm alanları doldurunuz!";
                title="HATA!";
                break;
            case "done":
                msg="İşlem başarılı";
                title="Olumlu";
                break;
            case "error":
                msg="Bir Hata Oluştu";
                title="Error!";
                break;
            default:
                msg=str;
                title="Mesaj";

        }
        JOptionPane.showMessageDialog(null,msg, title ,JOptionPane.INFORMATION_MESSAGE);
    }
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
    }

    public static boolean confirm(String str) {
        String msg;
        switch (str){
            case "sure":
                msg="Bu işlemi gerçekleştirmek istediğinize emin misiniz ?";
                break;
            default:
                msg=str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Son kararın mı ?",JOptionPane.YES_NO_OPTION)==0;

    }
    public static void optionPaneTR(){
        UIManager.put("optionPane.okButtonText","Tamam");
        UIManager.put("optionPane.yesButtonText","Evet");
        UIManager.put("optionPane.noButtonText","Hayır");
    }
}
