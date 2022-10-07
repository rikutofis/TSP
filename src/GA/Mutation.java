package GA;

import main.TSP;

public class Mutation {
    public enum  MUTATION {
        SWAP,
        INVERSION,
        SHUFFLE,
        SWAP2
    }

    //swaps two arbitrary genes
    public static void swapMutation(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            if(Math.random() < GA.MUTATION_RATE) {
                int index1 = (int) Math.floor(Math.random() * TSP.city_size);
                int index2 = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).swap(index1, index2);
            }
        }
    }

    //inverts a section of the chromosome
    public static void inverstionMutaiton(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            if(Math.random() < GA.MUTATION_RATE) {
                int index1 = (int) Math.floor(Math.random() * TSP.city_size);
                int index2 = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).inversion(Math.min(index1, index2), Math.max(index1, index2));
            }
        }
    }

    //shuffles a section of the chromosome
    public static void shuffleMutation(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            if(Math.random() < GA.MUTATION_RATE) {
                int index1 = (int) Math.floor(Math.random() * TSP.city_size);
                int index2 = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).shuffle(Math.min(index1, index2), Math.max(index1, index2));
            }
        }
    }

    //swaps number of city times
    public static void swap2Mutation(Population population) {
        for(int i = GA.ELITISM; i < GA.POPULATION_SIZE; i++) {
            for(int j = 0; j < TSP.city_size; j++)
            if(Math.random() < GA.MUTATION_RATE) {
                int index = (int) Math.floor(Math.random() * TSP.city_size);

                population.getIndividual(i).swap(j, index);
            }
        }
    }
}
