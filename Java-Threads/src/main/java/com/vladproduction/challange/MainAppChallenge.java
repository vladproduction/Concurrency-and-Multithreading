package com.vladproduction.challange;

import java.util.*;

public class MainAppChallenge {
    public static void main(String[] args) {

        //Design #1
        Design d1 = new Design(1,"Design #1");
        VotingRunnable votingRunnableD1 = new VotingRunnable(d1);
        CountingRunnable countingRunnableD1 = new CountingRunnable(d1);

        Thread votingD1 = new Thread(votingRunnableD1);
        Thread countingD1 = new Thread(countingRunnableD1);
        votingD1.start();
        countingD1.start();

        //Design #2
        Design d2 = new Design(2,"Design #2");
        VotingRunnable votingRunnableD2 = new VotingRunnable(d2);
        CountingRunnable countingRunnableD2 = new CountingRunnable(d2);

        Thread votingD2 = new Thread(votingRunnableD2);
        Thread countingD2 = new Thread(countingRunnableD2);
        votingD2.start();
        countingD2.start();

        //Design #3
        Design d3 = new Design(3,"Design #3");
        VotingRunnable votingRunnableD3 = new VotingRunnable(d3);
        CountingRunnable countingRunnableD3 = new CountingRunnable(d3);

        Thread votingD3 = new Thread(votingRunnableD3);
        Thread countingD3 = new Thread(countingRunnableD3);
        votingD3.start();
        countingD3.start();

        try{
            Thread.sleep(15000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        votingRunnableD1.doStop = true;
        votingRunnableD2.doStop = true;
        votingRunnableD3.doStop = true;

        countingRunnableD1.doStop = true;
        countingRunnableD2.doStop = true;
        countingRunnableD3.doStop = true;

        System.out.println("Voting has stopped for design " + d1.getName());
        System.out.println("Total votes for " + d1.getName() + " : " + d1.getVotes().size());

        System.out.println("Voting has stopped for design " + d2.getName());
        System.out.println("Total votes for " + d2.getName() + " : " + d2.getVotes().size());

        System.out.println("Voting has stopped for design " + d3.getName());
        System.out.println("Total votes for " + d3.getName() + " : " + d3.getVotes().size());

        System.out.println("============================");
        Map<String, Integer> all = new TreeMap<>();
        all.put(d1.getName(), d1.getVotes().size());
        all.put(d2.getName(), d2.getVotes().size());
        all.put(d3.getName(), d3.getVotes().size());
        for (Map.Entry<String, Integer> entry : all.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("============================");

        // Find and print the winner
        String winner = null;
        int maxVotes = -1;

        for (Map.Entry<String, Integer> entry : all.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winner = entry.getKey();
            }
        }
        System.out.println("============================");
        System.out.println("===========WINNER===========");
        if (winner != null) {
            System.out.println(winner + " with " + maxVotes + " votes.");
        } else {
            System.out.println("No votes were cast.");
        }
        System.out.println("============================");




    }
}
