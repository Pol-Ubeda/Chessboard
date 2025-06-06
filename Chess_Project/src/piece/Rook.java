package piece;

import chessboard.ChessPanel;

public class Rook extends Piece{
    public Rook(int color, int col, int row){
        super(color, col, row);

        if(color == ChessPanel.WHITE){
            image = getImage("/pieces/w-rook");
        }
        else{
            image = getImage("/pieces/b-rook");
        }
    }
    
    public boolean canMove(int targetCol, int targetRow) {
    	if(inBoard(targetCol, targetRow)) {
    		int diffCol = targetCol - preCol;
    		int diffRow = targetRow - preRow;
    		
    		if(diffCol * diffRow == 0 && diffCol + diffRow != 0) {
    			if(rookPathCollision(diffCol, diffRow) == true) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    public boolean rookPathCollision(int diffCol, int diffRow) {
    	int opponentCount = 0;
    	if(diffCol > 0) {
    		for(int i = 1; i <= diffCol; i++) {
    			if(!validSquare(preCol + i, preRow)) {
    				return false;
    			}
    			if(collisionPiece != null) {
    				opponentCount++;
    			}
    		}
    	}
    	if(diffCol < 0) {
    		for(int i = 1; i <= -diffCol; i++) {
    			if(!validSquare(preCol - i, preRow)) {
    				return false;
    			}
    			if(collisionPiece != null) {
    				opponentCount++;
    			}
    		}
    	}
    	if(diffRow > 0) {
    		for(int i = 1; i <= diffRow; i++) {
    			if(!validSquare(preCol, preRow + i)) {
    				return false;
    			}
    			if(collisionPiece != null) {
    				opponentCount++;
    			}
    		}
    	}
    	if(diffRow < 0) {
    		for(int i = 1; i <= -diffRow; i++) {
    			if(!validSquare(preCol, preRow - i)) {
    				return false;
    			}
    			if(collisionPiece != null) {
    				opponentCount++;
    			}
    		}
    	}
    	
    	if(opponentCount > 1) return false;
    	return true;
    }

}
