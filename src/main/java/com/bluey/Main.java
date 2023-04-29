package com.bluey;

import com.bluey.atproto.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {
        // load properties from properties file
        String configFilePath = "src/config.properties";
        FileInputStream fileInputStream = new FileInputStream(configFilePath);
        Properties properties = new Properties();
        properties.load(fileInputStream);

        if(properties.getProperty("BSKY_USERNAME").equals("$USERNAME") || properties.getProperty("BSKY_PASSWORD").equals("$PASSWORD")){
            throw new IllegalArgumentException("Must config credentials in src/config.properties");
        }

//         create bluesky session with those credentials
        Session session = Server.createSession(properties.getProperty("BSKY_USERNAME"), properties.getProperty("BSKY_PASSWORD"));

        if(session != null){

            Post post = new Post("test", Instant.now().toString());

            Record<Post> record = new Record<>(properties.getProperty("BSKY_USERNAME"), NSID.POST, post);

            String response = Server.post(session, record);

            System.out.println(response.toString());

            if(response.contains("Token could not be verified")){
                Server.deleteSession(session);
            }
        }
    }
}