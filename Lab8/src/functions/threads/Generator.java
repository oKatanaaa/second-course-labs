package functions.threads;

import functions.basic.Log;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class Generator extends Thread {
    private Task task;
    private Semaphore semaphore;
    public Generator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        task.taskCount = 100;
        Random rand = new Random();
        for (int i = 0; i < task.taskCount; i++) {
            if(this.isInterrupted()) {
                System.out.println("Generator is interrupted.");
                break;
            }
            try {

                semaphore.acquire();

                task.fun = new Log(Math.abs(rand.nextGaussian()) * 9 + 1 + Double.MIN_VALUE);
                task.leftIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + Double.MIN_VALUE;
                task.rightIntegrationBorder = Math.abs(rand.nextGaussian()) * 100 + 100;
                task.discreteStep = Math.abs(rand.nextGaussian()) + Double.MIN_VALUE;
                System.out.println(this.getClass().getName() + " " + task.leftIntegrationBorder + " " +
                        task.rightIntegrationBorder + " " +
                        task.discreteStep);

                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
