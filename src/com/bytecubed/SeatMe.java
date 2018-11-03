package com.bytecubed;

import java.util.List;
import java.util.Map;

public class SeatMe {

    private static List<Map> tables;

    public SeatMe(List tables) {
        this.tables = tables;
    }


    public boolean canSeat(Map guestInfo, Map table ){
        for (String party: (String[]) table.get("seating")) {
            return !guestInfo.containsValue(party);
        }
        return true;
    }
}
