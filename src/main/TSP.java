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
        // initCities(100);

        // initCities(new File("src/res/berlin52.txt"));
        // stretchLocation(0.7);
        // shiftLocation(0, 30);

        // initCities(new File("src/res/st70.txt"));
        // stretchLocation(9);
        // shiftLocation(0, 20);

        // initCities(new File("src/res/rat99.txt"));
        // stretchLocation(4);
        // shiftLocation(20, 0);

        // initCities(new File("src/res/ts225.txt"));
        // stretchLocation(0.05);
        // shiftLocation(-100, -100);
        
        // initCities(new File("src/res/kroB200.txt"));
        // stretchLocation(0.35);
        // shiftLocation(20, 20);

        // initCities(new File("src/res/a280.txt"));
        // stretchLocation(4);

        initCities(new File("src/res/pr299.txt"));
        // stretchLocation(0.22);
        // shiftLocation(-460, -200);
        
        // initCities(new File("src/res/rat575.txt"));
        // stretchLocation(1.8);
        // shiftLocation(20, 10);

        initAdjacencyMatrix();

        gui = new GUI(cities, false);

        global_best_path = new Path();


        for(int i = 0; i < 100; i++) {
            new GA(this);
            // new NN(this);
        }
        System.out.println();
        System.out.println(global_best_path);
    }


    private void initCities(int size) {
        cities = new City[size];
        city_size = size;

        for(int i = 0; i < size; i++){
            int x = (int) Math.round(Math.random() * GUI.SCREEN_WIDTH);
            int y = (int) Math.round(Math.random() * GUI.SCREEN_LENGTH);

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
            int x = reader.nextInt();
            int y = reader.nextInt();

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
