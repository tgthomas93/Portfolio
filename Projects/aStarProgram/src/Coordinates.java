//Class is used to hold x, y values for each Node to serve as a unique Key
public class Coordinates {

    private int row;
    private int col;

    public void setValues(int x, int y) {
        row = y;
        col = x;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
