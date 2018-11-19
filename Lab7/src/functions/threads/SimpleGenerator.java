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
        Task task = new Task();
        task.taskCount = 100;
        Random rand = new Random();
        for (int i = 0; i < task.taskCount; i++) {
            task.fun = new Log(Math.abs(rand.nextGaussian()) * 9 + 1 + Double.MIN_VALUE);
            task.leftIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + Double.MIN_VALUE;
            task.rightIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + 100;
            task.discreteStep = Math.abs(rand.nextGaussian()) + Double.MIN_VALUE;
            System.out.println("Source " + task.leftIntegrationBorder + " " +
                    task.rightIntegrationBorder + " " +
                    task.discreteStep);
        }
    }
}
