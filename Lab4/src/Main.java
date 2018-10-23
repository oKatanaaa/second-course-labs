import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Sin;


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

        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(
                new Exp(), 0, 10, 11
        );
        try {
            FileWriter newFile = new FileWriter("//home//okatanaa//second-course-labs//Lab4//testFile.txt");
            TabulatedFunctions.writeTabulatedFunction(tabulatedExp, newFile);
            newFile.write("Hello!");
            System.out.println(newFile.toString());
            newFile.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}