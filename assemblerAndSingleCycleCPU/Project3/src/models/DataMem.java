package models;

import java.util.ArrayList;
import java.lang.*;

public class DataMem {
    private static ArrayList<Byte> mem = new ArrayList<Byte>(1200);
    private static int numReads = 0;
    private static int numWrites = 0;

    public DataMem() {
        for(int i = 0; i < 1200; i++){
            mem.add((byte) 0);
        }
    }

    public void writeToMem(int index, int val) {
        if (index > mem.size()) {
            System.out.println("Error: invalid memory address");
        } else {
            mem.set(index, (byte) (val >> 24));
            mem.set(index + 1, (byte) ((val & 0xFF0000) >> 16));
            mem.set(index + 2, (byte) ((val & 0xFF00) >> 8));
            mem.set(index + 3, (byte) ((val & 0xFF)));
            numWrites++;
        }
    }

    public Integer readFromMem(int index, boolean update) {
        if(update){
            numReads++;
        }
        if(index < mem.size() - 3) {
            int val = mem.get(index) << 24 | mem.get(index + 1) << 16 | mem.get(index + 2) << 8 | mem.get(index + 3);
            return val;
        }
        return -1;
    }

    public int getSize(){
        return mem.size();
    }

    public static int getNumWrites(){
        return numWrites;
    }

    public static int getNumReads(){
        return numReads;
    }
}