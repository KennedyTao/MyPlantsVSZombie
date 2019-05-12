package plantsVSZombie.entities.plants;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class WallNut extends Plants{

	/** 坚果剩1/2 生命值后的动图*/
	private Image wallnutHurt;
	
	/** 坚果剩1/4 生命值后的动图*/
	private Image wallnutCry;
	
	/** 坚果的状态, 1表示正常，2 表示残缺， 3 表示快死亡*/
	private int wallnutStatus;
	
	public WallNut() {
		super();
	}
	
	/**
	 * 坚果
	 * @param x x坐标
	 * @param y y坐标
	 * @throws FileNotFoundException 文件路径错误
	 * @throws IOException 读写错误
	 */
	public WallNut(int x, int y, int cellX, int cellY) throws FileNotFoundException, IOException {
		super(15000, true, 50, "images/Plants/WallNut/WallNut_8.png", "images/Plants/WallNut/WallNut_6.png", 
				"images/Plants/WallNut/WallNut_7.png", "images/Plants/WallNut/WallNut.gif", 
				0, 0, cellX, cellY);
		
		this.startPoint = new Point(x, y);
		this.maxHp = 3000;
		this.hp = maxHp;
		
		this.wallnutStatus = 1;
		
		wallnutHurt = new ImageIcon("images/Plants/WallNut/WallNut_2.gif").getImage();
		wallnutCry = new ImageIcon("images/Plants/WallNut/WallNut_3.gif").getImage();
	}

	public Image getWallnutHurt() {
		return wallnutHurt;
	}

	public void setWallnutHurt(Image wallnutHurt) {
		this.wallnutHurt = wallnutHurt;
	}

	public Image getWallnutCry() {
		return wallnutCry;
	}

	public void setWallnutCry(Image wallnutCry) {
		this.wallnutCry = wallnutCry;
	}
	
	public int getWallnutStatus() {
		return wallnutStatus;
	}

	public void setWallnutStatus(int wallnutStatus) {
		this.wallnutStatus = wallnutStatus;
	}

	/**
	 * 重写坚果的draw方法
	 */
	@Override
	public void drawPlant(int x, int y,JPanel panel, Graphics g) {
		if(!this.isDead()) {
			if(this.getWallnutStatus() == 1) {
				g.drawImage(this.getGifImage(), x, y, width, length, null);
				
			}else if(this.getWallnutStatus() == 2) {
				g.drawImage(this.getWallnutHurt(), x, y, width, length, null);

			}else if(this.getWallnutStatus() == 3) {
				g.drawImage(this.getWallnutCry(), x, y, width, length, null);

			}else {
				
			}
		}
	}
}
