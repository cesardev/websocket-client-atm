package com.neo.mx.atm.fxcomponents.tray.models;

public class Location {

    private double x, y;

    public Location(double xLoc, double yLoc) {
        this.x = xLoc;
        this.y = yLoc;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
