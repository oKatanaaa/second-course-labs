package gui;

import functions.*;

import java.io.*;

public class TabulatedFunctionDocument implements TabulatedFunction{
    private TabulatedFunction tabulatedFunction;
    private String fileName;
    private boolean modified = false;
    private boolean fileNameAssigned = false;

    public void newFunction(double leftX, double rightX, int pointsCount) {
        if(this.fileNameAssigned)
            this.modified = true;

        this.tabulatedFunction = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
    }

    public void saveFunction() throws IOException {
        FileWriter file = new FileWriter(this.fileName);
        TabulatedFunctions.writeTabulatedFunction(this.tabulatedFunction, file);
        file.close();
    }

    public void saveFunctionAs(String fileName) throws IOException {
        if(!this.fileNameAssigned) {
            this.fileName = fileName;
            this.fileNameAssigned = true;
        }

        FileWriter file = new FileWriter(fileName);
        TabulatedFunctions.writeTabulatedFunction(this.tabulatedFunction, file);
        file.close();
    }

    public void loadFunction(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.fileNameAssigned = true;

        FileReader file = new FileReader(this.fileName);
        this.tabulatedFunction = TabulatedFunctions.readTabulatedFunction(file);
    }

    public void tabulateFunction(Function fun, double leftX, double rightX, int pointsCount) {
        if(this.fileNameAssigned)
            this.modified = true;

        this.tabulatedFunction = TabulatedFunctions.tabulate(fun, leftX, rightX, pointsCount);
    }

    public boolean isModified(){
        return modified;
    }

    public boolean isFileNameAssigned(){
        return fileNameAssigned;
    }

    @Override
    public int getPointsCount() {
        return this.tabulatedFunction.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return this.tabulatedFunction.getPoint(index);
    }

    @Override
    public double getPointY(int index) {
        return this.tabulatedFunction.getPointY(index);
    }

    @Override
    public double getPointX(int index) {
        return this.tabulatedFunction.getPointX(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if(this.fileNameAssigned)
            this.modified = true;

        this.tabulatedFunction.setPoint(index, point);
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if(this.fileNameAssigned)
            this.modified = true;

        this.tabulatedFunction.setPointX(index, x);
    }

    @Override
    public void setPointY(int index, double y) {
        if(this.fileNameAssigned)
            this.modified = true;

        this.tabulatedFunction.setPointY(index, y);
    }

    @Override
    public void deletePoint(int index) {
        if(this.fileNameAssigned)
            this.modified = true;

        this.tabulatedFunction.deletePoint(index);
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if(this.fileNameAssigned)
            this.modified = true;

        this.tabulatedFunction.addPoint(point);
    }

    @Override
    public String toString(){
        return this.tabulatedFunction.toString();
    }

    @Override
    public Object clone() {
        TabulatedFunctionDocument newDoc = new TabulatedFunctionDocument();
        newDoc.tabulatedFunction = (TabulatedFunction) this.tabulatedFunction.clone();
        return newDoc;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof TabulatedFunction))
            return false;

        if(obj instanceof TabulatedFunctionDocument){
            TabulatedFunctionDocument doc = (TabulatedFunctionDocument) obj;
            return this.tabulatedFunction.equals(doc.tabulatedFunction) &&
                    this.fileName.equals(doc.fileName);
        }

        return this.tabulatedFunction.equals((TabulatedFunction) obj);
    }

    @Override
    public double getLeftDomainBorder() {
        return this.tabulatedFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return this.tabulatedFunction.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return this.tabulatedFunction.getFunctionValue(x);
    }
}
