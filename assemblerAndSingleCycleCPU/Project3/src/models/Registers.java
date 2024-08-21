package models;

import java.util.ArrayList;
import java.lang.*;

public class Registers{
    private static ArrayList<Integer> reg = new ArrayList<Integer>(32);

    public Registers(){
        // initialize registers with arbitrary data
        for(int i = 0; i < 32; i++) {
            reg.add(i+1);
        }
    }

    public void writeToReg(int index, int val){
        if(index > reg.size()){
            System.out.println("Error: invalid index");
        }
        else{
            reg.set(index, val);
        }
    }

    public Integer readFromReg(int index){
        return reg.get(index);
    }
}