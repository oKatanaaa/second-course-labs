package gui.frames;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;
import gui.components.DialogComponent;
import gui.models.TabulatedFunctionTableModel;
import gui.panels.AddPointPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogTableFrame extends JFrame {

    private DialogComponent dialogComponent;
    private JFileChooser fileChooser;
    private TabulatedFunctionTableModel tableModel;
    private JTable table;

    public DialogTableFrame(TabulatedFunction fun) {
        setLayout(new BorderLayout());
        setResizable(true);
        setSize(600, 400);
        setVisible(true);
        setResizable(true);

        fileChooser = new JFileChooser();
        this.dialogComponent = new DialogComponent(this);

        setUpMenuBar();
        // Add table
        this.table = new JTable();
        this.tableModel = new TabulatedFunctionTableModel(fun, this);
        table.setModel(this.tableModel);
        // Add created table to scroll panel
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add point reduction menu
        AddPointPanel addPointPanel = new AddPointPanel();
        add(addPointPanel, BorderLayout.SOUTH);

    }

    private void setUpMenuBar(){
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Set up file menu
        JMenu fileMenu = new JMenu("File");

        JMenuItem newDoc = new JMenuItem("New document");
        newDoc.addActionListener(new NewDocListener());

        JMenuItem openDoc = new JMenuItem("Open document");
        openDoc.addActionListener(new OpenDocListener());

        JMenuItem saveDoc = new JMenuItem("Save document");
        saveDoc.addActionListener(new SaveDocListener());

        JMenuItem saveDocAs = new JMenuItem("Save document as");
        saveDocAs.addActionListener(new SaveDocAsListener());

        fileMenu.add(newDoc);
        fileMenu.add(openDoc);
        fileMenu.add(saveDoc);
        fileMenu.add(saveDocAs);
        // Add the file menu to the menu bar
        menuBar.add(fileMenu);

        JMenu tabulateMenu = new JMenu("Tabulate");
        JMenuItem loadAndTabulate = new JMenuItem("Load and tabulate function");
        tabulateMenu.add(loadAndTabulate);
        menuBar.add(tabulateMenu);
        setJMenuBar(menuBar);
    }
    private class NewDocListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int dialogComponentStatus = outer.dialogComponent.showDialog();
            if(dialogComponentStatus == DialogComponent.OK) {
                double leftDomainBorder = outer.dialogComponent.getLeftDomainBorder();
                double rightDomainBorder = outer.dialogComponent.getRightDomainBorder();
                int pointsCount = outer.dialogComponent.getPointsCount();
                TabulatedFunction fun = new ArrayTabulatedFunction(leftDomainBorder, rightDomainBorder, pointsCount);
                outer.tableModel.setFun(fun);
                outer.table.updateUI();
            }
        }
    }

    private class OpenDocListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            DialogTableFrame.this.fileChooser.showOpenDialog(DialogTableFrame.this);
            System.out.println(DialogTableFrame.this.fileChooser.getSelectedFile());
        }
    }
    private class SaveDocListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //TODO
        }
    }
    private class SaveDocAsListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //TODO
        }
    }
}
