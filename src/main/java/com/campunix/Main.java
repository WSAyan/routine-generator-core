package com.campunix;

import java.util.List;

import static java.util.Arrays.*;

public class Main {
    public static void main(String[] args) {
        List<Gene> genes = asList(
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

        RoutineGenerator generator = new RoutineGenerator.RoutineGeneratorBuilder()
                .setAvailableGenes(genes)
                .setTotalPopulation(10)
                .build();

        generator.generate();
    }
}