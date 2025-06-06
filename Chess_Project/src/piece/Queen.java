package piece;

import chessboard.ChessPanel;

public class Queen extends Piece{
    public Queen(int color, int col, int row){
        super(color, col, row);

        if(color == ChessPanel.WHITE){
            image = getImage("/pieces/w-queen");
        }
        else{
            image = getImage("/pieces/b-queen");
        }
    }
    
    //queens is basically rook and bishop
    public boolean canMove(int targetCol, int targetRow) {
    	int diffCol = targetCol - preCol;
    	int diffRow = targetRow - preRow;
    	
    	if(inBoard(targetCol, targetRow)) {
    		if(diffCol * diffRow == 0 && diffCol + diffRow != 0) {
    			if(rookPathCollision(diffCol, diffRow) == true) {
    				return true;
    			}
    		}
    		
    		if(Math.abs(diffCol) == Math.abs(diffRow) && diffCol != 0 &&
    				bishopPathCollision(diffCol, diffRow) == true) {
    			
    			return true;
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