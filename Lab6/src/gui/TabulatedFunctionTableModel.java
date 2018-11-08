package gui;

import functions.ArrayTabulatedFunction;
import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TabulatedFunctionTableModel extends DefaultTableModel {
    private TabulatedFunction fun;
    private Component parent;


    TabulatedFunctionTableModel(TabulatedFunction fun, Component parent) {
        this.fun = fun;
        this.parent = parent;
    }

    public int getRowCount() {
        return this.fun.getPointsCount();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "x";
            case 1:
                return "y";

            default:
                    throw new IllegalArgumentException("Wrong index!");
        }
    }

    public Class getColumnClass(int index) {
        return Double.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return 0 <= rowIndex && rowIndex < this.fun.getPointsCount() &&
                0 <= columnIndex && columnIndex <= 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return this.fun.getPointX(rowIndex);
            case 1:
                return this.fun.getPointY(rowIndex);

            default:
                    throw new IllegalArgumentException("Wrong column index!");
        }
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if(!(value instanceof Integer) || !(value instanceof Double))
            JOptionPane.showMessageDialog(this.parent, "Incorrect value!");

        if(!isCellEditable(rowIndex, columnIndex))
            JOptionPane.showMessageDialog(this.parent, "Incorrect row and column indexes!");

        if(columnIndex == 0) {
            try {
                this.fun.setPointX(rowIndex, (Double) value);
            } catch (InappropriateFunctionPointException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.parent, "Incorrect value!");
            }
        }

        if(columnIndex == 1) {
            this.fun.setPointY(rowIndex, (Double) value);
        }
    }
}
