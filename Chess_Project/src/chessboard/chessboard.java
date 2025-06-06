package chessboard;

import javax.swing.*;

public class chessboard{
    public static void main(String[] args){
        JFrame frame = new JFrame("Chessboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        ChessPanel chessPanel = new ChessPanel();
        frame.add(chessPanel);
        frame.pack();

        frame.setVisible(true);

        chessPanel.launch();
    }
}