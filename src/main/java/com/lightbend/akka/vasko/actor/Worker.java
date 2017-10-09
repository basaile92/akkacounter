package com.lightbend.akka.vasko.actor;

import akka.actor.*;
import com.lightbend.akka.vasko.main.CounterMain;
import com.lightbend.akka.vasko.message.CharcountWork;
import com.lightbend.akka.vasko.message.Work;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worker extends AbstractActor{

    public static String patternMatching = "i";
    public int counter;
    public String msg;

    public Worker() {
    }

    public Worker(String msg) {
        this.msg = msg;
    }

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
        System.out.println(msg + counter);
        getContext().stop(getSelf());
    }



}
