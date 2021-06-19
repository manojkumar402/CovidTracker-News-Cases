package com.example.bottomnavigation;

public class StateModel{
    private String mStateName;
    private String mRecovered;
    private String mDeaths;
    private String mCases;
    private int mIncreTot;
    private int mIncreDeath;
    private int mIncreRecoverd;


    StateModel(String stateName, String  NumRecovered, String NumDeaths, String NumCases, int incrmentRec, int incrementDeath, int incrementTot){
        this.mStateName = stateName;
        this.mRecovered = NumRecovered;
        this.mDeaths = NumDeaths;
        this.mCases = NumCases;
        this.mIncreTot = incrementTot;
        this.mIncreRecoverd = incrmentRec;
        this.mIncreDeath = incrementDeath;
    }



    public String getmStateName() {
        return mStateName;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public String getmCases() {
        return mCases;
    }

    public int getmIncreTot() {
        return mIncreTot;
    }

    public int getmIncreDeath() {
        return mIncreDeath;
    }

    public int getmIncreRecoverd() {
        return mIncreRecoverd;
    }



}
