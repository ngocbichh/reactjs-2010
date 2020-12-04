/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.controller;

import caro.view.ServerView;
import java.net.UnknownHostException;

/**
 *
 * @author ngocb
 */
public class ServerRun {
    public static void main(String[] args) throws UnknownHostException{
        ServerView view = new ServerView();
        view.setVisible(true);
    }
}
