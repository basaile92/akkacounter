package com.lightbend.akka.vasko.actor;

import akka.actor.*;
import com.lightbend.akka.vasko.message.EndOfFile;
import com.lightbend.akka.vasko.message.FileWork;
import com.lightbend.akka.vasko.main.CounterMain;
import com.lightbend.akka.vasko.message.Work;

import java.io.*;

public class FileReader extends AbstractActor{

    public Receive createReceive() {
        return receiveBuilder()
                .match(FileWork.class, message -> {
                    InputStream inputStream = new FileInputStream(message.filePath);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    ActorRef routerManager = CounterMain.actorSystem.actorOf(Props.create(RouterManager.class));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {

                        routerManager.tell(new Work(line), getSelf());
                    }
                    routerManager.tell(new EndOfFile(), ActorRef.noSender());
                })
                .build();
    }
}
