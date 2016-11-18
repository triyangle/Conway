/*
 * Created on May 24, 2004
 *
 * Latest update on April 21, 2011
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

// Note that the JComponent is set up to listen for mouse clicks
// and mouse movement.  To achieve this, the MouseListener and
// MousMotionListener interfaces are implemented and there is additional
// code in init() to attach those interfaces to the JComponent.


public class Display extends JComponent implements MouseListener, MouseMotionListener {
	public static final int ROWS = 80;
	public static final int COLS = 100;
	public static Cell[][] cell = new Cell[ROWS][COLS];
	private final int X_GRID_OFFSET = 25; // 25 pixels from left
	private final int Y_GRID_OFFSET = 40; // 40 pixels from top
	private final int CELL_WIDTH = 5;
	private final int CELL_HEIGHT = 5;

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH;
	private final int DISPLAY_HEIGHT;
	private StartButton startStop;
	private boolean paintloop = false;
	private boolean running = true;


	public Display(int width, int height) {
		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		init();
	}


	public void init() {
		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		initCells();

		addMouseListener(this);
		addMouseMotionListener(this);

		// Example of setting up a button.
		// See the StartButton class nested below.
		startStop = new StartButton();
		startStop.setBounds(100, 550, 100, 36);
		add(startStop);
		startStop.setVisible(true);
		repaint();
	}


	public void paintComponent(Graphics g) {
		final int TIME_BETWEEN_REPLOTS = 100; // change to your liking

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		drawButtons();

		if (paintloop) {
			try {
				Thread.sleep(TIME_BETWEEN_REPLOTS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextGeneration();
			repaint();
		}
	}


	public void initCells() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col] = new Cell(row, col);
			}
		}

		cell[0][0].setAlive(true);
		cell[79][99].setAlive(true);
		cell[3][2].setAlive(true);
		cell[36][22].setAlive(true); // sample use of cell mutator method
		cell[36][23].setAlive(true); // sample use of cell mutator method
		cell[36][24].setAlive(true); // sample use of cell mutator method
	}


	public void togglePaintLoop() {
		paintloop = !paintloop;
	}


	public void setPaintLoop(boolean value) {
		paintloop = value;
	}


	void drawGrid(Graphics g) {
		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(X_GRID_OFFSET,
					Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)), X_GRID_OFFSET
					+ COLS * (CELL_WIDTH + 1), Y_GRID_OFFSET
					+ (row * (CELL_HEIGHT + 1)));
		}
		for (int col = 0; col <= COLS; col++) {
			g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET
					+ ROWS * (CELL_HEIGHT + 1));
		}
	}


	void drawCells(Graphics g) {
		// Have each cell draw itself
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				// The cell cannot know for certain the offsets nor the height
				// and width; it has been set up to know its own position, so
				// that need not be passed as an argument to the draw method
				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,
						CELL_HEIGHT, g);
			}
		}
	}


	private void drawButtons() {
		startStop.repaint();
	}


	private void nextGeneration() {

		Cell.calcNeighbors(cell);

		for(int row = 0; row < ROWS; row++) {

			for(int col = 0; col < COLS; col++) {

				if(cell[row][col].getAliveNextTurn() == true) {

					cell[row][col].setAlive(true);

				} else {

				if(cell[row][col].getAliveNextTurn() == false) {

					cell[row][col].setAlive(false);

				}

				}

			}

		}

		repaint();


	}

	public void mouseClicked(MouseEvent arg0) {

		int x = arg0.getX();
		int y = arg0.getY();

		for(int row = 0; row < ROWS; row++) {

			for(int col = 0; col < COLS; col++) {

				int ybottom = Y_GRID_OFFSET + CELL_HEIGHT + (row * (CELL_HEIGHT + 1));
				int xright = X_GRID_OFFSET + CELL_WIDTH + (col * (CELL_WIDTH + 1));

				int xleft = X_GRID_OFFSET + (col * (CELL_WIDTH + 1));
				int ytop = Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1));

				if(((x < xright) && (y < ybottom)) && ((x > xleft) && (y > ytop)) && cell[row][col].getAlive() == false) {

					cell[row][col].setAlive(true);
					repaint();

				} else {

				if(((x < xright) && (y < ybottom)) && ((x > xleft) && (y > ytop)) && cell[row][col].getAlive() == true) {

					cell[row][col].setAlive(false);
					repaint();

				}

				}

			}

		}

		//System.out.println(x);
		//System.out.println(y);

	}


	public void mouseEntered(MouseEvent arg0) {

	}


	public void mouseExited(MouseEvent arg0) {

	}


	public void mousePressed(MouseEvent arg0) {

	}


	public void mouseReleased(MouseEvent arg0) {

	}


	public void mouseDragged(MouseEvent arg0) {

	}


	public void mouseMoved(MouseEvent arg0) {

	}


	private class StartButton extends JButton implements ActionListener {

		StartButton() {
			super("Start");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {

			nextGeneration(); // test the start button

			if(this.getText().equals("Start")) {

				togglePaintLoop();
				setText("Stop");

			} else {

				togglePaintLoop();
				setText("Start");
			}

			repaint();
		}
	}

}

class Neighbor {

	private int x;
	private int y;

	public Neighbor() {

	}

	public Neighbor(int x, int y) {

		this.x = x;
		this.y = y;

	}

	public int getX() {

		return x;
	}

	public int getY() {

		return y;
	}

}
