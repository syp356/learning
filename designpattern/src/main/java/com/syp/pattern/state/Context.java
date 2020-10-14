package com.syp.pattern.state;

public class Context {
    public static final State STATE_LOGIN = new ConcreateStateA();
    public static final State STATE_UNLOGIN = new ConcreateStateA();
    private State currentState = STATE_UNLOGIN;
    {
        STATE_LOGIN.setContext(this);
        STATE_UNLOGIN.setContext(this);
    }

    public void setState(State state){
        this.currentState = state;
    }

    public State getState(){
        return this.currentState;
    }

    public void favorite(){
        this.currentState.favorite();
    }

    public void comment(String commnet){
        this.currentState.comment(commnet);
    }
}
