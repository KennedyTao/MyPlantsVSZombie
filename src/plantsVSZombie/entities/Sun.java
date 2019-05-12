package plantsVSZombie.entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import plantsVSZombie.config.Config;
import plantsVSZombie.entities.plants.SunFlower;

/**
 * 阳光类
 * @author KamHowe
 */
public class Sun extends Model{
	
	/** 属于的向日葵*/
	private SunFlower sunFlower;

	/** 冷却时间*/
	public static final int FALLING_SUN_COOL = 15;
	
	/** 起始下落位置(如果是植物产出的则为植物的中心点)*/
	private Point startPlace;
	
	/** 停止地点*/
	private Point stopPlace;
	
	/** 阳光动态图*/
	private Image sunImage;
	
	/**
	 * 自然掉落的阳光
	 */
	public Sun() {
		Random random = new Random();
		
		//随机产生掉落的终点
		int stopY = random.nextInt(Config.CELL_HEIGHT * 4) + Config.HEIGHT_LINE_ZERO - Config.CELL_HEIGHT;
		
		int stopX = random.nextInt(Config.CELL_WIDTH * 5) + Config.WIDTH_LINE_ZERO;
		
		stopPlace = new Point(stopX, stopY);
		startPlace = new Point(stopX, 0);
		
		this.hp = 0;
		this.maxHp = 0;
		this.isDead = false;
		this.width = 50;
		this.length = 50;
		
		sunImage = new ImageIcon("images/OtherModels/Sun/Sun.gif").getImage();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				//阳光下落
				while(getStartPlace().y < getStopPlace().y) {
					setStartPlace(new Point(getStartPlace().x, getStartPlace().y + 1));
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	/**
	 * 植物产生的阳光, 先判断植物是否已经产生过还没被捡去的阳光
	 */
	public Sun(SunFlower sunFlower) {
		
		this.sunFlower = sunFlower;
		
		int x = sunFlower.startPoint.x + 30;
		int y = sunFlower.startPoint.y - 50;
		stopPlace = new Point(x, y);
		startPlace = new Point(x, y);
		
		sunImage = new ImageIcon("images/OtherModels/Sun/Sun.gif").getImage();
		
		this.hp = 0;
		this.maxHp = 0;
		this.isDead = false;
		this.width = 50;
		this.length = 50;
		
	}
	

	public Point getStartPlace() {
		return startPlace;
	}

	public SunFlower getSunFlower() {
		return sunFlower;
	}

	public void setSunFlower(SunFlower sunFlower) {
		this.sunFlower = sunFlower;
	}

	public void setStartPlace(Point startPlace) {
		this.startPlace = startPlace;
	}

	public Point getStopPlace() {
		return stopPlace;
	}

	public void setStopPlace(Point stopPlace) {
		this.stopPlace = stopPlace;
	}
	
	/**
	 * 绘出阳光
	 */
	public void draw(JPanel panel, Graphics g) {
		if(!this.isDead()) {
			g.drawImage(sunImage, startPlace.x, startPlace.y + Config.CELL_HEIGHT -10, 50, 50, panel);
		}
	}

	@Override
	public Rectangle getRectangle() {
		if(getStartPlace() == null) {
			return null;
		}
		return new Rectangle(getStartPlace().x , getStartPlace().y + Config.CELL_HEIGHT -10, getWidth(), getLength());
	}
}
