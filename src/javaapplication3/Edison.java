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
    float celcius;
    float lux;
    float thresh;
    float absdeg;


    public Edison(float celcius, float lux, float thresh, float absdeg) {
        this.celcius = celcius;
        this.lux = lux;
        this.thresh = thresh;
        this.absdeg = absdeg;
    }

    public float getCelcius() {
        return celcius;
    }

    public void setCelcius(float celcius) {
        this.celcius = celcius;
    }

    public float getLux() {
        return lux;
    }

    public void setLux(float lux) {
        this.lux = lux;
    }

    public float getThresh() {
        return thresh;
    }

    public void setThresh(float thresh) {
        this.thresh = thresh;
    }

    public float getAbsdeg() {
        return absdeg;
    }

    public void setAbsdeg(float absdeg) {
        this.absdeg = absdeg;
    }

    @Override
    public String toString() {
        return "Edison{" + "celcius=" + celcius + ", lux=" + lux + ", thresh=" + thresh + ", absdeg=" + absdeg + '}';
    }
    
    
}
