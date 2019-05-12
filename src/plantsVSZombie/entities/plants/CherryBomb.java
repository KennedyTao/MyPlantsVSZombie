package plantsVSZombie.entities.plants;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CherryBomb extends Plants{
	
	private Image imageBoom;

	public CherryBomb() {
		super();
	}

	/**
	 * 樱桃炸弹
	 * @param x x坐标
	 * @param y y坐标
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public CherryBomb(int x, int y, int cellX, int cellY) throws FileNotFoundException, IOException {
		super(20000, true, 150, "images/Plants/CherryBomb/cherryboom_3.png", "images/Plants/CherryBomb/cherryboom_2.png",
				"images/Plants/CherryBomb/cherryboom.png", "images/Plants/CherryBomb/cherryboom.gif",
				1000, 0, cellX, cellY);
		this.startPoint = new Point(x, y);
		this.maxHp = 500;
		this.hp = maxHp;
		
		imageBoom = new ImageIcon("images/Plants/CherryBomb/Boom.gif").getImage();
	}

	public Image getImageBoom() {
		return imageBoom;
	}

	public void setImageBoom(Image imageBoom) {
		this.imageBoom = imageBoom;
	}
	
	/**
	 * 樱桃爆炸
	 */
	@Override
	public void drawPlant(int x, int y,JPanel panel, Graphics g) {
		if(!this.isDead()) {
			g.drawImage(getGifImage(), x, y, width, length, panel);
			g.drawImage(imageBoom, x, y, width, length, panel);
			
		}
	}
}
