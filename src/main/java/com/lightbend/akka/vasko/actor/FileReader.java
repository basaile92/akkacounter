package com.lightbend.akka.vasko.actor;

import akka.actor.*;
import akka.routing.RoundRobinPool;
import com.lightbend.akka.vasko.message.FileWork;
import com.lightbend.akka.vasko.message.FinishWork;
import com.lightbend.akka.vasko.main.CounterMain;
import com.lightbend.akka.vasko.message.Work;

import java.io.FileInputStream;
import java.io.InputStream;

public class FileReader extends AbstractActor{

    public Receive createReceive() {
        return receiveBuilder()
                .match(FileWork.class, message -> {
                    InputStream inputStream = new FileInputStream(message.filePath);
                    byte[] buf = new byte[8];
                    ActorRef routerManager = CounterMain.actorSystem.actorOf(Props.create(RouterManager.class));

                    //ActorRef routerManager = getContext().actorOf(new RoundRobinPool(5).props(Props.create(Worker.class)),"router");

                    while ((inputStream.read(buf)) >= 0) {

                        routerManager.tell(new Work(new String(buf)), getSelf());
                    }
                    //ActorRef printer = getContext().actorOf(Props.create(Printer.class));
                    //srouterManager.tell(new Display(), printer);
                })

                .build();
    }
}
