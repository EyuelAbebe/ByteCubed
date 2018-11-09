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

    public boolean seatingPossible(){
        for(Map guest: guestInfos){
            if (!this.seatGuest(guest)){return false;}
        }

        return true;
    }

    public boolean seatGuest(Map guest){

        for(Map table: tables){

            int spaceAvailable = (int) table.get("spaceAvailable") ;
            int guestSize = (int) guest.get("size");
            List<String> seating = (List) table.get("seating");

            if (canSeat(guest, spaceAvailable, guestSize, seating)){
                seating.add(guest.get("name").toString().trim().toUpperCase());
                table.replace("spaceAvailable", spaceAvailable - guestSize);
                return true;
            }
        }

        return false;
    }

    public boolean canSeat(Map guestInfo, int tableSizeAvailable, int guestSize, List seating){

        if ( tableSizeAvailable <  guestSize ){
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
