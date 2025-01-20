package com;
import com.sun.net.httpserver.HttpServer;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class Main
{
    private static HttpServer server;
    public static void main(String[] args)  throws IOException
    {
        startUI();
    }


    //Small Swing gui to select which server you want to start
    public static void startUI(){
        JFrame frame = new JFrame("Start Server");
        JButton helloWorldbutton = new JButton("Start Hello World Server");
        JButton jsonButton = new JButton("Start Json Server");
        JButton stopServerButton = new JButton("Stop Server");
        helloWorldbutton.addActionListener(e->{
               try {
                   helloWorldServer();
                   helloWorldbutton.setEnabled(false);
                   jsonButton.setEnabled(false);
               } catch (IOException ex) {
                   throw new RuntimeException(ex);
               }
           });
        jsonButton.addActionListener(e ->{
                 try {
                     jsonServer();
                     jsonButton.setEnabled(false);
                     helloWorldbutton.setEnabled(false);
                 } catch (IOException ex) {
                     throw new RuntimeException(ex);
                 }
             });
        stopServerButton.addActionListener( e -> {
                    stopServer();
                    helloWorldbutton.setEnabled(true);
                    jsonButton.setEnabled(true);
                });

        BorderLayout bord = new BorderLayout();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(bord);
        frame.add(helloWorldbutton,BorderLayout.LINE_START);
        frame.add(jsonButton,BorderLayout.LINE_END);
        frame.add(stopServerButton,BorderLayout.PAGE_END);

        frame.pack();
        frame.show();
    }


    //helloWorld server prints helloWorld on localhost:8000/
    public static void helloWorldServer() throws IOException{
        server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", new HelloWorldHandler());

        server.setExecutor(null);
        server.start();
    }

    //jsonServer retrieves products from products.json
    //retrieves all products on localhost:8000/
    //retrieves product with product id on localhost:8000/:id
    //retrieves products with category name on localhost:8000/?category=:SOME_CATEGORY
    public static void jsonServer() throws IOException{
        server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", new JsonHandler());

        server.setExecutor(null);
        server.start();
    }

    public static void stopServer(){
        server.stop(0);
    }

}
