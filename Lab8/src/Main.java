import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.threads.*;
import gui.frames.dialogTableFrame.DialogTableFrame;


import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        double[] values = new double[] {1, 2, 3, 4, 5, 6};
        ArrayTabulatedFunction tempArr1 = new ArrayTabulatedFunction(0, 10, values);
        for(FunctionPoint point: tempArr1)
            System.out.println(point);

        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(0, 10, values);
        for(FunctionPoint point: list)
            System.out.println(point);

        Function f = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        TabulatedFunctions.setTabulatedFunctionFactory(new
                LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        TabulatedFunctions.setTabulatedFunctionFactory(new
                ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        TabulatedFunction f2;
        f2 = TabulatedFunctions.createTabulatedFunction(
                0, 10, 3, ArrayTabulatedFunction.class);
        System.out.println(f2.getClass());
        System.out.println(f2);

        f2 = TabulatedFunctions.createTabulatedFunction(
                0, 10, new double[] {0, 10},ArrayTabulatedFunction.class);
        System.out.println(f2.getClass());
        System.out.println(f2);
        f2 = TabulatedFunctions.createTabulatedFunction(
                new FunctionPoint[] {
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                },
                LinkedListTabulatedFunction.class

        );
        System.out.println(f2.getClass());
        System.out.println(f2);

        f2 = TabulatedFunctions.tabulate(
                new Sin(), 0, Math.PI, 11, LinkedListTabulatedFunction.class);
        System.out.println(f2.getClass());
        System.out.println(f2);
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

    public static void simpleThreads() throws InterruptedException {
        Task task = new Task();
        task.taskCount = 100;
        SimpleGenerator generator = new SimpleGenerator(task);
        SimpleIntegrator integrator = new SimpleIntegrator(task);
        Thread genThread = new Thread(generator);
        Thread intThread = new Thread(integrator);
        genThread.setPriority(Thread.MAX_PRIORITY);
        intThread.setPriority(Thread.MIN_PRIORITY);


        genThread.start();
        intThread.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task task = new Task();
        task.taskCount = 100;
        Semaphore semaphore = new Semaphore(1);
        Generator generator = new Generator(task, semaphore);
        Integrator integrator = new Integrator(task, semaphore);
        generator.start();
        integrator.start();

        Thread.sleep(150);
        generator.interrupt();
        integrator.interrupt();
    }
}