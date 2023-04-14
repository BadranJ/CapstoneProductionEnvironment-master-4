package com.example.capstone.Models.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
// import java.util.Map;
// import java.security.KeyStore.Entry;
import java.util.Map;

public class Process extends ACProcess {

    State state1;
    State state2;
    char process;
    List<State> solvedStates = new ArrayList<>();
    Map<String, List<Double>> CpCvMap = new HashMap<>();
    private double heat;
    private double work;
    private double deltaEntropy;
    private double deltaEnthalpy;

    private static Map<String, List<Double>> stateValues = new HashMap<>();
    private double n;
    private double tolerance = 0.001;

    public Process(State state1, State state2, char process) {
        super(state1, state2, process);
        this.state1 = state1;
        this.state2 = state2;
        this.process = process;

        List<Double> CpCvColdAir = new ArrayList<>();
        CpCvColdAir.add(1.005); // Cp value for cold air
        CpCvColdAir.add(0.718); // Cv value for cold air, this will change with other types of air
        CpCvMap.put("ColdAir", CpCvColdAir);

    }


    public Process(State state1, State state2, double n, char process) {
        super(state1, state2, process);
        this.state1 = state1;
        this.state2 = state2;
        this.process = process;
        this.n = n;

        List<Double> CpCvColdAir = new ArrayList<>();
        CpCvColdAir.add(1.005); // Cp value for cold air
        CpCvColdAir.add(0.718); // Cv value for cold air, this will change with other types of air
        CpCvMap.put("ColdAir", CpCvColdAir);

    }

    // public Process(List<Process> p){
    // super(p);
    // }



    public void validateStates() {

        double R = 0.287;

        //if state1 has all values check if its a valid state

        //if state1 has all values check if its a valid state

        if(state1.hasAllValues()==true){

            double pv=state1.getValues().get(1)*state1.getValues().get(2);
            double rt=state1.getValues().get(0)*R;

            if(Math.abs(pv - rt) > 1e-6){ //for tolerance
                throw new IllegalArgumentException("The values for the state " +state1.toString()+" are not valid \n since P=" + state1.getValues().get(1) +" * v="+state1.getValues().get(2)+ " !="+ " R=0.287 * T="+state1.getValues().get(0));
            }
        }

        //state2 as well
        if(state2.hasAllValues()==true){
            double pv=state2.getValues().get(1)*state2.getValues().get(2);
            double rt=state2.getValues().get(0)*R;
            if(Math.abs(pv - rt) > 1e-6){
                throw new IllegalArgumentException("The values for the state " +state2.toString()+" are not valid  \n since P=" + state2.getValues().get(1) +" * v="+state2.getValues().get(2)+ " !="+ " R=0.287 * T="+state2.getValues().get(0));
            }
        }

        switch (this.process) {
            case 'p': // constant pressure

                if (state1.getPressure() != 0 && state2.getPressure() != 0 && state1.getPressure() != state2.getPressure()) {
                    throw new IllegalArgumentException("Pressure must be constant for a constant pressure process.");
                }
//                if (state1.getPressure() == 0 && state2.getPressure() == 0) {
//                    throw new IllegalArgumentException("Both states cannot have 0 pressure for a constant pressure process.");
//                }
                if (state1.getPressure() == 0) {
                    state1.setPressure(state2.getPressure());
                } else if (state2.getPressure() == 0) {
                    state2.setPressure(state1.getPressure());
                }

                int n = 1;



                if ((state1.getTemp() != 0 && state2.getTemp() != 0 && state1.getVolume() != 0 && state2.getVolume() != 0)) { // here we force it to check if the values in the states are consistent whether the values exist or not.
                    double calculatedPressure1 = state1.getTemp() * R / state1.getVolume();
                    double calculatedPressure2 = state2.getTemp() * R / state2.getVolume();

                    if (Math.abs(calculatedPressure1 - calculatedPressure2) > 1e-6) {
                        throw new IllegalArgumentException("The given states are not consistent for a constant pressure process.");

                    }
                }

                break;
            case 'v': // constant volume
                if (state1.getVolume() != 0 && state2.getVolume() != 0 && state1.getVolume() != state2.getVolume()) {
                    throw new IllegalArgumentException("Volume must be constant for a constant volume process.");
                }
                //if (state1.getVolume() == 0 && state2.getVolume() == 0) {
                //  throw new IllegalArgumentException("Both states cannot have 0 volume for a constant volume process.");
                //}
                if (state1.getVolume() == 0) {
                    state1.setVolume(state2.getVolume());
                } else if (state2.getVolume() == 0) {
                    state2.setVolume(state1.getVolume());
                }

                int k = 1;

                if ((state1.getTemp() != 0 && state2.getTemp() != 0) && (state1.getPressure() != 0 && state2.getPressure() != 0)) {
                    double calculatedVolume1 = (R * state1.getTemp()) / state1.getPressure();
                    double calculatedVolume2 = (R * state2.getTemp()) / state2.getPressure();

                    if (Math.abs(calculatedVolume1 - calculatedVolume2) > 1e-6) {
                        throw new IllegalArgumentException("The given states are not consistent for a constant volume process.");
                    }
                }

                break;

            case 't': // constant temperature
                // Update the validation for constant temperature process
                if (state1.getTemp() != 0 && state2.getTemp() != 0 && state1.getTemp() != state2.getTemp()) {
                    throw new IllegalArgumentException("The given states are not consistent for a constant temperature process.");
                }


                if (state1.getPressure() != 0 && state2.getPressure() != 0 && state1.getVolume() != 0 && state2.getVolume() != 0) {

                    double checkT1 = state1.getPressure() * state1.getVolume() / R;
                    double checkT2 = state2.getPressure() * state2.getVolume() / R;
                    if (Math.abs(checkT1 - checkT2) > 1e-3) {
                        throw new IllegalArgumentException("The given states are not consistent for a constant temperature process.");
                    }
                }
                break;
            case 'x':
                break;
            case 'y':
                break;

            default:
                throw new IllegalArgumentException("Invalid process type.");
        }
    }


