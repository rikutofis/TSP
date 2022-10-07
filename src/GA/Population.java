package GA;

import NN.NN;
import main.TSP;

public class Population {
    private Individual[] population;

    public Population() {

    }

    public Population(int size) {
        population = new Individual[size];
    }


    public static Population initPopulation(int population_size) {
        Population population = new Population(population_size);

        for(int i = 0; i < population_size; i++) {
            Individual individual = new Individual(TSP.city_size);
            individual.shuffle();
            population.setIndividual(i, individual);
        }

        return population;
    }

    public static Population initPopulationNN(int population_size, double ratio) {
        Population population = new Population(population_size);

        for(int i = 0; i < population_size; i++) {
            if(Math.random() < ratio) {
                population.setIndividual(i, new Individual(NN.nearestNeighbor()));
            }
            else {
                Individual individual = new Individual(TSP.city_size);
                individual.shuffle();
                population.setIndividual(i, individual);
            }
        }

        return population;
    }

    public Individual[] getPopulation() {
        return population;
    }

    public Individual getIndividual(int index) {
        return population[index];
    }

    public void setIndividual(int index, Individual individual) {
        population[index] = individual;
    }

    public Individual getElitist() {
        Individual individual = population[0];

        for(int i = 1; i < population.length; i++) {
            if(individual.getDistance() > population[i].getDistance()) {
                individual = population[i];
            }
        }

        return individual;
    }

    public Population removeIndividual(Individual individual) {
        Population newPopulation = new Population(population.length - 1);

        boolean encountered = false;
        for(int i = 0; i < population.length - 1; i++) {
            if(this.getIndividual(i) == individual) {
                encountered = true;
                i++;
            }

            if(!encountered) {
                newPopulation.setIndividual(i, this.getIndividual(i));
            }
            else {
                newPopulation.setIndividual(i-1, this.getIndividual(i));
            }
        }

        return newPopulation;
    }

    public Individual selection() {
        switch(GA.SELECTION_METHOD) {
            case ROULETTE_WHEEL:
                return Selection.rouletteWheelSelection(this);
            case TOURNAMENT:
                return Selection.tournamentSelection(this);
        }

        return null;
    }

    //Ordered Crossover
    public static Individual crossover(Individual parent1, Individual parent2) {
        if(GA.CROSSOVER_RATE > Math.random()) {
            return parent1;
        }

        Individual child = new Individual(parent1.length() - 1);
        for(int i = 0; i < child.length(); i++) {
            child.setGene(i, -1);
        }

        int index1 = (int) Math.floor(Math.random() * (child.length() - 1));
        int index2 = (int) Math.floor(Math.random() * (child.length() - 1));

        int beginIndex = Math.min(index1, index2);
        int endIndex = Math.max(index1, index2);

        for(int i = beginIndex; i < endIndex; i++) {
            child.setGene(i, parent1.getGene(i));
        }

        int index = 0;
        for(int i = 0; i < child.length() - 1; i++) {
            if(child.getGene(i) == -1) {
                while(contains(child, parent2.getGene(index))) {
                    index++;
                }
                
                child.setGene(i, parent2.getGene(index));
            }
        }

        child.setGene(child.length() - 1, child.getGene(0));
        child.calcDistance();

        return child;
    }

    private static boolean contains(Individual individual, int gene) {
        for(int i : individual.getPath()) {
            if(i == gene) {
                return true;
            }
        }

        return false;
    }

    public void mutation() {
        switch(GA.MUTATE_METHOD) {
            case SWAP:
                Mutation.swapMutation(this);
                return;
            case INVERSION:
                Mutation.inverstionMutaiton(this);
                return;
            case SHUFFLE:
                Mutation.shuffleMutation(this);
                return;
            case SWAP2:
                Mutation.swap2Mutation(this);
                return;
        }
    }

    public double averageDistance() {
        double sum = 0;

        for(Individual individual : population) {
            sum += individual.getDistance();
        }

        return sum/population.length;
    }


    public Population clone() {
        Population newPopulation = new Population(population.length);
        
        for(int i = 0; i < population.length; i++) {
            newPopulation.setIndividual(i, population[i].clone());
        }

        return newPopulation;
    }

    @Override
    public String toString() {
        String output = "Population: \n";

        for(Individual i : population) {
            output += i.toString() + "\n";
        }

        return output;
    }

    
}
