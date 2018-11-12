package gui.frames.dialogTableFrame;

import functions.FunctionPoint;
import functions.FunctionPointIndexOutOfBoundsException;
import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDeletePointPanel extends JPanel {
    private JTextField xField;
    private JTextField yField;

    private DialogTableFrame owner;

    public AddDeletePointPanel(DialogTableFrame owner) {
        setLayout(new BorderLayout());
        this.owner = owner;
        // SET LEFT PART OF THE PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));

        // Set first text field
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.add(new JLabel("New point x: "), BorderLayout.WEST);
        xField = new JTextField("0");
        xField.setColumns(10);
        firstPanel.add(xField, BorderLayout.EAST);
        firstPanel.revalidate();

        // Set second text field
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());
        secondPanel.add(new JLabel("New point y: "), BorderLayout.WEST);
        yField = new JTextField("10");
        yField.setColumns(10);
        secondPanel.add(yField, BorderLayout.EAST);
        secondPanel.revalidate();

        leftPanel.add(firstPanel);
        leftPanel.add(secondPanel);
        add(leftPanel, BorderLayout.WEST);

        // SET RIGHT PART OF THE PANEL
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 1));

        // Set add point button
        JButton addPointBtn = new JButton("Add point");
        addPointBtn.addActionListener(new AddListener());
        rightPanel.add(addPointBtn);

        // Set delete point button
        JButton deletePointBtn = new JButton("Delete point");
        deletePointBtn.addActionListener(new DeleteListener());
        rightPanel.add(deletePointBtn);

        add(rightPanel, BorderLayout.EAST);
    }

    private class DeleteListener implements ActionListener {
        DialogTableFrame outer = AddDeletePointPanel.this.owner;
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int rowIndex = outer.table.getSelectedRow();
            try {
                outer.functionDocument.deletePoint(rowIndex);
                outer.table.revalidate();
                outer.table.repaint();
            } catch (FunctionPointIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(outer, "Can't delete point.");
            }
        }
    }

    private class AddListener implements ActionListener {
        DialogTableFrame outer = AddDeletePointPanel.this.owner;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                //System.out.println(outer.functionDocument);
                outer.functionDocument.addPoint(new FunctionPoint(x, y));
                //System.out.println(outer.functionDocument);
                outer.table.revalidate();
                outer.table.repaint();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(outer, "Incorrect point data.");
            } catch (InappropriateFunctionPointException e) {
                JOptionPane.showMessageDialog(outer, "Point already is in the table.");
            }

        }
    }
}
