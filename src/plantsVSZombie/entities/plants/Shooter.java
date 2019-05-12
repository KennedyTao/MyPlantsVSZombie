package plantsVSZombie.entities.plants;

import java.io.FileNotFoundException;
import java.io.IOException;
import plantsVSZombie.entities.BulletType;

/**
 * 射手类
 * @author KamHowe
 */
public class Shooter extends Plants{
	
	/** 字弹的类型*/
	private BulletType type;	
	
	/** 种植的道路*/
	private int road;
	
	
	public Shooter() {
		super();
	}

	/**
	 * 射手植物的构造方法
	 * @param cooldowntime 冷却时间
	 * @param isCooling 是否在冷却
	 * @param consumption 消耗阳光量
	 * @param selectedImageName 被选择时的图片的路径
	 * @param coolingImageName 冷却时的图片的路径
	 * @param imageName 植物在plant bar的静态图片的路径
	 * @param gifImageName 植物种植后的gif动图
	 * @param attrackPower 攻击力
	 * @param recharge 再次触发时间间隔
	 * @param type 子弹类型
	 * @throws IOException 读写错误
	 * @throws FileNotFoundException 找不到文件 
	 */
	public Shooter(int cooldowntime, boolean isCooling, int consumption, String selectedImageName, String coolingImageName, 
			String imageName, String gifImageName, int attrackPower, int recharge, BulletType type, int road, int cellX, int cellY)
			throws FileNotFoundException, IOException {
		super(cooldowntime, isCooling, consumption, selectedImageName, coolingImageName, imageName, gifImageName, attrackPower,
				recharge, cellX, cellY);
		
		this.type = type;
		this.road = road;
	}
	
	public BulletType getType() {
		return type;
	}

	public void setType(BulletType type) {
		this.type = type;
	}

	public int getRoad() {
		return road;
	}

	public void setRoad(int road) {
		this.road = road;
	}

}
