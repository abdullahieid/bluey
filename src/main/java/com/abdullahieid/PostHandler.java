package com.abdullahieid;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.abdullahieid.atproto.Record;
import com.abdullahieid.atproto.Server;
import com.abdullahieid.atproto.Session;
import com.abdullahieid.bsky.Post;
import com.abdullahieid.bsky.embed.Image;
import com.abdullahieid.bsky.embed.Media;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class PostHandler implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String s, Context context) {
        final LambdaLogger logger = context.getLogger();
        logger.log("Function '" + context.getFunctionName() + "' called.");
        logger.log("Starting program.");

        // load properties from properties file
        Properties properties;
        try {
            logger.log("Loading properties...");
            properties = new Properties();
            InputStream fins = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(fins);
        } catch (FileNotFoundException e) {
            logger.log("Error loading propeties.");
            logger.log("Error reader file: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.log("Error loading propeties.");
            logger.log("Error loading file contents to properties: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.log("Properties loaded.");

        if(properties.getProperty("BSKY_USERNAME").equalsIgnoreCase("$USERNAME") || properties.getProperty("BSKY_PASSWORD").equalsIgnoreCase("$PASSWORD")){
            logger.log("Config credentials not provided in src/main/resources/config.properties. Program aborted.");
            throw new IllegalArgumentException("Must config credentials in src/main/resources/config.properties");
        }
        String provider = properties.getProperty("PROVIDER");

        // create bluesky session with those credentials
        Session session = null;
        try {
            logger.log("Creating session...");
            session = Server.createSession(provider, properties.getProperty("BSKY_USERNAME"), properties.getProperty("BSKY_PASSWORD"));
        } catch (IOException e) {
            logger.log("IO error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            logger.log("URI syntax error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            logger.log("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        logger.log("Session created.");

        if(session != null){
            logger.log("Creating post.");

            // create post with this image already at this location

            // for vanilla text post
            Post post = Post.createPost("test");
            Record<Post> record = Record.createRecord(NSID.POST.nsid, properties.getProperty("BSKY_USERNAME"), NSID.POST, post);

            // for image post::
            // check out src/createBlob.ts to see how i uploaded the image blob
//            Media media = new Media(properties.getProperty("IMAGE_CID"), properties.getProperty("IMAGE_MIMETYPE"));
//            Image[] image = new Image[]{new Image("ALT_OF_IMAGE", media)};
//            Post post = Post.createImagePost("test", image);
//            Record<Post> record = Record.createRecord(NSID.POST.nsid, properties.getProperty("BSKY_USERNAME"), NSID.POST, post);

            String response;
            try {
                logger.log("Creating post...");
                response = Server.postImage(provider, session, record);
            } catch (IOException e) {
                logger.log("IO error: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                logger.log("Error: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                logger.log("Execution exception: " + e.getMessage());
                throw new RuntimeException(e);
            }

            logger.log("Post created.");

            try {
                logger.log("Deleting session...");
                Server.deleteSession(provider, session);
            } catch (IOException e) {
                logger.log("IO error: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                logger.log("Error: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                logger.log("URI syntax error: " + e.getMessage());
                throw new RuntimeException(e);
            }

            logger.log("Session deleted.");
            session = null;
            logger.log("Program successful.");
        }else {
            logger.log("Error creating session. Program unsuccessful.");
        }
        return "Program complete.";
    }
}