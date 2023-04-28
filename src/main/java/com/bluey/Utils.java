package com.bluey;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Utils {
    public static HttpRequest createProcedureRequest(String url, String body) throws URISyntaxException {
        return createProcedureRequest(null, url, body);
    }

    // because http headers are ALWAYS strings apparetly, and header only accepts strings so...
    public static HttpRequest createProcedureRequest(Map<String, String> queries, String url, String body) {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body));

        if(queries != null){
            for (Map.Entry<String, String> key : queries.entrySet()) {
                requestBuilder.header(key.getKey(), key.getValue());
            }
        }

        HttpRequest request = requestBuilder.build();

        return request;
    }

    // make generic
    public static HttpResponse<String> createProcedureResponse(HttpRequest request) throws IOException, InterruptedException {

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}