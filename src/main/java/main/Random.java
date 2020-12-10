package main;

public class Random {
    public Random() { }

    public int inBoundsInt(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    public float inBoundsFloat(float min, float max) {
        min *= 100;
        max *= 100;
        return (float) ((Math.random() * ((max - min) + 1)) + min) / 100;
    }
}
