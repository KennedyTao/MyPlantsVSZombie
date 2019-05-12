package plantsVSZombie.entities.plants;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPanel;


public class SunFlower extends Plants{
	
	/** 判断向日葵是否已经生产过未捡去的阳光*/
	private boolean isExistSun;
	
	public JPanel panel;

	/**
	 * 默认构造方法
	 */
	public SunFlower() {
		super();
	}

	/**
	 * 向日葵
	 * @param x 起始x坐标
	 * @param y 起始y坐标
	 * @throws FileNotFoundException 图片路径错误
	 * @throws IOException 图片读取错误
	 */
	public SunFlower(int x, int y, JPanel panel, int cellX, int cellY) throws FileNotFoundException, IOException {
		super(5000, false, 50, "images/Plants/SunFlower/SunFlower_2.png",
				"images/Plants/SunFlower/SunFlower_5.png", "images/Plants/SunFlower/SunFlower_3.png",
				"images/Plants/SunFlower/SunFlower.gif", 0, 10000, cellX, cellY);
		
		this.setStartPoint(new Point(x, y));
		this.maxHp = 300;
		this.hp = maxHp;
		this.isExistSun = false;
		
		this.panel = panel;
		
	}
	
	public boolean isExistSun() {
		return isExistSun;
	}

	public void setExistSun(boolean isExistSun) {
		this.isExistSun = isExistSun;
	}
	
	
	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}
