package edu.gemini.model;

public class AbstractTelePositionPair {
    private double direction;
    private double degree;

    public AbstractTelePositionPair() {
    }

    public AbstractTelePositionPair(double direction, double degree) {
        this.direction = direction;
        this.degree = degree;
    }

    public double getDirection() {
        return direction;
    }

    public double getDegree() {
        return degree;
    }

    @Override
    public String toString() {
        return "TelePositionPair{" +
                "direction=" + direction +
                ", degree=" + degree +
                '}';
    }
}