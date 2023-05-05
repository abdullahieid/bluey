package com.abdullahieid;

import lombok.NonNull;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Utils {
    public static HttpRequest createProcedureRequest(@NonNull String url) {
        return createProcedureRequest(null, url, null);
    }
    public static HttpRequest createProcedureRequest(@NonNull Map<String, String> headers, @NonNull String url) {
        return createProcedureRequest(headers, url, null);
    }
    public static HttpRequest createProcedureRequest(@NonNull String url, @NonNull String body) {
        return createProcedureRequest(null, url, body);
    }

    // because http headers are ALWAYS strings apparetly, and header only accepts strings so...
    public static HttpRequest createProcedureRequest(Map<String, String> headers, @NonNull String url, String body) {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));

        if(body != null){
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
        }else {
            requestBuilder.POST(HttpRequest.BodyPublishers.noBody());
        }

        if(headers != null){
            headers.forEach(requestBuilder::header);
//            for (Map.Entry<String, String> header : headers.entrySet()) {
//                requestBuilder.header(header.getKey(), header.getValue());
//            }
        }

        HttpRequest request = requestBuilder.build();

        return request;
    }

//    public static HttpRequest createProcedureRequest(Map<String, String> headers, @NonNull byte[] bytes, @NonNull String url) {
//
//        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
//                .uri(URI.create(url));
//
//        if(bytes != null){
//            requestBuilder.POST(HttpRequest.BodyPublishers.ofByteArray(bytes));
//        }
//
//        if(headers != null){
//            headers.forEach(requestBuilder::header);
////            for (Map.Entry<String, String> header : headers.entrySet()) {
////                requestBuilder.header(header.getKey(), header.getValue());
////            }
//        }
//
//        HttpRequest request = requestBuilder.build();
//
//        return request;
//    }

    // make generic
    public static HttpResponse<String> createProcedureResponse(@NonNull HttpRequest request) throws IOException, InterruptedException {

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static CompletableFuture<HttpResponse<String>> createProcedureResponseAsync(@NonNull HttpRequest request) {

        CompletableFuture<HttpResponse<String>> response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}