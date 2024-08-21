#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

void printError(int code, int lineNum, char* line);
bool checkInputForErrors(FILE* inputFile);
void printToBinary(FILE* inputFile, FILE* outputFile);

int BUFFER = 50;

int main(int argc, char* argv[]) {

    if(argc != 3) {
        fputs("Invalid number of command-line arguments\n", stderr);
        exit(1);
    }

    FILE* inFile = fopen(argv[1], "r");
    if(inFile == NULL) {
        fputs("Unable to open input file\n", stderr);
        exit(1);
    }
    if(checkInputForErrors(inFile)) {
        fclose(inFile);
        exit(1);
    }

    FILE* outFile = fopen(argv[2], "wb");
    if(outFile == NULL) {
        fputs("Unable to open output file\n", stderr);
        exit(1);
    }

    printToBinary(inFile, outFile);

    return 0;
}


/****************************************
 * Desc: prints the specified error message
 * Param: code - the ID code for the error
 *              1 = invalid operation
 *              2 = invalid register
 *              3 = invalid format
****************************************/
void printError(int code, int lineNum, char* line) {
    switch(code) {
        case 1:
            printf("Error: invalid operation\n\t%d | %s\n\n", lineNum, line);
            break;
        case 2:
            printf("Error: invalid format\n\t%d | %s\n\n", lineNum, line);
            break;
        case 3:
            printf("Error: invalid register number\n\t%d | %s\n\n", lineNum, line);
            break;
        case 4:
            printf("Error: invalid address\n\t%d | %s\n\n", lineNum, line);
            break;
        default:
            printf("Error: unknown error");
            break;
    }
}

/****************************************
 * Desc: checks assembly input file for errors
 * Param: inputFile - the file being read from
 * Return: returns true if error(s) are found
****************************************/
bool checkInputForErrors(FILE* inputFile) {
    bool errorFound = false;
    char* currLine = (char*)malloc(BUFFER*sizeof(char));
    char* testLine = (char*)malloc(BUFFER*sizeof(char));
    char* errorOutput = (char*)malloc(BUFFER*10*sizeof(char));
    char* instruction = (char*)malloc(5*sizeof(char));
    int rd, rs, rt, immediate;
    int i = 1;

    // Loop through inputFile
    while(fgets(currLine, BUFFER, inputFile)) {
        // remove trailing newline if present
        currLine[strcspn(currLine, "\n")] = 0;
        sscanf(currLine, "%s", instruction);
        if      (strcmp(instruction, "add") == 0) {
            sscanf(currLine, "add $%d, $%d, $%d", &rd, &rs, &rt);
            sprintf(testLine, "add $%d, $%d, $%d", rd, rs, rt);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rd < 0 || rd > 31 || rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "sub") == 0) {
            sscanf(currLine, "sub $%d, $%d, $%d", &rd, &rs, &rt);
            sprintf(testLine, "sub $%d, $%d, $%d", rd, rs, rt);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rd < 0 || rd > 31 || rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "and") == 0) {
            sscanf(currLine, "and $%d, $%d, $%d", &rd, &rs, &rt);
            sprintf(testLine, "and $%d, $%d, $%d", rd, rs, rt);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rd < 0 || rd > 31 || rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "or") == 0) {
            sscanf(currLine, "or $%d, $%d, $%d", &rd, &rs, &rt);
            sprintf(testLine, "or $%d, $%d, $%d", rd, rs, rt);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rd < 0 || rd > 31 || rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "slt") == 0) {
            sscanf(currLine, "slt $%d, $%d, $%d", &rd, &rs, &rt);
            sprintf(testLine, "slt $%d, $%d, $%d", rd, rs, rt);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rd < 0 || rd > 31 || rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "beq") == 0) {
            sscanf(currLine, "beq $%d, $%d, %d", &rs, &rt, &immediate);
            sprintf(testLine, "beq $%d, $%d, %d", rs, rt, immediate);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "lw") == 0) {
            sscanf(currLine, "lw $%d, %d($%d)", &rt, &immediate, &rs);
            sprintf(testLine, "lw $%d, %d($%d)", rt, immediate, rs);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "sw") == 0) {
            sscanf(currLine, "sw $%d, %d($%d)", &rt, &immediate, &rs);
            sprintf(testLine, "sw $%d, %d($%d)", rt, immediate, rs);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if registers are out of range
            else if (rt < 0 || rt > 31 || rs < 0 || rs > 31) {
                printError(3, i, currLine);
                errorFound = true;
            }
        }
        else if (strcmp(instruction, "j") == 0) {
            unsigned int address = 0;
            sscanf(currLine, "j %u", &address);
            sprintf(testLine, "j %u", address);
            // if format is invalid
            if (strcmp(currLine, testLine) != 0) {
                printError(2, i, currLine);
                errorFound = true;
            }
            // if address is greater than (2^27) - 1 
            //  (because address portion of MIPS can only use 26 bits)
            else if (address > 134217727 || address < 0) {
                printError(4, i, currLine);
                errorFound = true;
            }
        }
        // invalid operation
        else {
            printError(1, i, currLine);
            errorFound = true;
        }
        i++;
    }
    // Reset file pointer to start of file
    rewind(inputFile);
    free(currLine);
    free(errorOutput);
    return errorFound;
}


