package godotClasses;

import java.util.Scanner;

public class Input {
    public static int isActionPressed(String actionName){
        System.out.println("Is it pressed: " + actionName + "?");
        Scanner in = new Scanner(System.in);
        if("y".equals(in.nextLine()))
            return 1;
        else
            return 0;
    }
}
