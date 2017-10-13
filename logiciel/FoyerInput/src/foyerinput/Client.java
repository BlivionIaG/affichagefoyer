/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foyerinput;

import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Button;

/**
 *
 * @author kletor21
 */
public class Client {

    public Client(String clientName, PApplet window) {
        this.name = clientName;
        this.info = false;
        this.display = new ControlP5(window);
    }

    public void draw(PApplet window, int x, int y, int size) {
        window.textSize(size);
        window.text(name, x, y);
    }
    
    public String getName(){
        return name;
    }
    
    public void setInfo(boolean newState){
        this.info = newState;
    }
    
    public boolean getInfo(){
        return info;
    }

    private String name;
    private boolean info;
    private ControlP5 display;
    private Button toogleInfo, remove;
}