/****************************************
 * Desc: prints the contents of the input assembly file into the 
 *         output file as binary
 * Param: inputFile - the file being read from
 *        outputFile - the file being written to in binary
****************************************/
void printToBinary(FILE* inputFile, FILE* outputFile) {
    char* currLine = (char*)malloc(BUFFER*sizeof(char));
    char* instruction = (char*)malloc(5*sizeof(char));
    unsigned int opCode, rd, rs, rt, shamt, funct;
    unsigned int binLine;
    while(fgets(currLine, BUFFER, inputFile)) {
        sscanf(currLine, "%s", instruction);
        binLine = 0;
        if      (strcmp(instruction, "add") == 0) {
            // Parse line for rd, rs, and rt
            sscanf(currLine, "add $%u, $%u, $%u", &rd, &rs, &rt);
            opCode = 0;
            shamt = 0;
            funct = 0b100000;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | (rd<<11);
            binLine = binLine | (shamt<<6);
            binLine = binLine | funct;
        }
        else if (strcmp(instruction, "sub") == 0) {
            sscanf(currLine, "sub $%u, $%u, $%u", &rd, &rs, &rt);
            opCode = 0;
            shamt = 0;
            funct = 0b100010;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | (rd<<11);
            binLine = binLine | (shamt<<6);
            binLine = binLine | funct;
        }
        else if (strcmp(instruction, "and") == 0) {
            sscanf(currLine, "and $%u, $%u, $%u", &rd, &rs, &rt);
            opCode = 0;
            shamt = 0;
            funct = 0b100100;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | (rd<<11);
            binLine = binLine | (shamt<<6);
            binLine = binLine | funct;
        }
        else if (strcmp(instruction, "or") == 0) {
            sscanf(currLine, "or $%u, $%u, $%u", &rd, &rs, &rt);
            opCode = 0;
            shamt = 0;
            funct = 0b100101;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | (rd<<11);
            binLine = binLine | (shamt<<6);
            binLine = binLine | funct;
        }
        else if (strcmp(instruction, "slt") == 0) {
            sscanf(currLine, "slt $%u, $%u, $%u", &rd, &rs, &rt);
            opCode = 0;
            shamt = 0;
            funct = 0b101010;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | (rd<<11);
            binLine = binLine | (shamt<<6);
            binLine = binLine | funct;
        }
        else if (strcmp(instruction, "beq") == 0) { 	
            int immediate;
            sscanf(currLine, "beq $%u, $%u, %d", &rs, &rt, &immediate);
            // Zero out last 16 bits of immediate to prevent masking from ones from a negative number
            immediate = immediate & 0b00000000000000001111111111111111;
            opCode = 0b000100;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | immediate;
        }
        else if (strcmp(instruction, "lw") == 0) {
            int immediate;
            sscanf(currLine, "lw $%u, %d($%u)", &rt, &immediate, &rs);
            // Zero out last 16 bits of immediate to prevent masking from ones from a negative number
            immediate = immediate & 0b00000000000000001111111111111111;
            opCode = 0b100011;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | immediate;
        }
        else if (strcmp(instruction, "sw") == 0) {
            int immediate;
            sscanf(currLine, "sw $%u, %d($%u)", &rt, &immediate, &rs);
            // Zero out last 16 bits of immediate to prevent masking from ones from a negative number
            immediate = immediate & 0b00000000000000001111111111111111;
            opCode = 0b101011;
            binLine = opCode<<26;
            binLine = binLine | (rs<<21);
            binLine = binLine | (rt<<16);
            binLine = binLine | immediate;
        }
        else if (strcmp(instruction, "j") == 0) {
            unsigned int address = 0;
            sscanf(currLine, "j %u\n", &address);
            opCode = 0b000010;
            binLine = opCode<<26;
            binLine = binLine | address;
        }
        // Should never be reached (checked prior to this stage)
        else {
            printf("Invalid/unsupported instruction type\n");
        }
        // Switch endian
        int b1 = (binLine & 0x000000ff) << 24u;
        int b2 = (binLine & 0x0000ff00) << 8u;
        int b3 = (binLine & 0x00ff0000) >> 8u;
        int b4 = (binLine & 0xff000000) >> 24u;
        binLine = b1 | b2 | b3 | b4;

        // write new binary line to file
        fwrite(&binLine, sizeof(binLine), 1, outputFile);
    }

    free(currLine);
    free(instruction);
}