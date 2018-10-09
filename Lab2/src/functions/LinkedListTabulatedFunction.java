package functions;

import help.*;

public class LinkedListTabulatedFunction implements TabulatedFunction{
    private static class FunctionNode{
        private FunctionPoint point;
        private FunctionNode prev;
        private FunctionNode next;

        public FunctionNode(FunctionPoint point, FunctionNode prev, FunctionNode next){
            this.point = point;
            this.prev = prev;
            this.next = next;
        }

        public FunctionNode(){
            this.point = null;
            this.prev = null;
            this.next = null;
        }

        public FunctionNode(FunctionPoint point){
            this.point = new FunctionPoint(point);
            this.prev = null;
            this.next = null;
        }

        public String toString(){
            return point.toString();
        }
    }

    private FunctionNode head;
    {
        head = new FunctionNode();
        head.point = new FunctionPoint();
        head.prev = head;
        head.next = head;
    }

    private int size;

    public LinkedListTabulatedFunction(){
        this.size = 0;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int count){
        if(leftX > rightX)
            throw new IllegalArgumentException("leftX cannot be greater rightX!");

        if(Double.compare(leftX,rightX) == 0)
            throw new IllegalArgumentException("leftX must be different from rightX!");

        if(count < 2)
            throw new IllegalArgumentException("count must be >= 2!");

        double deltaX = (rightX - leftX) / (count - 1);

        for(int i = 0; i < count - 1; i++){
            FunctionNode temp = new FunctionNode(new FunctionPoint(leftX + deltaX*i, 0));
            addNodeToTail(temp);
        }

        FunctionNode lastNode = new FunctionNode(new FunctionPoint(rightX, 0));
        addNodeToTail(lastNode);
    }
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values){
        if(leftX > rightX)
            throw new IllegalArgumentException("leftX cannot be greater rightX!");

        if(Double.compare(leftX,rightX) == 0)
            throw new IllegalArgumentException("leftX must be different from rightX!");

        double deltaX = (rightX - leftX) / (values.length - 1);

        for(int i = 0; i < values.length - 1; i++){
            FunctionNode temp = new FunctionNode(new FunctionPoint(leftX + deltaX*i, values[i]));
            addNodeToTail(temp);
        }

        FunctionNode lastNode = new FunctionNode(new FunctionPoint(rightX, values[values.length - 1]));
        addNodeToTail(lastNode);
    }

    /**
     * This function insert node into position between head and head.prev
     * in order to get increasing sequence while filling the list
     * @param newNode
     */
    private void addNodeToTail(FunctionNode newNode){
        newNode.prev = this.head.prev;
        newNode.next = this.head;
        this.head.prev.next = newNode;
        this.head.prev = newNode;

        // If list contains only head
        if(this.size == 0)
            this.head.next = newNode;

        this.size++;
    }

    private FunctionNode getNodeByIndex(int index){
        FunctionNode temp = this.head;

        // Node is closer to head
        if(index < this.size / 2) {
            for (int i = 0; i < index + 1; i++)
                temp = temp.next;
        }
        // Node is closer to tail
        else {
            for (int i = 0; i < this.size - index; i++)
                temp = temp.prev;
        }

        return temp;
    }

    /**
     * Insert newNode in between of leftNode and rightNode
     * @param newNode
     * @param leftNode
     * @param rightNode
     */
    private void addNode(FunctionNode newNode, FunctionNode leftNode, FunctionNode rightNode){
        leftNode.next = newNode;
        rightNode.prev = newNode;
        newNode.prev = leftNode;
        newNode.next = rightNode;

        this.size++;
    }
    private FunctionNode addNodeByIndex(int index){
        FunctionNode newNode = new FunctionNode();
        addNode(newNode, getNodeByIndex(index - 1), getNodeByIndex(index + 1));
        return newNode;
    }
    private FunctionNode deleteNodeByIndex(int index){
        FunctionNode deletedNode = getNodeByIndex(index);
        deletedNode.prev = null;
        deletedNode.next = null;
        FunctionNode leftNode = getNodeByIndex(index - 1);
        FunctionNode rightNode = getNodeByIndex(index + 1);
        leftNode.next = rightNode;
        rightNode.prev = leftNode;
        return deletedNode;
    }

    public double getLeftDomainBorder() {
        FunctionNode leftNode = getNodeByIndex(0);
        return leftNode.point.getX();
    }
    public double getRightDomainBorder() {
        FunctionNode rightNode = getNodeByIndex(size-1);
        return rightNode.point.getX();
    }

    @Override
    public double getFunctionValue(double x){
        if(x < getLeftDomainBorder())
            return Double.NaN;
        if(getRightDomainBorder() < x)
            return Double.NaN;

        FunctionNode leftNode = findLeftNodeNeighbor(x);
        // x already is in the list
        if(Addition.doubleEquals(leftNode.point.getX(), x))
            return leftNode.point.getY();

        double x1 = leftNode.point.getX();
        double x2 = leftNode.next.point.getX();
        double y1 = leftNode.point.getY();
        double y2 = leftNode.next.point.getY();
        double interpolatedY = (x-x1)/(x2-x1)*(y2-y1) + y1;
        return interpolatedY;
    }

    /**
     * This function return link to the first node which X value is
     * lesser than new X parameter value
     * @param x
     * @return
     */
    private FunctionNode findLeftNodeNeighbor(double x){
        FunctionNode temp = this.head.next;
        while(x >= temp.point.getX()){
            temp = temp.next;
        }
        return temp.prev;
    }

    @Override
    public int getPointsCount() {
        return this.size;
    }

    @Override
    public FunctionPoint getPoint(int index) {
        if(index < 0 || this.size < index)
            throw new FunctionPointIndexOutOfBoundsException("Index is out of boundaries");

        FunctionPoint temp = getNodeByIndex(index).point;
        return new FunctionPoint(temp);
    }
    @Override
    public double getPointY(int index) {
        if(index < 0 || this.size < index)
            throw new FunctionPointIndexOutOfBoundsException("Index is out of boundaries");

        FunctionPoint temp = getNodeByIndex(index).point;
        return temp.getY();
    }
    @Override
    public double getPointX(int index) {
        if(index < 0 || this.size < index)
            throw new FunctionPointIndexOutOfBoundsException("Index is out of boundaries");

        FunctionPoint temp = getNodeByIndex(index).point;
        return temp.getX();
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException{
        if(index < 0 || this.size < index)
            throw new FunctionPointIndexOutOfBoundsException();

        // Check if new first x value is bigger than second x value(it is error) x0New > x1 < x2 < x3 < x4...
        if(index == 0 && point.getX() > getNodeByIndex(1).point.getX())
            throw new InappropriateFunctionPointException("New X value at [index] is bigger than X value at [index + 1]");
        // Check if new last x value is lesser than previous x value(it is error) ...x8 < x9 < x10 < x11 > x12New
        if(index == this.size - 1 && point.getX() < getNodeByIndex(this.size - 2).point.getX())
            throw new InappropriateFunctionPointException("New X value at [index] is lesser than X value at [index - 1]");
        // Check if this expression is false: xPrevious < xNew < xNext
        FunctionNode toUpdate = getNodeByIndex(index);
        double xPrev = toUpdate.prev.point.getX();
        double xNext = toUpdate.next.point.getX();
        if(this.size > 2 &&
                (point.getX() < xPrev || xNext < point.getX()))
            throw new InappropriateFunctionPointException("New X value at [index] is bigger than X value at [index + 1] or \n" +
                    "New X value at [index] is lesser than X value at [index - 1]");

        toUpdate.point = new FunctionPoint(point);
    }
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if(index < 0 || this.size < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");
        // New x value is bigger than rightX value
        if(index == 0 && x > getNodeByIndex(index + 1).point.getX())
            throw new InappropriateFunctionPointException("X value is out of interval");
        // New x value is lesser than leftX value
        if(x < getNodeByIndex(index - 1).point.getX() || getNodeByIndex(index + 1).point.getX() < x)
            throw new InappropriateFunctionPointException("X value is out of interval");

        getNodeByIndex(index).point.setX(x);
    }
    @Override
    public void setPointY(int index, double y){
        if(index < 0 || this.size < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        getNodeByIndex(index).point.setY(y);
    }

    @Override
    public void deletePoint(int index){
        if(index < 0 || this.size < index)
            throw new FunctionPointIndexOutOfBoundsException("Index out of boundaries");

        if(this.size < 3)
            throw new IllegalStateException("List contains less than 3 points!");

        FunctionNode leftNodeNeighbor = getNodeByIndex(index - 1);
        FunctionNode rightNodeNeighbor = getNodeByIndex(index + 1);

        leftNodeNeighbor.next = rightNodeNeighbor;
        rightNodeNeighbor.prev = leftNodeNeighbor;

        this.size--;
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        FunctionNode leftNodeNeighbor = findLeftNodeNeighbor(point.getX());
        // Point with this X value already exists
        if(Double.compare(leftNodeNeighbor.point.getX(), point.getX()) == 0)
            throw new InappropriateFunctionPointException("Point with this X value already is in array!");

        addNode(new FunctionNode(point), leftNodeNeighbor, leftNodeNeighbor.next);
    }


    @Override
    public String toString(){
        StringBuffer stringList = new StringBuffer();
        stringList.append("[ ");

        FunctionNode temp = head.next;

        while(temp != head){
            stringList.append(temp + " ");
            temp = temp.next;
        }
        stringList.append("]");
        return stringList.toString();
    }

}

