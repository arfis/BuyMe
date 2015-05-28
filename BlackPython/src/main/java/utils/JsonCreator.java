package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 5/28/2015.
 */
public class JsonCreator {

    public JSONObject createMail(String mail){
        JSONObject parent = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email",mail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject createUsedCoupons(String used){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        String[] coupons = used.split(";");

        for(String coup : coupons) {
            jsonArray.put(coup);
        }
        try {
            jsonObject.put("used_id",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