    public void passProperties() {

        double p1 = state1.getPressure();
        double t1 = state1.getTemp();
        double v1 = state1.getVolume();
        double p2 = state2.getPressure();
        double t2 = state2.getTemp();
        double v2 = state2.getVolume();
        double R = 0.287;
        double n = this.getN();
        // double Cp = 1.005;
        // double Cv = 0.718;


        switch (this.process) {
            case 't':

                double t1_init = state1.getTemp();
                double t2_init = state2.getTemp();

                // Validation check
                if (state1.getTemp() != 0 && state2.getTemp() != 0 && state1.getTemp() != state2.getTemp()) {
                    throw new IllegalArgumentException("Invalid input: conflicting temperatures for an isothermal process");
                }

                //deciding from which state to pass the property from


                if (t1_init == t2_init) {
                    t2 = t1;
                    this.state2.setTOriginal(t2);
                } else if (t1_init == 0 && t2_init != 0) {
                    t1 = t2;
                    this.state1.setTOriginal(t1);
                } else {
                    t2 = t1;
                    this.state2.setTOriginal(t2);
                }

                if (p1 != 0 & p2 != 0) {
                    this.setWork(R * (t1) * Math.log(p1 / p2));
                } else {
                    this.setWork(R * (t1) * (Math.log(v2 / v1)));
                }
                this.setHeat(this.getWork());


                break;
            case 'p':


                double p1_init = state1.getPressure();
                double p2_init = state2.getPressure();

                // Validation check
                if (state1.getPressure() != 0 && state2.getPressure() != 0 && state1.getPressure() != state2.getPressure()) {
                    throw new IllegalArgumentException("Invalid input: conflicting pressures for an isobaric process");
                }

                //deciding from which state to pass the property from

                if (p1_init == p2_init) {
                    p2 = p1;
                    this.state2.setPOriginal(p2);
                } else if (p1_init == 0 && p2_init != 0) {
                    p1 = p2;
                    this.state1.setPOriginal(p1);
                } else {
                    p2 = p1;
                    this.state2.setPOriginal(p2);
                }
                this.setWork(p1 * (v2 - v1));
                double CpColdAir = CpCvMap.get("ColdAir").get(0);
                this.setHeat(CpColdAir * (t2 - t1));

                break;
            case 'v':
                double v1_init = state1.getVolume();
                double v2_init = state2.getVolume();

                // Validation check
                if (state1.getVolume() != 0 && state2.getVolume() != 0 && state1.getVolume() != state2.getVolume()) {
                    throw new IllegalArgumentException("Invalid input: conflicting volumes for an isochoric process");
                }

                if (v1_init == v2_init) {
                    v2 = v1;
                    this.state2.setVOriginal(v2);
                } else if (v1_init == 0 && v2_init != 0) {
                    v1 = v2;
                    this.state1.setVOriginal(v1);
                } else {
                    v2 = v1;
                    this.state2.setVOriginal(v2);
                }

                this.setWork(0);
                double CvColdAir = CpCvMap.get("ColdAir").get(1);
                this.setHeat(CvColdAir * (t2 - t1));


                break;

            default:
                System.out.println("Something was wrong in solveState process");


        }

        // delta entropy and delta enthalpy calculated after passing properties and work
        // & heat
        double CpColdAir = CpCvMap.get("ColdAir").get(0);
        this.setDeltaEntropy((CpColdAir * Math.log(t1 / t2)) - (R * Math.log(p2 / p1)));
        this.setDeltaEnthalpy(CpColdAir * (t2 - t1));


        state1.setTemp(t1);
        state1.setPressure(p1);
        state1.setVolume(v1);

        state2.setTemp(t2);
        state2.setPressure(p2);
        state2.setVolume(v2);

    }


