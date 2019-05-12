package plantsVSZombie.entities.zombies;

import java.util.Random;

import plantsVSZombie.config.Config;

/**
 * 僵尸生产工厂
 * @author KamHowe
 */
public class ZombieFactory {
	
	/**
	 * 在指定的道路上生产一只指定类型的僵尸
	 * @param road 道路
	 * @param type 僵尸类型
	 * @return 一只僵尸
	 */
	public static Zombie initZombie(int road, ZombieType type) {
		if(type == ZombieType.NormalZobie) {
			Zombie zombie =  new NormalZombie(Config.WIDTH_LINE_ZERO + 10 * Config.CELL_WIDTH, 
					Config.HEIGHT_LINE_ZERO + Config.CELL_HEIGHT * (road - 1) - 15, road);
			
			return zombie;
			
		}else if(type == ZombieType.CatZombie) {
			Zombie zombie = new CatZombie(Config.WIDTH_LINE_ZERO + 10 * Config.CELL_WIDTH, 
					Config.HEIGHT_LINE_ZERO + Config.CELL_HEIGHT * (road - 1) - 30, road);
			
			return zombie;
		}
		return null;
	}
	
	/**
	 * 随机生产一只僵尸
	 * @return 一只僵尸
	 */
	public static Zombie randomZombie() {
		Random random = new Random();
		int road = random.nextInt(5) + 1;
		int typeNum = random.nextInt(2) + 1;
		ZombieType type = null;
		
		if(typeNum == 1) {
			type = ZombieType.NormalZobie;
			
		}else if(typeNum == 2) {
			type = ZombieType.CatZombie;
			
		}
		
		return ZombieFactory.initZombie(road, type);

	}
	
	/**
	 * 在随机道路上生产一只指定类型的僵尸
	 * @param type 僵尸类型
	 * @return 一只僵尸
	 */
	public static Zombie randomRoadZombie(ZombieType type) {
		Random random = new Random();
		int road = random.nextInt(5) + 1;
		return ZombieFactory.initZombie(road, type);
	}
	
	/**
	 * 在指定道路上生产一只随机类型的僵尸
	 * @param road 道路
	 * @return 一只僵尸
	 */
	public static Zombie ramdomTypeZombie(int road) {
		Random random = new Random();
		int typeNum = random.nextInt(2) + 1;
		ZombieType type = null;
		
		if(typeNum == 1) {
			type = ZombieType.NormalZobie;
			
		}else if(typeNum == 2) {
			type = ZombieType.CatZombie;
			
		}
		return ZombieFactory.initZombie(road, type);
	}
	
	
}
