package plantsVSZombie.entities.zombies;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import plantsVSZombie.config.Config;
import plantsVSZombie.entities.Model;
import plantsVSZombie.entities.plants.Plants;

public class Zombie extends Model{
	
	/** 僵尸种类*/
	private ZombieType type;
	
	/** 僵尸速度*/
	private int speed;
	
	/** 攻击力*/
	private int attackPower;
	
	/** 是否死亡*/
	private boolean isDead;
	
	/** 是否被炸死*/
	private boolean isBoomDead;
	
	/** 是否在攻击*/
	private boolean isAttack;
	
	/** 死亡时的gif图*/
	private Image deadImage;
	
	/** 被炸死时的gif图*/
	private Image boomDeadImage;
	
	/** 吃植物时的gif图*/
	private Image attackImage;
	
	/** 僵尸走的道路*/
	private int road;
	
	/** 判断僵尸是否播放完死亡效果*/
	private boolean isRealDead;
	
	
	/**
	 * 僵尸的构造方法
	 * @param type 僵尸类型
	 * @param speed 僵尸的速度
	 * @param attackPower 僵尸的攻击力
	 * @param gifImageName 僵尸行动的gif图
	 * @param attackImageName 僵尸攻击的gif图
	 * @param road 僵尸走的道路
	 */
	public Zombie(ZombieType type, int x, int y, int speed, int attackPower, String gifImageName, String attackImageName, int road) {
		super();
		this.type = type;
		this.speed = speed;
		this.attackPower = attackPower;
		this.startPoint = new Point(x, y);
		
		//加载图片
		this.setGifImage(new ImageIcon(gifImageName).getImage());
		this.attackImage = new ImageIcon(attackImageName).getImage();
		this.deadImage = new ImageIcon("images/Zombies/ZombieDie.gif").getImage();
		this.boomDeadImage = new ImageIcon("images/Zombies/BoomDie.gif").getImage();
		
		this.setDead(false);
		this.road = road;
		
		this.isBoomDead = false;
		this.isAttack = false;
		this.isRealDead = false;
		
		this.width = 75;
		this.length = 130;
	}

	/**
	 * 默认构造方法
	 */
	public Zombie() {
		super();
	}

	public ZombieType getType() {
		return type;
	}

	public void setType(ZombieType type) {
		this.type = type;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public Image getDeadImage() {
		return deadImage;
	}

	public void setDeadImage(Image deadImage) {
		this.deadImage = deadImage;
	}

	public Image getBoomDeadImage() {
		return boomDeadImage;
	}

	public void setBoomDeadImage(Image boomDeadImage) {
		this.boomDeadImage = boomDeadImage;
	}

	public Image getAtackImage() {
		return attackImage;
	}

	public void setAttackImage(Image attackImage) {
		this.attackImage = attackImage;
	}

	
	public int getRoad() {
		return road;
	}

	public void setRoad(int road) {
		this.road = road;
	}

	public boolean isBoomDead() {
		return isBoomDead;
	}

	public void setBoomDead(boolean isBoomDead) {
		this.isBoomDead = isBoomDead;
	}

	public boolean isAttack() {
		return isAttack;
	}

	public void setAttack(boolean isAttack) {
		this.isAttack = isAttack;
	}

	public boolean isRealDead() {
		return isRealDead;
	}

	public void setRealDead(boolean isRealDead) {
		this.isRealDead = isRealDead;
	}

	/**
	 * 向左移动僵尸
	 * @param distance 向左移动的距离
	 */
	public void move(int distance) {
		if(this.getStartPoint().getX() - distance > Config.WIDTH_LINE_ZERO - 100) {
			this.startPoint = new Point(this.getStartPoint().x - distance, this.getStartPoint().y);
			
		}
	}
	
	/**
	 * 根据僵尸的速度向左移动僵尸
	 */
	public void move() {
		if(this.getStartPoint().getX() - speed > Config.WIDTH_LINE_ZERO - 100) {
			this.startPoint = new Point(this.getStartPoint().x - speed, this.getStartPoint().y);
			
		}
	}
	
	/**
	 * 绘出僵尸死亡的动画
	 * @param panel 模型的载体
	 * @param g 像素
	 */
	public void drawDeathImage(JPanel panel, Graphics g) {
		g.drawImage(deadImage, this.startPoint.x, this.startPoint.y, panel);
	}

	/**
	 * 绘出僵尸被炸死的动画
	 * @param panel 模型的载体
	 * @param g 像素
	 */
	public void drawBoomDeathImage(JPanel panel, Graphics g) {
		g.drawImage(boomDeadImage, this.startPoint.x, this.startPoint.y, panel);
	}
	
	/**
	 * 绘出僵尸移动的动画
	 * @param panel 模型的载体
	 * @param g 像素
	 */
	public void drawZombie(JPanel panel, Graphics g) {
		g.drawImage(getGifImage(), this.startPoint.x, this.startPoint.y, panel);
	}

	/**
	 * 绘出僵尸攻击的动画
	 * @param panel 模型的载体
	 * @param g 像素
	 */
	public void drawZombieAttack(JPanel panel, Graphics g) {
		g.drawImage(attackImage, this.startPoint.x, this.startPoint.y, panel);
	}
	
	/**
	 * 根据僵尸的状态画出它的动作
	 * @param panel 模型的载体
	 * @param g 像素
	 */
	public void drawMe(JPanel panel, Graphics g) {
		if(isBoomDead) {
			drawBoomDeathImage(panel, g);
			this.isRealDead = true;
		}else if(isDead) {
			drawDeathImage(panel, g);
			this.isRealDead = true;
		}else if(isAttack) {
			drawZombieAttack(panel, g);
		}else {
			drawZombie(panel, g);
		}
	}
	
	/**
	 * 僵尸对一个植物攻击
	 * @param plant
	 */
	public void zombieAttack(Plants plant) {
		plant.hp -= this.attackPower;
	}
}
