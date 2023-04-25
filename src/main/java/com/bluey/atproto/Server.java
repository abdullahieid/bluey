package com.bluey.atproto;

import com.bluey.Session;
import com.bluey.records.CreateSession;
import com.bluey.records.XRPCError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Server {
    private static final ObjectMapper mapper = new ObjectMapper();;

    public static Session createSession(String url,
                                        String identifier,
                                        String password) throws IOException, URISyntaxException, InterruptedException {
        if((identifier == null) || (password == null)){
            return null; // create custom exception
        }

        CreateSession createSession = new CreateSession(identifier, password);
        String json = mapper.writeValueAsString(createSession);

//        HttpRequest request = Utils.createProcedureRequest(url, json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers("Content-Type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

//        HttpResponse<String> response = Utils.createProcedureResponse(request);

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Session session;
        if(response != null){
            JsonNode jsonNode = mapper.readTree(response.body());
            System.out.println(jsonNode);
            System.out.println(response.statusCode());
            if(response.statusCode() == 400){
                XRPCError xrpcError = new XRPCError(jsonNode.get("error").toString(),
                        jsonNode.get("message").toString());
                System.out.println(xrpcError);
                return null;
            }
            if(response.statusCode() == 401){
                XRPCError xrpcError = new XRPCError(jsonNode.get("error").toString(),
                        jsonNode.get("message").toString());
                System.out.println("WRONG PASSWORD");
            }
            if(response.statusCode() == 200){
                session = Session.getSession(jsonNode.get("did").toString(),
                        jsonNode.get("handle").toString(),
                        jsonNode.get("email").toString(),
                        jsonNode.get("accessJwt").toString(),
                        jsonNode.get("refreshJwt").toString());
                System.out.println(session);
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
    }

}
