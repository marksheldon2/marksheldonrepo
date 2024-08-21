package controller;

import models.*;
import view.*;
import java.io.*;

public class TextController {
    private String filename;
    private File file;
    private InstrMem instrMem;
    private DataMem dataMem;
    private ALU alu;
    private int pc = 0;
    private int register1 = 0, register2 = 0, destRegister = 0, address = 0, funct = 0, instruction = 0;
    private Registers reg;
    private GUIView view;
    private int numAdd = 0, numSub = 0, numAnd = 0, numOr = 0, numSlt = 0, numLoad = 0, numStore = 0, numBeq = 0, numJump = 0, numCycles = 0;

    public TextController(String fn, int mode, GUIView v) throws FileNotFoundException{
        filename = fn;
        instrMem = new InstrMem(fn);
        dataMem = new DataMem();
        alu = new ALU();
        reg = new Registers();
        view = v;
    }

    public void processInstructions(int mode) {

        if(mode == 1) {
            // Cycle instructions after enter press NOT IMPLEMENTED YET
            if (pc < instrMem.getSize()) {
                instruction = fetchInstruction(pc);
                numCycles++;
                updateScoreboard();
                int instrID = instructionDecode(instruction);
                numCycles++;
                updateScoreboard();
                int result = executeInstruction(instrID);
                numCycles++;
                updateScoreboard();
                int val;
                switch (instrID) {
                    case 1: // store
                        val = memoryStage(instrID, result);
                        numCycles++;
                        updateScoreboard();
                        numCycles++;
                        updateScoreboard(); // update for unused writeback cycle
                        break;
                    case 2: // load
                        val = memoryStage(instrID, result);
                        numCycles++;
                        updateScoreboard();
                        writeBack(val);
                        numCycles++;
                        updateScoreboard();
                        break;
                    case 5: // r-type
                        numCycles++;
                        updateScoreboard(); // update for unused memory cycle
                        writeBack(result);
                        numCycles++;
                        updateScoreboard();
                        break;
                    // branch and jump don't require memory or write back stages
                    default:
                        numCycles++;
                        updateScoreboard(); // update for unused memory cycle
                        numCycles++;
                        updateScoreboard(); // update for unused writeback cycle
                        break;
                }
                updateScoreboard();
                pc += 4;
            }
        }
        else {
            // Cycle through all instructions
            numAdd = 0;
            numSub = 0;
            numAnd = 0;
            numOr = 0;
            numSlt = 0;
            numLoad = 0;
            numStore = 0;
            numBeq = 0;
            numJump = 0;
            numCycles = 0;
            for (pc = 0; pc < instrMem.getSize(); pc += 4) {
                instruction = fetchInstruction(pc);
                numCycles++;
                updateScoreboard();
                int instrID = instructionDecode(instruction);
                numCycles++;
                updateScoreboard();
                int result = executeInstruction(instrID);
                numCycles++;
                updateScoreboard();
                int val;
                switch (instrID) {
                    case 1: // store
                        val = memoryStage(instrID, result);
                        numCycles++;
                        updateScoreboard();
                        numCycles++;
                        updateScoreboard(); // update for unused writeback cycle
                        break;
                    case 2: // load
                        val = memoryStage(instrID, result);
                        numCycles++;
                        updateScoreboard();
                        writeBack(val);
                        numCycles++;
                        updateScoreboard();
                        break;
                    case 5: // r-type
                        numCycles++;
                        updateScoreboard(); // update for unused memory cycle
                        writeBack(result);
                        numCycles++;
                        updateScoreboard();
                        break;
                    // branch and jump don't require memory or write back stages
                    default:
                        numCycles++;
                        updateScoreboard(); // update for unused memory cycle
                        numCycles++;
                        updateScoreboard(); // update for unused writeback cycle
                        break;
                }
            }
        }
    }

    private int fetchInstruction(int index){
        if(index < instrMem.getSize()-3) {
            return (instrMem.readFromMem(index, true));
        }
        System.out.println("fetchInstruction: invalid index");
        return -1;
    }

