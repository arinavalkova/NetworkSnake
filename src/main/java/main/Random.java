package main;

public class Random {
    public Random() { }

    public int inBounds(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
