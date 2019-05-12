package plantsVSZombie.entities.plants;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import plantsVSZombie.entities.BulletType;

public class PeaShooter extends Shooter{

	/**
	 * 默认构造方法
	 */
	public PeaShooter() {
		super();
	}

	
	public PeaShooter(int x, int y, int road, int cellX, int cellY) throws FileNotFoundException, IOException {
		
		super(7000, true, 100,"images/Plants/Shooter/Peashooter_2.png",
				"images/Plants/Shooter/Peashooter_5.png", "images/Plants/Shooter/Peashooter_3.png",
				"images/Plants/Shooter/Peashooter.gif", 40, 3000, BulletType.SINGLEPEA, road, cellX, cellY);
		
		this.startPoint = new Point(x, y);
		this.maxHp = 300;
		this.hp = maxHp;
	}
	
}
