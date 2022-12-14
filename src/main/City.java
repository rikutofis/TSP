package main;

public class City {
    private double x;
    private double y;

    private int index;

    private static double[][] adjacency_matrix;

    public City(Double x, Double y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public double calcDistance(City c) {
        int index1 = this.index;
        int index2 = c.index;

        if(adjacency_matrix != null && adjacency_matrix[Math.min(index1, index2)][Math.max(index1, index2)] > 0) {
            return adjacency_matrix[Math.min(index1, index2)][Math.max(index1, index2)];
        }

        double x = Math.pow(this.getX() - c.getX(), 2);
        double y = Math.pow(this.getY() - c.getY(), 2);

        return Math.sqrt(x+y);
    }

    public static void setAdjacencyMatrix(double[][] adjacency_matrix) {
        City.adjacency_matrix = adjacency_matrix;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