    public List<State> solveProcess() {


        double CvColdAir = CpCvMap.get("ColdAir").get(1);
        double CpColdAir = CpCvMap.get("ColdAir").get(0);
        double nn = CpColdAir / CvColdAir; //isentropic n value.

        double R = 0.287;

        validateStates();


        switch (this.process) {
            case 'x': //Polytropic

                //determine first which state has fewer known properties.

                if ((state1.getPressure() != 0 && state1.getTemp() != 0) || (state1.getPressure() != 0 && state1.getVolume() != 0) || (state1.getTemp() != 0 && state1.getVolume() != 0)) {

                    this.state1.solve();
                    System.out.println("Initially solving state 1 for polytropic " + state1.getValues());

                    if (state2.getPressure() != 0 & state2.getVolume() == 0 & state2.getTemp() == 0) {
                        this.state2.setVolume((this.state1.getVolume()) * (Math.pow(state2.getPressure() / this.state1.getPressure(), -1 / this.getN())));
                    } else if (state2.getPressure() == 0 & state2.getVolume() != 0 & state2.getTemp() == 0) {
                        this.state2.setPressure(this.state1.getPressure() * (Math.pow(this.state1.getVolume() / state2.getVolume(), this.getN())));
                    } else {
                        this.state2.setPressure(this.state1.getPressure() * Math.pow(state2.getTemp() / this.state1.getTemp(), this.getN() / (this.getN() - 1)));
                    }
                    this.state2.solve();

                } else {
                    this.state2.solve();
                    System.out.println("Initially solving state 2 for polytropic " + state2.getValues());

                    if (state1.getPressure() != 0 & state1.getVolume() == 0 & state1.getTemp() == 0) {
                        this.state1.setVolume((this.state2.getVolume()) * (Math.pow(state1.getPressure() / this.state2.getPressure(), -1 / this.getN())));
                    } else if (state1.getPressure() == 0 & state1.getVolume() != 0 & state1.getTemp() == 0) {
                        this.state1.setPressure(this.state2.getPressure() * (Math.pow(this.state2.getVolume() / state1.getVolume(), this.getN())));
                    } else {
                        this.state1.setPressure(this.state2.getPressure() * Math.pow(state1.getTemp() / this.state2.getTemp(), this.getN() / (this.getN() - 1)));
                    }
                    this.state1.solve();
                }


                double work = (R * (this.state2.getTemp() - this.state1.getTemp())) / (1 - this.getN());
                this.setWork(work);

                double heatt = (CvColdAir) * (this.state2.getTemp() - this.state1.getTemp()) + this.getWork();

                this.setHeat(heatt);
                //this.setWork((R * (t2 - this.state1.getTemp())) / (1 - this.getN()));
                //this.setHeat(CvColdAir * (t2 - t1) + this.getWork());


                break;

            case 'y': //Isentropic

                if ((state1.getPressure() != 0 && state1.getTemp() != 0) || (state1.getPressure() != 0 && state1.getVolume() != 0) || (state1.getTemp() != 0 && state1.getVolume() != 0)) {
                    this.state1.solve();
                    System.out.println("Initially solving state 1 for isentropic " + state1.getValues());

                    if (state2.getPressure() != 0 & state2.getVolume() == 0 & state2.getTemp() == 0) {
                        this.state2.setVolume((this.state1.getVolume()) * (Math.pow(state2.getPressure() / this.state1.getPressure(), -1 / nn)));
                    } else if (state2.getPressure() == 0 & state2.getVolume() != 0 & state2.getTemp() == 0) {
                        this.state2.setPressure(this.state1.getPressure() * (Math.pow(this.state1.getVolume() / state2.getVolume(), nn)));
                    } else {
                        this.state2.setPressure(this.state1.getPressure() * Math.pow(state2.getTemp() / this.state1.getTemp(), nn / (nn - 1)));
                    }
                    this.state2.solve();

                } else {
                    this.state2.solve();
                    System.out.println("Initially solving state 2 for isentropic " + state2.getValues());

                    if (state1.getPressure() != 0 & state1.getVolume() == 0 & state1.getTemp() == 0) {
                        this.state1.setVolume((this.state2.getVolume()) * (Math.pow(state1.getPressure() / this.state2.getPressure(), -1 / nn)));
                    } else if (state1.getPressure() == 0 & state1.getVolume() != 0 & state1.getTemp() == 0) {
                        this.state1.setPressure(this.state2.getPressure() * (Math.pow(this.state2.getVolume() / state1.getVolume(), nn)));
                    } else {
                        this.state1.setPressure(this.state2.getPressure() * Math.pow(state1.getTemp() / this.state2.getTemp(), nn / (nn - 1)));
                    }
                    this.state1.solve();
                }


                double workk = (R * (state2.getTemp() - state1.getTemp())) / (1 - nn);
                this.setWork(workk);
                this.setHeat(0);

                break;

            default:
                // List<State> solvedStates = new ArrayList<>();

                // checking which state to solve first based on which has fewer properties.

                if ((state1.getPressure() != 0 && state1.getTemp() != 0) || (state1.getPressure() != 0 && state1.getVolume() != 0) || (state1.getTemp() != 0 && state1.getVolume() != 0)) {

                    this.state2.solve();
                    System.out.println(
                            "We are asserting that all values in " + state1.toString() + " are: " + state1.assertState());

                    //this.passProperties();
                    this.state1.solve();

                    System.out.println(
                            "We are asserting that all values in " + state2.toString() + " are: " + state2.assertState());
                } else {

                    this.state1.solve();
                    System.out.println(
                            "We are asserting that all values in " + state2.toString() + " are: " + state2.assertState());

                    //this.passProperties();
                    this.state2.solve();

                    System.out.println(
                            "We are asserting that all values in " + state1.toString() + " are: " + state1.assertState());

                }


                this.solvedStates.add(state1);
                this.solvedStates.add(state2);
        }


        validateStates();


        return solvedStates;
    }


