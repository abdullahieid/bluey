package com.bluey;

import com.bluey.atproto.Server;
import com.bluey.embed.Image;
import com.bluey.embed.Media;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException, ExecutionException {
        // load properties from properties file
        FileInputStream fileInputStream = new FileInputStream("src/config.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);

        if(properties.getProperty("BSKY_USERNAME").equals("$USERNAME") || properties.getProperty("BSKY_PASSWORD").equals("$PASSWORD")){
            throw new IllegalArgumentException("Must config credentials in src/config.properties");
        }
        String provider = properties.getProperty("PROVIDER");

        // create bluesky session with those credentials
        Session session = Server.createSession(provider, properties.getProperty("BSKY_USERNAME"), properties.getProperty("BSKY_PASSWORD"));

        if(session != null){

            // create post with this image already at this location

            // for vanilla text post
            Post post = Post.createPost("test");
            Record<Post> record = Record.createRecord(NSID.POST.nsid, properties.getProperty("BSKY_USERNAME"), NSID.POST, post);

            // for image post::
            // check out src/createBlob.ts to see how i uploaded the image blob
//            Media media = new Media("$CID_OF_IMAGE_BLOB_ALREADY_POSTED", "$IMAGE_TYPE_SUCH_AS image/jpeg");
//            Image[] image = new Image[]{new Image("ALT_OF_IMAGE", media)};
//            Post post = Post.createImagePost("test", image);
//            Record<Post> record = Record.createRecord(NSID.POST.nsid, properties.getProperty("BSKY_USERNAME"), NSID.POST, post);

            String response = Server.post(provider, session, record);

            Server.deleteSession(provider, session);

            session = null;
        }
    }
}