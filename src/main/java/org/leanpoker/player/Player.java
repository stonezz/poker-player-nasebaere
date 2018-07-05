package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class Player {

    static final String VERSION = "v1.18";

    public static int betRequest(JsonElement request) {

        int bet = evaluateBet(request);

        return bet;
    }

    private static int evaluateBet(JsonElement request) {
        JsonObject jobject = request.getAsJsonObject();

        JsonArray players = jobject.getAsJsonArray("players");

        int currentBuyIn = jobject.get("current_buy_in").getAsInt();

        int betAll = 0;

        JsonObject player = players.get(5).getAsJsonObject();
        int bet=  player.get("bet").getAsInt();

       // for(int i=0; i<players.size(); i++) {
       //    JsonObject player = players.get(i).getAsJsonObject();
       //    int bet=  player.get("bet").getAsInt();
        //   if(bet >= betAll) {
        //       betAll = bet;
        //   }
        //}

        return currentBuyIn - bet + 50;
    }

    public static void showdown(JsonElement game) {


    }
}
