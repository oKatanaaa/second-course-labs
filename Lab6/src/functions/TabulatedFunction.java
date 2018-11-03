package functions;

import java.io.Serializable;

public interface TabulatedFunction extends Function, Serializable, Cloneable {
    int getPointsCount();

    FunctionPoint getPoint(int index);
    double getPointY(int index);
    double getPointX(int index);

    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;
    void setPointX(int index, double x) throws InappropriateFunctionPointException;
    void setPointY(int index, double y);

    void deletePoint(int index);
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    Object clone();

}
