package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class Player {

    static final String VERSION = "stonezz v1.14";

    public static int betRequest(JsonElement request) {

        int bet = evaluateBet(request);

        return bet;
    }

    private static int evaluateBet(JsonElement request) {
        JsonObject jobject = request.getAsJsonObject();
        JsonArray players = jobject.getAsJsonArray("players");

        int betAll =0;

        for(int i=0; i<players.size(); i++) {
            JsonObject player = players.get(i).getAsJsonObject();

           int bet=  player.get("bet").getAsInt();
           if(bet >= betAll) {
               betAll = bet;
           }
        }

        return betAll + 1;
    }

    public static void showdown(JsonElement game) {
    }
}
