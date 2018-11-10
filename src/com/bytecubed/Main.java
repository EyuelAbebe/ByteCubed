package com.bytecubed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    /**
     * Given a line from input file, extracts either table or guest info. Saves table under tables variable or
     * appends guest info to guestsInfos
     *
     * @param guest line string from input file.
     */
    private static void extractInfo(String guest, List tables, List guestInfos){

        if (guest.contains("tables")){
            Arrays.stream(guest.split(" "))
                    .filter(s -> s.contains("-"))
                    .forEach(s ->  {
                        Map table = new HashMap<String, Object>();
                        String[] info = s.split("-");
                        int size = Integer.valueOf(info[1]);
                        List<String> seating = new ArrayList<>();

                        table.put("name" , info[0].toString().trim().toUpperCase());
                        table.put("size", size);
                        table.put("spaceAvailable", size);
                        table.put("seating", seating);

                        tables.add(table);
                    });
            return;
        }

        HashMap<String, Object> info = new HashMap();
        String[] line = guest.split(",");
        info.put("name", line[0]);
        info.put("size", Integer.valueOf(line[1].replaceAll("[^0-9]","")));

        if (guest.contains("dislikes")){
            info.put("dislikes", Arrays.stream(guest.split("dislikes")[1].split(","))
                    .map(s -> s.toString().trim().toUpperCase())
                    .collect(Collectors.toList()));
        }

        guestInfos.add(info);
    }

    /**
     *  Given a file containing tables and guest list, prints possible arrangements.
     *
     * @param givenSetup
     */
    public static void arrangeSeates(String givenSetup){

        List<Map> tables = new ArrayList<>();
        List<Map> guestInfos = new ArrayList();

        try {
            Files.lines(Paths.get(givenSetup))
                    .forEach(line -> extractInfo(line.toLowerCase(), tables, guestInfos));

            System.out.println("[==================> ARRANGEMENT: " + givenSetup.toLowerCase() + " <==================]");
            System.out.println("GUEST INFORMATION: ");
            guestInfos.stream()
                    .forEach(info -> System.out.println("Guest name: " + info.get("name") + ", Party size: " +
                            info.get("size")  + ", Dislikes : " + info.get("dislikes")));

            System.out.println("\n");

            WeddingSeating seatingArrangment = new WeddingSeating(tables, guestInfos);

            if (seatingArrangment.seatingPossible()){
                System.out.println("TABLE ARRANGEMENT: ");
                tables.stream()
                        .forEach(table -> System.out.println( "Table : " + table.get("name") + ", Size : " + table.get("size") +
                                ", SpaceAvailable : " + table.get("spaceAvailable") + ", Seating: " +  table.get("seating")));
            }else{
                System.out.println("POSSIBLE TABLE ARRANGEMENT NOT FOUND");
            };
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error reading Input file. Please use input.txt in resources folder.");
        }
        System.out.println("[=========================================================================]");
        System.out.println("\n");
    }

    public static void main(String[] args) {
        arrangeSeates("resources/input.txt");
        arrangeSeates("resources/input2.txt");
        arrangeSeates("resources/input3.txt");
        arrangeSeates("resources/input4.txt");
    }
}
