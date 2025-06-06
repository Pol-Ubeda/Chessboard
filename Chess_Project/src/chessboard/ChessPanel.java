package chessboard;

import piece.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;


public class ChessPanel extends JPanel implements Runnable{
    public static final int Width = 800;
    public static final int Height = 800;
    final int FPS = 60;
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;
    boolean gameOver = false;
    Thread chessThread;
    Board board = new Board();
    Mouse mouse = new Mouse();
    
    boolean canMove;
    boolean yourTurn = true;
    
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activePiece;
    
    public ChessPanel(){
        setPreferredSize(new Dimension(Width,Height));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(pieces, simPieces);
    }

    public void launch(){
        chessThread = new Thread(this);
        chessThread.start();
    }

    public void setPieces(){
        //White Team
        pieces.add(new Pawn(WHITE, 0, 6));
        pieces.add(new Pawn(WHITE, 1, 6));
        pieces.add(new Pawn(WHITE, 2, 6));
        pieces.add(new Pawn(WHITE, 3, 6));
        pieces.add(new Pawn(WHITE, 4, 6));
        pieces.add(new Pawn(WHITE, 5, 6));
        pieces.add(new Pawn(WHITE, 6, 6));
        pieces.add(new Pawn(WHITE, 7, 6));
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Bishop(WHITE, 2, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));

        //Black Team
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 3, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 5, 1));
        pieces.add(new Pawn(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 7, 1));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new King(BLACK, 3, 0));
        pieces.add(new Queen(BLACK, 4, 0));
    }

    public void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target){
        target.clear();
        for(int i = 0; i<source.size(); i++){
            target.add(source.get(i));
        }
    }

    @Override
    public void run(){
        //GAME LOOP
        double drawInterval = 100000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(chessThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime -lastTime)/drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }

    }

    private void update(){
    	
    	//Check if the game is finished
    	if(gameOver == true) return;

    	//Mouse Pressed
    	if(mouse.pressed) {
    		//When no active piece, check if we can pick up valid piece
    		if(activePiece == null) {
    			for(Piece p : simPieces) {
    				if(p.color == currentColor &&
    						p.col == mouse.x/Board.SQUARE_SIZE &&
    						p.row == mouse.y/Board.SQUARE_SIZE) {
    					
    					yourTurn = true;
    					activePiece = p;
    				}
    				if(p.color != currentColor && 
    						p.col == mouse.x/Board.SQUARE_SIZE &&
    						p.row == mouse.y/Board.SQUARE_SIZE) {
    					
    					yourTurn = false;
    				}
    			}
    		}
    		else {
    			hover();
    		}
    	}
    	
    	//Mouse Lifted
    	if(mouse.pressed == false) {
    		
    		//When not clicking mouse, no need to show "not your turn" message
    		yourTurn = true;
    		
    		
    		if(activePiece != null) {
    			
    			//If piece can move to target
    			if(canMove) {
    				
    				//if its a pawn's piece first move, change firstMove attribute
    				if(activePiece instanceof Pawn) {
    					((Pawn) activePiece).firstMove = false;
    					
    					if(activePiece.color == WHITE && ((Pawn) activePiece).row == 0) {
    						((Pawn) activePiece).turnedQueen = true;
    					}
    					if(activePiece.color == BLACK && ((Pawn) activePiece).row == 7) {
    						((Pawn) activePiece).turnedQueen = true;
    					}
    				}
    				
    				activePiece.updatePosition();

    				//If we hit a piece of diff color we eat it and remove it from pieces on board
    	    		if(activePiece.collisionPiece != null) {
    	    			simPieces.remove(activePiece.collisionPiece.getIndex());
    	    			copyPieces(simPieces, pieces);
    	    			
    	    			//Check game over
    	    			if(gameOver(activePiece.collisionPiece)) {
    	    				gameOver = true;
    	    				return;
    	    			}
    	    		}
    				
    	    		currentColor += 1;
    				currentColor%=2;
    				activePiece = null;
    			}    			
    			else{
    				activePiece.resetPosition();
    				activePiece = null;
    			}
    		}
    	}

    }
    
    private void hover() {
    	canMove = false;
    	  	    	   	
    	activePiece.x = mouse.x - Board.HALF_SQUARE_SIZE;
    	activePiece.y = mouse.y - Board.HALF_SQUARE_SIZE;
    	activePiece.col = activePiece.getCol(activePiece.x);
    	activePiece.row = activePiece.getRow(activePiece.y);
    	
    	
    	if(activePiece.canMove(activePiece.col,  activePiece.row)) {
    		canMove = true;
    	}
    }
    
    
    private boolean gameOver(Piece eaten) {
    	if(eaten instanceof King) {
    		return true;    		
    	}
    	return false;
    }

    public void paintComponent(Graphics g){
        super.paintComponent((g));

        //Paint Board
        Graphics2D g2 = (Graphics2D)g;
        board.draw(g2);

        //Paint Pieces
        for(Piece p : simPieces){  	
            p.draw(g2);
        }
        
        //Draw highlighted square in active piece position
        if(activePiece != null) {        	
        	if(canMove) {
        		g2.setColor(Color.white);
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            	g2.fillRect(activePiece.col*Board.SQUARE_SIZE,  activePiece.row*Board.SQUARE_SIZE, 
            			Board.SQUARE_SIZE,  Board.SQUARE_SIZE);
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            	activePiece.draw(g2);
        	} 	
        }
        
        //Show not your turn message when clicking opponent pieces
        if(!yourTurn) {
        	g2.setColor(Color.BLACK);
        	g2.setFont(new Font("Arial", Font.BOLD, 20));
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        	g2.fillRect(330,  335, 150,  40);
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        	
        	g2.setColor(Color.RED);
    		
        	g2.drawString("Not your turn!", 340, 360);
        }
        
        
        //Game over message
        if (gameOver == true) {
        	
        	
        	if(currentColor == WHITE) {
        		
        		g2.setColor(Color.BLACK);
            	g2.setFont(new Font("Arial", Font.BOLD, 50));
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            	g2.fillRect(80,  350, 645,  100);
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            	
            	g2.setColor(Color.WHITE);
        		
            	g2.drawString("WHITE WINS THE GAME!", 98, 410);
        	}
        	else {
        		
        		g2.setColor(Color.WHITE);
            	g2.setFont(new Font("Arial", Font.BOLD, 50));
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            	g2.fillRect(80,  350, 650,  100);
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            	
            	g2.setColor(Color.BLACK);
            	
        		g2.drawString("BLACK WINS THE GAME!", 98, 410);
        	}
        }
    }

    
}
