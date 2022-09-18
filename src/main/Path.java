package main;

public class Path {
    protected int[] path;
    protected double distance;

    protected static double[][] adjacency_matrix;

    public Path() {
        path = new int[0];
        distance = Double.MAX_VALUE;
    }

    public Path(int size) {
        path = new int[size + 1];

        for(int i = 0; i < path.length - 1; i++) {
            path[i] = i;
        }
        path[path.length - 1] = path[0];

        calcDistance();
    }

    public Path(int[] path) {
        this.path = path;
        
        calcDistance();
    }


    public int[] getPath() {
        return path;
    }

    public void setPath(int[] path) {
        this.path = path;
        calcDistance();
    }

    public double getDistance() {
        return distance;
    }

    public int get(int index) {
        return path[index];
    }

    public void set(int index, int value) {
        path[index] = value;
    }

    public int length() {
        return path.length;
    }

    protected void calcDistance() {
        distance = 0;

        for(int i = 0; i < path.length - 1; i++) {
            int index1 = path[i];
            int index2 = path[i+1];

            if(index1 < index2) {
                distance += adjacency_matrix[index1][index2];
            }
            else {
                distance += adjacency_matrix[index2][index1];
            }   
        }
    }

    public static void setAdjacencyMatrix(double[][] adjacency_matrix) {
        Path.adjacency_matrix = adjacency_matrix;
    }

    public Path clone() {
        Path path = new Path();
        path.setPath(this.path.clone());

        return path;
    }

    @Override
    public String toString() {
        String output = "";

        for(int i : path) {
            output += i + ", ";
        }

        return output.substring(0, output.length() - 2);
    }


}
