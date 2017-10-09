package com.lightbend.akka.vasko.actor;


import akka.actor.*;
import akka.routing.*;
import com.lightbend.akka.vasko.main.CounterMain;
import com.lightbend.akka.vasko.message.EndOfFile;
import com.lightbend.akka.vasko.message.Work;

public class RouterManager extends AbstractActor {

    Router router;
    int counter;
    private int workers;
    boolean endOfFile = false;

    {
        router = new Router(new RoundRobinRoutingLogic());
    }



    public Receive createReceive() {
        return receiveBuilder()
                .match(Work.class, message -> {
                    ActorRef r = getContext().actorOf(Props.create(Worker.class));
                    getContext().watch(r);
                    router = router.addRoutee(new ActorRefRoutee(r));
                    router.route(message, getSelf());
                    workers++;
                })
                .match(EndOfFile.class, message -> {
                    endOfFile = true;
                    if (workers == 0) {
                        printAndKill();
                    }
                })
                .match(Integer.class, result -> counter += result)
                .match(Terminated.class, message -> {
                    workers--;
                    router = router.removeRoutee(message.actor());
                    if (workers == 0 && endOfFile) {
                        printAndKill();
                    }
                })
                .build();
    }

    private void printAndKill() {
        System.out.println("There are: " + counter + " " + Worker.patternMatching);
        CounterMain.actorSystem.terminate();
    }

}