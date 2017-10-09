package com.lightbend.akka.vasko.actor;

import akka.actor.*;
import com.lightbend.akka.vasko.message.Work;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worker extends AbstractActor{

    public static String patternMatching = "e";
    public int counter = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Work.class, this::count)
                .build();
    }

    private void count(Work message) {
        Pattern pattern = Pattern.compile(patternMatching);
        Matcher matcher = pattern.matcher(message.payload);
        while (matcher.find()) {
            counter++;
        }
        ActorRef sender = getSender();
        sender.tell(counter, getSelf());
        getContext().stop(getSelf());
    }



}
