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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class PostHandler implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String s, Context context) {
        final LambdaLogger logger = context.getLogger();
        logger.log("Function '" + context.getFunctionName() + "' called at '" + System.currentTimeMillis() + "'");
        logger.log("Starting program.");

        if((System.getenv("BSKY_USERNAME") == null) || (System.getenv("BSKY_PASSWORD") == null) ||
                System.getenv("BSKY_USERNAME").equalsIgnoreCase("$USERNAME") || System.getenv("BSKY_PASSWORD").equalsIgnoreCase("$PASSWORD")){
            logger.log("Credentials not provided in environmental variables. Program aborted.");
            throw new IllegalArgumentException("Must config credentials in environmental variables.");
        }

        // create bluesky session with credentials in environment labels
        Session session = null;
        try {
            logger.log("Creating session...");
            session = Server.createSession();
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
            Media media = new Media(System.getenv("IMAGE_CID"), System.getenv("IMAGE_MIMETYPE"));
            Image[] image = new Image[]{new Image("Captain it's Wednesday", media)};
            Post post = Post.createImagePost("Captain it's Wednesday", image);
            Record<Post> record = Record.createRecord(NSID.POST.nsid, System.getenv("BSKY_USERNAME"), NSID.POST, post);

            String response;
            try {
                logger.log("Creating post...");
                response = Server.postImage(System.getenv("PROVIDER"), session, record);
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
                Server.deleteSession(System.getenv("PROVIDER"), session);
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