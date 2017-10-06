package com.lightbend.akka.vasko.actor;

import akka.actor.AbstractActor;
import com.lightbend.akka.vasko.message.CharcountWork;

public class Printer extends AbstractActor {


    public Receive createReceive() {
        return receiveBuilder()
                .match(CharcountWork.class, message -> {
                    System.out.println("Il y a "+  message.charCount + " char dans le fichier");

                })
                .build();
    }

}
