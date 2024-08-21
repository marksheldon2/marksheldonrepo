//package view;

import controller.TextController;
import view.*;
import java.io.*;

public class Main {
    public static void main(String[] args)throws FileNotFoundException{
        int mode = 0;
        GUIView screen = new GUIView();
        TextController test = new TextController("test.bin", mode, screen);
        //the controller waits for a keystroke from the gui
        screen.registerObserver(test);
    }

}