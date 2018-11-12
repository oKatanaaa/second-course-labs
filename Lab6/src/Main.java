import functions.*;
import gui.frames.dialogTableFrame.DialogTableFrame;


import java.awt.*;

public class Main{
    public static void main(String[] args) {
        double[] values = new double[] {1, 2, 3, 4, 5, 6};
        ArrayTabulatedFunction tempArr1 = new functions.ArrayTabulatedFunction(0, 10, values);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DialogTableFrame frame = new DialogTableFrame();
                frame.setVisible(true);
            }
        });
    }
}