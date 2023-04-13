package com.example.capstone.Models.Logic;

import java.util.ArrayList;
import java.util.List;

public abstract class ACSolver {
    static List<Process> PROCESSES = new ArrayList<>();

    public ACSolver(List<Process> p) {
        PROCESSES.clear();
        PROCESSES.addAll(p);
    }

    public List<Process> getProcesses() {
        return this.getProcesses();
    }


    public ACSolver(Process p){
        PROCESSES.clear();
        PROCESSES.add(p);
    }




}