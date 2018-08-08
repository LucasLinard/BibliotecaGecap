package tech.linard.bibliotecagecap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UtilJson {

    public static List<Volume> converteJsonEmVolume(JSONObject jsonObject) throws JSONException {
        List<Volume> volumes = new ArrayList<>();
        JSONArray items = jsonObject.optJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject currentItem = (JSONObject) items.get(i);
            String id = currentItem.getString("id");

            JSONObject volumeInfo = currentItem.optJSONObject("volumeInfo");
            JSONObject saleInfo = currentItem.optJSONObject("saleInfo");
            JSONObject accessInfo = currentItem.optJSONObject("accessInfo");

            String title = volumeInfo.optString("title");
            String subtitle = volumeInfo.optString("subtitle");

            String authors = volumeInfo.optJSONArray("authors").toString();
            String publisher = volumeInfo.optString("publisher");
            String publishedDate = volumeInfo.optString("publishedDate");
            String description = volumeInfo.optString("description");
            int pageCount = volumeInfo.optInt("pageCount");
            String categories = volumeInfo.optJSONArray("categories").toString();
            JSONArray industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");

            HashMap hashMapIndustryIdentifiers = new HashMap();

            for (int x = 0; x < industryIdentifiers.length(); x++) {
                JSONObject object = industryIdentifiers.getJSONObject(x);
                Iterator<?> keys = object.keys();
                String chave = null;
                String valor = null;
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (key.equals("type")) {
                        chave = object.getString(key);
                    }
                    if (key.equals("identifier")) {
                        valor = object.getString(key);
                    }
                }
                if (chave != null && valor != null) {
                    hashMapIndustryIdentifiers.put(chave, valor);
                }
            }

            JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
            String smallThumbnail = imageLinks.optString("smallThumbnail");
            String thumbnail = imageLinks.optString("thumbnail");
            volumes.add(new Volume(id
                    , title
                    , subtitle
                    , authors
                    , publisher
                    , publishedDate
                    , description
                    , pageCount
                    , categories
                    , hashMapIndustryIdentifiers
                    , smallThumbnail
                    , thumbnail)
            );
        }
        return volumes;
    }
}
