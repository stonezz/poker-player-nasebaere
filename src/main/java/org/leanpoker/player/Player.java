package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class Player {

    static final String VERSION = "stonezz v1.12";

    public static int betRequest(JsonElement request) {

       JsonObject jobject = request.getAsJsonObject();
      JsonArray jarray = jobject.getAsJsonArray("players");

   //     for(int i=0; i<jarray.size(); i++) {
 //  JsonObject item = items2.get(i).getAsJsonObject();
 //System.out.println(item.get("start").getAsString());}

        return 1200;
    }

    public static void showdown(JsonElement game) {
    	
    }
}
