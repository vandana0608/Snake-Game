import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class snakegame extends JPanel implements KeyListener, ActionListener {
	private static final long serialVersionUID = -4699006574969748184L;

	private int[] xLength, yLength;

	private int[] enemyX = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475,
			500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850 };
	private int[] enemyY = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500,
			525, 550, 575, 600, 625 };

	private ImageIcon titleImage, upMouth, rightMouth, downMouth, leftMouth, snakeBody, enemyImage;

	private boolean up, right, down, left, gameOver;

	private Timer timer;
	private Random random = new Random();

	private final int delay = 100;
	private int length, moves, score, x = random.nextInt(34), y = random.nextInt(23);

	public snakegame() {
		titleImage = new ImageIcon(snakegame.class.getResource("/assets/snaketitle.jpg"));
		upMouth = new ImageIcon(snakegame.class.getResource("/assets/upmouth.png"));
		rightMouth = new ImageIcon(snakegame.class.getResource("/assets/rightmouth.png"));
		downMouth = new ImageIcon(snakegame.class.getResource("/assets/downmouth.png"));
		leftMouth = new ImageIcon(snakegame.class.getResource("/assets/leftmouth.png"));
		snakeBody = new ImageIcon(snakegame.class.getResource("/assets/snakeimage.png"));
		enemyImage = new ImageIcon(snakegame.class.getResource("/assets/enemy.png"));
		xLength = new int[750];
		yLength = new int[750];
		up = right = down = left = gameOver = false;
		moves = score = 0;
		length = 3;

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		if (moves == 0) {
			xLength[0] = 100;
			xLength[1] = 75;
			xLength[2] = 50;

			yLength[0] = yLength[1] = yLength[2] = 100;
		}

		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);

		titleImage.paintIcon(this, g, 25, 11);

		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);

		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);

		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Scores: " + score, 780, 30);

		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length: " + length, 780, 50);

		rightMouth.paintIcon(this, g, xLength[0], yLength[0]);

		for (int i = 0; i < length; i++) {
			if (i == 0 && up) {
				upMouth.paintIcon(this, g, xLength[i], yLength[i]);
			}
			if (i == 0 && right) {
				rightMouth.paintIcon(this, g, xLength[i], yLength[i]);
			}
			if (i == 0 && down) {
				downMouth.paintIcon(this, g, xLength[i], yLength[i]);
			}
			if (i == 0 && left) {
				leftMouth.paintIcon(this, g, xLength[i], yLength[i]);
			}
			if (i != 0) {
				snakeBody.paintIcon(this, g, xLength[i], yLength[i]);
			}
		}

		if ((enemyX[x] == xLength[0] && enemyY[y] == yLength[0])) {
			score++;
			length++;
			x = random.nextInt(34);
			y = random.nextInt(23);
		}
		enemyImage.paintIcon(this, g, enemyX[x], enemyY[y]);

		for (int i = 1; i < length; i++) {
			if (xLength[i] == xLength[0] && yLength[i] == yLength[0]) {
				up = right = down = left = false;
				gameOver = true;
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("GAME OVER", 300, 300);
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("SPACE TO RESTART", 350, 340);
			}
		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if (right) {
			for (int i = length - 1; i >= 0; i--)
				yLength[i + 1] = yLength[i];

			for (int i = length; i >= 0; i--) {
				if (i == 0)
					xLength[i] = xLength[i] + 25;
				else
					xLength[i] = xLength[i - 1];

				if (xLength[i] > 850)
					xLength[i] = 25;
			}
			repaint();
		}

		if (left) {
			for (int i = length - 1; i >= 0; i--)
				yLength[i + 1] = yLength[i];

			for (int i = length; i >= 0; i--) {
				if (i == 0)
					xLength[i] = xLength[i] - 25;
				else
					xLength[i] = xLength[i - 1];

				if (xLength[i] < 25)
					xLength[i] = 850;
			}
			repaint();
		}

		if (up) {
			for (int i = length - 1; i >= 0; i--)
				xLength[i + 1] = xLength[i];

			for (int i = length; i >= 0; i--) {
				if (i == 0)
					yLength[i] = yLength[i] - 25;
				else
					yLength[i] = yLength[i - 1];

				if (yLength[i] < 75)
					yLength[i] = 625;
			}
			repaint();
		}

		if (down) {
			for (int i = length - 1; i >= 0; i--)
				xLength[i + 1] = xLength[i];

			for (int i = length; i >= 0; i--) {
				if (i == 0)
					yLength[i] = yLength[i] + 25;
				else
					yLength[i] = yLength[i - 1];

				if (yLength[i] > 625)
					yLength[i] = 75;
			}
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			moves = 0;
			score = 0;
			length = 3;
			gameOver = false;
			repaint();
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !gameOver) {
			moves++;
			right = true;
			if (!left)
				right = true;
			else {
				right = false;
				left = true;
			}
			up = false;
			down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT && !gameOver) {
			moves++;
			left = true;
			if (!right)
				left = true;
			else {
				left = false;
				right = true;
			}
			up = false;
			down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP && !gameOver) {
			moves++;
			up = true;
			if (!down)
				up = true;
			else {
				up = false;
				down = true;
			}
			left = false;
			right = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN && !gameOver) {
			moves++;
			down = true;
			if (!up)
				down = true;
			else {
				down = false;
				up = true;
			}
			right = false;
			left = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

	@Override
	public void keyTyped(KeyEvent e) {


	}
}
