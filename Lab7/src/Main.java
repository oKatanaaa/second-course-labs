import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.threads.Task;
import gui.frames.dialogTableFrame.DialogTableFrame;


import java.awt.*;
import java.util.Random;

public class Main{
    public static void main(String[] args) {
        nonThread();
      /*  double[] values = new double[] {1, 2, 3, 4, 5, 6};
        ArrayTabulatedFunction tempArr1 = new functions.ArrayTabulatedFunction(0, 10, values);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DialogTableFrame frame = new DialogTableFrame();
                frame.setVisible(true);
            }
        });*/
    }

    public static void nonThread() {
        Task task = new Task();
        task.taskCount = 100;
        Random rand = new Random();
        for (int i = 0; i < task.taskCount; i++) {
            task.fun = new Log(Math.abs(rand.nextGaussian()) * 9 + 1 + Double.MIN_VALUE);
            task.leftIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + Double.MIN_VALUE;
            task.rightIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + 100;
            task.discreteStep = Math.abs(rand.nextGaussian()) + Double.MIN_VALUE;
            double result = Functions.integrate(task.fun, task.leftIntegrationBorder, task.rightIntegrationBorder, task.discreteStep);
            System.out.println("Source " + task.leftIntegrationBorder + " " +
                    task.rightIntegrationBorder + " " +
                    task.discreteStep + " " +
                    result);
        }
    }

    public static void simpleThreads() {
        Task task = new Task();
        task.taskCount = 100;
    }
}