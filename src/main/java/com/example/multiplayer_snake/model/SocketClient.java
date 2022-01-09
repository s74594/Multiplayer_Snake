package com.example.multiplayer_snake.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.Flow;

public class SocketClient implements Flow.Subscriber<String> {

    public static Socket socket;
    public static PrintWriter writer;
    Flow.Subscription subscription;

    public static void connect() {
        try {
            // connect to server
            socket = new Socket("127.0.0.1", 5000);
            System.out.println(">>> Client connected using port " + socket.getLocalPort());

            // create output writer
            writer = new PrintWriter(socket.getOutputStream());

            // create input reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // start concurrent input message handler
            new Thread(new InputMessageHandler(reader)).start();

        } catch (IOException e) {
            System.out.println(e.getMessage() + " - Kann keine Verbindung zu Server herstellen, bitte Server überprüfen!!!");
        }
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("Observer Pattern - Socketclient - subscription started");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        System.out.printf("SocketClient - Received new state = %s\n", item);
        writer.println(item);
        writer.flush();
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        System.out.println("Subscription ended");
    }
}