package functions;



public class ArrayTabulatedFunction implements TabulatedFunction{

    private int pointsCount;
    private int capacity;
    private FunctionPoint[] array;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        if(leftX > rightX)
            throw new IllegalArgumentException();

        if(Double.compare(leftX,rightX) == 0)
            throw new IllegalArgumentException();

        this.pointsCount = pointsCount;
        capacity = 2 * pointsCount;
        array = new FunctionPoint[capacity];

        fillArray(leftX, rightX, pointsCount);
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        if(leftX > rightX)
            throw new IllegalArgumentException();

        if(Double.compare(leftX,rightX) == 0)
            throw new IllegalArgumentException();

        pointsCount = values.length;
        capacity = 2 * pointsCount;
        array = new FunctionPoint[capacity];

        fillArray(leftX, rightX, values);
    }

    private void fillArray(double leftX, double rightX, int pointsCount){
        double deltaX = (rightX - leftX) / (pointsCount - 1);
        for(int i = 0; i < pointsCount - 1; i++)
            array[i] = new FunctionPoint(leftX + deltaX*i, 0);

        // We need this assignment because of double inaccuracy
        array[pointsCount - 1] = new FunctionPoint(rightX, 0);
    }
    private void fillArray(double leftX, double rightX, double[] values){
        double deltaX = (rightX - leftX) / (pointsCount-1);
        for(int i = 0; i < pointsCount - 1; i++)
            array[i] = new FunctionPoint(leftX + deltaX*i, values[i]);

        // We need this assignment because of double inaccuracy
        array[pointsCount - 1] = new FunctionPoint(rightX, values[pointsCount - 1]);
    }

    public double getLeftDomainBorder() {
        return array[0].getX();
    }
    public double getRightDomainBorder() {
        return array[pointsCount - 1].getX();
    }

    public double getFunctionValue(double x){
        if(!isBetweenLeftRightBorders(x, 0, pointsCount - 1))
            return Double.NaN;
        int i = isAlreadyInArray(x);
        // Try to find indexes x is located between (index, index + 1)
        if(i == -1)
            i = findX(x);
        double x1 = array[i].getX();
        double x2 = array[i+1].getX();
        double y1 = array[i].getY();
        double y2 = array[i+1].getY();
        double interpolatedY = (x-x1)/(x2-x1)*(y2-y1) + y1;
        return interpolatedY;
    }
    public int getPointsCount(){
        return pointsCount;
    }

    public FunctionPoint getPoint(int index){
        if(index < 0 || pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException();

        return new FunctionPoint(array[index]);
    }
    public double getPointY(int index){
        if(index < 0 || pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        return array[index].getY();
    }
    public double getPointX(int index){
        if(index < 0 || pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        return array[index].getX();
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if(index < 0 || pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException();

        // Check if new first x value is bigger than second x value(it is error) x0New > x1 < x2 < x3 < x4...
        if(index == 0 && point.getX() > array[1].getX())
            throw new InappropriateFunctionPointException();
        // Check if new last x value is lesser than previous x value(it is error) ...x8 < x9 < x10 < x11 > x12New
        if(index == pointsCount - 1 && point.getX() < array[pointsCount - 2].getX())
            throw new InappropriateFunctionPointException();
        // Check if this expression is false: xPrevious < xNew < xNext
        if(pointsCount > 2 && !isBetweenLeftRightBorders(point.getX(), index - 1, index + 1))
            throw new InappropriateFunctionPointException();

        FunctionPoint newPoint = new FunctionPoint(point);
        array[index] = newPoint;
    }
    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if(index < 0 || pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        if(index == 0 && x > array[index + 1].getX())
            throw new InappropriateFunctionPointException("X value is out of interval");

        if(x < array[index - 1].getX() || array[index + 1].getX() < x)
            throw new InappropriateFunctionPointException("X value is out of interval");

        array[index].setX(x);
    }
    public void setPointY(int index, double y){
        if(index < 0 || pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        array[index].setY(y);
    }

    public void deletePoint(int index){
        if(index < 0 || pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        if(pointsCount < 3)
            throw new IllegalStateException();
        for(int i = index; i < pointsCount-1; i++)
            array[i] = array[i+1];
        pointsCount--;
    }
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if(capacity == pointsCount + 1)
            reserve(capacity*2);
        
        int newInd = findX(point.getX());
        // Is below left domain order
        if(newInd == -1)
            insert(0, point);
        // Is above left domain order
        else if(newInd == -2)
            insert(pointsCount, point);
        // Is in domain of definition
        else if(newInd == -3)
            throw new IllegalArgumentException("Unknown error!");
        // Check if new point already is in array
        else if(array[newInd].getX() == point.getX() && array[newInd].getY() == point.getY())
            throw new InappropriateFunctionPointException();
        else
            insert(newInd+1, point);
        pointsCount++;
    }

    // PRIVATE FUNCTIONS

    private void insert(int index, FunctionPoint point){
        // This function turns each element to the right from index position
        for(int i = pointsCount - 1; i > index - 1; i--)
            array[i + 1] = array[i];
        
        array[index] = new FunctionPoint(point);
    }
    private void reserve(int newSize){
        FunctionPoint[] newArray = new FunctionPoint[newSize];
        for(int i = 0; i < pointsCount; i++)
            newArray[i] = array[i];
        array = newArray;
    }
    private boolean isBetweenLeftRightBorders(double x, int leftBorderInd, int rightBorderInd){
        return array[leftBorderInd].getX() <= x && x <= array[rightBorderInd].getX();
    }
    private int findX(double x){
        /* This method finds two Points x is between and returns the index of the left Point. 
        If x is below definition of domain - return -1, if is above - return -2,
        else - return -3;
        */
        // Check if x is below the left domain border
        if(x < array[0].getX())
            return -1;
        // Check if x is above the right domain border
        if(array[pointsCount - 1].getX() < x)
            return -2;
        for(int i = 0; i < pointsCount - 1; i++)
            if(x < array[i].getX())
                return i - 1;
        return -3;
    }



    @Override
    public String toString(){
        StringBuffer temp = new StringBuffer();
        temp.append("[ ");
        for(int i = 0; i < pointsCount; ++i){
            temp.append(array[i] + " ");
        }
        temp.append("]");
        return temp.toString();
    }

    private int isAlreadyInArray(double x){
        // This method checks if x is already in array.
        // If it is - return index position, if not - return 1
        for(int i = 0; i < pointsCount; ++i)
            if(Double.compare(x, array[i].getX()) == 0)
                return i;
        return -1;
    }



}