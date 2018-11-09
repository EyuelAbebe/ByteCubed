package com.bytecubed;

import java.util.List;
import java.util.Map;

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
            }
        }

        return false;
    }

    /**
     *  Takes in guest and table info and determines of guest(s) can seat.
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
}
