package plantsVSZombie.entities;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 抽象模型类
 * @author KamHowe
 */
public abstract class Model {
	
	/** 模型的起始像素点*/
	public Point startPoint;
	
	/** 模型长度*/
	public int length;
	
	/** 模型宽度*/
	public int width;
	
	/** 模型静态实体*/
	public BufferedImage image;
	
	/** 模型动态*/
	public Image gifImage;
	
	/** 当前生命值*/
	public int hp;
	
	/** 最大生命值*/
	public int maxHp;
	
	/** 判断是否死亡*/
	public boolean isDead;

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Image getGifImage() {
		return gifImage;
	}

	public void setGifImage(Image gifImage) {
		this.gifImage = gifImage;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	/**
	 * 获得模型所在的矩形
	 * @return Rectangle
	 */
	public Rectangle getRectangle() {
		if(getStartPoint() == null) {
			return null;
		}
		return new Rectangle(getStartPoint(), new Dimension(getLength(), getWidth()));
	}
	
	/**
	 * 根据路径名获取图片对象
	 * @param filename 路径名
	 * @return BufferedImage对象
	 * @throws FileNotFoundException 找不到路径
	 * @throws IOException 读写错误
	 */
	public static BufferedImage getBufferImage(String filename) throws FileNotFoundException, IOException {
		BufferedImage image = null;
		image = ImageIO.read(new FileInputStream(filename));
		if(image != null) {
			return image;
		}else {
			return null;
		}
	}
}