    public boolean allSolved() {
        boolean flag = true;

        for (Iterator<State> iter = STATES.iterator(); iter.hasNext(); ) {
            State s = iter.next();
            if (s.isSolved() == false) {
                flag = false;
            }
            System.out.println(s.toString() + " State solved: " + s.getSolvedFlag() + " Flag: " + flag);
        }

        return flag;
    }
    @Override
    public String toString() {
        return "Process{" +
                "leftState=" + state1.getValues() +
                ", rightState=" + state2.getValues() +
                ", processType=" + this.process +
                '}';
    }


//    public Map<String, List<Double>> getSolvedStatesValues() {
//        Map<String, List<Double>> statesValues = new HashMap<>();
//        // List<Double> vals = new ArrayList<>();
//        for (Iterator<State> s = this.solvedStates.iterator(); s.hasNext();) {
//            State stat = s.next();
//            statesValues.put(stat.toString(), stat.getValues());
//        }
//        return statesValues;
//    }


    // getters

    public static Map<String, List<Double>> getStateValues() {
        return stateValues;
    }


    public char getProcess() {
        return this.process;
    }

    public double getN() {
        return this.n;
    }

    public boolean getState1Solved() {
        return this.state1.getSolvedFlag();
    }

    public boolean getState2Solved() {
        return this.state2.getSolvedFlag();
    }

    public State getState1() {
        return this.state1;
    }

    public State getState2() {
        return this.state2;
    }

    public double getWork() {
        return this.work;
    }

    public double getHeat() {
        return this.heat;
    }

    public double getDeltaEntropy() {
        return this.deltaEntropy;
    }

    public double getDeltaEnthalpy() {
        return this.deltaEnthalpy;
    }

    // setters

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public void setN(double n) {
        this.n = n;
    }

    public void setWork(double work) {
        this.work = work;
    }

    public void setDeltaEntropy(double deltaEntropy) {
        this.deltaEntropy = deltaEntropy;
    }

    public void setDeltaEnthalpy(double deltaEnthalpy) {
        this.deltaEnthalpy = deltaEnthalpy;
    }

    public void passPropertiesReverse() {

        State temp = state1;
        state1 = state2;
        state2 = temp;
        passProperties();
        temp = state1;
        state1 = state2;
        state2 = temp;

    }

}

