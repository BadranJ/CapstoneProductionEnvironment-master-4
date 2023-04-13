package com.example.capstone.Models.Logic;


import java.util.*;

public class Solver extends ACSolver {

    public Solver(List<Process> p) {
        super(p);
        sortProcesses();
    }

    public Solver(Process p) {
        super(p);
        sortProcesses();
    }

    private void sortProcesses() {
        Collections.sort(PROCESSES, (Process a, Process b) -> {
            int knownVarsA = a.state1.countKnownProperties() + a.state2.countKnownProperties();
            int knownVarsB = b.state1.countKnownProperties() + b.state2.countKnownProperties();
            return knownVarsA - knownVarsB; //was knownVarsB-knownVarsA.
        });
    }

    private void propagateKnownInformation() {
        for (int i = 0; i < PROCESSES.size(); i++) {
            Process currentProcess = PROCESSES.get(i);
            for (int j = i + 1; j < PROCESSES.size(); j++) {
                Process otherProcess = PROCESSES.get(j);

                // Check if both processes share the same state and call passProperties() accordingly
                if (currentProcess.state1.equals(otherProcess.state1)) {
                    currentProcess.passProperties();
                    otherProcess.passProperties();
                } else if (currentProcess.state1.equals(otherProcess.state2)) {
                    currentProcess.passProperties();
                    otherProcess.passPropertiesReverse();
                } else if (currentProcess.state2.equals(otherProcess.state1)) {
                    currentProcess.passPropertiesReverse();
                    otherProcess.passProperties();
                } else if (currentProcess.state2.equals(otherProcess.state2)) {
                    currentProcess.passPropertiesReverse();
                    otherProcess.passPropertiesReverse();
                }
            }
        }
    }
    public Map<String, List<Double>> CompleteSolve() {
        Map<String, List<Double>> finalProccesses = new TreeMap<>();

        while (true) {
            boolean newStatesSolved = false;

            propagateKnownInformation();
            sortProcesses();

            for (Iterator<Process> p = PROCESSES.iterator(); p.hasNext();) {
                Process currentProcess = p.next();

                boolean prevState1Solved = currentProcess.state1.isSolved();
                boolean prevState2Solved = currentProcess.state2.isSolved();

                currentProcess.solveProcess();

                if (!prevState1Solved && currentProcess.state1.isSolved()) {
                    newStatesSolved = true;
                }
                if (!prevState2Solved && currentProcess.state2.isSolved()) {
                    newStatesSolved = true;
                }

                finalProccesses.put(currentProcess.state1.toString(), currentProcess.state1.getValues());
                finalProccesses.put(currentProcess.state2.toString(), currentProcess.state2.getValues());
            }

            if (!newStatesSolved) {
                break;
            }
        }

        return finalProccesses;
    }
    public void PrintResult(List<Map<String, List<Double>>> proccesses) {
        for (Iterator<Map<String, List<Double>>> iter = proccesses.iterator(); iter.hasNext(); ) {

        }
    }
}
