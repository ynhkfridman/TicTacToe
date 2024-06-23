package com.fusion.TicTacToe.model;


public class Game {
    private String[][] board;
	private String currentPlayer;
    private boolean gameOver;
    private String winner;
    private int numMoves;
    private static final  String EMPTY_CELL = "";


	public Game() {
        board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
        currentPlayer = "X";
        gameOver = false;
        numMoves = 0;
    }

    public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
		this.gameOver = true;
	}
	

    public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
    
    public boolean getGameOver() {
    	return this.gameOver;
    }
	
    public String[][] getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isTie() {
    	if(isGameOver() && getWinner() == EMPTY_CELL) {
    		return true;
    	}
        return false;
    }


    public boolean makeMove(int row, int col) {
    	numMoves++;
    	if(numMoves == board.length * board[0].length) {
    		setGameOver(true);
    	}   
        if (board[row][col].isEmpty() && !getGameOver()) {
            board[row][col] = String.valueOf(currentPlayer);
            if (checkWin(row, col)) {
                setGameOver(true);
            } else {         	
                currentPlayer = (currentPlayer == "X") ? "O" : "X";
            }
            return true;
        }
        return false;
    }

    public boolean checkWin(int row, int col) {
        if (checkRow(row) || checkColumn(col) || checkDiagonals()) {
        	setWinner(currentPlayer);
        	
        }
        return false;
        
    }

    private boolean checkRow(int row) {
        return board[row][0].equals(String.valueOf(currentPlayer)) &&
               board[row][1].equals(String.valueOf(currentPlayer)) &&
               board[row][2].equals(String.valueOf(currentPlayer));
    }

    private boolean checkColumn(int col) {
        return board[0][col].equals(String.valueOf(currentPlayer)) &&
               board[1][col].equals(String.valueOf(currentPlayer)) &&
               board[2][col].equals(String.valueOf(currentPlayer));
    }

    private boolean checkDiagonals() {
        return (board[0][0].equals(String.valueOf(currentPlayer)) &&
                board[1][1].equals(String.valueOf(currentPlayer)) &&
                board[2][2].equals(String.valueOf(currentPlayer))) ||
               (board[0][2].equals(String.valueOf(currentPlayer)) &&
                board[1][1].equals(String.valueOf(currentPlayer)) &&
                board[2][0].equals(String.valueOf(currentPlayer)));
    }
}