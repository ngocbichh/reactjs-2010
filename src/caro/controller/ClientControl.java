/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.controller;

import caro.model.Request;
import caro.model.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ngocb
 */
public class ClientControl {
    private Socket mySocket;
    private String serverHost;
    private int serverPort=8888;
    
    public ClientControl(){
    }

    public ClientControl(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }
    
    public Socket openConnection(){
        try{
            mySocket = new Socket(serverHost, serverPort);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return mySocket;
    }
    
    public boolean sendData(User user){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Object receiveData(){
        String rs = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o  =ois.readObject();
            if(o instanceof  String){
                rs = (String) o;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return rs;
    }
    
    public boolean closeConnection(){
        try {
            mySocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
