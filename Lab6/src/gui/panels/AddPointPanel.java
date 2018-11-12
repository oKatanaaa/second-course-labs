package gui.panels;

import javax.swing.*;
import java.awt.*;

public class AddPointPanel extends JPanel {
    private JTextField xField;
    private JTextField yField;

    public AddPointPanel() {
        setLayout(new BorderLayout());

        // SET LEFT PART OF THE PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));

        // Set first text field
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.add(new JLabel("Left domain border: "), BorderLayout.WEST);
        xField = new JTextField("0");
        xField.setColumns(10);
        firstPanel.add(xField, BorderLayout.EAST);
        firstPanel.revalidate();

        // Set second text field
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());
        secondPanel.add(new JLabel("Right domain border: "), BorderLayout.WEST);
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
        rightPanel.add(addPointBtn);

        // Set delete point button
        JButton deletePointBtn = new JButton("Delete point");
        rightPanel.add(deletePointBtn);

        add(rightPanel, BorderLayout.EAST);

    }
}
