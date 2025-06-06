package piece;

import chessboard.ChessPanel;

public class Bishop extends Piece{
	int i, diffCol, diffRow;
	
    public Bishop(int color, int col, int row){
    	
        super(color, col, row);

        if(color == ChessPanel.WHITE){
            image = getImage("/pieces/w-bishop");
        }
        else{
            image = getImage("/pieces/b-bishop");
        }
    }
    
    public boolean canMove(int targetCol, int targetRow) {  
    	
    	if(inBoard(targetCol, targetRow)) {
    		diffCol = targetCol - preCol;
    		diffRow = targetRow - preRow;
    		if(Math.abs(diffCol) == Math.abs(diffRow) && diffCol != 0 &&
    				bishopPathCollision(diffCol, diffRow) == true) {
    			
    			return true;
    		}
    	}    	
    	return false;
    }
    
    //method returning true when no other piece is in the bishop's selected target path
    public boolean bishopPathCollision(int diffCol, int diffRow) {
    	int opponentCount = 0;
    	if(diffCol > 0) {
    		if(diffRow > 0) {
    			for( int i = 1; i <= diffCol; i++) {
    				if(!validSquare(preCol + i, preRow + i)) {
    					return false;
    				}
    				
    				if(collisionPiece != null) {
    					opponentCount++;
    				}
    			}
    		}
    		else {
    			for( int i = 1; i <= diffCol; i++) {
    				if(!validSquare(preCol + i, preRow - i)) {
    					return false;
    				}
    				
    				if(collisionPiece != null) {
    					opponentCount++;
    				}
    			}
    		}
    	}
    	else {
    		if(diffRow > 0) {
    			for( int i = 1; i <= -diffCol; i++) {
    				if(!validSquare(preCol - i, preRow + i)) {
    					return false;
    				}
    				
    				if(collisionPiece != null) {
    					opponentCount++;
    				}
    			}
    		}
    		else {
    			for( int i = 1; i <= -diffCol; i++) {
    				if(!validSquare(preCol - i, preRow - i)) {
    					return false;
    				}
    				
    				if(collisionPiece != null) {
    					opponentCount++;
    				}
    			}
    		}
    	}
    	
    	if(opponentCount > 1) return false;
    	
    	return true;
    }

}
