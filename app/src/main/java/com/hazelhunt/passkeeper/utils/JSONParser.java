package com.hazelhunt.passkeeper.utils;

import com.hazelhunt.passkeeper.database.PKDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    private static final String JSON_ID = "id";
    private static final String JSON_URL = "url";
    private static final String JSON_LOGIN = "login";
    private static final String JSON_PASS = "password";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_EXTRA = "extra";

    public static PKDataModel parseToPKDataModel(JSONArray resultArray, int index) throws JSONException {
        JSONObject obj = resultArray.getJSONObject(index);

        String id = obj.getString(JSON_ID);
        String url = obj.getString(JSON_URL);
        String email = obj.getString(JSON_EMAIL);
        String login = obj.getString(JSON_LOGIN);
        String pass = obj.getString(JSON_PASS);
        String extra = obj.getString(JSON_EXTRA);

        return  new PKDataModel(Integer.parseInt(id), url, login, pass, email, extra);
    }

    public static JSONObject parseToJSON(PKDataModel pkDataModel) throws JSONException {
        return new JSONObject(
                "{\"id\":-1," +
                "\"login\":" + "\"" + pkDataModel.getLogin() + "\"," +
                "\"password\":" + "\"" + pkDataModel.getPass() + "\"," +
                "\"email\":" + "\"" + pkDataModel.getEmail() + "\"," +
                "\"url\":" + "\"" + pkDataModel.getUrl() + "\"," +
                "\"extra\":" + "\"" + pkDataModel.getExtra() + "\"}" );
    }

}
