package Algorithms;

import java.util.Arrays;
import UI.CatalogItem;

public class SudokuSolver implements AlgorithmItem{

	int sizeX = 9, sizeY = 9;
	int standardSize = 3;
	int[][][] board;
	private CatalogItem ci;
	
	public SudokuSolver(CatalogItem ci){
		int[][][] boardC =  {{ {0,0}, {8,1}, {0,0}, {0,0}, {0,0}, {9,1}, {7,1}, {4,1}, {3,1} }, { {0,0}, {5,1}, {0,0}, {0,0}, {0,0}, {8,1}, {0,0}, {1,1}, {0,0} }, { {0,0}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0} },
		{ {8,1}, {0,0}, {0,0}, {0,0}, {0,0}, {5,1}, {0,0}, {0,0}, {0,0} }, { {0,0}, {0,0}, {0,0}, {8,1}, {0,0}, {4,1}, {0,0}, {0,0}, {0,0} }, { {0,0}, {0,0}, {0,0}, {3,1}, {0,0}, {0,0}, {0,0}, {0,0}, {6,1} },
		{ {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {7,1}, {0,0} }, { {0,0}, {3,1}, {0,0}, {5,1}, {0,0}, {0,0}, {0,0}, {8,1}, {0,0} }, { {9,1}, {7,1}, {2,1}, {4,1}, {0,0}, {0,0}, {0,0}, {5,1}, {0,0} }};
	
		board = createCopy(boardC);
		this.ci = ci;
		ci.refresh();
	} //[x][y][0=val,1=ss]

	public void run(){
		move(board);
		ci.popMessage("Algorithm Done", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean validMove(int x, int y, int value, int[][][] board){

		//check horizontal
		for(int i = 0; i<sizeX; i++){
			if(i==x)
				continue;
			if(board[i][y][0]==value)
				return false;
		}
		//check vertical
		for(int i =0; i<sizeY; i++){
			if(i==y)
				continue;
			if(board[x][i][0]==value)
				return false;
		}
		//check 3x3
		int posX = 3*(x/3)+1, posY = 3*(y/3)+1;

		for(int i = -1; i<=1; i++){
			for(int j = -1; j<=1; j++){
				try{
					if(posX+i==x && posY+j==y)
						continue;
					if(board[i+posX][j+posY][0]==value)
						return false;
				}catch(ArrayIndexOutOfBoundsException e){};
			}
		}

		return true;
	}

	private boolean move(int[][][] boardCopy){
		for(int i =0; i<sizeX; i++){
			for(int j = 0; j<sizeY; j++){
				if(boardCopy[i][j][0]==0){
					for(int value = 1; value<10; value++){
						boardCopy[i][j][0] = value;
						visualSleep();
						if(validMove(i,j,value, boardCopy) && move(boardCopy)){
							return true;
						}
						boardCopy[i][j][0] = 0;
						visualSleep();
					}
					return false;
				}
			}
		}
		return true;
	}

	private void visualSleep(){
		try{
			ci.refresh();
			Thread.sleep(ci.getSleepTime());	
		}catch(Exception ie){ie.printStackTrace();};
	}

	public int[][][] getBoard(){
		return board;
	}

	private int[][][] createCopy(int[][][] board){
		int[][][] copy = new int[9][9][2];
		for(int i=0; i<9; i++){
			for(int j=0; j<=i;j++){
				copy[i][j][0] = board[i][j][0];
				copy[i][j][1] = board[i][j][1];
				copy[j][i][0] = board[j][i][0];
				copy[j][i][1] = board[j][i][1];
			}
		}
		return copy;
	}

	public void reset(){
		int[][][] boardC =  {{ {0,0}, {8,1}, {0,0}, {0,0}, {0,0}, {9,1}, {7,1}, {4,1}, {3,1} }, { {0,0}, {5,1}, {0,0}, {0,0}, {0,0}, {8,1}, {0,0}, {1,1}, {0,0} }, { {0,0}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0} },
		{ {8,1}, {0,0}, {0,0}, {0,0}, {0,0}, {5,1}, {0,0}, {0,0}, {0,0} }, { {0,0}, {0,0}, {0,0}, {8,1}, {0,0}, {4,1}, {0,0}, {0,0}, {0,0} }, { {0,0}, {0,0}, {0,0}, {3,1}, {0,0}, {0,0}, {0,0}, {0,0}, {6,1} },
		{ {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {7,1}, {0,0} }, { {0,0}, {3,1}, {0,0}, {5,1}, {0,0}, {0,0}, {0,0}, {8,1}, {0,0} }, { {9,1}, {7,1}, {2,1}, {4,1}, {0,0}, {0,0}, {0,0}, {5,1}, {0,0} }};
		
		board = createCopy(boardC);
	}

	private void print(int[][][] board){
		System.out.println("Answer: ");
		for(int i =0; i<sizeX; i++){
			for(int j = 0; j<sizeY; j++){
				System.out.print(board[i][j][0]+" ");
			}
			System.out.println();
		}
	}
}