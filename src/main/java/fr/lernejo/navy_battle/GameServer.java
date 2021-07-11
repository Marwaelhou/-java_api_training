package fr.lernejo.navy_battle;


import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.core.xyz;
import fr.lernejo.navy_battle.core.FireResult;
import fr.lernejo.navy_battle.core.Option;
import fr.lernejo.navy_battle.core.Server;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class GameServer extends ServerMain {
    private final Option<Server> local = new Option<>();
    private final Option<Server> remote = new Option<>();
    protected final Option<GamePlay> gamePlay = new Option<>();

    @Override
    public void startServer(int port, String connectURL) throws IOException {
        local.set(new Server(
            UUID.randomUUID().toString(), "http://localhost:" + port, "I'll beat you!"
        ));

        if (connectURL != null)
            new Thread(() -> this.requestStart(connectURL)).start();

        super.startServer(port, connectURL);
    }

    @Override
    public void createContextes(HttpServer server) {
        server.createContext("/api/game/start", s -> startGame(new Request(s)));
        server.createContext("/api/game/fire", s -> handleFire(new Request(s)));
    }
    public void requestStart(String server) {
        try {
            gamePlay.set(new GamePlay());
            this.remote.set(new Server("temp", server, "good luck"));
            var resp = sendPOSTRequest(server + "/api/game/start", this.local.get().toJSON());

            this.remote.set(Server.fromJSON(resp).withURL(server));
            System.out.println("Will fight against " + remote.get().getServerURL());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the game!");
        }
    }
    public void startGame(Request handler) throws IOException {
        try {
            remote.set(Server.fromJSON(handler.getJSONObject()));
            gamePlay.set(new GamePlay());
            System.out.println("Will fight against " + remote.get().getServerURL());

            handler.sendJSON(202, local.get().toJSON());

            fire();

        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
    public void fire() throws IOException, InterruptedException {
        xyz coordination = gamePlay.get().getNextPlaceToHit();
        var resp = sendGETRequest(remote.get().getServerURL() + "/api/game/fire?cell=" + coordination.toString());

        if (!resp.getBoolean("shipLeft")) {
            gamePlay.get().wonGame();
            return;
        }

        gamePlay.get().setFireResult(coordination, FireResult.fromAPI(resp.getString("consequence")));
    }

    public void handleFire(Request handler) throws IOException {
        try {
            var position = new xyz(handler.getQueryParameter("cell"));
            handler.sendJSON(200, new JSONObject().put("consequence", gamePlay.get().hit(position).goAPI())
                .put("The ship Left", gamePlay.get().localMapShipLeft()));

            if (!gamePlay.get().localMapShipLeft()) {
                System.out.println("loosers :(");
                return;
            }

            fire();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
}

