package org.computationalimmunology.ext.vectraserver.core.api;

import java.io.IOException;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;

public class StatusUtils {

    public static void checkStatusCode(int statusCode) throws IOException {
        if (statusCode >= 400 && statusCode < 500) {
            IOException e = new IOException("Client error. HTTP statuscode: " + statusCode);
            VectraServerLog.error("Client error with fetching webpage", e);
            throw e;
        }
        if (statusCode >= 500) {
            IOException e = new IOException("Server error. HTTP statuscode: " + statusCode);
            VectraServerLog.error("Server error with fetching webpage", e);
            throw e;
        }
    }

    private StatusUtils(){
        // should not be initialized
    }
    
}
