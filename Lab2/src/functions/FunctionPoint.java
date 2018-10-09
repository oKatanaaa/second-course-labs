package functions;

public class FunctionPoint{
    private double x;
    private double y;


    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point){
        x = point.x;
        y = point.y;
    }
    //dsfd
    public FunctionPoint(){
        x = 0;
        y = 0;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public String toString(){
        return "(" + x + "," + y + ")";
    }
}