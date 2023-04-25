package com.bluey;

// single mingle maybe idek
public class Session {
    private static Session session;
    private final String email;
    private final String handle;
    private final String did;
    private final String accessJwt;
    private final String refreshJwt;

    private Session(String did,
                    String handle,
                    String email,
                    String accessJwt,
                    String refreshJwt){
        this.did = did;
        this.handle = handle;
        this.email = email;
        this.accessJwt = accessJwt;
        this.refreshJwt = refreshJwt;
    }

    private String getDid(){
        return this.did;
    }
    private String getHandle(){
        return this.handle;
    }
    private String getEmail(){
        return this.email;
    }
    private String getAccessJwt(){
        return this.accessJwt;
    }
    private String getRefreshJwt(){
        return this.refreshJwt;
    }

    public static Session getSession(String Jwt) {
        if((session != null) && (Jwt.equals(session.getAccessJwt()))){
            return session;
        }

        if((session != null) && (Jwt.equals(session.getRefreshJwt()))){
            return session;
        }

        return null;
    }

    public static Session getSession(String did,
                              String handle,
                              String email,
                              String accessJwt,
                              String refreshJwt){
        if((session != null) && (accessJwt.equals(session.getAccessJwt()))){
            return session;
        }

        if((session != null) && (refreshJwt.equals(session.getRefreshJwt()))){
            return session;
        }

        if((did != null) && (handle != null) && (email != null) && (accessJwt != null) && (refreshJwt != null)){
            session = new Session(email, handle, did, accessJwt, refreshJwt);
            return session;
        }

        return null;
    }
}