    private int instructionDecode(int instruction){
        int opcode = (instruction >> 26);
        if(opcode < 0){
            opcode = makePositive(opcode);
        }
        int instrBody = (instruction & 0x3FFFFFF);
        System.out.println("Opcode: " + opcode +" instrBody: " + instrBody);
        int result = 0;

        switch (opcode) {
            case 43:
                System.out.println("Store");
                numStore++;

                register1 = (instrBody >> 21);
                register2 = ((instrBody & 0x01F0000) >> 16);
                address = ((instrBody & 0x000F800));
//                numAdd++;
                // store code for execute function
                return 1;

            case 35:
                System.out.println("Load");
                numLoad++;

                register1 = (instrBody >> 21);
                register2 = ((instrBody & 0x01F0000) >> 16);
                address = ((instrBody & 0x000F800));
                // load code for execute function
                return 2;

            case 4:
                System.out.println("Branch");
                numBeq++;
                register1 = (instrBody >> 21);
                register2 = ((instrBody & 0x01F0000) >> 16);
                address = ((instrBody & 0x000F800));
                // branch code for execute function
                return 3;

            case 2:
                System.out.println("Jump");
                numJump++;
                address = (instrBody & 0xFFFFFF);
                // jump code for execute function
                return 4;

            case 0:
                System.out.println("R-Type");
                funct = (instrBody & 0x3F);
                register1 = (instrBody >> 21);
                register2 = ((instrBody & 0x01F0000) >> 16);
                destRegister = ((instrBody & 0x000F800) >> 11);

                switch (funct) {
                    case 0x20:
                        numAdd++;
                        break;
                    case 0x22:
                        numSub++;
                        break;
                    case 0x24:
                        numAnd++;
                        break;
                    case 0x25:
                        numOr++;
                        break;
                    case 0x2B:
                        numSlt++;
                        break;
                }
                // r-type code for execute function
                return 5;
            default:
                System.out.println("Not valid instruction");
                return -1;
        }
    }

    private int executeInstruction(int instrID) {
        switch (instrID) {
            case 1: // store
                return alu.execute(reg.readFromReg(register1), address, 0x20);
            case 2: // load
                return alu.execute(reg.readFromReg(register1), address, 0x20);
            case 3: // branch
                if(alu.execute(reg.readFromReg(register1), reg.readFromReg(register2), 0x22) == 0){
                    alu.execute(pc, address, 0x20);
//                    numAdd++;
                }
//                numSub++;
                return 0;
            case 4: // jump
                alu.execute(pc, address, 0x20);
//                numAdd++;
                return 0;
            case 5: // R-Type Instruction
                return alu.execute(reg.readFromReg(register1), reg.readFromReg(register2), funct);
        }
        return -1;
    }

    private int memoryStage(int loadOrStore, int result){
        if(loadOrStore == 1){
            //store
            dataMem.writeToMem(result, reg.readFromReg(register2));
            // arbitrary return, no write back stage
            return 0;
        }
        else{
            //load
            destRegister=register2;
            System.out.println("TEST");
            return dataMem.readFromMem(result, true);
        }
    }

    private void writeBack(int value){
        reg.writeToReg(destRegister, value);
    }

    private int makePositive(int x){
        int offset = 0;
        for(int i = 16; i >= 1; i/=2){
            if((-32 + i + offset) == x){
                offset+=i;
                break;
            }
            else if((-32 + i + offset) < x){
                offset += i;

            }
        }
        return 32 + offset;
    }

    public void updateScoreboard(){
        view.setRegisterData(registersToString());
        view.setDataMemData(dataMemToString());
        view.setInstrMemData(instrMemToString());
        view.setStatsData(statsToString());
        view.setPCData("0x"+Integer.toHexString(pc)+"\t"+instructionsToString());
        //TextView.printToScreen(this.toString());
    }

