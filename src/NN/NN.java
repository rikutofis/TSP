package NN;

import java.util.ArrayList;

import main.City;
import main.Path;
import main.TSP;

public class NN {
    public NN(TSP tsp) {
        Path path = nearestNeighbor();
        tsp.setPath(path);

        System.out.println(path.getDistance());
    }

    public static Path nearestNeighbor() {
        Path path = new Path(TSP.city_size);

        ArrayList<City> unvisited = new ArrayList<>();
        for(City c : TSP.getCities()) {
            unvisited.add(c);
        }

        City visiting = unvisited.remove((int) Math.floor(Math.random() * unvisited.size()));
        path.set(0, visiting.getIndex());

        int index = 0;
        double min = Double.MAX_VALUE;
        for(int i = 1; i < path.length() - 1; i++) {
            for(int j = 0; j < unvisited.size(); j++) {
                if(visiting.calcDistance(unvisited.get(j)) < min) {
                    index = j;
                    min = visiting.calcDistance(unvisited.get(j));
                }
            }

            visiting = unvisited.remove(index);
            path.set(i, visiting.getIndex());
            index = 0;
            min = Double.MAX_VALUE;
        }

        path.set(path.length() - 1, path.get(0));
        path.calcDistance();

        return path;
    }
}
