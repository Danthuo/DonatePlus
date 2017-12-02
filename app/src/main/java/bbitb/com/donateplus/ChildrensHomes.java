package bbitb.com.donateplus;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Daniel Thuo on 30/11/2017.
 */

public class ChildrensHomes {

    public String title;
    public String description;
    public String imageUrl;
    public String instructionUrl;
    public String label;

    public static ArrayList<ChildrensHomes> getChildrensHomesFromFile(String filename, Context context){
        final ArrayList<ChildrensHomes> homeList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("homes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray home = json.getJSONArray("homes");

            // Get Recipe objects from data
            for(int i = 0; i < home.length(); i++){
                ChildrensHomes hom = new ChildrensHomes();

                hom.title = home.getJSONObject(i).getString("title");
                hom.description = home.getJSONObject(i).getString("description");
                hom.imageUrl = home.getJSONObject(i).getString("image");
                hom.instructionUrl = home.getJSONObject(i).getString("url");

                homeList.add(hom);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return homeList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
