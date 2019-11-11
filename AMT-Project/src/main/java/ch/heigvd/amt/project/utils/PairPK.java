package ch.heigvd.amt.project.utils;

public class PairPK {
    private int first;
    private int second;

    public PairPK(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
