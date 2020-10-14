package com.syp.pattern.state;

public abstract class State {
    protected Context context;
    public abstract void favorite();
    public abstract void comment(String comment);

    public void setContext(Context context) {
        this.context = context;
    }
}
