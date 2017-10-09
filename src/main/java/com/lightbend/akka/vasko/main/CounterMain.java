package com.lightbend.akka.vasko.main;

import akka.actor.*;
import com.lightbend.akka.vasko.actor.FileReader;
import com.lightbend.akka.vasko.message.FileWork;


public class CounterMain {

    public static ActorSystem actorSystem = ActorSystem.create("vasko");


        public static void main(String[] args) {

            try {
                ActorRef fileReader = actorSystem.actorOf(Props.create(FileReader.class));
                fileReader.tell(new FileWork("./pom.xml"), ActorRef.noSender());
            } finally {
            }
        }


}