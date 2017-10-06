package com.lightbend.akka.vasko.actor;

import akka.actor.*;
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
                    System.out.println("FileReaderLaunched");
                    InputStream inputStream = new FileInputStream(message.filePath);
                    byte[] buf = new byte[8];
                    ActorRef routerManager = CounterMain.actorSystem.actorOf(Props.create(RouterManager.class));
                    while ((inputStream.read(buf)) >= 0) {

                        routerManager.tell(new Work(new String(buf)), getSelf());
                    }
                    routerManager.tell(new Work(""), getSelf());
                })

                .build();
    }
}
