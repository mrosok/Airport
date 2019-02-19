package no.martr;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Airport {

    private RunwayQueue landings = new RunwayQueue("landing");
    private RunwayQueue departures = new RunwayQueue("departure");

    private int numberOfPlanes, numberOfPlanesRejected;
    private int numberOfLandings, numberOfDepartures;
    private int sumWaitingTimeLandings, sumWaitingTimeDepartures;
    private int numberOfEmptyRunways;

    public Airport() {
        this.numberOfPlanes = 0;
        this.numberOfPlanesRejected = 0;
        this.numberOfEmptyRunways = 0;
        this.numberOfLandings = 0;
        this.numberOfDepartures = 0;
        this.sumWaitingTimeDepartures =0;
        this.sumWaitingTimeLandings = 0;
    }

    private class Plane {

        private int arrivalTime;
        private int number;

        public Plane(int arrivalTime) {
            Airport.this.numberOfPlanes++;
            this.arrivalTime = arrivalTime;
            this.number = numberOfPlanes;
        }

        public int waitingTime(int time) {
            return time - arrivalTime;
        }
    }

    private class RunwayQueue {
        private String name;
        private Queue<Plane> queue;
        private int sumOfQueues;

        public RunwayQueue(String name) {
            this.name = name;
            this.queue = new LinkedList<>();
            this.sumOfQueues = 0;
        }

        public void add(Plane plane) {
            this.queue.add(plane);
        }

        public Plane remove() {
            return this.queue.remove();
        }

        public int size() {
            return queue.size();
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    public void simulate(int maxTime, int queueSize, double mean_landings, double mean_departures) {

        for (int time = 0; time < maxTime; time++) {

            System.out.print("\n" + time + ":");

            addPlanesTo(landings, time, mean_landings, queueSize);
            addPlanesTo(departures, time, mean_departures, queueSize);
            this.landings.sumOfQueues += landings.size();
            this.departures.sumOfQueues += departures.size();
            System.out.println();
            useRunway(time);
        }
        
        report(maxTime);
    }

    private void addPlanesTo(RunwayQueue queue, int time, double mean, int queueSize) {

            for (int i = 0; i < getPoissonRandom(mean); i++) {
                Plane plane = new Plane(time);
                if (queue.size() < queueSize) {
                    queue.add(plane);
                    System.out.print("\tPlane " + plane.number + " added to " + queue + " queue\n");
                } else {
                    System.out.print("\t" + queue + " queue is full. Plane " + plane.number + " is rejected\n");
                    this.numberOfPlanesRejected++;
                }
            }
        }

    private void useRunway(int time) {

        if (!landings.isEmpty()) {
            landPlane(time);
        } else if (!departures.isEmpty()) {
            departPlane(time);
        } else {
            System.out.print("\tRunway is empty\n");
            this.numberOfEmptyRunways++;
        }
    }

    private void landPlane(int time) {
        Plane plane = landings.remove();
        System.out.print("\tPlane " + plane.number + " is landing\n");
        this.numberOfLandings++;
        System.out.print("\tWaiting time: " + plane.waitingTime(time) + " units\n");
        this.sumWaitingTimeLandings += plane.waitingTime(time);
    }

    private void departPlane(int time) {
        Plane plane = departures.remove();
        System.out.print("\tPlane " + plane.number + " is departing\n");
        this.numberOfDepartures++;
        System.out.print("\tWaiting time: " + plane.waitingTime(time) + " units\n");
        this.sumWaitingTimeDepartures += plane.waitingTime(time);
    }
    
    private void report(int maxTime) {
        System.out.println("\nStatistics after " + maxTime + " units of time:");
        System.out.println("Number of planes processed: " + this.numberOfPlanes);
        System.out.println("Number of departures: " + this.numberOfDepartures);
        System.out.println("Number of landings: " + this.numberOfLandings);
        System.out.println("Number of planes rejected: " + this.numberOfPlanesRejected);
        System.out.println("Number of planes still in landing queue after simulation: " + this.landings.size());
        System.out.println("Number of planes still in departures queue after simulation: " + this.departures.size());
        System.out.println("Average length of landing queue: " + (float) this.landings.sumOfQueues / maxTime);
        System.out.println("Average length of departures queue: " + (float) this.departures.sumOfQueues / maxTime);
        System.out.println("Percentage time runway was empty: " + (float) numberOfEmptyRunways / maxTime * 100);
        System.out.println("Average wait time for landings: " + (float) sumWaitingTimeLandings / maxTime);
        System.out.println("Average wait time for departures: " + (float) sumWaitingTimeDepartures / maxTime);
        System.out.println("\nNote: Average wait time is only calculated for planes actively having landed or departed. Planes still waiting in queue are not considered");
    }

    private static int getPoissonRandom(double mean) {

        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}