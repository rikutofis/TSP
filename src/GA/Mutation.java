package GA;

import main.TSP;

public class Mutation {
    public enum  MUTATION {
        SWAP,
        INVERSION,
        SHUFFLE,
        SWAP2
    }

    public static void swapMutation(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            if(Math.random() < GA.MUTATION_RATE) {
                int index1 = (int) Math.floor(Math.random() * TSP.city_size);
                int index2 = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).swap(index1, index2);
            }
        }
    }

    public static void inverstionMutaiton(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            if(Math.random() < GA.MUTATION_RATE) {
                int index1 = (int) Math.floor(Math.random() * TSP.city_size);
                int index2 = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).inversion(Math.min(index1, index2), Math.max(index1, index2));
            }
        }
    }

    public static void shuffleMutation(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            if(Math.random() < GA.MUTATION_RATE) {
                int index1 = (int) Math.floor(Math.random() * TSP.city_size);
                int index2 = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).shuffle(Math.min(index1, index2), Math.max(index1, index2));
            }
        }
    }

    public static void swap2Mutation(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            if(Math.random() < GA.MUTATION_RATE) {
                int index1 = (int) Math.floor(Math.random() * TSP.city_size);
                int index2 = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).swap(Math.min(index1, index2), Math.max(index1, index2));
            }
        }
    }
}
