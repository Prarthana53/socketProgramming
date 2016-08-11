/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prarthana.chatserver.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Pavilion G4
 */
public class ClientListener extends Thread{
    private Socket socket;
    private Client client;
    private ClientHandler handler;

    public ClientListener(Socket socket,ClientHandler handler) {
        this.socket = socket;
        this.handler=handler;
    }

    @Override
    public void run() {
        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println("Welcome.............");
            ps.println("Enter your name: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String name = br.readLine();
            System.out.println("Hello " + name);
            client=new Client(name, socket);
            handler.addClient(client);
            handler.broadcastMessage("\n"+client.getUserName()+" has joined the chat room.");
            while(!isInterrupted()){
                String msg=br.readLine();
                String[] tokens=msg.split(";;");
                System.out.println(tokens.length);
                if(tokens[0].equalsIgnoreCase("pm")){
                if(tokens.length>2){
                handler.privateMessage(tokens[1],"(PM) from "+ client.getUserName()+" >"+tokens[2]);
                
                }
                }
                else{
                handler.broadcastMessage(client.getUserName()+" says >" + msg);
                }
            }  
        } catch (IOException ioe) {

            System.out.println(ioe.getMessage());
        }
    }


    
    
    
}
