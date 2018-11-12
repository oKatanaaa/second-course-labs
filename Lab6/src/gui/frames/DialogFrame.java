package gui.frames;

import gui.components.DialogComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEGHT = 200;

    public DialogFrame(){
        super.setTitle("DialogTest");
        super.setSize(DEFAULT_WIDTH, DEFAULT_HEGHT);
        gui.components.DialogComponent dialogComponent = new gui.components.DialogComponent(this);
        dialogComponent.setVisible(true);
        JButton button = new JButton("Show dialog");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(dialogComponent.showDialog());
            }
        });
        super.add(button);
    }
}
