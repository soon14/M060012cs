package com.cg.mrice.slidemenu.model;


import com.cg.mrice.slidemenu.interfaces.Resourceble;

/**
 * Created by Konstantin on 23.12.2014.
 */
public class SlideMenuItem implements Resourceble {
    private String name;
    private int imageRes;
    private int position;

    public SlideMenuItem(String name, int imageRes, int position) {
        this.name = name;
        this.imageRes = imageRes;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
