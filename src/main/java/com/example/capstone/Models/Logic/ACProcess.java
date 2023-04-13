package com.example.capstone.Models.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ACProcess {

    static Map<Character, List<State>> PROCESSES = new HashMap<>();
    static List<State> STATES = new ArrayList<>();
    private State state1;
    private State state2;
    private char process;

    public ACProcess(State state1, State state2, char process) {
        this.state1 = state1;
        this.state2 = state2;
        this.process = process;

        STATES.add(this.state1);
        STATES.add(this.state2);
        PROCESSES.put(this.process, STATES);
    }

    // public ACProcess(List<Process> processes){

    // }


}