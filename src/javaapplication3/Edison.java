/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

/**
 *
 * @author Carlos
 */
public class Edison {
    float temp;
    float lux;
    float sound;

    public Edison(float temp, float lux, float sound) {
        this.temp = temp;
        this.lux = lux;
        this.sound = sound;
    }

    @Override
    public String toString() {
        return "Edison{" + "temp=" + temp + ", lux=" + lux + ", sound=" + sound + '}';
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getLux() {
        return lux;
    }

    public void setLux(float lux) {
        this.lux = lux;
    }

    public float getSound() {
        return sound;
    }

    public void setSound(float sound) {
        this.sound = sound;
    }
    
    
}
