import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Sin;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws InappropriateFunctionPointException {
        Function[] funs = new Function[]{new Sin(), new Cos()};
        double x = 0;
        while (x <= 2 * Math.PI) {
            for (Function fun : funs)
                System.out.print(String.valueOf(fun.getFunctionValue(x)) + " ");
            x += 0.1;
        }
        // Reset x value
        x = 0;
        System.out.println();


        TabulatedFunction[] tabulatedFuns = new TabulatedFunction[] {
                TabulatedFunctions.tabulate(funs[0], 0, 2 * Math.PI, 10),
                TabulatedFunctions.tabulate(funs[1], 0, 2 * Math.PI, 10)
        };
        while (x <= 2 * Math.PI) {
            for (Function fun : tabulatedFuns)
                System.out.print(String.valueOf(fun.getFunctionValue(x)) + " ");
            x += 0.1;
        }
        // Reset x value
        x = 0;
        System.out.println();


        Function squareTabulatedSin = Functions.power(tabulatedFuns[0], 2);
        Function squareTabulatedCos = Functions.power(tabulatedFuns[1], 2);
        Function squareSumSinCos = Functions.sum(squareTabulatedSin,squareTabulatedCos);
        while(x <= 2 * Math.PI) {
            System.out.print(String.valueOf(squareSumSinCos.getFunctionValue(x)) + " ");
            x += 0.1;
        }
        // Reset x value
        x = 0;
        System.out.println();

        // WRITING TABULATED FUNCTION
        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(
                new Exp(), 0, 10, 11
        );
        try {
            FileWriter fileToWrite = new FileWriter("//home//okatanaa//second-course-labs//Lab4//testFile.txt");
            TabulatedFunctions.writeTabulatedFunction(tabulatedExp, fileToWrite);
            fileToWrite.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        // READING TABULATED FUNCTION

        try {
            FileReader fileToRead = new FileReader("//home//okatanaa//second-course-labs//Lab4//testFile.txt");
            TabulatedFunction readedFun = TabulatedFunctions.readTabulatedFunction(fileToRead);
            fileToRead.close();

            while(x <= 10){
                System.out.print(tabulatedExp.getFunctionValue(x) + " ");
                System.out.print(readedFun.getFunctionValue(x) + " ");
                x+= 1;
            }
        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage() + "4");
        } catch (Exception e){
            System.out.println(e.getMessage() + "4");
        }


    }
}