package GA;

import GA.Mutation.MUTATION;
import GA.Selection.SELECTION;
import main.Graph;
import main.TSP;

public class GA {
    public final static int POPULATION_SIZE = 500;
    public final static double MUTATION_RATE = 0.05;
    public final static double CROSSOVER_RATE = 0.7;

    public final static int MAX_GENERATION = 10000;
    public final static int MAX_NOT_IMPROVED = 10000;

    public final static int TOURNAMENT_SIZE = 5;

    public final static int ELITISM = 1; //number of the elites to keep for the next generation

    public final static SELECTION SELECTION_METHOD = SELECTION.TOURNAMENT; 
    public final static MUTATION MUTATE_METHOD = MUTATION.INVERSION;

    private Population currPopulation;
    private Population newPopulation;

    private Individual best_path;

    private int notImproved;

    public GA(TSP tsp) {
        Graph graph = new Graph(false, false);
        // graph.setSolution(1211);

        currPopulation = Population.initPopulation(POPULATION_SIZE);
        // currPopulation = Population.initPopulationNN(POPULATION_SIZE, 0.005);
        newPopulation = new Population(POPULATION_SIZE);

        best_path = new Individual();

        notImproved = 0;

        for(int i = 0; i < MAX_GENERATION; i++) {
            for(Individual path : currPopulation.getPopulation()) { //visualising
                tsp.setPath(path);
            }

            graph.addAverage(currPopulation.averageDistance());

            Individual elitist = currPopulation.getElitist(); //finding the elitist
            if(best_path.getDistance() > elitist.getDistance()) {
                best_path = elitist.clone();
                notImproved = 0;
            }

            graph.addBest(best_path.getDistance());

            if(notImproved == MAX_NOT_IMPROVED) { //Check max not improved
                break;
            }
            notImproved++;


            for(int j = 0; j < ELITISM; j++) { //Elitism
                newPopulation.setIndividual(j, elitist);
                
                Population temp = currPopulation.clone();
                temp.removeIndividual(elitist);
                elitist = temp.getElitist();
            }


            for(int j = ELITISM; j < POPULATION_SIZE; j++) { //Selection & Crossover
                Individual parent1 = currPopulation.selection();
                Individual paretn2 = currPopulation.selection();

                Individual child = Population.crossover(parent1, paretn2);

                newPopulation.setIndividual(j, child);
            }

            newPopulation.mutation(); //Mutation

            currPopulation = newPopulation; //pass on to next generation
            newPopulation = new Population(POPULATION_SIZE);
        }

        System.out.println(best_path.getDistance());
    }
}
