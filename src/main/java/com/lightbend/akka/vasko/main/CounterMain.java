package com.lightbend.akka.vasko.main;

import akka.actor.*;
import com.lightbend.akka.vasko.actor.FileReader;
import com.lightbend.akka.vasko.actor.Printer;
import com.lightbend.akka.vasko.actor.RouterManager;
import com.lightbend.akka.vasko.message.CharcountWork;
import com.lightbend.akka.vasko.message.FileWork;

import java.io.IOException;

public class CounterMain {

    public static ActorSystem actorSystem = ActorSystem.create("vasko");


        public static void main(String[] args) {

            try {
                ActorRef fileReader = actorSystem.actorOf(Props.create(FileReader.class));
                fileReader.tell(new FileWork("/etc/passwd"), ActorRef.noSender());
            } finally {
            }
        }


}