package erland.network;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import java.net.Socket;
import java.io.*;

/**
 * Manages a network connection
 * @author Erland Isaksson
 */
public class NetworkConnection implements NetworkConnectionInterface, Runnable {
    /** The Socket object for the connection */
    protected Socket socket;
    /** Indicates if connection should be disconnected */
    protected boolean bQuit = false;
    /** The Thread object for the thread listening on the connection */
    protected Thread thread;
    /** The output object used when writing data on the connection */
    protected PrintStream output;
    /** The input object used when reading data from the connection */
    protected BufferedReader input;
    /** The name of the connection */
    protected String name;
    /** The client listening object which should be called when something happends */
    protected NetworkClientListenerInterface listener;

    /**
     * Creates a new instance and initialize the connection on the connection handled by the specified socket.
     * Recieves the login information and and manage the login sequense
     * @param socket The Socket object representing the connection
     * @param listener The server listener object which should be called when something happends
     */
    public NetworkConnection(Socket socket, NetworkServerListenerInterface listener) {
        this.socket = socket;
        this.listener = listener;
        if(init()) {
            System.out.println("read username");
            this.name = read();
            System.out.println("read password");
            String password = read();
            System.out.println("check login: "+name+", "+password);
            if(listener.login(this,name,password)) {
                write("connected");
                listener.connected(this);
                start();
            }else {
                write("disconnected");
                disconnect();
            }
        }
    }

    /**
     * Creates a new instance and initialize the connection on the connection handled by the specified socket.
     * Sends the specified login information and and manage the login sequense
     * @param socket The Socket object representing the connection
     * @param listener The client listener object which should be called when something happends
     */
    public NetworkConnection(String username, String password, Socket socket, NetworkClientListenerInterface listener) {
        this.socket = socket;
        this.listener = listener;
        this.name = username;
        if(init()) {
            System.out.println("write username");
            write(username);
            System.out.println("write password");
            write(password);
            System.out.println("read connection status");
            String status = read();
            if(status.equals("connected")) {
                listener.connected(this);
                start();
            }else {
                disconnect();
            }
        }
    }

    /**
     * Initialize the connection
     * @return true/false (success/failure)
     */
    protected boolean init() {
        try {
            output = new PrintStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isAlive() {
        return (thread!=null && thread.isAlive());
    }

    /**
     * Starts the thread listening on the connection
     */
    protected void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void disconnect() {
        bQuit = true;
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.disconnected(this);
    }

    /**
     * Read data from the connection, this method will lock until a whole line of data has been recieved
     * @return The line of data read from the connection
     */
    protected String read() {
        String str = null;
        try {
            str = input.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return str;
    }

    public String getName() {
        return name;
    }

    public boolean write(String string) {
        output.println(string);
        return true;
    }

    /**
     * Implementation of the listeing thread that reads data from the connection and
     * detects if the connection has been broken.
     */
    public void run() {
        while(!bQuit) {
            String str = read();
            if(str!=null) {
                listener.message(this,str);
            }else {
                disconnect();
            }
        }
    }
}
