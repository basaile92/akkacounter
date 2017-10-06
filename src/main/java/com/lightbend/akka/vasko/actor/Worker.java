package com.lightbend.akka.vasko.actor;

import akka.actor.*;
import com.lightbend.akka.vasko.main.CounterMain;
import com.lightbend.akka.vasko.message.CharcountWork;
import com.lightbend.akka.vasko.message.Work;

public class Worker extends AbstractActor{

    private int counter;

    private void count(String message) {

        for(int i = 0; i < message.length(); i++)
            this.counter++;
    }


    public Receive createReceive() {
        return receiveBuilder()
                .match(Work.class, message -> {
                    if(message.payload.equals("")){
                        ActorRef routerManager = CounterMain.actorSystem.actorOf(Props.create(RouterManager.class));
                        routerManager.tell(new CharcountWork(counter), getSender());

                    }else {
                        count(message.payload);
                    }
                })
                .build();
    }

}
