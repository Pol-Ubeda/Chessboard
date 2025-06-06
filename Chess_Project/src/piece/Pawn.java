package piece;

import chessboard.ChessPanel;

public class Pawn extends Piece{
    public boolean firstMove = true;
	public boolean turnedQueen = false;
	
    public Pawn(int color, int col, int row){
        super(color, col, row);

        if(color == ChessPanel.WHITE){
            image = getImage("/pieces/w-pawn");
        }
        else{
            image = getImage("/pieces/b-pawn");
        }
    }
    
    public boolean canMove(int targetCol, int targetRow) {
    	int diffCol = targetCol - preCol;
    	int diffRow = targetRow - preRow;
    	
    	if(inBoard(targetCol, targetRow)) {
    		if(turnedQueen == false) {
    			if(simplePawnCheck(targetCol, targetRow)){
    				return true;
    			}
    		}
    		if(turnedQueen == true) {
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
    	}
    	//System.out.println("simple false");
    	return false;
    }
    
    private boolean simplePawnCheck(int targetCol, int targetRow) {
    	int diffCol = targetCol - preCol;
    	int diffRow = targetRow - preRow;
    	//First we check the possible position in same column as piece
    	if(diffCol == 0) {
    		
    		//This checks if we can move one or two positions in front of us as white piece
    		if(color == ChessPanel.WHITE) {
    			
    			//Here we check for the position in front of us
    			if(targetRow - preRow == -1 && validSquare(targetCol, targetRow)) {
    				//if there's no black piece in front we can move there
    				if(collisionPiece == null)	return true;    				    				
    			}
    			
    			//Here we check if its first move, so we can move 2 pieces in front of us, making sure no piece is in the way
    			if(firstMove == true && targetRow - preRow == -2 && validSquare(targetCol, targetRow)) {
    				if(collisionPiece == null) {
    					
    					if(validSquare(targetCol, targetRow + 1)) {
    						if(collisionPiece == null) {
    							return true;
    						}
    					}
    				}
    			}
    		}
    		
    		//The same for black piece
    		if(color == ChessPanel.BLACK) {
    			
    			//Here we check for the first in front of us
    			if(targetRow - preRow == 1 && validSquare(targetCol, targetRow)) {
    				//if there's no white piece in front we can move there
    				if(collisionPiece == null)	return true;    				    				
    			}
    			
    			//Here we check for two position in front us, making sure no piece is in the middle
    			if(firstMove == true && targetRow - preRow == 2 && validSquare(targetCol, targetRow)) {
    				if(collisionPiece == null && validSquare(targetCol, targetRow - 1)) {
    					if(collisionPiece == null) {
    						return true;
    					}
    				}
    			}
    		}
    	}
    	
    	//Now we check if we can eat diagonals
    	if(Math.abs(diffCol) == 1) {
    		if(color == ChessPanel.WHITE && diffRow == -1) {
    			if(validSquare(targetCol, targetRow)) {
    				//keeping in mind we can only move to diagonal to eat a piece
        			if(collisionPiece != null) {
        				return true;
        			}
        		}
    		}
    		if(color == ChessPanel.BLACK) {
    			if(validSquare(targetCol, targetRow)) {
    				if(collisionPiece != null) {
    					return true;
    				}
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
