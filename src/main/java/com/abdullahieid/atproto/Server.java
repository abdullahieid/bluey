package com.abdullahieid.atproto;

import com.abdullahieid.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Map.entry;

public class Server {
    private static final Gson gson = new Gson();

    public static Session createSession(String provider, String identifier, String password) throws IOException, URISyntaxException, InterruptedException {
        if((identifier == null) || (password == null)){
            return null; // create custom exception
        }

        CreateSession credentials = new CreateSession(identifier, password);
        String json = gson.toJson(credentials);

        final Map<String, String> headers = Map.ofEntries(entry("Content-Type", "application/json;charset=UTF-8"));

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.server.createSession", json);
        HttpResponse<String> response = Utils.createProcedureResponse(request);

        Session session;
        if(response != null){
            if(response.statusCode() == 400){
                XRPCError xrpcError = gson.fromJson(response.body(), XRPCError.class);
                return null;
            }
            if(response.statusCode() == 401){
                XRPCError xrpcError = gson.fromJson(response.body(), XRPCError.class);
            }
            if(response.statusCode() == 200){
                JsonObject sessionCredentials = gson.fromJson(response.body(), JsonObject.class);

                String string = sessionCredentials.get("handle").getAsString();

                session = Session.getSession(sessionCredentials.get("did").getAsString(),
                        sessionCredentials.get("handle").getAsString(),
                        sessionCredentials.get("email").getAsString(),
                        sessionCredentials.get("accessJwt").getAsString(),
                        sessionCredentials.get("refreshJwt").getAsString());

                return session;
            }
        }
        return null;
    }

    public static String deleteSession(String provider, Session session) throws IOException, InterruptedException, URISyntaxException {
        final Map<String, String> headers = Map.ofEntries(entry("Authorization","Bearer " + session.getRefreshJwt()));

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.server.deleteSession");

        HttpResponse<String> response = Utils.createProcedureResponse(request);

        return response.body();
    }

    public static <T> String post(String provider, Session session, Record<T> record) throws IOException, InterruptedException {
        final Map<String, String> headers = Map.ofEntries(entry("Authorization","Bearer " + session.getAccessJwt()),
                                                    entry("Content-Type","application/json;charset=UTF-8"));

        String json = gson.toJson(record);

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.repo.createRecord", json);

        HttpResponse<String> response = Utils.createProcedureResponse(request);

        return response.body();
    }

    public static <T> String postImage(String provider, Session session, Record<T> record) throws IOException, InterruptedException, ExecutionException {
        final Map<String, String> headers = Map.ofEntries(entry("Authorization","Bearer " + session.getAccessJwt()),
                                                    entry("Content-Type","application/json;charset=UTF-8"));

        String json = gson.toJson(record);

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.repo.createRecord", json);

        CompletableFuture<HttpResponse<String>> response = Utils.createProcedureResponseAsync(request);

        while(!response.isDone()){
            Thread.sleep(1000);
        }

        return response.get().body();
    }
}