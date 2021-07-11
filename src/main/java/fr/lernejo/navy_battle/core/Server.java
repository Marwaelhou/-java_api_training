package fr.lernejo.navy_battle.core;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {
    private final String serverID;
    private final String serverURL;
    private final String message;

    public Server(String serverID, String url, String message) {
        this.serverID = serverID;
        this.serverURL = url;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getServerURL() {
        return serverURL;
    }

    public String getServerID() {
        return serverID;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", serverID);
        obj.put("message", message);
        obj.put("url", serverURL);
        return obj;
    }

    public static Server fromJSON(JSONObject object) throws JSONException {
        return new Server(
            object.getString("id"),
            object.getString("message"),
            object.getString("url")
        );
    }

    public Server withURL(String url) {
        return new Server(
            this.serverID,
            url,
            this.message
        );
    }
}
