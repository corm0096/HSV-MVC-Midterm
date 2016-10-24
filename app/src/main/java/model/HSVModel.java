package model;

import android.graphics.Color;

import java.util.Observable;

/**
 * Created by DC on 2016-10-23.
 *
 * Getters will expect values from 0 to 100 (int) except hue, which requires 0 to 360.
 * For convenience, getHueBar() (etc) returns integers for seekbar updates, while
 * getHue() (etc) returns the values HSV requires.
 * getHSV() returns the full array Color.HSVToColor() requires.
 */

public class HSVModel extends Observable
{

    // CLASS VARIABLES
    public static final int MAX_SV   = 100;
    public static final Integer MAX_HUE = 360;
    public static final float MIN_SV   = 0;
    public static final Integer MIN_HUE=0;

    // INSTANCE VARIABLES
    private float hue;
    private float saturation;
    private float value;

    //Empty constructor
    public HSVModel() {
        this(MAX_HUE, MAX_SV, MAX_SV);
    }

    //Constructor with expected arguments
    public HSVModel( Integer hue, int sat, int val )
    {
        super();

        this.saturation  = (float)sat/100;
        this.value = (float)val/100;
        this.hue   = (float)hue;
    }

    //Palette setters.
    public void setBlack()
    {
        this.setHSV(0,0,0);
    }
    public void setRed()
    {
        this.setHSV(0,100,100);
    }
    public void setLime()
    {
        this.setHSV(120,100,100);
    }
    public void setBlue()
    {
        this.setHSV(240, 100, 100);
    }
    public void setYellow()
    {
        this.setHSV(60,100,100);
    }
    public void setCyan()
    {
        this.setHSV(180,100,100);
    }
    public void setMagenta()
    {
        this.setHSV(300,100,100);
    }
    public void setSilver(){this.setHSV(0,0,75);}
    public void setGray()
    {
        this.setHSV(0,0,50);
    }
    public void setMaroon()
    {
        this.setHSV(0,100,50);
    }
    public void setOlive()
    {
        this.setHSV(60,100,50);
    }
    public void setGreen()
    {
        this.setHSV(120,100,50);
    }
    public void setPurple()
    {
        this.setHSV(300,100,50);
    }
    public void setTeal()
    {
        this.setHSV(180,100,50);
    }
    public void setNavy()
    {
        this.setHSV(240,100,50);
    }

    public int getHueBar() { return (int)hue;}
    public int getSatBar() { return (int)(saturation*100);}
    public int getValBar() { return (int)(value*100);}

    public void setHue (int hue)
    {
        this.hue=(float)hue;
        this.updateObservers();
    }
    public void setSat (int sat)
    {
        this.saturation=(float)sat/100;
        this.updateObservers();
    }
    public void setVal(int val)
    {
        this.value=(float)val/100;
        this.updateObservers();
    }

    //Returns array required by HSVToColor
    public float[] getHSV()
    {
        float returnval[]=new float[3];
        returnval[0]=this.hue;
        returnval[1]=this.saturation;
        returnval[2]=this.value;

        return returnval;
    }

    //Permits direct setting of the colour swatch
    public void setHSV(int hue, float sat, float val)
    {
        this.hue = (float)hue;
        this.value=(float)val/100;
        this.saturation=(float)sat/100;
        this.updateObservers();
    }

    private void updateObservers()
    {
        this.setChanged();
        this.notifyObservers();
    }
}