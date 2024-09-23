package com.campunix;

import java.util.*;
import java.util.stream.Collectors;

public class RoutineGenerator {

    public List<Gene> availableGenes;
    public int totalPopulation;
    private final Random random = new Random();

    private RoutineGenerator(RoutineGeneratorBuilder builder) {
        this.availableGenes = builder.availableGenes;
        this.totalPopulation = builder.totalPopulation;
    }

    public static class RoutineGeneratorBuilder {
        private List<Gene> availableGenes = new ArrayList<>();
        private int totalPopulation = 10; // Default value

        // Set available genes
        public RoutineGeneratorBuilder setAvailableGenes(List<Gene> availableGenes) {
            this.availableGenes = availableGenes;
            return this;
        }

        // Set total population
        public RoutineGeneratorBuilder setTotalPopulation(int totalPopulation) {
            this.totalPopulation = totalPopulation;
            return this;
        }

        // Build and return the RoutineGenerator object
        public RoutineGenerator build() {
            return new RoutineGenerator(this);
        }
    }

    public void generate() {
        List<Chromosome> chromosomes = initializePopulation();

        int generation = 0;
        while (generation < 1000) { // max generations
            // Evaluate fitness
            for (Chromosome chromosome : chromosomes) {
                chromosome.CalculateFitness();
            }

            // Sort population by fitness
            chromosomes = chromosomes.stream()
                    .sorted(Comparator.comparingDouble(Chromosome::getFitness).reversed())
                    .collect(Collectors.toList());

            // If best solution found, break
            if (chromosomes.get(0).getFitness() == 1.0) {
                System.out.println("Optimal schedule found:");
                printSchedule(chromosomes.get(0));
                return;
            }

            // Selection
            List<Chromosome> newChromosomes = selectBestPopulation(chromosomes);

            newChromosomes.addAll(performCrossover(newChromosomes));
            newChromosomes.addAll(performMutation(newChromosomes));

            chromosomes = newChromosomes;
            generation++;
        }
    }

    private List<Chromosome> initializePopulation() {
        List<Chromosome> chromosomes = new ArrayList<>();
        for (int i = 0; i < totalPopulation; i++) {
            chromosomes.add(new Chromosome(availableGenes));
        }
        return chromosomes;
    }

    private static List<Chromosome> selectBestPopulation(List<Chromosome> population) {
        return population.stream().limit(5).collect(Collectors.toList()); // Select top 5 schedules
    }

    private static List<Chromosome> performCrossover(List<Chromosome> population) {
        List<Chromosome> newPopulation = new ArrayList<>(population);
        for (int i = 0; i < population.size() - 1; i += 2) {
            Chromosome parent1 = population.get(i);
            Chromosome parent2 = population.get(i + 1);
            Chromosome child = parent1.Crossover(parent2);
            newPopulation.add(child);
        }
        return newPopulation;
    }

    private List<Chromosome> performMutation(List<Chromosome> population) {
        for (Chromosome schedule : population) {
            if (random.nextDouble() < 0.1) { // Mutation rate of 10%
                schedule.Mutate();
            }
        }
        return population;
    }

    private static void printSchedule(Chromosome schedule) {
        // Assuming you're using a JSON library such as Gson
        // System.out.println(new com.google.gson.Gson().toJson(schedule.getGenes()));

        List<Gene> ordered = schedule.getGenes().stream()
                .sorted(Comparator.comparingInt(Gene::getCellNumber))
                .toList();
        for (int i = 0; i < schedule.getGenes().size(); i++) {
            Gene gene = ordered.get(i);
            System.out.printf("Time Slot %d: Class - %s, Teacher - %s, CellNumber - %d, (row, col) = (%d, %d)%n",
                    i + 1, gene.getCourseCode(), gene.getCourseTeacher(), gene.getCellNumber(),
                    gene.getCellNumber() / 5, gene.getCellNumber() % 5);
        }
    }
}

