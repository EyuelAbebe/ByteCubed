package com.bytecubed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeddingSeating {

    private static List<Map> tables;
    private static List<Map> guestInfos;

    public WeddingSeating(List tables, List guestInfos) {
        this.tables = tables;
        this.guestInfos = guestInfos;
    }

    /**
     *  Determines if its is possible to sit guest on tables.
     *
     * @return boolean.
     */
    public boolean seatingPossible(){
        for(Map guest: guestInfos){
            if (!this.seatGuest(guest)){return false;}
        }

        return true;
    }

    /**
     *  Sits a guest on one of the tables available, then updates table info or returns false.
     *
     * @param guest guest info.
     * @return boolean.
     */
    public boolean seatGuest(Map guest){

        for(Map table: tables){

            int spaceAvailable = (int) table.get("spaceAvailable") ;
            int guestSize = (int) guest.get("size");
            List<String> seating = (List) table.get("seating");

            if (canSeat(guest, spaceAvailable, seating)){
                seating.add(guest.get("name").toString().trim().toUpperCase());
                table.replace("spaceAvailable", spaceAvailable - guestSize);
                return true;
            }else{
                for (String sittingGuest: seating){
                    Map sittngGuestInfo = guestInfos.stream()
                            .filter( guestInfo -> guestInfo.get("name").toString().trim().equalsIgnoreCase(sittingGuest))
                            .findAny()
                            .orElse(null);


                    if (canMove((Map)sittngGuestInfo, table)){
                       seating.add(guest.get("name").toString().trim().toUpperCase());
                       table.replace("spaceAvailable", spaceAvailable - guestSize);
                       return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *  Takes in guest and table info and determines if guest(s) can seat.
     *
     * @param guestInfo guest information.
     * @param tableSizeAvailable space available on table.
     * @param seating list of people sitting on table.
     * @return boolean.
     */
    public boolean canSeat(Map guestInfo, int tableSizeAvailable, List<String> seating){

        if ( tableSizeAvailable <  (int) guestInfo.get("size") ){
            return false;
        }

        if (guestInfo.get("dislikes") != null){
            for (String dislike: (List<String>) guestInfo.get("dislikes")) {
                if (seating.stream().anyMatch(s -> s.equals(dislike))) {return false;};
            }
        }
        return true;
    }

    /**
     *  Given a guest and a table guest sitting on, determines if we can move guest to a different table.
     *
     * @param guestInfo guest/party name.
     * @param srcTable table name guest currently sitting on.
     * @return boolean
     */
    public boolean canMove(Map guestInfo, Map srcTable){

        for(Map destTable : tables){
            if ( destTable.get("name") != srcTable.get("name")){
                List<String> destTableSeating = (List) destTable.get("seating");
                if (canSeat(guestInfo,  (int) destTable.get("spaceAvailable"), destTableSeating)){
                    move(guestInfo, srcTable, destTable);
                    return true;
                };
            }
        }
        return false;
    }

    /**
     * Moves a guest from srcTable to destinationTable. Updates tables info accordingly.
     *
     * @param guestInfo
     * @param srcTable
     * @param destinationTable
     */
    public void move(Map guestInfo, Map srcTable, Map destinationTable){

        int guestSize = (int) guestInfo.get("size");
        srcTable.replace("spaceAvailable", (int) srcTable.get("spaceAvailable") + guestSize);
        destinationTable.replace("spaceAvailable", (int) destinationTable.get("spaceAvailable") - guestSize);


        List<String> guestTableSeating = (List) destinationTable.get("seating");
        guestTableSeating.add(guestInfo.get("name").toString().trim().toUpperCase());

        List<String> srcTableSeating = (List) srcTable.get("seating");
        srcTableSeating.remove(guestInfo.get("name").toString().trim().toUpperCase());

    }
}
