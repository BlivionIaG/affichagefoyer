/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foyerinput;

import processing.core.*;

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
        x_factor = (float) 1 / 32;
        y_factor = (float) 1 / 32;
        w_factor = (float) 3 / 4;
        h_factor = (float) 1 / 8;

        screen_width = 800;
        screen_height = 600;

        fullscreen = false;
        clientsPath = "clients.txt";
        permanentPath = "permanent.txt";

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
            } else if (tmp[0].equals("X_FACTOR")) {
                x_factor = Float.parseFloat(tmp[1]);
            } else if (tmp[0].equals("Y_FACTOR")) {
                y_factor = Float.parseFloat(tmp[1]);
            } else if (tmp[0].equals("W_FACTOR")) {
                w_factor = Float.parseFloat(tmp[1]);
            } else if (tmp[0].equals("H_FACTOR")) {
                h_factor = Float.parseFloat(tmp[1]);
            } else if (tmp[0].equals("SCREEN_WIDTH")) {
                screen_width = Integer.parseInt(tmp[1]);
            } else if (tmp[0].equals("SCREEN_HEIGHT")) {
                screen_height = Integer.parseInt(tmp[1]);
            }
        }
    }

    public void settings() {
        clients = new ArrayList<>();

        loadConfig("config.txt");
        size(screen_width, screen_height);
        if (fullscreen) {
            fullScreen();
        }
    }

    public void setup() {
        surface.setResizable(true);
        frameRate(30);

        gui = new ControlP5(this);

        gui.addTextfield("clientName")
                .setPosition(Math.round(width * x_factor), Math.round(height * y_factor))
                .setFont(createFont("arial", 20))
                .setAutoClear(false)
                .getCaptionLabel().hide();

        gui.addButton("addClient")
                .setValue(0)
                .setPosition(width * (2 * x_factor + w_factor), height * y_factor)
                .getCaptionLabel().hide();
    }

    public void draw() {
        gui.get(Textfield.class, "clientName").setSize(Math.round(width * w_factor), Math.round(height * h_factor));
        gui.get(Button.class, "addClient").setSize(Math.round(width * (x_factor * 5)), Math.round(height * h_factor));
        background(192);

        int limit = clients.size();
        for (int i = 0; i < limit; i++) {
            clients.get(i).draw(
                    this,
                    Math.round(width * x_factor),
                    Math.round(height * ((i + 1) * h_factor + y_factor)),
                    Math.round(width * (x_factor + w_factor)),
                    Math.round(width * (x_factor * 5 / 2)),
                    Math.round(height * h_factor)
            );
        }

        update();
    }

    public void update() {
        updateList(clients);
        sortClients();
        updateFile(clientsPath, clients);
    }

    public void updateList(List<Client> list) {
        for (int i = 0; i < list.size(); i++) {
            while (list.get(i).getDestroy()) {
                list.remove(i);
                if (i == 0) {
                    return;
                }
            }
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

    public void sortClients() {
        List<Client> tmp = new ArrayList<>();
        for (Client it : clients) {
            if (it.getInfo()) {
                tmp.add(it);
            }
        }
        for (Client it : clients) {
            if (!it.getInfo()) {
                tmp.add(it);
            }
        }

        clients = tmp;
    }

    public void loadInterfaceImage(String path) {
        PImage a = loadImage(getClass().getResource("/images/yourimagename").getPath());
    }

    public void addClient() {
        Textfield tmp = gui.get(Textfield.class, "clientName");

        if (tmp.getText().length() < 1) {
            return;
        }

        clients.add(new Client(tmp.getText(), this));
        update();
        tmp.clear();
    }

    public static void main(String[] args) {
        PApplet.main("foyerinput.FoyerInput");
    }

    private float x_factor, y_factor, w_factor, h_factor;
    private int screen_width, screen_height;
    
    PImage[] removeButton;

    private ControlP5 gui;
    private List<Client> clients;

    private boolean fullscreen;
    private String clientsPath, permanentPath;
}
