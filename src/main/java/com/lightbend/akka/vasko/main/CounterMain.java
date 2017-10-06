package com.lightbend.akka.vasko.main;

import akka.actor.*;
import com.lightbend.akka.vasko.actor.RouterManager;
import com.lightbend.akka.vasko.message.FileWork;

import java.io.IOException;

public class CounterMain {

    public static ActorSystem actorSystem = ActorSystem.create("vasko");


        public static void main(String[] args) {

            try {
                ActorRef routerManager = actorSystem.actorOf(Props.create(RouterManager.class));
                routerManager.tell(new FileWork("/default.profraw"), ActorRef.noSender());
                System.out.print("Finished ?");
                System.in.read();

            } catch (IOException e) {

            } finally {
                actorSystem.terminate();
            }
        }


}