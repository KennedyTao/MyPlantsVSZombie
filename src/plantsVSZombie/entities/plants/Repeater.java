package plantsVSZombie.entities.plants;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import plantsVSZombie.entities.BulletType;

public class Repeater extends Shooter{

	public Repeater() {
		super();
	}

	/**
	 * 双发射手
	 * @param x 初始位置x
	 * @param y 初始位置y
	 * @throws FileNotFoundException 文件查找错误
	 * @throws IOException 读写错误
	 */
	public Repeater(int x, int y, int road, int cellX, int cellY) throws FileNotFoundException, IOException {
		super(7000, true, 200, "images/Plants/Repeater/Repeater_1.png", "images/Plants/Repeater/Repeater_4.png",
				"images/Plants/Repeater/Repeater_3.png", "images/Plants/Repeater/Repeater.gif",
				40, 3000, BulletType.DOUBLEPEA, road, cellX, cellY);
		
		this.startPoint = new Point(x, y);
		this.maxHp = 400;
		this.hp = maxHp;
	}
}
