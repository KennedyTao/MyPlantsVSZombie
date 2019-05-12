package plantsVSZombie.entities.plants;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import plantsVSZombie.entities.BulletType;

public class SnowPea extends Shooter{

	public SnowPea() {
		super();
	}

	/**
	 * 雪花豌豆
	 * @param x x坐标
	 * @param y y坐标
	 * @throws FileNotFoundException 找不到文件
	 * @throws IOException 读取错误
	 */
	public SnowPea(int x, int y, int road, int cellX, int cellY) throws FileNotFoundException, IOException {
		super(8000, true, 175, "images/Plants/SnowPea/SnowPea_1.png", "images/Plants/SnowPea/SnowPea_4.png",
				"images/Plants/SnowPea/SnowPea_3.png", "images/Plants/SnowPea/SnowPea.gif", 
				65, 3000, BulletType.ICEPEA, road, cellX, cellY);
		
		this.startPoint = new Point(x, y);
		this.maxHp = 350;
		this.hp = maxHp;
	}
}
