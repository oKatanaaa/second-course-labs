package gui;

import javax.swing.*;

public class TabulateParamsWindow extends JDialog {
    TabulateParamsWindow(JFrame owner) {
        super(owner, "Function parameters");
        super.setModal(true);
        super.setResizable(false);
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        JTextField leftDBField = new JTextField("0");
        super.add(leftDBField);
    }
}
