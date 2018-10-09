package help;

public class Addition {
    final static public double EPSILON = 1e-12;

    public static boolean doubleEquals(double a, double b){
        return Math.abs(a- b) < EPSILON;
    }
}
