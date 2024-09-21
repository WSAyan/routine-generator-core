package com.campunix;

import java.util.*;
import java.util.stream.Collectors;

public class RoutineGenerator {

    public List<Gene> availableGenes = Arrays.asList(
            new Gene("CSE-203", "EI", "2-1", 1, true),
            new Gene("CSE-205", "NAR", "2-1", 1, false),
            new Gene("CSE-206", "GM", "2-1", 1, false),
            new Gene("CSE-207", "MMB", "2-1", 1, false),
            new Gene("CSE-208", "MZR", "2-1", 1, false),
            new Gene("CSE-209", "MAI", "2-1", 1, false),
            new Gene("CSE-210", "MAI", "2-1", 1, true),
            new Gene("CSE-212", "EI", "2-1", 1, true),
            new Gene("CSE-303", "SKS", "3-1", 2, false),
            new Gene("CSE-305", "BA", "3-1", 2, false),
            new Gene("CSE-307", "JKD", "3-1", 2, false),
            new Gene("CSE-309", "AKA", "3-1", 2, false),
            new Gene("CSE-314", "SB", "3-1", 2, true),
            new Gene("CSE-304", "SKS", "3-1", 2, true)
    );

    public int totalPopulation = 10;
    private final Random random = new Random();

    public Chromosome generate() {
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
                return chromosomes.get(0);
            }

            // Selection
            List<Chromosome> newChromosomes = selectBestPopulation(chromosomes);

            newChromosomes = performCrossover(newChromosomes);

            newChromosomes = performMutation(newChromosomes);

            chromosomes = newChromosomes;
            generation++;
        }

        return chromosomes.get(0);
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

    // Perform mutation to maintain genetic diversity
    private List<Chromosome> performMutation(List<Chromosome> population) {
        for (Chromosome schedule : population) {
            if (random.nextDouble() < 0.1) { // Mutation rate of 10%
                schedule.Mutate();
            }
        }
        return population;
    }

    // Print the schedule
    private static void printSchedule(Chromosome schedule) {
        // Assuming you're using a JSON library such as Gson
        //System.out.println(new com.google.gson.Gson().toJson(schedule.getGenes()));

        List<Gene> ordered = schedule.getGenes().stream()
                .sorted(Comparator.comparingInt(Gene::getCellNumber))
                .collect(Collectors.toList());
        for (int i = 0; i < schedule.getGenes().size(); i++) {
            Gene gene = ordered.get(i);
            System.out.println(String.format("Time Slot %d: Class - %s, Teacher - %s, CellNumber - %d, (row, col) = (%d, %d)",
                    i + 1, gene.getCourseCode(), gene.getCourseTeacher(), gene.getCellNumber(),
                    gene.getCellNumber() / 5, gene.getCellNumber() % 5));
        }
    }
}

