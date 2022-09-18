package GA;

public class Selection {
    public enum SELECTION {
        ROULETTE_WHEEL,
        TOURNAMENT
    }

    public static Individual rouletteWheelSelection(Population population) {
        double[] modifiedFitness = new double[GA.POPULATION_SIZE];

        double sum = 0;
        for(Individual individual : population.getPopulation()) {
            sum += individual.getFitness();
        }

        for(int i = 0; i < GA.POPULATION_SIZE; i++) {
            modifiedFitness[i] = population.getIndividual(i).getFitness() / sum;
        }


        double probability = Math.random();
        double sumProbability = 0;

        int index = -1;
        while(sumProbability <= probability) {
            index++;
            sumProbability += modifiedFitness[index];
        }

        return population.getIndividual(index);
    }

    public static Individual tournamentSelection(Population population) {
        Population tournament = new Population(GA.TOURNAMENT_SIZE);

        for(int i = 0; i < GA.TOURNAMENT_SIZE; i++) {
            int index = (int) Math.floor(Math.random() * GA.POPULATION_SIZE);
            tournament.setIndividual(i, population.getIndividual(index));
        }

        return tournament.getElitist();
    }
}
