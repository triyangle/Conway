/*
 * Created on Dec 1, 2004
 * Last update: June 24, 2010
 */

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	private int myX, myY; // x,y position on grid
	private boolean myAlive; // alive (true) or dead (false)
	private int myNeighbors; // count of neighbors with respect to x,y
	private boolean myAliveNextTurn; // Used for state in next iteration
	private Color myColor; // Based on alive/dead rules
	private final Color DEFAULT_ALIVE = Color.ORANGE;
	private final Color DEFAULT_DEAD = Color.GRAY;

	public Cell(int x, int y) {
		this(x, y, false, Color.GRAY);
	}

	public Cell(int row, int col, boolean alive, Color color) {
		myAlive = alive;
		myColor = color;
		myX = col;
		myY = row;
	}

	public boolean getAlive() {
		return myAlive;
	}

	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}

	public Color getColor() {
		return myColor;
	}

	public void setAlive(boolean alive) {
		if (alive) {
			setAlive(true, DEFAULT_ALIVE);
		} else {
			setAlive(false, DEFAULT_DEAD);
		}
	}

	public void setAlive(boolean alive, Color color) {
		myColor = color;
		myAlive = alive;
	}

	public void setAliveNextTurn(boolean alive) {
		myAliveNextTurn = alive;
	}

	public boolean getAliveNextTurn() {

		return myAliveNextTurn;
	}

	public void setColor(Color color) {
		myColor = color;
	}

	public int getNeighbors() {
		return myNeighbors;
	}

	public static void calcNeighbors(Cell[][] cell) {

		for(int row = 0; row < Display.ROWS; row++) {

			for(int col = 0; col < Display.COLS; col++) {

				int aliveNeighbors = 0;

				Neighbor [] neighbors = new Neighbor[8];

				if(row == 0 && col == 0) {

					neighbors[0] = new Neighbor(1,0);
					neighbors[1] = new Neighbor(0,1);
					neighbors[2] = new Neighbor(1,1);
					neighbors[3] = new Neighbor(99,0);
					neighbors[4] = new Neighbor(99,1);
					neighbors[5] = new Neighbor(0,79);
					neighbors[6] = new Neighbor(1,79);
					neighbors[7] = new Neighbor(99,79);

				} else {

				if(row == 0 && col == 99) {

					neighbors[0] = new Neighbor(98,0);
					neighbors[1] = new Neighbor(98,1);
					neighbors[2] = new Neighbor(99,1);
					neighbors[3] = new Neighbor(0,0);
					neighbors[4] = new Neighbor(0,1);
					neighbors[5] = new Neighbor(79,0);
					neighbors[6] = new Neighbor(99,79);
					neighbors[7] = new Neighbor(98,79);

				} else {

				if(row == 79 && col == 0) {

					neighbors[0] = new Neighbor(0,78);
					neighbors[1] = new Neighbor(1,78);
					neighbors[2] = new Neighbor(1,79);
					neighbors[3] = new Neighbor(0,0);
					neighbors[4] = new Neighbor(1,0);
					neighbors[5] = new Neighbor(99,0);
					neighbors[6] = new Neighbor(99,78);
					neighbors[7] = new Neighbor(99,79);

				} else {

				if(row == 79 && col == 99) {

					neighbors[0] = new Neighbor(99,78);
					neighbors[1] = new Neighbor(98,78);
					neighbors[2] = new Neighbor(98, 79);
					neighbors[3] = new Neighbor(0,0);
					neighbors[4] = new Neighbor(98,0);
					neighbors[5] = new Neighbor(99,0);
					neighbors[6] = new Neighbor(0,78);
					neighbors[7] = new Neighbor(0,79);

				} else {

				if(row == 0 && col != 0 && col != 99) {

					neighbors[0] = new Neighbor((col-1),row);
					neighbors[1] = new Neighbor((col+1),row);
					neighbors[2] = new Neighbor((col-1),(row+1));
					neighbors[3] = new Neighbor(col,(row+1));
					neighbors[4] = new Neighbor((col+1),(row+1));
					neighbors[5] = new Neighbor((col-1),79);
					neighbors[6] = new Neighbor(col,79);
					neighbors[7] = new Neighbor((col+1),79);

				} else {

				if(row == 79 && col != 0 && col != 99) {

					neighbors[0] = new Neighbor((col-1),row);
					neighbors[1] = new Neighbor((col+1),row);
					neighbors[2] = new Neighbor((col-1),(row-1));
					neighbors[3] = new Neighbor(col,(row-1));
					neighbors[4] = new Neighbor((col+1),(row-1));
					neighbors[5] = new Neighbor((col-1),0);
					neighbors[6] = new Neighbor(col,0);
					neighbors[7] = new Neighbor((col+1),0);

				} else {

				if(col == 0 && row != 0 && row != 99) {

					neighbors[0] = new Neighbor(col,(row-1));
					neighbors[1] = new Neighbor(col,(row+1));
					neighbors[2] = new Neighbor((col+1),(row-1));
					neighbors[3] = new Neighbor((col+1),row);
					neighbors[4] = new Neighbor((col+1),(row+1));
					neighbors[5] = new Neighbor(99,(row-1));
					neighbors[6] = new Neighbor(99,row);
					neighbors[7] = new Neighbor(99,(row+1));

				} else {

				if(col == 99 && row != 0 && row != 99) {

					neighbors[0] = new Neighbor(col,(row-1));
					neighbors[1] = new Neighbor(col,(row+1));
					neighbors[2] = new Neighbor((col-1),(row-1));
					neighbors[3] = new Neighbor((col-1),row);
					neighbors[4] = new Neighbor((col-1),(row+1));
					neighbors[5] = new Neighbor(0,(row-1));
					neighbors[6] = new Neighbor(0,row);
					neighbors[7] = new Neighbor(0,(row+1));

				} else {

					neighbors[0] = new Neighbor((col-1),(row-1));
					neighbors[1] = new Neighbor(col,(row-1));
					neighbors[2] = new Neighbor((col+1),(row-1));
					neighbors[3] = new Neighbor((col-1),row);
					neighbors[4] = new Neighbor((col+1),row);
					neighbors[5] = new Neighbor((col-1),(row+1));
					neighbors[6] = new Neighbor(col,(row+1));
					neighbors[7] = new Neighbor((col+1),(row+1));

				}
				}
				}
				}
				}
				}
				}
				}

				for(Neighbor neighbor:neighbors) {

					if(cell[neighbor.getY()][neighbor.getX()].getAlive() == true) {

						aliveNeighbors++;

					}

				}

				if(cell[row][col].getAlive() == true) {

					if(aliveNeighbors <= 1 || aliveNeighbors >= 4) {

						cell[row][col].setAliveNextTurn(false);

					} else {

						cell[row][col].setAliveNextTurn(true);

					}

				} else {

				if(cell[row][col].getAlive() == false) {

					if(aliveNeighbors == 3) {

						cell[row][col].setAliveNextTurn(true);

					} else {

						cell[row][col].setAliveNextTurn(false);

					}

				}

				}

			}

		}

	}




	public void draw(int x_offset, int y_offset, int width, int height,
			Graphics g) {
		// I leave this understanding to the reader
		int xleft = x_offset + 1 + (myX * (width + 1));
		int xright = x_offset + width + (myX * (width + 1));
		int ytop = y_offset + 1 + (myY * (height + 1));
		int ybottom = y_offset + height + (myY * (height + 1));
		Color temp = g.getColor();

		g.setColor(myColor);
		g.fillRect(xleft, ytop, width, height);
	}

}
