package com.abdullahieid.atproto;

public class XRPCError {
    private String error;
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "XRPCError{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
