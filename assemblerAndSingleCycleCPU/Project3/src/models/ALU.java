package models;

import models.*;

/*
 * Does ADD, SUBTRACT, AND, OR, LESS THAN, EQUAL TO
 */

public class ALU{
    private int rsData;
    private int rtData;
    private int funct;
    private int aluAdds = 0, aluSubs = 0, aluAnds = 0, aluOrs = 0;
    public ALU(){
        rsData = 0;
        rtData = 0;
        funct = 0;
    }

    public int execute(int rs, int rt, int f){
        rsData = rs;
        rtData = rt;
        funct = f;

        switch (funct) {
            case 0x20:
                aluAdds++;
                return rsData + rtData;
            case 0x22:
                aluSubs++;
                return rsData - rtData;
            case 0x24:
                aluAnds++;
                return rsData & rtData;
            case 0x25:
                aluOrs++;
                return rsData | rtData;
            case 0x2B:
                aluAdds++;
                aluSubs++;
                return ((rsData - rtData) < 0 ? 0 : 1);
            default:
                System.out.println("Not valid R-Type instruction.");
                return -1;
        }
    }

    public int getAluAdds() {
        return aluAdds;
    }

    public int getAluSubs() {
        return aluSubs;
    }

    public int getAluOrs() {
        return aluOrs;
    }

    public int getAluAnds() {
        return aluAnds;
    }
}