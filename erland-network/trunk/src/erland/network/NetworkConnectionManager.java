package erland.network;

import java.util.Vector;

/**
 * This is a connection manager that can manage the connections of all
 * clients connected to a server
 * @author Erland Isaksson
 */
public class NetworkConnectionManager implements Runnable, NetworkConnectionManagerInterface {
    /** Connected clients */
    protected Vector clients;
    /** Indicates if network manager should disconnect all connections and shutdown */
    protected boolean bQuit = false;
    /** Thread that detects disconnected clients and removes them from the client list */
    Thread thread;

    /**
     * Starts the network manager
     */
    public void start() {
        clients = new Vector();
        thread = new Thread(this);
    }

    /**
     * Shutdown the network manager
     */
    public void stop() {
        bQuit = false;
        thread.interrupt();
        for(int i=0;i<clients.size();i++) {
            NetworkConnectionInterface client = (NetworkConnectionInterface) clients.elementAt(i);
            client.disconnect();
        }
        cleanConnections();
    }

    /**
     * Add a new client connection to the network manager
     * @param client The client connection to add
     */
    public void add(NetworkConnectionInterface client) {
        clients.add(client);
    }

    /**
     * Remove a client connection from the network manager
     * @param client The client connection to remove
     */
    public void remove(NetworkConnectionInterface client) {
        clients.remove(client);
    }

    /**
     * Check all client connections and remove client connections from the client list
     * if they has been disconnected
     */
    protected void cleanConnections() {
        boolean bFinished = false;
        while(!bFinished) {
            bFinished = true;
            for(int i=0;i<clients.size();i++) {
                NetworkConnectionInterface client = (NetworkConnectionInterface) clients.elementAt(i);
                if(client == null || !client.isAlive()) {
                    clients.remove(i);
                    bFinished = false;
                    break;
                }
            }
        }
    }
    /**
     * Main thread that checks the client connections and remove disconnected client connections
     * from the client list
     */
    public void run() {
        while(!bQuit) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cleanConnections();
        }
    }

    public boolean write(String clientName, String message) {
        for(int i=0;i<clients.size();i++) {
            NetworkConnectionInterface client = (NetworkConnectionInterface) clients.elementAt(i);
            if(clientName.equals(client.getName())) {
                return client.write(message);
            }
        }
        return true;
    }

    public boolean write(String message) {
        for (int i = 0; i < clients.size(); i++) {
            NetworkConnectionInterface client = (NetworkConnectionInterface) clients.elementAt(i);
            client.write(message);
        }
        return true;
    }
}
