package gui.models;

import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;

public class TabulatedFunctionTableModel implements TableModel {
    private TabulatedFunction fun;
    private Component parent;


    public TabulatedFunctionTableModel(TabulatedFunction fun, Component parent) {
        this.fun = fun;
        this.parent = parent;
    }

    @Override
    public int getRowCount() {
        if(this.fun == null)
            return 1;
        return this.fun.getPointsCount();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int index) {
        System.out.println("getColumnName");
        switch (index) {
            case 0:
                return "x";
            case 1:
                return "y";

            default:
                throw new IllegalArgumentException("Wrong index!");
        }
    }
    @Override
    public Class getColumnClass(int index) {
        return Double.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
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
    @Override
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

    public void setFun(TabulatedFunction fun) {
        this.fun = fun;
    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {
        //TODO
    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {
        //TODO
    }
}