    public String toString(){
        String data = "****************Instruction****************\n";
        data += instructionsToString();
        data += "\n****************Registers****************\n";
        data += registersToString();
        data += "\n****************Instruction Memory****************\n";
        data += instrMemToString();
        data += "\n****************Statistics****************\n";
        data += statsToString();
        data += "\n****************Data Memory****************\n";
        data += dataMemToString();
        return data;
    }

    public String instructionsToString() {
        String binary = Integer.toBinaryString(instruction);
        while(binary.length() != 32){
            binary = "0" + binary;
        }
        String data = "Instruction\t" + binary.substring(0, 7) + " " + binary.substring(8, 15) + " " + binary.substring(16, 23) + " " + binary.substring(24, 31) + " " + "\n";
        return data;
    }

    public String registersToString() {
        String data="";
        for(int i = 0; i < 32; i++){
            String binary = Integer.toBinaryString(reg.readFromReg(i));
            while(binary.length() != 32){
                binary = "0" + binary;
            }
            data += "Register " + i + "\t" + binary.substring(0, 7) + " " + binary.substring(8, 15) + " " + binary.substring(16, 23) + " " + binary.substring(24, 31) + " " + "\n";
        }
        return data;
    }

    public String instrMemToString() {
        String data = "";
        for(int i = 0; i < instrMem.getSize(); i+=4){
            String binary = Integer.toBinaryString(instrMem.readFromMem(i, false));
            while(binary.length() != 32){
                binary = "0" + binary;
            }
            // Formatting if/else
            if(i < 16) {
                data += "0x" + Integer.toHexString(i) + "\t\t" + binary.substring(0, 7) + " " + binary.substring(8, 15) + " " + binary.substring(16, 23) + " " + binary.substring(24, 31) + " " + "\n";
            }
            else {
                data += "0x" + Integer.toHexString(i) + "\t" + binary.substring(0, 7) + " " + binary.substring(8, 15) + " " + binary.substring(16, 23) + " " + binary.substring(24, 31) + " " + "\n";
            }
        }
        return data;
    }

    public String statsToString() {
        String data = "Number of cycles\t" + numCycles + "\n";
        data += "Number of adds\t" + numAdd + "\n";
        data += "Number of subs\t" + numSub + "\n";
        data += "Number of ands\t" + numAnd + "\n";
        data += "Number of ors\t" + numOr + "\n";
        data += "Number of slts\t" + numSlt + "\n";
        data += "Number of loads\t" + numLoad + "\n";
        data += "Number of stores\t" + numStore + "\n";
        data += "Number of beqs\t" + numBeq + "\n";
        data += "Number of jumps\t" + numJump + "\n";
        data += "Number of instruction memory reads\t" + (InstrMem.getNumReads()) + "\n";
        data += "Number of data memory reads\t" + (DataMem.getNumReads()) + "\n";
        data += "Number of memory writes\t" + (DataMem.getNumWrites()+InstrMem.getNumWrites()) + "\n";
        data += "Number of alu adds\t" + (alu.getAluAdds()) + "\n";
        data += "Number of alu subs\t" + (alu.getAluSubs()) + "\n";
        data += "Number of alu ands\t" + (alu.getAluAnds()) + "\n";
        data += "Number of alu ors\t" + (alu.getAluOrs()) + "\n";
        return data;
    }

    public String dataMemToString() {
        String data = "";
        for(int i = 0; i < dataMem.getSize(); i+=4){
            String binary = Integer.toBinaryString(dataMem.readFromMem(i, false));
            while(binary.length() != 32){
                binary = "0" + binary;
            }
            if(i < 16) {
                data += "0x" + Integer.toHexString(i) + "\t\t" + binary.substring(0, 7) + " " + binary.substring(8, 15) + " " + binary.substring(16, 23) + " " + binary.substring(24, 31) + " " + "\n";
            }
            else {
                data += "0x" + Integer.toHexString(i) + "\t" + binary.substring(0, 7) + " " + binary.substring(8, 15) + " " + binary.substring(16, 23) + " " + binary.substring(24, 31) + " " + "\n";
            }
        }
        data += "\n\n";
        return data;
    }
}
