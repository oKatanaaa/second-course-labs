package functions.threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }
    @Override
    public void run() {
        for(int i = 0; i < task.taskCount; i++) {
            synchronized (task) {
                if (task.fun == null) {
                    System.out.println(this.getClass().getName() + " continues");
                    continue;
                }
                double result = Functions.integrate(task.fun, task.leftIntegrationBorder, task.rightIntegrationBorder, task.discreteStep);
                System.out.println(this.getClass().getName() + " " + task.leftIntegrationBorder + " " +
                        task.rightIntegrationBorder + " " +
                        task.discreteStep + " " +
                        result);
            }
        }
    }
}
