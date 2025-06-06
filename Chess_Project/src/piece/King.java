package piece;

import chessboard.ChessPanel;

public class King extends Piece{
    public King(int color, int col, int row){
        super(color, col, row);

        if(color == ChessPanel.WHITE){
            image = getImage("/pieces/w-king");
        }
        else{
            image = getImage("/pieces/b-king");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
    	    	
    	if(inBoard(targetCol, targetRow)) {
    		if(Math.abs(targetCol-preCol) + Math.abs(targetRow-preRow) == 1 ||
    				Math.abs(targetCol-preCol) * Math.abs(targetRow-preRow) == 1) {
    			if(validSquare(targetCol, targetRow))	return true;
    		}
    	}    	
    	return false;
    }
}
