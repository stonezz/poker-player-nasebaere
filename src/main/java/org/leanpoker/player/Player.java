package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {

    static final String VERSION = "v1.20";

    public static int betRequest(JsonElement request) {

        int bet = evaluateBet(request);

        return bet;
    }

    private static int evaluateBet(JsonElement request) {
        JsonObject jobject = request.getAsJsonObject();

        JsonArray players = jobject.getAsJsonArray("players");

        int currentBuyIn = jobject.get("current_buy_in").getAsInt();

        JsonObject player = players.get(5).getAsJsonObject();
        int bet=  player.get("bet").getAsInt();

        JsonArray cards = player.get("hole_cards").getAsJsonArray();


        List<Card> karten = new ArrayList<>();

      for(int i=0;i< cards.size();i++) {
          JsonObject card = cards.get(i).getAsJsonObject();

          String wertStr = card.get("rank").getAsString();
          String farbe = card.get("suit").getAsString();

          int wert = getWert(wertStr);
          
          Card c = new Card(wert, farbe);
          karten.add(c);
      }

        return currentBuyIn - bet;
    }

    private static int getWert(String wertStr) {

        int wert = 0;

        try {

           if (wertStr.equals("K")) {
               wert = 13;
           } else if (wertStr.equals("A")) {
               wert = 14;
           } else if (wertStr.equals("Q")) {
               wert = 12;
           } else if (wertStr.equals("J")) {
               wert = 11;
           } else {
               wert = Integer.valueOf(wertStr);
           }
       }
       catch(Exception e) {
System.out.println(e);
       }
        return wert;
    }

    public static void showdown(JsonElement game) {


    }

    static class Card {
        public int wert;
        public String farbe;

        public Card(int wert, String farbe) {
            this.wert = wert;
            this.farbe = farbe;
        }
    }
}
