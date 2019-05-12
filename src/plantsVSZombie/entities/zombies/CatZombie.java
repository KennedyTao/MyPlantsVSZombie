package plantsVSZombie.entities.zombies;

import javax.swing.ImageIcon;

public class CatZombie extends Zombie{

	/**
	 * 默认构造方法
	 */
	public CatZombie() {
		super();
	}

	/**
	 * 带帽僵尸
	 * @param x 起始x坐标
	 * @param y 起始y坐标
	 */
	public CatZombie(int x, int y, int road) {
		super(ZombieType.CatZombie, x, y, 1, 15, "images/Zombies/CatZombie/ConeheadZombie.gif",
				"images/Zombies/CatZombie/ConeheadZombieAttack.gif", road);
		
		this.setDeadImage(new ImageIcon("images/Zombies/ZombieDie.gif").getImage());
		this.setBoomDeadImage(new ImageIcon("images/Zombies/BoomDie.gif").getImage());
		
		this.maxHp = 650;
		this.hp = maxHp;
	}
	
}
