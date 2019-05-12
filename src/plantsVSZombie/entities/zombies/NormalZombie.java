package plantsVSZombie.entities.zombies;

import javax.swing.ImageIcon;

public class NormalZombie extends Zombie{

	/**
	 * 默认构造方法
	 */
	public NormalZombie() {
		super();
	}

	/**
	 * 普通僵尸
	 * @param x 起始的x坐标
	 * @param y 起始的y坐标
	 */
	public NormalZombie(int x, int y, int road) {
		super(ZombieType.NormalZobie, x, y, 1, 10, "images/Zombies/NormalZombie/Zombie.gif", 
				"images/Zombies/NormalZombie/ZombieAttack.gif", road);
		this.setDeadImage(new ImageIcon("images/Zombies/ZombieDie.gif").getImage());
		this.setBoomDeadImage(new ImageIcon("images/Zombies/BoomDie.gif").getImage());
		
		this.maxHp = 400;
		this.hp = maxHp;
		
	}
	
	

}
