/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foyerinput;

import processing.core.PApplet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.BufferedWriter;

import controlP5.ControlP5;
import controlP5.Button;
import controlP5.Textfield;

/**
 *
 * @author kevin
 */
public class FoyerInput extends PApplet {

    void loadConfig(String path) {
        fullscreen = false;
        clientsPath = "clients.txt";

        List<String> cfg = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            cfg = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String it : cfg) {
            String tmp[] = it.split("=");
            tmp[0] = tmp[0].toUpperCase();
            if (tmp[0].equals("FULLSCREEN")) {
                fullscreen = tmp[1].toUpperCase().equals("TRUE");
            } else if (tmp[0].equals("CLIENTS_PATH")) {
                clientsPath = tmp[1];
            }
        }
    }

    public void settings() {
        clients = new ArrayList<>();

        loadConfig("config.txt");
        size(800, 600);
        if (fullscreen) {
            fullScreen();
        }
    }

    public void setup() {
        surface.setResizable(true);
        gui = new ControlP5(this);

        gui.addTextfield("clientName")
                .setPosition(width / 32, 0)
                .setSize(width * 3 / 4, height / 8)
                .setFont(createFont("arial", 20))
                .setAutoClear(false)
                .getCaptionLabel().hide();

        gui.addButton("addClient")
                .setValue(0)
                .setPosition(width * 3 / 4 + width * 3 / 32, 0)
                .setSize(width / 8, height / 8)
                .getCaptionLabel().hide();
    }

    public void draw() {
        gui.get(Textfield.class, "clientName").setSize(width * 3 / 4, height / 8);
        gui.get(Button.class, "addClient").setSize(width / 8, height / 8);
        background(192);

        int limit = clients.size();
        for (int i = 0; i < limit; i++) {
            clients.get(i).draw(this, 0, i * height / 8, height / 8);
        }
    }

    public void updateFile(String path, List<Client> list) {
        try (BufferedWriter file = Files.newBufferedWriter(Paths.get(path))) {
            for (Client it : list) {
                file.write(it.getName() + ":" + it.getInfo() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient() {
        Textfield tmp = gui.get(Textfield.class, "clientName");

        if (tmp.getText().length() < 1) {
            return;
        }

        clients.add(new Client(tmp.getText(), this));
        updateFile(clientsPath, clients);
        tmp.clear();
    }

    public static void main(String[] args) {
        PApplet.main("foyerinput.FoyerInput");
    }

    private ControlP5 gui;
    private List<Client> clients;

    private boolean fullscreen;
    private String clientsPath;
}
