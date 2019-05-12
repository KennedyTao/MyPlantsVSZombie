package plantsVSZombie.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import plantsVSZombie.config.Config;
import plantsVSZombie.entities.zombies.Zombie;

/**
 * 植物子弹类
 * @author KamHowe
 */
public class Bullet extends Model{
	
	/** 子弹类型*/
	private BulletType type;
	
	/** 攻击力*/
	private int attackPower;
	
	/** 判断子弹是否存在*/
	private boolean isExist;
	
	/** 表示子弹在哪条道路上*/
	private int road;
	
	/**
	 * 构造方法
	 */
	public Bullet() {
		
	}
	
	/**
	 * 带类型的构造方法
	 * @param type 子弹类型
	 */
	public Bullet(int x , int y, BulletType type, int road) {
		
		//初始化子弹图片
		if(type == BulletType.SINGLEPEA || type == BulletType.DOUBLEPEA) {
			gifImage = new ImageIcon("images/Plants/Bullets/PeaShooterBullet.gif").getImage();
			attackPower = 40;
			
			
		}else if(type == BulletType.ICEPEA) {
			gifImage = new ImageIcon("images/Plants/Bullets/SnowPeaShooterBullet.gif").getImage();
			attackPower = 60;
		}
		
		this.road = road;
		this.startPoint = new Point(x, y);
		
		isExist = true;
		length = 20;
		width = 20;
	}

	public BulletType getType() {
		return type;
	}

	public void setType(BulletType type) {
		this.type = type;
	}

	public int getAttractPower() {
		return attackPower;
	}

	public void setAttractPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	
	public int getRoad() {
		return road;
	}

	public void setRoad(int road) {
		this.road = road;
	}

	/**
	 * 判断子弹是否与任何僵尸发生碰撞,并触发死亡
	 * @param zombieList 检查有没有撞到僵尸
	 */
	public void Collide(List<Zombie> zombieList) {
		for(Zombie zombie : zombieList) {
			
			//如果子弹已经发生过碰撞，则失效
			if(this.isDead() == true) {
				break;
			}
			
			//判断是否在同一条道路上
			if(this.road == zombie.getRoad()) {
				
				//如果横向坐标对应
				if(this.startPoint.x + width > zombie.startPoint.x 
						&& this.startPoint.x < zombie.startPoint.x + zombie.width){
					
					//僵尸扣血
					zombie.setHp(zombie.getHp() - this.attackPower);
					
					//判断僵尸是否死亡
					if(zombie.getHp() < 0) {
						zombie.setDead(true);
					}
					
					//子弹失效
					this.setDead(true);
				}
			}
			
		}
	}

	/**
	 * 判断字弹是否越界
	 * @return true:越界 false:没有越界
	 */
	public boolean isOut() {
		if(this.startPoint.x >= Config.WINDOWS_WIDTH) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 向右移动一定的距离
	 * @param distance 移动的距离
	 */
	public void move(int distance) {
		
		this.startPoint.x += distance;
		
		//判断是否越界
		if(this.isOut()) {
			this.setDead(true);
		}
	}
	
	/**
	 * 画出子弹
	 * @param panel 模型的载体
	 * @param g 像素
	 */
	public void draw(JPanel panel, Graphics g) {
		if(!this.isDead()) {
			g.drawImage(gifImage, startPoint.x, startPoint.y, width, length, panel);
		}
	}

}
