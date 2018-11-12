import functions.*;
import functions.basic.*;
import gui.frames.DialogTableFrame;


import java.awt.*;
import java.io.*;

public class Main{
    public static void main(String[] args) {
        double[] values = new double[] {1, 2, 3, 4, 5, 6};
        ArrayTabulatedFunction tempArr1 = new functions.ArrayTabulatedFunction(0, 10, values);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DialogTableFrame frame = new DialogTableFrame(tempArr1);
                frame.setVisible(true);
            }
        });
    }
}