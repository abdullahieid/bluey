package com.bluey.atproto;

import com.bluey.*;
import com.bluey.Record;
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

        System.out.println("Creating session...");

        CreateSession credentials = new CreateSession(identifier, password);
        String json = gson.toJson(credentials);
        Map<String, String> headers = Map.ofEntries(entry("Content-Type", "application/json;charset=UTF-8"));

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.server.createSession", json);
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
                System.out.println(session.toString());
                return session;
            }
        }
        return null;
    }

    public static String deleteSession(String provider, Session session) throws IOException, InterruptedException, URISyntaxException {
        System.out.println("Deleting session...");

        Map<String, String> headers = Map.ofEntries(entry("Authorization","Bearer " + session.getRefreshJwt()));
        System.out.println(session.getAccessJwt());

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.server.deleteSession");

        HttpResponse<String> response = Utils.createProcedureResponse(request);

        System.out.println("Status code:: " + response.statusCode());
        System.out.println("Response:: " + response.body());

        return response.body();
    }

    public static <T> String post(String provider, Session session, Record<T> record) throws IOException, InterruptedException {
        System.out.println("Creating post...");

        Map<String, String> headers = Map.ofEntries(entry("Authorization","Bearer " + session.getAccessJwt()),
                                                    entry("Content-Type","application/json;charset=UTF-8"));

        String json = gson.toJson(record);

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.repo.createRecord", json);

        HttpResponse<String> response = Utils.createProcedureResponse(request);

        System.out.println("Status code:: " + response.statusCode());
        System.out.println("Response:: " + response.body());
        return response.body();
    }

    public static <T> String postImage(String provider, Session session, Record<T> record) throws IOException, InterruptedException, ExecutionException {
        System.out.println("Creating post...");

        Map<String, String> headers = Map.ofEntries(entry("Authorization","Bearer " + session.getAccessJwt()),
                                                    entry("Content-Type","application/json;charset=UTF-8"));

        String json = gson.toJson(record);

        HttpRequest request = Utils.createProcedureRequest(headers, "https://"+provider+"/xrpc/com.atproto.repo.createRecord", json);

        CompletableFuture<HttpResponse<String>> response = Utils.createProcedureResponseAsync(request);

        while(!response.isDone()){
            Thread.sleep(1000);
        }

        System.out.println("Status code:: " + response.get().statusCode());
        System.out.println("Response:: " + response.get().body());
        return response.get().body();
    }
}