package models;

import java.util.ArrayList;
import java.lang.*;
import java.io.*;

public class InstrMem {
    private static ArrayList<Integer> mem = new ArrayList<Integer>();
    private static int numReads = 0;
    private static int numWrites = 0;
    private String filename;
    private File file;

    public InstrMem(String fn) throws FileNotFoundException{
        filename = fn;
        readFromFile();
    }

    public InstrMem() {
    }

    public void readFromFile() throws FileNotFoundException {
        file = new File(filename);
        FileInputStream input = new FileInputStream(file);
        long length = file.length();
        int instruction = 0;

        //writes all the instruction to instruction memory
        for(int i = 0; i < length; i+=4){
            try{
                instruction = (input.read() << 24) | (input.read() << 16) | (input.read() << 8) | input.read();
            }catch(IOException e){
                e.printStackTrace();
            }
            writeToMem(instruction, false);
        }
    }

    public void writeToMem(int val, boolean update) {
        mem.add(val >> 24);
        mem.add((val & 0xFF0000) >> 16);
        mem.add((val & 0xFF00) >> 8);
        mem.add(val & 0xFF);
        if(update) {
            numWrites++;
        }
    }

    public Integer readFromMem(int index, boolean update) {
        if(index < mem.size()-3) {
            int instruction = (mem.get(index) << 24) | (mem.get(index + 1) << 16) | (mem.get(index + 2) << 8) | mem.get(index + 3);
            return instruction;
        }
        if(update){
            numReads++;
        }
        System.out.println("Instruction Memory: invalid reading index");
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