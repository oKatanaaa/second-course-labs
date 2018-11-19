package functions.threads;

import functions.Functions;

import java.util.concurrent.Semaphore;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for(int i = 0; i < task.taskCount; i++) {
            if(this.isInterrupted()) {
                System.out.println("Integrator is interrupted.");
                break;
            }
            try {
                semaphore.acquire();
                if (task.fun == null) {
                    System.out.println(this.getClass().getName() + " continues");
                    continue;
                }


                double result = Functions.integrate(task.fun, task.leftIntegrationBorder, task.rightIntegrationBorder, task.discreteStep);
                System.out.println(this.getClass().getName() + " " + task.leftIntegrationBorder + " " +
                        task.rightIntegrationBorder + " " +
                        task.discreteStep + " " +
                        result);

                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
