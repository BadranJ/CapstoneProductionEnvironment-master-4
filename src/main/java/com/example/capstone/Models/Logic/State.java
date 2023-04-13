package com.example.capstone.Models.Logic;

import java.util.ArrayList;
import java.util.List;

public class State {

    private double P;

    private double v;

    private double T;

    private boolean pGiven;

    private boolean vGiven;

    private boolean tGiven;

    private boolean solved;

    private double pOriginal;

    private double vOriginal;

    private double tOriginal;

    private double n;

    private String stateName;

    //private double heat;

    //private double work;

    public State(double T, double P, double v, String stateName) {

        this.P = P;

        this.v = v;

        this.T = T;


        //this.work = work;
        //this.heat = heat;

        this.stateName = stateName;

        this.pGiven = false; // these givens are supposed to be whether this was given from passed properties, AKA another state through a process
        this.vGiven = false;
        this.tGiven = false;
        this.solved = false;

        this.pOriginal = P; // these are supposed to be the original values inputted by the user
        this.vOriginal = v;
        this.tOriginal = T;
    }



    public synchronized List<Double> solve(){
        // throw an exception for one more than one value is 0, because that is not solvable
        // This is for a ideal gas with cold air

        List<Double> solvedState = new ArrayList<>();

        double P = this.getPressure();

        double T = this.getTemp();

        double v = this.getVolume();

        double R = 0.287;

        if (P == 0) {

            P = (T * R) / v;

            this.setTFlag(true);
            this.setVFlag(true); // if user given info



        }

        else if (T == 0) {

            T = (P * v) / R;

            this.setPFlag(true);
            this.setVFlag(true);

        }

        else if(v==0){

            v = (R * T) / P;

            this.setTFlag(true);
            this.setPFlag(true);

        }

        solvedState.add(T);
        solvedState.add(P);
        solvedState.add(v);

        this.setTemp(T);
        this.setPressure(P);
        this.setVolume(v);

        this.setSolved(this.isSolved());

        return solvedState;

    }

    public boolean assertState() {
        double tolerance = 1e-6;

        if (!pGiven) {
            if (Math.abs(this.getTemp() - this.getTOriginal()) > tolerance ||
                    Math.abs(this.getVolume() - this.getVOriginal()) > tolerance) {
                return false;
            }
        } else if (!tGiven) {
            if (Math.abs(this.getPressure() - this.getPOriginal()) > tolerance ||
                    Math.abs(this.getVolume() - this.getVOriginal()) > tolerance) {
                return false;
            }
        } else {
            if (Math.abs(this.getTemp() - this.getTOriginal()) > tolerance ||
                    Math.abs(this.getPressure() - this.getPOriginal()) > tolerance) {
                return false;
            }
        }

        return true;
    }




    @Override
    public String toString(){
        return this.stateName;
    }

    public boolean isSolved(){ // this should make sure that all of the values are neither 0 or infinity (sometimes dividing by 0 happens in the cycle, a 0 is passed)\
        // the only time we should accept a 0, is if that was user inputted information, which is why I have the question below, should we really ever accept that? For now, yes

        // this method and assert state are used within the solve class to try and decide when all of the states are fully solved so we can stop the cycle, currently does not work
        // and will run an endless loop without the counter and break statement

        // this method is called within the process class allSolved(), if we peel back the "onion of problems" this class and these two methods are the first step, then using them
        // in allSolved() inside of processes would be the next step to solve, and then in theory calling allSolved() in the cycle of the Solver class should fix all of our error
        // handling issues


        // should any single number ever be 0?
        boolean flag = true;
        if (this.getTemp() == 0 || Double.isNaN(this.getTemp())) {
            flag = false;
        } else if (this.getPressure() == 0 || Double.isNaN(this.getPressure())) {
            flag = false;
        } else if (this.getVolume() == 0 || Double.isNaN(this.getVolume())) {
            flag = false;
        } else if (!this.assertState()) {
            flag = false;
        }
        return flag;
    }

    public int countKnownProperties() {
        int count = 0;
        if (P != 0) count++;
        if (T != 0) count++;
        if (v != 0) count++;
        return count;
    }


    // setters

    public synchronized void setPressure(double P) {

        this.P = P;
    }

    public synchronized void setVolume(double v) {

        this.v = v;
    }

    public synchronized void setTemp(double T) {

        this.T = T;

    }

    public void setPFlag(boolean b) {
        this.pGiven = b;
    }

    public void setVFlag(boolean b) {
        this.vGiven = b;
    }

    public void setTFlag(boolean b) {
        this.tGiven = b;
    }

    public void setSolved(boolean b) {
        this.solved = b;
    }

    public void setPOriginal(double P) {
        this.pOriginal = P;
    }

    public void setTOriginal(double T) {
        this.tOriginal = T;
    }

    public void setVOriginal(double v) {
        this.vOriginal = v;
    }



    // getters

    public List<Double> getValues(){
        List<Double> vals = new ArrayList<>();

        vals.add(this.getTemp());
        vals.add(this.getPressure());
        vals.add(this.getVolume());
        return vals;

    }
    public boolean hasAllValues() {
        int count = 0;
        for (double element : this.getValues())
            if (element != 0) { //because in our program 0 is "null"
                count++;
            }
        return count == this.getValues().size();
    }

    public double getPressure() {

        return this.P;

    }

    public double getVolume() {

        return this.v;

    }

    public double getTemp() {

        return this.T;

    }

    public boolean getPFlag() {
        return this.pGiven;
    }

    public boolean getVFlag() {
        return this.vGiven;
    }

    public boolean getTFlag() {
        return this.tGiven;
    }

    public boolean getSolvedFlag() {
        return this.solved;
    }

    public double getPOriginal() {
        return this.pOriginal;
    }

    public double getTOriginal() {
        return this.tOriginal;
    }

    public double getVOriginal() {
        return this.vOriginal;
    }

}