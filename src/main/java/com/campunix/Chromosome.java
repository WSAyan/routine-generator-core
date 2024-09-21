package com.campunix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.campunix.GeneExtensions.*;

public class Chromosome {
    public int TotalSlot = 5;
    public int TotalSemester = 2;

    public List<Gene> Genes;

    public double Fitness;

    private static final Random rand = new Random();

    public Chromosome() {
        Genes = new ArrayList<>();
    }

    public Chromosome(List<Gene> availableGenes) {
        Genes = Genes != null ? Genes : new ArrayList<>();
        for (Gene gene : availableGenes) {
            // Can be initialized in a different way
            gene.setCellNumber(CalculateCellNumber(gene));
            Genes.add(gene);
            // System.out.println(new Gson().toJson(gene)); // For debugging if needed
        }
    }

    public void CalculateFitness() {
        int conflicts = 0;

        for (int i = 0; i < Genes.size(); i++) {
            for (int j = i + 1; j < Genes.size(); j++) {
                if (Genes.get(i).getCellNumber() == Genes.get(j).getCellNumber()) {
                    conflicts++;
                }

                if (hasSameCourseTeacherOf(Genes.get(i), Genes.get(j)) &&
                        isInSameSlotOnSameDay(Genes.get(i), Genes.get(j), TotalSlot, TotalSemester)) {
                    conflicts++;
                }

                if (Genes.get(i).isLab() &&
                        hasSameCourseTeacherOf(Genes.get(i), Genes.get(j)) &&
                        isInPreviousSlotOnSameDay(Genes.get(i), Genes.get(j), TotalSlot, TotalSemester)) {
                    conflicts++;
                }
            }
        }

        Fitness = 1.0 / (1 + conflicts);
    }

    public Chromosome Crossover(Chromosome other) {
        Chromosome child = new Chromosome();

        for (int i = 0; i < Genes.size(); i++) {
            Gene gene = rand.nextDouble() < 0.5 ? Genes.get(i) : other.Genes.get(i);
            child.Genes.add(gene);
        }

        return child;
    }

    public void Mutate() {
        int index = rand.nextInt(Genes.size());
        Genes.get(index).setCellNumber(CalculateCellNumber(Genes.get(index)));
    }

    private int CalculateCellNumber(Gene gene) {
        int totalCellInADay = TotalSemester * TotalSlot;
        int currentSemester = gene.getSemesterNumber();

        int cellNumber = (rand.nextInt(5) + ((currentSemester - 1) * TotalSlot))
                + (rand.nextInt(5) * totalCellInADay);

        return cellNumber;
    }

    public double getFitness() {
        return Fitness;
    }

    public List<Gene> getGenes() {
        return Genes;
    }
}

