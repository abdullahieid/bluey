package com.bluey.atproto;

import com.bluey.Session;
import com.bluey.Utils;
import com.bluey.CreateSession;
import com.bluey.XRPCError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static java.util.Map.entry;

public class Server {
    private static final Gson gson = new Gson();

    public static Session createSession(String identifier, String password) throws IOException, URISyntaxException, InterruptedException {
        if((identifier == null) || (password == null)){
            return null; // create custom exception
        }

        System.out.println("Creating session...");

        CreateSession credentials = new CreateSession(identifier, password);
        String json = gson.toJson(credentials);
        Map<String, String> queries = Map.ofEntries(entry("Content-Type", "application/json;charset=UTF-8"));

        HttpRequest request = Utils.createProcedureRequest(queries, "https://bsky.social/xrpc/com.atproto.server.createSession", json);
        HttpResponse<String> response = Utils.createProcedureResponse(request);

        Session session;
        if(response != null){
            if(response.statusCode() == 400){
                XRPCError xrpcError = gson.fromJson(response.body(), XRPCError.class);
                System.out.println(xrpcError.toString());
                return null;
            }
            if(response.statusCode() == 401){
                XRPCError xrpcError = gson.fromJson(response.body(), XRPCError.class);
                System.out.println(xrpcError.toString());
            }
            if(response.statusCode() == 200){
                JsonObject sessionCredentials = gson.fromJson(response.body(), JsonObject.class);

                session = Session.getSession(sessionCredentials.get("did").toString(),
                        sessionCredentials.get("handle").toString(),
                        sessionCredentials.get("email").toString(),
                        sessionCredentials.get("accessJwt").toString(),
                        sessionCredentials.get("refreshJwt").toString());

                System.out.println("Session created.");
                return session;
            }
        }
        return null;
    }
}