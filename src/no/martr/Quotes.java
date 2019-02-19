package no.martr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quotes {

    private static final List<String> quotes = new ArrayList<>() {
        {

            add("\"I just want to tell you both good luck. We're all counting on you.\"");
            add("\"Surely you can't be serious?\"\n\"I am serious, and don't call me Shirley.\"");
            add("\"Nervous?\"\n\"Yes. Very\"\n\"First time?\"\n\"No, I've been nervous lots of times.\"");
            add("\"There's no reason to become alarmed, and we hope you'll enjoy the rest of your flight. By the way, is there anyone on board who knows how to fly a plane?\"");
            add("\"These people need to go to a hospital\"\n\"What is it?\"\n\"It's a big place where sick people go.\"");
            add("\"Looks like I picked the wrong week to quit sniffing glue.\"");
        }};

    public static String getRandomQuote() {
        Random r = new Random();
        return quotes.get(r.nextInt(quotes.size()));
    }
}

