package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    private double x;
    private double y;


    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point){
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint(){
        this.x = 0;
        this.y = 0;
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

    @Override
    public String toString(){
        if(this == null)
            return "null";
        else
            return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FunctionPoint) {
            FunctionPoint other = (FunctionPoint) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }


}