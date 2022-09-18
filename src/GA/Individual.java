package GA;

import java.util.Arrays;
import java.util.Collections;

import main.Path;

public class Individual extends Path {
    private double fitness;

    public Individual() {
        distance = Double.MAX_VALUE;
        fitness = 1/distance;
    }

    public Individual(int size) {
        path = new int[size + 1];

        for(int i = 0; i < path.length - 1; i++) {
            path[i] = i;
        }

        path[path.length - 1] = path[0];

        calcDistance();
    }

    public Individual(int[] path) {
        this.path = path;
        
        calcDistance();
    }

    public Individual(Path path) {
        this.path = path.getPath().clone();
        fitness = 1/path.getDistance();
    }


    public double getFitness() {
        return fitness;
    }

    public int getGene(int index) {
        return path[index];
    }

    public void setGene(int index, int gene) {
        path[index] = gene;
    }

    public void shuffle() {
        for(int i = 0; i < path.length - 1; i++) {
            int j = (int) Math.floor(Math.random() * (path.length - 1));

            innerSwap(i, j);
        }

        path[path.length - 1] = path[0];

        calcDistance();
    }

    private void innerSwap(int index1, int index2) {
        int temp = path[index1];
        path[index1] = path[index2];
        path[index2] = temp;
    }

    public void shuffle(int index1, int index2) {
        for(int i = index1; i < index2; i++) {
            int index = (int) Math.floor(Math.random() * (index2 - index1)) + index1;

            innerSwap(i, index);
        }

        path[path.length - 1] = path[0];

        calcDistance();
    }

    public void inversion(int index1, int index2) {
        while(index1 < index2) {
            swap(index1, index2);
            index1++;
            index2--;
        }

        path[path.length - 1] = path[0];

        calcDistance();
    }

    public void swap(int index1, int index2) {
        int temp = path[index1];
        path[index1] = path[index2];
        path[index2] = temp;

        path[path.length - 1] = path[0];

        calcDistance();
    }

    @Override
    protected void calcDistance() {
        distance = 0;

        for(int i = 0; i < path.length - 1; i++) {
            int index1 = path[i];
            int index2 = path[i+1];

            distance += adjacency_matrix[Math.min(index1, index2)][Math.max(index1, index2)];  
        }

        fitness = 1/distance;
    }

    @Override
    public Individual clone() {
        Individual individual = new Individual();
        individual.setPath(this.path.clone());

        return individual;
    }
}
