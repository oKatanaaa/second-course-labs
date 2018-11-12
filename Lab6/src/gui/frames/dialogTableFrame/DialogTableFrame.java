package gui.frames.dialogTableFrame;

import gui.TabulatedFunctionDocument;
import gui.components.DialogComponent;
import gui.models.TabulatedFunctionTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class DialogTableFrame extends JFrame {
    protected DialogComponent dialogComponent;
    protected JFileChooser fileChooser;
    protected TabulatedFunctionTableModel tableModel;
    protected JTable table;
    protected TabulatedFunctionDocument functionDocument;
    protected AddDeletePointPanel addDeletePointPanel;

    public DialogTableFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        setSize(600, 400);
        setVisible(true);
        setResizable(true);
        this.addWindowListener(new DialogTableCloseListener());

        this.fileChooser = new JFileChooser();
        this.fileChooser.setCurrentDirectory(new File("."));
        this.dialogComponent = new DialogComponent(this);
        this.functionDocument = new TabulatedFunctionDocument();
        this.functionDocument.newFunction(0, 10, 11);
        this.addDeletePointPanel = new AddDeletePointPanel(this);
        add(addDeletePointPanel, BorderLayout.SOUTH);

        setUpMenuBar();
        // Add table
        this.table = new JTable();
        this.tableModel = new TabulatedFunctionTableModel(this.functionDocument, this);
        table.setModel(this.tableModel);
        // Add created table to scroll panel
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add point reduction menu


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

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());


        fileMenu.add(newDoc);
        fileMenu.add(openDoc);
        fileMenu.add(saveDoc);
        fileMenu.add(saveDocAs);
        fileMenu.add(exit);
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

                TabulatedFunctionDocument functionDocument = new TabulatedFunctionDocument();
                functionDocument.newFunction(leftDomainBorder, rightDomainBorder, pointsCount);

                outer.tableModel.setFun(functionDocument);
                outer.table.revalidate();
                outer.table.repaint();
                outer.functionDocument = functionDocument;
            }
        }
    }

    private class OpenDocListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int fileChooserStatus = outer.fileChooser.showOpenDialog(DialogTableFrame.this);
            if(fileChooserStatus == JFileChooser.APPROVE_OPTION) {
                TabulatedFunctionDocument functionDocument = new TabulatedFunctionDocument();
                try {
                    functionDocument.loadFunction(outer.fileChooser.getSelectedFile().getPath());
                    outer.tableModel.setFun(functionDocument);
                    outer.table.revalidate();
                    outer.table.repaint();
                    outer.functionDocument = functionDocument;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SaveDocListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(!functionDocument.isFileNameAssigned()) {
                int fileChooserStatus = outer.fileChooser.showSaveDialog(DialogTableFrame.this);
                if(fileChooserStatus == JFileChooser.APPROVE_OPTION) {
                    TabulatedFunctionDocument functionDocument = outer.functionDocument;
                    try {
                        functionDocument.saveFunctionAs(fileChooser.getSelectedFile().getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            else {
                try {
                    functionDocument.saveFunction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private class SaveDocAsListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int fileChooserStatus = outer.fileChooser.showSaveDialog(DialogTableFrame.this);
            if(fileChooserStatus == JFileChooser.APPROVE_OPTION) {
                TabulatedFunctionDocument functionDocument = outer.functionDocument;
                try {
                    functionDocument.saveFunctionAs(outer.fileChooser.getSelectedFile().getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ExitListener implements ActionListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(outer.functionDocument.isModified()) {
                if(JOptionPane.showConfirmDialog(outer, "Are you sure you want to exit?\nData won't be saved.") == JOptionPane.OK_OPTION){
                    outer.dispose();
                }
            } else {
                outer.dispose();
            }
        }
    }

    private class DialogTableCloseListener implements WindowListener {
        DialogTableFrame outer = DialogTableFrame.this;

        @Override
        public void windowOpened(WindowEvent windowEvent) {

        }

        @Override
        public void windowClosing(WindowEvent windowEvent) {
            if(outer.functionDocument.isModified()) {
                if(JOptionPane.showConfirmDialog(outer, "Are you sure you want to exit?\nData won't be saved.") == JOptionPane.OK_OPTION){
                    outer.dispose();
                }
            } else {
                outer.dispose();
            }
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
    }
}
