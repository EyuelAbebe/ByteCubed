package com.bytecubed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeddingSeating {

    private static List<Map> tables;
    private static List<Map> guestInfos;

    // Used to record list of moved guests from table.
    private static List<String> movedGuests = new ArrayList<>();

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
    private boolean seatGuest(Map guest){

        movedGuests.clear();

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

        for(Map table: tables){
            int guestSize = (int) guest.get("size");
            List<String> seating = (List) table.get("seating");

            if (guestSize > (int) table.get("size")){
                continue;
            }

            for (String sittingGuest: seating){
                Map sittngGuestInfo = guestInfos.stream()
                        .filter( guestInfo -> guestInfo.get("name").toString().trim().equalsIgnoreCase(sittingGuest))
                        .findAny()
                        .orElse(null);

                if (canMove(sittngGuestInfo, table)) {
                    int tableSpaceAvailable = (int) table.get("spaceAvailable");
                    int sittingGuestSize = (int) sittngGuestInfo.get("size");

                    table.replace("spaceAvailable", tableSpaceAvailable + sittingGuestSize);

                    if (guestSize <= (int) table.get("spaceAvailable")) {
                        seating.add(guest.get("name").toString().trim().toUpperCase());
                        int spaceAvailable = (int) table.get("spaceAvailable");
                        table.replace("spaceAvailable", spaceAvailable - guestSize);
                        ((List) table.get("seating")).removeAll(movedGuests);
                        return true;
                    }
                }
            }
            ((List) table.get("seating")).removeAll(movedGuests);
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
    private boolean canSeat(Map guestInfo, int tableSizeAvailable, List<String> seating){

        if ( tableSizeAvailable <  (int) guestInfo.get("size") ){
            return false;
        }

        if (seating.isEmpty()){return true;}

        if (guestInfo.get("dislikes") != null){
            for (String dislike: (List<String>) guestInfo.get("dislikes")) {
                if (seating.stream().anyMatch(s -> s.equalsIgnoreCase(dislike))) {return false;};
            }
        }

        for (String sitting: seating){
            if (dislikes(sitting, guestInfo.get("name").toString())){
                return false;
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
    private boolean canMove(Map guestInfo, Map srcTable){

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
     * Moves a guest from srcTable to destinationTable. Updates destination table info accordingly.
     *
     * @param guestInfo
     * @param srcTable
     * @param destinationTable
     */
    private void move(Map guestInfo, Map srcTable, Map destinationTable){

        int guestSize = (int) guestInfo.get("size");
        destinationTable.replace("spaceAvailable", (int) destinationTable.get("spaceAvailable") - guestSize);

        List<String> destTableSeating = (List) destinationTable.get("seating");
        destTableSeating.add(guestInfo.get("name").toString().trim().toUpperCase());

        movedGuests.add(guestInfo.get("name").toString().trim().toUpperCase());
    }


    /**
     * Checks if sittingGuest dislikes incomingGuest
     *
     * @param sittingGuest name of sitting guest
     * @param incomingGuestName name of guest looking to sit
     * @return boolean
     */
    private boolean dislikes(String sittingGuest, String incomingGuestName){

        Map sittngGuestInfo = guestInfos.stream()
                .filter( guestInfo -> guestInfo.get("name").toString().trim().equalsIgnoreCase(sittingGuest))
                .findAny()
                .orElse(null);

        if (sittngGuestInfo.get("dislikes") != null){
            return ((List<String>)sittngGuestInfo.get("dislikes")).contains(incomingGuestName.trim().toUpperCase());
        }
        return false;
    }
}
