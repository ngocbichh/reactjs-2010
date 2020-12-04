/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.controller;

import caro.model.Request;
import caro.model.User;
import caro.view.ServerView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl implements Runnable{

    private Connection con;
    private ServerSocket myServer = null;
    private Socket clientSocket;
    private int serverPort=8888;

    public ServerControl() throws IOException {
        getDBConnection("carodb", "root", "admin1234");
        openServer(serverPort);
        while (true) {
            listening();
        }
    }

    private void getDBConnection(String dbName, String username, String password) {
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.jdbc.Driver";

        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("thanh cong");
        } catch (Exception e) {
            System.out.println("That bai");
            e.printStackTrace();
        }
    }

    private void openServer(int serverPort) {
        try {
            myServer = new ServerSocket(serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listening() {
        try {
            clientSocket = myServer.accept();
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            Object o = ois.readObject();
            if (o instanceof User) {
                User user = (User) o;
                if (checkUser(user)) {
                    oos.writeObject("ok");
                } else {
                    oos.writeObject("false");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkUser(User user) throws Exception{
        String query = "Select * FROM users WHERE id ='" + user.getId() +"'AND username ='" 
                + user.getUsername() + "'AND password ='" + user.getPassword() + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
    
    public void stop(){
        try {
            myServer.close();
            System.out.println("Máy Chủ bị đóng..!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
