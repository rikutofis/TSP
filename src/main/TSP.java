package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import GA.GA;
import NN.NN;

public class TSP {
    private GUI gui;

    private Scanner reader;

    private static City[] cities;
    private Path global_best_path;

    private double[][] adjacency_matrix;

    public static int city_size;

    public TSP() {
        // initCities(new File("src/res/rat99.txt"));
        // stretchLocation(4);
        // shiftLocation(50, 0);
        
        // initCities(new File("src/res/kroB200.txt"));
        // stretchLocation(0.35);
        // shiftLocation(40, 50);

        initCities(new File("src/res/pr299.txt"));
        // stretchLocation(0.22);
        // shiftLocation(-460, -200);

        initAdjacencyMatrix();

        gui = new GUI(cities, false);

        global_best_path = new Path();

        for(int i = 0; i < 100; i++) {
            // gui.reset();
            new GA(this);
            // new NN(this);
        }

        System.out.println();
        System.out.println(global_best_path);



        // Path path = new Path(new int[]{141, 140, 138, 96, 95, 94, 97, 82, 83, 98, 99, 100, 101, 81, 78, 80, 19, 17, 18, 16, 14, 15, 84, 85, 93, 86, 90, 88, 89, 87, 13, 12, 11, 10, 9, 8, 7, 6, 91, 92, 143, 145, 144, 147, 146, 148, 149, 150, 151, 152, 153, 154, 155, 213, 214, 292, 291, 289, 290, 288, 287, 286, 216, 215, 217, 211, 212, 221, 208, 209, 207, 206, 203, 204, 205, 298, 226, 225, 224, 279, 282, 281, 280, 277, 278, 275, 276, 274, 231, 232, 229, 230, 228, 202, 227, 199, 198, 201, 163, 162, 136, 134, 135, 137, 161, 133, 132, 131, 128, 103, 129, 130, 164, 165, 200, 167, 166, 196, 197, 233, 234, 273, 272, 271, 237, 235, 236, 240, 239, 238, 268, 269, 270, 242, 241, 195, 192, 193, 194, 169, 168, 173, 171, 170, 172, 178, 188, 186, 177, 175, 174, 176, 126, 122, 112, 110, 111, 57, 59, 56, 38, 42, 40, 39, 41, 36, 37, 35, 34, 31, 30, 29, 33, 32, 65, 62, 63, 104, 64, 61, 106, 105, 107, 108, 109, 60, 58, 43, 46, 47, 48, 49, 50, 51, 52, 53, 55, 54, 115, 113, 114, 116, 118, 119, 121, 120, 124, 123, 125, 127, 190, 191, 189, 187, 249, 250, 248, 243, 244, 245, 246, 247, 267, 266, 264, 265, 262, 260, 254, 251, 252, 185, 184, 255, 183, 182, 181, 180, 179, 117, 253, 259, 261, 263, 258, 257, 256, 45, 44, 67, 66, 68, 69, 70, 73, 72, 71, 76, 74, 79, 75, 77, 102, 21, 22, 20, 23, 24, 25, 26, 27, 28, 139, 160, 159, 210, 219, 223, 222, 218, 284, 285, 283, 220, 158, 157, 156, 142, 4, 5, 1, 2, 3, 0, 297, 295, 296, 293, 294, 141});
        // this.setPath(path);
    }


    private void initCities(int size) {
        cities = new City[size];
        city_size = size;

        for(int i = 0; i < size; i++){
            double x = Math.round(Math.random() * GUI.SCREEN_WIDTH);
            double y = Math.round(Math.random() * GUI.SCREEN_LENGTH);

            cities[i] = new City(x, y, i);
        }
    }

    private void initCities(File file) {
        ArrayList<City> cities = new ArrayList<>();

        try {
            reader = new Scanner(file);
        } 
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        int index = 0;

        while(reader.hasNextLine()) {
            reader.nextInt();
            double x = reader.nextDouble();
            double y = reader.nextDouble();

            cities.add(new City(x, y, index));
            index++;
        }

        TSP.cities = new City[cities.size()];
        TSP.cities = cities.toArray(TSP.cities);

        TSP.city_size = TSP.cities.length;
    }

    private void initAdjacencyMatrix() {
        adjacency_matrix = new double[cities.length][cities.length];

        for(int i = 0; i < cities.length - 1; i++) {
            for(int j = 1; j < cities.length; j++) {
                adjacency_matrix[i][j] = cities[i].calcDistance(cities[j]);
            }
        }

        Path.setAdjacencyMatrix(adjacency_matrix);
        City.setAdjacencyMatrix(adjacency_matrix);
    }

    private int[] getSolution(File file) {
        ArrayList<Integer> solution = new ArrayList<>();

        try {
            reader = new Scanner(file);
        } 
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        while(reader.hasNextLine()) {
            solution.add(reader.nextInt() - 1);
        }

        return solution.stream().mapToInt(i -> i).toArray();
    }

    private void stretchLocation(double scaleFactor) {
        for(City c : cities) {
            c.setX((int) (c.getX() * scaleFactor));
            c.setY((int) (c.getY() * scaleFactor));
        }
    }

    private void shiftLocation(int x, int y) {
        for(City c : cities) {
            c.setX(c.getX() + x);
            c.setY(c.getY() + y);
        }
    }

    public static City[] getCities() {
        return cities;
    }

    public void setPath(Path path) {
        if(path.getDistance() < global_best_path.getDistance()) {
            global_best_path = path.clone();
        }

        gui.setPath(path);
    }
}
