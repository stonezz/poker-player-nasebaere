package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class Player {

    static final String VERSION = "v1.24";

    public static int betRequest(JsonElement request) {

        int bet = evaluateBet(request);

        return bet;
    }

    private static int evaluateBet(JsonElement request) {

        System.out.println("Test");

        JsonObject jobject = request.getAsJsonObject();

        JsonArray players = jobject.getAsJsonArray("players");
        int currentBuyIn = jobject.get("current_buy_in").getAsInt();

        JsonObject player = players.get(5).getAsJsonObject();
        int bet =  player.get("bet").getAsInt();

        JsonArray cards = player.get("hole_cards").getAsJsonArray();
        List<Card> kartenInHand =  getKarten(cards);

        cards = jobject.get("community_cards").getAsJsonArray();
        List<Card> kartenAufTisch =  getKarten(cards);

        Map<Integer, Integer> kartenMap = new TreeMap<>(Collections.<Integer>reverseOrder());

        List<Card> alleKarten = new ArrayList<>();
        alleKarten.addAll(kartenInHand);
        alleKarten.addAll(kartenAufTisch);

        Card highestCard = null;
        for(Card karte : kartenInHand) {
            Integer anzahlKarten = kartenMap.get(new Integer(karte.wert));
            if(anzahlKarten == null) {
                kartenMap.put(new Integer(karte.wert), new Integer(1));
            }
            else {
                kartenMap.put(new Integer(karte.wert), ++anzahlKarten);
            }

            if(highestCard != null) {
                highestCard = highestCard.wert < karte.wert ? karte : highestCard;
            }{
              highestCard = karte;
            }
        }

        for(Card karte : kartenAufTisch) {
            Integer anzahlKarten = kartenMap.get(new Integer(karte.wert));
            if(anzahlKarten == null) {
                kartenMap.put(new Integer(karte.wert), new Integer(1));
            }
            else {
                kartenMap.put(new Integer(karte.wert), ++anzahlKarten);
            }
        }

        int  raise = 0;
        if(kartenAufTisch.size() == 0) {
          raise  += hasMindPairs(kartenMap) ? 10 : 0;
        }
        else if(kartenAufTisch.size() == 3) {
             raise += hasThtree(kartenMap) ? 10 : 0;
             raise  += hasMindPairs(kartenMap) ? 10 : 0;
        }
        else if(kartenAufTisch.size() == 4) {
            if(!hasThtree(kartenMap) &&  !hasMindPairs(kartenMap)) {
                if(highestCard.wert < 14) {
                    return 0;
                }
            }
        }

        if(currentBuyIn >= 300) {
            return 0;
        }

        int bett = currentBuyIn - bet + raise;
        return bett;
    }

    private static boolean hasMindPairs(Map<Integer, Integer> kartenMap) {
        for(Integer wert :  kartenMap.values()) {
            if(wert.intValue() > 1)
                return true;
        }

        return false;
    }

    private static boolean hasThtree(Map<Integer, Integer> kartenMap) {
        for(Integer wert :  kartenMap.values()) {
            if(wert.intValue() > 2)
                return true;
        }

        return false;
    }


    private static List<Card> getKarten(JsonArray cards) {
        List<Card> karten = new ArrayList<>();

        for(int i=0;i< cards.size();i++) {
            JsonObject card = cards.get(i).getAsJsonObject();

            String wertStr = card.get("rank").getAsString();
            String farbe = card.get("suit").getAsString();

            int wert = getWert(wertStr);

            Card c = new Card(wert, farbe);
            karten.add(c);
        }

        return karten;
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
