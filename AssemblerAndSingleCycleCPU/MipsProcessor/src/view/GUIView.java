package view;

import java.awt.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.*;
import java.util.List;
import controller.*;


public class GUIView extends JFrame implements KeyListener{
    private final JLabel message;
    private TextController controller;
    private static final int LINES_IN_TEXT = 4, LINE_TEXT_LENGTHS = 20;
    private float fontSize = 28;
    private JPanel mainPanel, bottomLeft, bottomRight, topLeft, topRight, pcPanel;
    private JTextArea reg, data, instr, stat, pc;

    public GUIView(){
        super("CPU Simulator");

        //this.setLayout(new GridLayout(0, 1));
        this.setSize(400,400);
        message = new JLabel("Press ENTER to run all instructions. Press SPACE to run one at a time.");
        message.setFont(message.getFont().deriveFont(fontSize));
        this.add(message, BorderLayout.NORTH);
        this.addKeyListener(this);
        this.setFocusable(true);

        mainPanel = new JPanel(new GridLayout(2, 3));

        topLeft = new JPanel();
        topLeft.setBorder(BorderFactory.createLineBorder(Color.black));

        bottomLeft = new JPanel();
        bottomLeft.setBorder(BorderFactory.createLineBorder(Color.black));

        topRight = new JPanel();
        topRight.setBorder(BorderFactory.createLineBorder(Color.black));

        bottomRight = new JPanel();
        bottomRight.setBorder(BorderFactory.createLineBorder(Color.black));

        pcPanel = new JPanel();
        pcPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        this.setBackground(Color.DARK_GRAY);

        JLabel registers = new JLabel("Registers");
        //registers.setForeground(Color.white);
        reg = new JTextArea(5, 30);
        reg.setEditable(false);
        reg.setText("No data right now");
        JScrollPane scrollReg = new JScrollPane(reg);
        bottomLeft.add(registers, BorderLayout.NORTH);
        bottomLeft.add(scrollReg, BorderLayout.SOUTH);

        JLabel dataMem = new JLabel("Data Memory");
        //dataMem.setForeground(Color.white);
        data = new JTextArea(5, 30);
        data.setEditable(false);
        data.setText("No data right now");
        JScrollPane scrollData = new JScrollPane(data);
        topRight.add(dataMem, BorderLayout.NORTH);
        topRight.add(scrollData, BorderLayout.SOUTH);


        JLabel instrMem = new JLabel("Instruction Memory");
        //instrMem.setForeground(Color.white);
        instr = new JTextArea(5, 30);
        instr.setEditable(false);
        instr.setText("No data right now");
        JScrollPane scrollInstr = new JScrollPane(instr);
        bottomRight.add(instrMem, BorderLayout.NORTH);
        bottomRight.add(scrollInstr, BorderLayout.SOUTH);

        JLabel stats = new JLabel("Statistics");
        //stats.setForeground(Color.white);
        stat = new JTextArea(5, 30);
        stat.setEditable(false);
        stat.setText("No data right now");
        JScrollPane scrollStat = new JScrollPane(stat);
        topLeft.add(stats, BorderLayout.NORTH);
        topLeft.add(scrollStat, BorderLayout.SOUTH);

        JLabel pcLabel = new JLabel("PC");
        //stats.setForeground(Color.white);
        pc = new JTextArea(5, 30);
        pc.setEditable(false);
        pc.setText("No data right now");
        JScrollPane scrollPc = new JScrollPane(pc);
        pcPanel.add(pcLabel, BorderLayout.NORTH);
        pcPanel.add(pc, BorderLayout.SOUTH);

        mainPanel.add(topLeft);
        mainPanel.add(topRight);
        mainPanel.add(bottomLeft);
        mainPanel.add(bottomRight);
        mainPanel.add(pcPanel);
        this.add(mainPanel, BorderLayout.CENTER);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void setRegisterData(String d){
        reg.setText(d);
    }
    public void setDataMemData(String d){
        data.setText(d);
    }
    public void setInstrMemData(String d){
        instr.setText(d);
    }
    public void setStatsData(String d){
        stat.setText(d);
    }
    public void setPCData(String d){
        pc.setText(d);
    }

    public void registerObserver(TextController c){
        controller = c;
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            controller.processInstructions(2);
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE){
            controller.processInstructions(1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

}