package no.martr;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Airport airport = new Airport();
        Scanner scanner = new Scanner(System.in);

        System.out.println(Quotes.getRandomQuote());
        System.out.println("-- Airplane! (1980)");
        System.out.println("       __|__\n" +
                "--@--@--(_)--@--@--\n\n");

        System.out.print("Enter units of time for this simulation: ");
        int maxTime = scanner.nextInt();
        System.out.print("Enter size of queues (landing/departing): ");
        int queueSize = scanner.nextInt();
        System.out.print("Expected number of landing planes per time unit: ");
        double mean_landings = scanner.nextDouble();
        System.out.print("Expected number of departing planes per time unit: ");
        double mean_departures = scanner.nextDouble();


        airport.simulate(maxTime, queueSize, mean_landings, mean_departures);
    }
}
