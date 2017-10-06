package com.lightbend.akka.vasko.actor;


import akka.actor.*;
import akka.routing.*;
import com.lightbend.akka.vasko.message.CharcountWork;
import com.lightbend.akka.vasko.message.FileWork;
import com.lightbend.akka.vasko.message.Work;

import java.util.ArrayList;
import java.util.List;

public class RouterManager extends AbstractActor {

    Router router;

    {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(Worker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(FileWork.class, message -> {
                    ActorRef r = getContext().actorOf(Props.create(FileReader.class));
                    router.route(message, r);
                })
                .match(Work.class, message -> {
                    ActorRef r = getContext().actorOf(Props.create(Worker.class));
                    router.route(message, r);
                })
                .match(CharcountWork.class, message -> {
                    ActorRef r = getContext().actorOf(Props.create(Printer.class));
                    router.route(message, r);
                })
                .match(Terminated.class, message -> {
                    router = router.removeRoutee(message.actor());
                    ActorRef r = getContext().actorOf(Props.create(Worker.class));
                    getContext().watch(r);
                    router = router.addRoutee(new ActorRefRoutee(r));
                })
                .build();
    }

}