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
public class Client extends PApplet {

    public Client(String clientName, PApplet window) {
        this.name = clientName;
        this.info = false;
        destroy = false;
        display = new ControlP5(window);

        remove = display.addButton("remove").setValue(0).plugTo(this, "remove").hide();
        toggleInfo = display.addButton("toggleInfo").setValue(0).plugTo(this, "toggleInfo").hide();
    }

    public void draw(PApplet window, int x, int y, int length, int wsize, int hsize) {
        if (!destroy) {
            window.textSize(hsize);
            window.text(name, x, y + hsize);

            toggleInfo.setPosition(length + x, y).setSize(wsize, hsize).show();
            if (info) {
                remove.setPosition(length + x + wsize, y).setSize(wsize,hsize).show();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setInfo(boolean newState) {
        this.info = newState;
    }

    public boolean getInfo() {
        return info;
    }

    public void toggleInfo() {
        info = !info;
    }

    public void remove() {
        display.hide();
        destroy = true;
    }
    
    public boolean getDestroy(){
        return destroy;
    }

    private String name;
    private boolean info, destroy;
    private ControlP5 display;
    private Button toggleInfo, remove;
}
