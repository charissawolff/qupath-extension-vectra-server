package org.computationalimmunology.ext.vectraserver.ui.commands.connectServer;

import java.util.function.Consumer;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;
import org.computationalimmunology.ext.vectraserver.core.api.ApiClient;
import org.computationalimmunology.ext.vectraserver.core.serverConnection.SSHConnectionManager;
import org.computationalimmunology.ext.vectraserver.ui.commands.AbstractAsyncCommand;

public class ConnectToServerCommand extends AbstractAsyncCommand<Boolean> {
    private final String username;
    private final String hostname;
    private final String password;
    private final String dbuser;
    private final String dbpass;
    private final int localPort;
    private final int remotePort;

    public ConnectToServerCommand(String username, String hostname, String password, String dbuser, String dbpass, int localPort, int remotePort){
        this.username = username;
        this.hostname = hostname;
        this.password = password;
        this.dbuser = dbuser;
        this.dbpass = dbpass;
        this.localPort = localPort;
        this.remotePort = remotePort;
    }

    protected Boolean execute(Consumer<String> progressReporter) throws Exception {
        progressReporter.accept("Logging into server...");
        try {
            SSHConnectionManager.getInstance().startSSHThread(username, hostname, password, localPort, remotePort);
        } catch (Exception e) {
            VectraServerLog.error("SSH connection failed.", e);
            progressReporter.accept(e.getMessage());
            return false;
        }
        try{
            VectraServerLog.log("SSH connection established. Logging into database...");
            progressReporter.accept("Logging into database...");
            ApiClient.getInstance().performDatabaseLogin(dbuser, dbpass);
        }catch (Exception e){
            VectraServerLog.error("Database login failed.", e);
            progressReporter.accept(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    protected void onSuccess(Boolean result) {
        if (result) {
            VectraServerLog.log("Successfully connected to server: " + hostname + " with user: " + username);
        } else {
            VectraServerLog.log("Failed to connect to server: " + hostname + " with user: " + username);
        }
    }

    @Override
    protected void onCancellation() {
        VectraServerLog.log("Cancelled while connecting to server: " + hostname + " with user: " + username);
    }

    @Override
    protected void onFailure(Throwable exception) {
        VectraServerLog.error("Failed to connect to server: " + hostname + " with user: " + username, exception);
    }

}
