package com.bytecubed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static String inputFile = "resources/input.txt";
    private static Map tables = new HashMap<String, Long>();
    private static List<Map> guestInfos = new ArrayList();

    /**
     * Given a line from input file, extracts either table or guest info. Saves table under tables variable or
     * appends guest info to guestsInfos
     *
     * @param guest line string from input file.
     */
    private static void extractInfo(String guest){

        if (guest.toLowerCase().contains("tables")){
            Arrays.stream(guest.split(" "))
                    .filter(s -> s.contains("-"))
                    .forEach(s ->  tables.put(s.split("-")[0], Long.valueOf(s.split("-")[1])));
            return;
        }

        HashMap<String, Object> info = new HashMap();
        String[] line = guest.split(",");
        info.put("name", line[0]);
        info.put("size", Long.valueOf(line[1].replaceAll("[^0-9]","")));

        if (guest.contains("dislikes")){
            info.put("dislikes", Arrays.stream(
                    guest.split("dislikes")[1]
                            .split(",")).collect(Collectors.toList()));
        }

        guestInfos.add(info);
    }

    public static void main(String[] args) {

        try {
            Files.lines(Paths.get(inputFile))
                    .forEach(line -> extractInfo(line));

        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Tables : " + Collections.singletonList(tables));
        guestInfos.stream()
                .forEach(info -> System.out.println("Guest " + info.get("name")  + " Infos : " + Collections.singletonList(info)));


    }
}
