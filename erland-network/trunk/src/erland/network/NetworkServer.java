package erland.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * A network server that listens, establish and manage client connections on a specified port
 * @author Erland Isaksson
 */
public class NetworkServer implements Runnable, NetworkConnectionManagerInterface {
    /** Indicates if the server should be shut down */
    private boolean bQuit;
    /** The server socket to use when listening for new connections */
    ServerSocket serverSocket = null;
    /** The factory for creating new connection objects */
    NetworkConnectionFactory factory;
    /** The connection manager that keeps track of all connected clients */
    NetworkConnectionManager manager;
    /** The server listening interface that should be called when something happends */
    NetworkServerListenerInterface listener;
    /** The Thread object representing the thread listening for new client connections */
    Thread thread;

    /**
     * Start a network server which should listen for new client connections on the specified port
     * @param factory The factory to use when creating new network connections
     * @param port The port which should be used to listen for new client connections
     * @param listener The server listening interface object that should be called when something happends
     * @return true/false (success/failure)
     * @throws IOException
     */
    public boolean start(NetworkConnectionFactory factory, int port, NetworkServerListenerInterface listener) throws IOException {
        if(serverSocket==null) {
            this.factory = factory;
            this.manager = new NetworkConnectionManager();
            this.manager.start();
            serverSocket = new ServerSocket(port);
            this.listener = listener;
            thread = new Thread(this);
            thread.start();
            return true;
        }else {
            return false;
        }
    }

    /**
     * Stop the network server, the connected clients will be disconnected and no new
     * clients can connect until the server is started again
     */
    public void stop() {
        bQuit = false;
        manager.stop();
        try {
            serverSocket.close();
            serverSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thread that listens for new client connections
     */
    public void run() {
        while(!bQuit) {
            try {
                Socket socket = serverSocket.accept();
                socket.setTcpNoDelay(true);
                NetworkConnectionInterface client = factory.create(socket,listener);
                manager.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean write(String message) {
        return manager.write(message);
    }

    public boolean write(String clientName,String message) {
        return manager.write(clientName,message);
    }
}
