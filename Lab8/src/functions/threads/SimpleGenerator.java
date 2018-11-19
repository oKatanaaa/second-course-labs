package functions.threads;

import functions.Functions;
import functions.basic.Log;

import java.util.Random;

public class SimpleGenerator implements Runnable {
    private Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }
    @Override
    public void run() {
        task.taskCount = 1000;
        Random rand = new Random();
        for (int i = 0; i < task.taskCount; i++) {
            synchronized (task) {
                task.fun = new Log(Math.abs(rand.nextGaussian()) * 9 + 1 + Double.MIN_VALUE);
                task.leftIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + Double.MIN_VALUE;
                task.rightIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + 100;
                task.discreteStep = Math.abs(rand.nextGaussian()) + Double.MIN_VALUE;
                System.out.println(this.getClass().getName() + " " + task.leftIntegrationBorder + " " +
                        task.rightIntegrationBorder + " " +
                        task.discreteStep);
            }
        }
    }
}
