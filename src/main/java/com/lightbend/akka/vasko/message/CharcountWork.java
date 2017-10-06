package com.lightbend.akka.vasko.message;

import java.io.Serializable;


public class CharcountWork implements Serializable{

    private static final long serialVersionUID = 1L;
    public final int charCount;
    public CharcountWork(int charCount) {
        this.charCount = charCount;
    }

}
