package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DialogComponent extends JDialog {
    public static final int OK = 1;
    public static final int CANCEL = 0;
    private int status;

    // Left domain border
    private JTextField textLDB;
    // Right domain border
    private JTextField textRDB;
    private JSpinner pointsCount;

    public DialogComponent(JFrame owner) {
        super(owner, "Function parameters", true);
        super.setSize(300, 200);
        super.setResizable(false);
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        super.setLayout(new GridLayout(4, 1));

        // Set first text field
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.add(new JLabel("Left domain border: "), BorderLayout.WEST);
        textLDB = new JTextField("0");
        textLDB.setColumns(10);
        firstPanel.add(textLDB, BorderLayout.EAST);
        firstPanel.revalidate();

        // Set second text field
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());
        secondPanel.add(new JLabel("Right domain border: "), BorderLayout.WEST);
        textRDB = new JTextField("10");
        textRDB.setColumns(10);
        secondPanel.add(textRDB, BorderLayout.EAST);
        secondPanel.revalidate();

        // Set spinner
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BorderLayout());
        thirdPanel.add(new JLabel("Points count: "), BorderLayout.WEST);
        pointsCount = new JSpinner();
        pointsCount.setModel(new SpinnerNumberModel(11, 2, Integer.MAX_VALUE, 1));
        // Change spinner's size
        JFormattedTextField temp = ((JSpinner.DefaultEditor) pointsCount.getEditor()).getTextField();
        temp.setColumns(8);
        thirdPanel.add(pointsCount, BorderLayout.EAST);
        thirdPanel.revalidate();

        // Set buttons
        // Button Cancel
        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(new BorderLayout());
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(actionEvent -> {
            DialogComponent.this.status = DialogComponent.this.CANCEL;
            DialogComponent.this.setVisible(false);
        });
        fourthPanel.add(buttonCancel, BorderLayout.WEST);
        // Button Ok
        JButton buttonOk = new JButton("Ok");
        buttonOk.addActionListener(actionEvent -> {
            DialogComponent.this.status = DialogComponent.this.OK;

            try{
                Double.parseDouble(textLDB.getText());
                Double.parseDouble(textRDB.getText());
                DialogComponent.this.setVisible(false);
            } catch(NumberFormatException e){
                JOptionPane.showMessageDialog(DialogComponent.this, "Incorrect values!");
            }

        });
        fourthPanel.add(buttonOk, BorderLayout.EAST);

        super.add(firstPanel);
        super.add(secondPanel);
        super.add(thirdPanel);
        super.add(fourthPanel);

        super.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                DialogComponent.this.status = DialogComponent.this.CANCEL;
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
    }

    public double getLeftDomainBorder() {
        return Double.parseDouble(textLDB.getText());
    }

    public double getRightDomainBorder() {
        return Double.parseDouble(textRDB.getText());
    }

    public int getPointsCount(){
        return (int) pointsCount.getValue();
    }

    public int showDialog(){
        super.setVisible(true);
        while(super.isVisible());
        return this.status;
    }

}
