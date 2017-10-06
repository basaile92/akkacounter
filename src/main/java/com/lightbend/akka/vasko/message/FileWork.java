package com.lightbend.akka.vasko.message;

import java.io.Serializable;

public class FileWork implements Serializable {

    private static final long serialVersionUID = 1L;
    public final String filePath;
    public FileWork(String filePath) {
        this.filePath = filePath;
    }

}
