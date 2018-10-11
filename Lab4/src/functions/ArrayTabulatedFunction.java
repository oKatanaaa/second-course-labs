package functions;


public class ArrayTabulatedFunction implements TabulatedFunction{

    private int pointsCount;
    private int capacity;
    private FunctionPoint[] array;

    public ArrayTabulatedFunction(FunctionPoint[] points){
        if(points.length < 2)
            throw new IllegalArgumentException();

        this.pointsCount = points.length;
        this.capacity = this.pointsCount * 2;
        this.array = new FunctionPoint[this.capacity];

        double prevX = points[0].getX();
        this.array[0] = new FunctionPoint(points[0]);

        for(int i = 1; i < points.length; i++){
            // Check x value order. It must an increasing sequence
            if(prevX > points[i].getX() || Double.compare(points[i].getX(),prevX) == 0)
                throw new IllegalArgumentException();

            prevX = points[i].getX();
            this.array[i] = new FunctionPoint(points[i]);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        if(leftX > rightX)
            throw new IllegalArgumentException();

        if(Double.compare(leftX,rightX) == 0)
            throw new IllegalArgumentException();

        this.pointsCount = pointsCount;
        this.capacity = 2 * pointsCount;
        this.array = new FunctionPoint[this.capacity];

        fillArray(leftX, rightX, pointsCount);
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        if(leftX > rightX)
            throw new IllegalArgumentException();

        if(Double.compare(leftX,rightX) == 0)
            throw new IllegalArgumentException();

        this.pointsCount = values.length;
        this.capacity = 2 * this.pointsCount;
        this.array = new FunctionPoint[this.capacity];

        fillArray(leftX, rightX, values);
    }

    private void fillArray(double leftX, double rightX, int pointsCount){
        double deltaX = (rightX - leftX) / (pointsCount - 1);
        for(int i = 0; i < pointsCount - 1; i++)
            this.array[i] = new FunctionPoint(leftX + deltaX*i, 0);

        // We need this assignment because of double inaccuracy
        this.array[pointsCount - 1] = new FunctionPoint(rightX, 0);
    }
    private void fillArray(double leftX, double rightX, double[] values){
        double deltaX = (rightX - leftX) / (this.pointsCount-1);
        for(int i = 0; i < this.pointsCount - 1; i++)
            this.array[i] = new FunctionPoint(leftX + deltaX*i, values[i]);

        // We need this assignment because of double inaccuracy
        this.array[this.pointsCount - 1] = new FunctionPoint(rightX, values[this.pointsCount - 1]);
    }

    public double getLeftDomainBorder() {
        return this.array[0].getX();
    }
    public double getRightDomainBorder() {
        return this.array[this.pointsCount - 1].getX();
    }

    public double getFunctionValue(double x){
        if(!isBetweenLeftRightBorders(x, 0, this.pointsCount - 1))
            return Double.NaN;
        int i = isAlreadyInArray(x);
        // Try to find indexes x is located between (index, index + 1)
        if(i == -1)
            i = findX(x);
        double x1 = this.array[i].getX();
        double x2 = this.array[i+1].getX();
        double y1 = this.array[i].getY();
        double y2 = this.array[i+1].getY();
        double interpolatedY = (x-x1)/(x2-x1)*(y2-y1) + y1;
        return interpolatedY;
    }
    public int getPointsCount(){
        return this.pointsCount;
    }

    public FunctionPoint getPoint(int index){
        if(index < 0 || this.pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException();

        return new FunctionPoint(this.array[index]);
    }
    public double getPointY(int index){
        if(index < 0 || this.pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        return this.array[index].getY();
    }
    public double getPointX(int index){
        if(index < 0 || this.pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        return this.array[index].getX();
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if(index < 0 || this.pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException();

        // Check if new first x value is bigger than second x value(it is error) x0New > x1 < x2 < x3 < x4...
        if(index == 0 && point.getX() > this.array[1].getX())
            throw new InappropriateFunctionPointException();
        // Check if new last x value is lesser than previous x value(it is error) ...x8 < x9 < x10 < x11 > x12New
        if(index == this.pointsCount - 1 && point.getX() < this.array[this.pointsCount - 2].getX())
            throw new InappropriateFunctionPointException();
        // Check if this expression is false: xPrevious < xNew < xNext
        if(this.pointsCount > 2 && !isBetweenLeftRightBorders(point.getX(), index - 1, index + 1))
            throw new InappropriateFunctionPointException();

        FunctionPoint newPoint = new FunctionPoint(point);
        this.array[index] = newPoint;
    }
    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if(index < 0 || this.pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        if(index == 0 && x > this.array[index + 1].getX())
            throw new InappropriateFunctionPointException("X value is out of interval");

        if(x < this.array[index - 1].getX() || this.array[index + 1].getX() < x)
            throw new InappropriateFunctionPointException("X value is out of interval");

        this.array[index].setX(x);
    }
    public void setPointY(int index, double y){
        if(index < 0 || this.pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        this.array[index].setY(y);
    }

    public void deletePoint(int index){
        if(index < 0 || this.pointsCount < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        if(this.pointsCount < 3)
            throw new IllegalStateException();
        for(int i = index; i < this.pointsCount-1; i++)
            this.array[i] = this.array[i+1];
        this.pointsCount--;
    }
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if(this.capacity == this.pointsCount + 1)
            reserve(this.capacity*2);
        
        int newInd = findX(point.getX());
        // Is below left domain order
        if(newInd == -1)
            insert(0, point);
        // Is above left domain order
        else if(newInd == -2)
            insert(this.pointsCount, point);
        // Is in domain of definition
        else if(newInd == -3)
            throw new IllegalArgumentException("Unknown error!");
        // Check if new point already is in array
        else if(this.array[newInd].getX() == point.getX() && this.array[newInd].getY() == point.getY())
            throw new InappropriateFunctionPointException();
        else
            insert(newInd+1, point);
        this.pointsCount++;
    }

    // PRIVATE FUNCTIONS

    private void insert(int index, FunctionPoint point){
        // This function turns each element to the right from index position
        for(int i = this.pointsCount - 1; i > index - 1; i--)
            this.array[i + 1] = this.array[i];

        this.array[index] = new FunctionPoint(point);
    }
    private void reserve(int newSize){
        FunctionPoint[] newArray = new FunctionPoint[newSize];
        for(int i = 0; i < this.pointsCount; i++)
            newArray[i] = this.array[i];
        this.array = newArray;
    }
    private boolean isBetweenLeftRightBorders(double x, int leftBorderInd, int rightBorderInd){
        return this.array[leftBorderInd].getX() <= x && x <= this.array[rightBorderInd].getX();
    }
    private int findX(double x){
        /* This method finds two Points x is between and returns the index of the left Point. 
        If x is below definition of domain - return -1, if is above - return -2,
        else - return -3;
        */
        // Check if x is below the left domain border
        if(x < this.array[0].getX())
            return -1;
        // Check if x is above the right domain border
        if(this.array[this.pointsCount - 1].getX() < x)
            return -2;
        for(int i = 0; i < this.pointsCount - 1; i++)
            if(x < this.array[i].getX())
                return i - 1;
        return -3;
    }



    @Override
    public String toString(){
        StringBuffer temp = new StringBuffer();
        temp.append("[ ");
        for(int i = 0; i < this.pointsCount; ++i){
            temp.append(this.array[i] + " ");
        }
        temp.append("]");
        return temp.toString();
    }

    private int isAlreadyInArray(double x){
        // This method checks if x is already in array.
        // If it is - return index position, if not - return 1
        for(int i = 0; i < this.pointsCount; ++i)
            if(Double.compare(x, this.array[i].getX()) == 0)
                return i;
        return -1;
    }



}