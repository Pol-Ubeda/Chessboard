package piece;

import chessboard.ChessPanel;

public class Knight extends Piece {
    public Knight(int color, int col, int row){
        super(color, col, row);

        if(color == ChessPanel.WHITE){
            image = getImage("/pieces/w-knight");
        }
        else{
            image = getImage("/pieces/b-knight");
        }
    }
    
    public boolean canMove(int targetCol, int targetRow) {
    	
    	if(inBoard(targetCol, targetRow)) {
    		System.out.println("target: " + targetCol + "  " + targetRow);
    		if(Math.abs( (targetCol - preCol) * (targetRow - preRow) ) == 2) {    			
    			if(validSquare(targetCol, targetRow)) {
    				System.out.println("reached");
    				return true;    				
    			}
    				
    		}
    	}
    	
    	return false;
    }

}
