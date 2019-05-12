package plantsVSZombie.controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import plantsVSZombie.config.Config;
import plantsVSZombie.config.GameStatus;
import plantsVSZombie.entities.Bullet;
import plantsVSZombie.entities.BulletType;
import plantsVSZombie.entities.PlantBar;
import plantsVSZombie.entities.Sun;
import plantsVSZombie.entities.plants.CherryBomb;
import plantsVSZombie.entities.plants.PeaShooter;
import plantsVSZombie.entities.plants.Plants;
import plantsVSZombie.entities.plants.Repeater;
import plantsVSZombie.entities.plants.SnowPea;
import plantsVSZombie.entities.plants.SunFlower;
import plantsVSZombie.entities.plants.WallNut;
import plantsVSZombie.entities.zombies.Zombie;
import plantsVSZombie.entities.zombies.ZombieFactory;
import plantsVSZombie.entities.zombies.ZombieType;
import plantsVSZombie.view.LoginFrame;
import plantsVSZombie.view.PlantsVSZombiesPanel;

/**
 * 游戏管理
 * @author KamHowe
 * @version 1.00
 */
public class GameController extends MouseAdapter{
	
	/** 游戏界面*/
	public PlantsVSZombiesPanel gamePanel;
	
	//植物栏上的植物
	public SunFlower barSunFlower;
	
	public PeaShooter barPeaShooter;
	
	public Repeater barRepeater;
	
	public SnowPea barSnowPea;
	
	public WallNut barWallNut;
	
	public CherryBomb barCherryBomb;
	
	/**植物栏*/
	PlantBar plantBar;
	
	/**植物栏集合*/
	public List<Plants> barPlantsList;
	
	/**各种植物集合*/
	public Map<String, List<Plants>> plantsMap;
	
	/**子弹集合,int 记录子弹所在的道路*/
	public Map<Integer, List<Bullet>> bulletMap;
	
	/**僵尸集合,int 表示僵尸走的道路*/
	public Map<Integer, List<Zombie>> zombieTeam;
	
	/**阳光量*/
	public int sun;
	
	/**阳光集合*/
	public List<Sun> sunList;
	
	/**鼠标的x坐标*/
	public int mouseX;
	
	/**鼠标的y坐标*/
	public int mouseY;
	
	/** 
	 * 	鼠标的状态，0表示正常状态，1-6 分别表示鼠标
	 *  点击了向日葵，豌豆，双豌豆，雪花豌豆，坚果，炸弹
	 *  7表示在其他界面
	 */
	public int mouseStatus;
	
	/**触发器计时器*/
	public Timer rechargeTimer;
	
	/**游戏计时器*/
	public Timer gameTimer;
	
	/**掉落阳光计时器*/
	public Timer fallingSunTimer;
	
	/**生产僵尸的计时器*/
	public Timer zombieTimer;
	
	/**僵尸难度增加的计时器*/
	public Timer zombieTimer_2;
	
	/**每个网格的状态*/
	public int[][] cellStatus;
	
	/** 游戏状态*/
	public GameStatus gameStatus;
	
	/** 游戏难度 1:easy 2:normal 3:hard */
	public int gameLever;
	
	/** 普通下僵尸起始刷新时间*/
	public int zombieEasyTime;

	/** 普通下刷新僵尸的时间*/
	public int zombieEasyRefresh;
	
	/**难度提升时僵尸起始刷新时间*/
	public int zombieHardTime;
	
	/** 难度提升时刷新僵尸的时间 */
	public int zombieHardRefresh;
	
	/** 本回合分数*/
	public int score;
	
	/** 正在游戏的用户*/
	public String logginguser;
	
	/** 是否已经paint了输的界面*/
	public boolean hasPaint;
	
	
	
	public AudioClip sound;
	
	public GameController() {
		super();
	}

	public GameController(PlantsVSZombiesPanel gamePanel) throws FileNotFoundException, IOException {
		super();
		this.gamePanel = gamePanel;
		
		//初始化植物集合
		plantsMap = gamePanel.getPlantsMap();
		
		//初始化子弹集合
		bulletMap = gamePanel.getBulletMap();
		
		//初始化僵尸集合
		zombieTeam = gamePanel.getZombieTeam();
		
		//初始化阳光量
		sun = gamePanel.getSun();
		
		//初始化阳光集合
		sunList = gamePanel.getSunList();
		
		rechargeTimer = new Timer();
		fallingSunTimer = new Timer();
		zombieTimer = new Timer();
		gameTimer = new Timer();
		zombieTimer_2 = new Timer();
		
		mouseX = 0;
		mouseY = 0;
		mouseStatus = gamePanel.getMouseStatus();
		
		plantBar = gamePanel.getPlantBar();
		barPlantsList = plantBar.getBarPlantsList();
		
		//获取植物栏上的植物
		barSunFlower = (SunFlower)barPlantsList.get(0);
		barPeaShooter = (PeaShooter)barPlantsList.get(1);
		barRepeater = (Repeater)barPlantsList.get(2);
		barSnowPea = (SnowPea)barPlantsList.get(3);
		barWallNut = (WallNut)barPlantsList.get(4);
		barCherryBomb = (CherryBomb)barPlantsList.get(5);
		
		//初始化网格
		cellStatus = gamePanel.getCellStatus();
		
		gameStatus = GameStatus.GAME;
		setGameLever(1);
		
		//初始化分数
		score = 0;
		
		logginguser = null;
		
		hasPaint = false;
		
	}
	
	
	public int getGameLever() {
		return gameLever;
	}

	/**
	 * 改变难度
	 * @param gameLever 游戏难度 1:easy 2:normal 3:hard
	 */
	public void setGameLever(int gameLever) {
		this.gameLever = gameLever;
		
		if(gameLever == 1 || gameLever > 3 || gameLever < 1) {
			//easy
			zombieEasyTime = 15000;
			zombieEasyRefresh = 10000;
			zombieHardTime =  60000;
			zombieHardRefresh = 20000;
			
			//初始分
			score = 300;
			
		}else if(gameLever == 2) {
			//normal
			zombieEasyTime = 15000;
			zombieEasyRefresh = 6000;
			zombieHardTime = 60000;
			zombieHardRefresh = 15000;
			
			//初始分
			score = 500;
			
		}else if(gameLever == 3) {
			//hard
			zombieEasyTime = 15000;
			zombieEasyRefresh = 5000;
			zombieHardTime = 50000;
			zombieHardRefresh = 12000;
			
			//初始分
			score = 800;
		}
	}

	public String getLogginguser() {
		return logginguser;
	}

	public void setLogginguser(String logginguser) {
		this.logginguser = logginguser;
	}

	/**
	 * 主游戏开始
	 */
	public void gameStart() {
		mainGameStart();
	}
	
	/**
	 * bgm
	 */
	public void playSound() {
		File soundFile = new File("music/game.wav");
		try {
			URL url = soundFile.toURI().toURL();
			sound = Applet.newAudioClip(url);
			sound.loop();	
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
				
	}
	
	
	/**
	 * 主游戏触发
	 */
	public void mainGameStart() {
		if(gameStatus == GameStatus.GAME) {
			
			gamePanel.setGameStatus(gameStatus);
			
			//开始触发器
			rechargeStart();
			
			//主游戏为4mins
			gameTimer.schedule(new TimerTask() {
				
				//结束游戏后的触发
				@Override
				public void run() {
					//取消一些计时器
					fallingSunTimer.cancel();
					zombieTimer.cancel();
					zombieTimer_2.cancel();
					
					
					//等待玩家刷完僵尸，或者僵尸赢了
					while(gameStatus == GameStatus.GAME) {
						Set<Integer> zombieSet = zombieTeam.keySet();
						
						boolean isExit = true;
						
						for(int key : zombieSet) {
							
							List<Zombie> zombieList = zombieTeam.get(key);
							
							if(!zombieList.isEmpty()) {
								isExit = false;
								break;
							}
						}
						
						if(isExit) {
							break;
						}
						
					}
					
					//切换游戏状态
					if(gameStatus == GameStatus.GAME) {
						gameStatus = GameStatus.GAMEWIN;
						sound.stop();

						if(gameLever == 1) {
							score += 1000;
							
						}else if(gameLever == 2) {
							score += 2000;
							
						}else if(gameLever == 3) {
							score += 3000;
							
						}
						
						gamePanel.setGameStatus(gameStatus);
						
					}else {
						
						gameStatus = GameStatus.GAMEOVER;
						sound.stop();
						gamePanel.setGameStatus(gameStatus);
						gamePanel.repaint();
					}
					
					
					//取消计时器
					rechargeTimer.cancel();
					fallingSunTimer.cancel();
					
					if(!hasPaint) {
						//切换界面计时器
						new Timer().schedule(new TimerTask() {
							
							@Override
							public void run() {
								
								LoginFrame frame = new LoginFrame();
								frame.getLoginPanel().setLogingUser(logginguser);
								
								if(logginguser != null) {
									frame.getLoginPanel().rewriteScore(score);
									frame.getLoginPanel().setGameStatus(GameStatus.SCORE);
									frame.getLoginPanel().initReader();
									frame.getLoginPanel().repaint();
									frame.getLoginPanel().setLogin(true);
								}else {
									frame.getLoginPanel().setGameStatus(GameStatus.FUNCTION);
								}
								
								frame.setVisible(true);
								//隐藏掉游戏界面的Frame组件
								JOptionPane.getFrameForComponent(gamePanel).dispose();
							}
						}, 5000);
					}
					
				}
			}, 60000 * 4);
			
			
		}
	}
	
	/**
	 * 开启触发器
	 */
	public void rechargeStart() {
		
		//开启其他计时器
		productZombie(zombieTimer);
		productSun(fallingSunTimer);
		
		rechargeTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				//触发监听
				zombieStatusListener();
				sunStatusListener();
				plantsStatusListener();
				bulletStatusListener();
				plantsBarListener();
				
				//update panel的数据
				updateModelsInPanel();
				
				//刷新屏幕
				gamePanel.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}, 100, 100);
	}
	
	
	/**
	 * 游戏输了的触发
	 */
	public void gameOver() {
		sound.stop();
		gameStatus = GameStatus.GAMEOVER;
		
		rechargeTimer.cancel();
		fallingSunTimer.cancel();
		gameTimer.cancel();
		
		gamePanel.setGameStatus(gameStatus);
		gamePanel.repaint();
		
		hasPaint = true;
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				LoginFrame frame = new LoginFrame();
				frame.getLoginPanel().setLogingUser(logginguser);
				
				if(logginguser != null) {
					
					frame.getLoginPanel().rewriteScore(score);
					frame.getLoginPanel().setGameStatus(GameStatus.SCORE);
					frame.getLoginPanel().initReader();
					
					frame.getLoginPanel().repaint();
					frame.getLoginPanel().setLogin(true);
					
				}else {
					frame.getLoginPanel().setGameStatus(GameStatus.FUNCTION);
				}
				
				frame.setVisible(true);
				//隐藏掉游戏界面的Frame组件
				JOptionPane.getFrameForComponent(gamePanel).dispose();
			}
		}, 5000);
	}

	
	/**
	 * 对僵尸状态进行监听，判断
	 */
	public void zombieStatusListener() {
		Set<Integer> zombieSet = zombieTeam.keySet();
		
		zombieSet.forEach((key) -> {
			List<Zombie> zombieList = zombieTeam.get(key);
			
			zombieList.forEach((zombie) -> {
				if(zombie.hp <= 0) {
					//判断僵尸是否死亡
					zombie.setDead(true);
					
				}else {
					
					//判断僵尸是否胜利
					if(zombie.startPoint.x <= Config.WIDTH_LINE_ZERO) {
						gameOver();
						return;
					}
					
					
					//判断僵尸是否和植物碰上
					Set<String> plantsSet = plantsMap.keySet();
					
					plantsSet.forEach((plantKey) -> {
						List<Plants> plantsList = plantsMap.get(plantKey);
						
						plantsList.forEach((plant) -> {
							
							//如果僵尸遇上了植物
							if(plant.getStartPoint().getX() + plant.getWidth() >= zombie.getStartPoint().getX()
									&& plant.getStartPoint().getX() <= zombie.getStartPoint().getX() 
									&& getPlantsRoad(plant) == zombie.getRoad()) {
								
								//僵尸状态为攻击
								zombie.setAttack(true);
								
								//攻击一次植物
								zombie.zombieAttack(plant);
								
								return;
								
							}
						});
					});
					if(!zombie.isAttack()) {
						zombie.move();
					}
				}
				
			});
			
			//移除已经真正死亡的僵尸
			if(!zombieList.isEmpty()) {
				for(int i = 0; i < zombieList.size(); i++) {
					if(zombieList.get(i).isRealDead()) {
						zombieList.remove(i);
						i--;
						break;
					}
				}
			}
		});
	}
	

	/**
	 * 对植物状态进行监听，判断
	 */
	public void plantsStatusListener() {
		Set<String> plantsSet = plantsMap.keySet();
		
		if(plantsSet.isEmpty()) {
			return;
		}
		
		plantsSet.forEach((key) -> {
			List<Plants> plantsList = plantsMap.get(key);
			
			if(plantsList.isEmpty()) {
				return;
			}
			
			plantsList.forEach((plant) -> {
				
				//判断植物是否死亡
				if(plant.getHp() <= 0) {
					plant.setDead(true);
					
				}else {
					
					//向日葵
					if(key.equals("sunFlower")) {
						
						SunFlower sunFlower = (SunFlower)plant;
						
						//如果到触发事件
						if(plant.getRecharge() <= 0 && !sunFlower.isExistSun()) {
							
							sunFlower.setExistSun(true);
							sunList.add(new Sun(sunFlower));
							
							plant.setRecharge(15000);
							
						}else if(sunFlower.isExistSun()) {
							//如果有阳光，就不冷却了
							
						}else {
							plant.setRecharge(plant.getRecharge() - 100);
						}
					}
					
					//豌豆射手
					if(key.equals("peaShooter")) {
						
						if(plant.getRecharge() <= 0) {
							PeaShooter peaShooter = (PeaShooter)plant;
							
							if(hasZombie(peaShooter.getRoad())) {
								Bullet bullet = new Bullet(peaShooter.startPoint.x + Config.CELL_WIDTH * 3 / 2, 
										peaShooter.startPoint.y + 24, BulletType.SINGLEPEA, peaShooter.getRoad());
								
								//加入到子弹集合中
								List<Bullet> bulletList = bulletMap.get(peaShooter.getRoad());
								bulletList.add(bullet);
								
								bulletMap.put(peaShooter.getRoad(), bulletList);
								
								plant.setRecharge(3000);
							}
						}else {
							plant.setRecharge(plant.getRecharge() - 100);
						}
					}
					
					if(key.equals("repeater")) {
						
						if(plant.getRecharge() <= 0) {
							Repeater repeater = (Repeater)plant;
							
							if(hasZombie(repeater.getRoad())) {
								Bullet bullet = new Bullet(repeater.startPoint.x + Config.CELL_WIDTH * 3 / 2, 
										repeater.startPoint.y + 24, BulletType.SINGLEPEA, repeater.getRoad());
								
								Bullet bullet2 = new Bullet(repeater.startPoint.x + Config.CELL_WIDTH , 
										repeater.startPoint.y + 24, BulletType.DOUBLEPEA, repeater.getRoad());
								
								//加入到子弹集合中
								List<Bullet> bulletList = bulletMap.get(repeater.getRoad());
								bulletList.add(bullet);
								bulletList.add(bullet2);
								
								bulletMap.put(repeater.getRoad(), bulletList);
								
								plant.setRecharge(3000);
							}
						}else {
							plant.setRecharge(plant.getRecharge() - 100);
						}
						
					}
					
					if(key.equals("snowPea")) {
						
						if(plant.getRecharge() <= 0) {
							SnowPea snowPea = (SnowPea)plant;
							
							if(hasZombie(snowPea.getRoad())) {
								Bullet bullet = new Bullet(snowPea.startPoint.x + Config.CELL_WIDTH * 3 / 2, 
										snowPea.startPoint.y + 24, BulletType.ICEPEA, snowPea.getRoad());
								
								//加入到子弹集合中
								List<Bullet> bulletList = bulletMap.get(snowPea.getRoad());
								bulletList.add(bullet);
								
								bulletMap.put(snowPea.getRoad(), bulletList);
								
								plant.setRecharge(3000);
							}
						}else {
							plant.setRecharge(plant.getRecharge() - 100);
						}
					}
					
					if(key.equals("wallNut")) {
						WallNut wallNut = (WallNut)plant;
						if(wallNut.getHp() <= wallNut.getMaxHp() / 4) {
							wallNut.setWallnutStatus(3);
							
						}else if(wallNut.getHp() <= wallNut.getMaxHp() / 2) {
							wallNut.setWallnutStatus(2);
						}else {
							wallNut.setWallnutStatus(1);
						}
					}
					
					if(key.equals("cherryBomb")) {
						//如果樱桃还活着，触发它的效果
						if(!plant.isDead()) {
							CherryBomb cherryBoom = (CherryBomb)plant;
							
							//获取爆炸中心
							int boomX = cherryBoom.getStartPoint().x + cherryBoom.width / 2;
							int boomY = cherryBoom.getStartPoint().y + cherryBoom.length / 2;
							
							//计算僵尸到圆心的距离是否在半径之内
							Set<Integer> zombieSet = zombieTeam.keySet();
							
							//遍历僵尸
							zombieSet.forEach((road) -> {
								List<Zombie> zombieList = zombieTeam.get(road);
								
								zombieList.forEach((zombie) -> {
									int zombieX = zombie.getStartPoint().x + zombie.getWidth() / 2;
									int zombieY = zombie.getStartPoint().y + zombie.getLength() / 2;
									
									//如果在爆炸范围内
									if((Math.pow((zombieX - boomX), 2) + Math.pow(zombieY - boomY, 2)) 
											<= Math.pow(Config.CELL_HEIGHT * 1.5, 2)) {
										
										zombie.setHp(zombie.getHp() - cherryBoom.getAttrackPower());
										
										//如果僵尸的生命值因此为0 了
										if(zombie.getHp() <= 0) {
											zombie.setBoomDead(true);
										}
										
									}
									
								});
							});
							
							plant.setDead(true);
						}
					}
				}
				
			});
			
			//删除已死亡的植物
			if(!plantsList.isEmpty()) {
				for(int i = 0; i<plantsList.size(); i++) {
					if(plantsList.get(i).isDead()) {
						
						//判断僵尸是否和植物碰上
						Set<Integer> zombieSet = zombieTeam.keySet();
						
						for(Integer zombiekey : zombieSet) {
							List<Zombie>zombieList = zombieTeam.get(zombiekey);
							
							for(Zombie zombie : zombieList) {
								
								//如果僵尸遇上了植物
								if(plantsList.get(i).getStartPoint().getX() + plantsList.get(i).getWidth() >= zombie.getStartPoint().getX()
										&& plantsList.get(i).getStartPoint().getX() <= zombie.getStartPoint().getX() 
										&& getPlantsRoad(plantsList.get(i)) == zombie.getRoad()) {
									
									//僵尸状态为攻击
									zombie.setAttack(false);
									
								}
							}
						}
						
						//恢复土地状态
						cellStatus[getPlantsRoad(plantsList.get(i)) - 1][getPlantsPosition(plantsList.get(i)) - 1] = 0;
						
						plantsList.remove(i);
						i--;
						break;
					}
				}
			}
		});
	}
	

	/**
	 * 判断指定的的道路上有没有僵尸
	 * @param road 指定的道路
	 * @return boolean
	 */
	public boolean hasZombie(int road) {
		List<Zombie> zombieList = zombieTeam.get(road);
		if(zombieList.isEmpty()) {
			return false;
		}
		return true ;
	}

	
	/**
	 * 对子弹状态进行监听，判断
	 */
	public void bulletStatusListener() {
		
		Set<Integer> bulletSet = bulletMap.keySet();
		
		bulletSet.forEach((key) -> {
	
			List<Bullet> bulletList = bulletMap.get(key);
			
			if(bulletList.isEmpty()) {
				return;
			}
			
			bulletList.forEach((bullet) -> {
				
				//判断子弹是否越界
				if(bullet.isOut()) {
					bullet.setDead(true);
				}
				
				if(!bullet.isDead()) {
					
					//判断子弹是否撞到僵尸
					Set<Integer> zombieSet = zombieTeam.keySet();
					
					//遍历僵尸
					zombieSet.forEach((road) -> {
						List<Zombie> zombieList = zombieTeam.get(road);
						
						bullet.Collide(zombieList);
					});
				}
				
				if(!bullet.isDead()) {
					bullet.move(50);
				}
			});
			
			//移除失效的子弹
			if(!bulletList.isEmpty()) {
				for(int i = 0; i < bulletList.size(); i++) {
					if(bulletList.get(i).isDead()) {
						bulletList.remove(i);
						i--;
						break;
					}
				}
			}
		});
	}
	
	
	/**
	 * 阳光状态的监听
	 */
	public void sunStatusListener() {
		if(!sunList.isEmpty()) {
			for(int i = 0; i<sunList.size(); i++) {
				if(sunList.get(i).isDead()) {
					
					if(sunList.get(i).getSunFlower() != null) {
						sunList.get(i).getSunFlower().setExistSun(false);
					}
					
					sunList.remove(i);
					i--;
					break;
				}
			}
		}
	}
	
	/**
	 * 每隔一定时间就生产阳光
	 * @param sunTimer
	 */
	public void productSun(Timer sunTimer) {
		
		//指定首次隔5秒，之后隔15秒生产阳光
		sunTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Sun sun = new Sun();
				sunList.add(sun);
			}
		}, 5000, 15000);
	}
	
	/**
	 * 每隔一定的时间就生产僵尸
	 * @param zombieTimer
	 */
	public void productZombie(Timer zombieTimer) {
		
		//指定首次隔15秒，之后隔6秒生产僵尸
		zombieTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Zombie zombie = ZombieFactory.randomZombie();
				int road = zombie.getRoad();
				List<Zombie> zombieList = zombieTeam.get(road);
				zombieList.add(zombie);
				zombieTeam.put(road, zombieList);
				
			}
		}, zombieEasyTime, zombieEasyRefresh);
		
		//在1min后刷更多的僵尸
		zombieTimer_2.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Zombie zombie = ZombieFactory.randomRoadZombie(ZombieType.CatZombie);
				int road = zombie.getRoad();
				List<Zombie> zombieList = zombieTeam.get(road);
				zombieList.add(zombie);
				zombieTeam.put(road, zombieList);
			}
		}, zombieHardTime, zombieHardRefresh);
	}
	
	/**
	 * 植物冷却监听
	 */
	public void plantsBarListener() {
		barPlantsList.forEach((plant) -> {
			if(plant.isCooling()) {
				plant.setCoolingtime(plant.getCoolingtime() - 500/10);
				
				if(plant.getCoolingtime() <= 0) {
					plant.setCooling(false);
				}
			}
		});
	}
	
	/**
	 * 更新panel中的植物模型
	 */
	public void updateModelsInPanel() {
		gamePanel.setPlantsMap(plantsMap);
		gamePanel.setZombieTeam(zombieTeam);
		gamePanel.setBulletMap(bulletMap);
		gamePanel.setSunList(sunList);
		gamePanel.setSun(sun);
		gamePanel.setCellStatus(cellStatus);
		gamePanel.setPlantBar(plantBar);
		gamePanel.mouseX = mouseX;
		gamePanel.mouseY = mouseY;
		gamePanel.setMouseStatus(mouseStatus);
		gamePanel.setGameStatus(gameStatus);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		//监听植物栏
		plantsBarClicked(e);
		
		//监听土地
		lineClicked(e);
		
		//监听阳光
		sunClicked(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		
		//改变鼠标位置
		mouseX = e.getX();
		mouseY = e.getY();
		gamePanel.mouseX = mouseX;
		gamePanel.mouseY = mouseY;
		
	}
	
	
	/**
	 * 植物栏的鼠标点击监听
	 * @param e 鼠标事件
	 */
	public void plantsBarClicked(MouseEvent e) {
		
		if(barSunFlower.getPlantsInPlantsBarRectangle().contains(e.getPoint())) {
			
			if(gamePanel.getMouseStatus() == 0 && !barSunFlower.isCooling() && sun >= barSunFlower.getConsumption()) {
				//改变游戏panel的鼠标状态
				mouseStatus = 1;
				
				//减少阳光量
				sun -= barSunFlower.getConsumption();
				
				//设置为冷却
				barSunFlower.setCooling(true);
				
				//重置冷却时间
				barSunFlower.setCoolingtime(barSunFlower.getCooldowntime());
				
			}else if(gamePanel.getMouseStatus() == 1) {
				//反选
				mouseStatus = 0;
				
				sun += barSunFlower.getConsumption();
				
				//设置为冷却好
				barSunFlower.setCooling(false);
				
				//重置冷却时间
				barSunFlower.setCoolingtime(0);
				
			}else {
				
			}
			
		}else if(barPeaShooter.getPlantsInPlantsBarRectangle().contains(e.getPoint())) {
			
			if(gamePanel.getMouseStatus() == 0  && !barPeaShooter.isCooling() 
					&& sun >= barPeaShooter.getConsumption()) {
				
				//改变游戏panel的鼠标状态
				mouseStatus = 2;
				
				//减少阳光量
				sun -= barPeaShooter.getConsumption();
				
				//设置为冷却
				barPeaShooter.setCooling(true);
				barPeaShooter.setCoolingtime(barPeaShooter.getCooldowntime());
				
			}else if(gamePanel.getMouseStatus() == 2) {
				//反选
				mouseStatus = 0;
				
				//增加阳光量
				sun += barPeaShooter.getConsumption();
				
				//设置为冷却好
				barPeaShooter.setCooling(false);
				barPeaShooter.setCoolingtime(0);
				
			}else {
				
			}
			
		}else if(barRepeater.getPlantsInPlantsBarRectangle().contains(e.getPoint())) {
			
			if(gamePanel.getMouseStatus() == 0 && !barRepeater.isCooling() 
					&& sun >= barRepeater.getConsumption()) {
				//改变游戏panel的鼠标状态
				mouseStatus = 3;
				
				//减少阳光量
				sun -= barRepeater.getConsumption();
				
				//设置为冷却
				barRepeater.setCooling(true);
				barRepeater.setCoolingtime(barRepeater.getCooldowntime());
				
			}else if(gamePanel.getMouseStatus() == 3) {
				//反选
				mouseStatus = 0;
				
				//增加阳光量
				sun += barRepeater.getConsumption();
				
				//设置为冷却好
				barRepeater.setCooling(false);
				barRepeater.setCoolingtime(0);
				
			}else {
				
			}
			
		}else if(barSnowPea.getPlantsInPlantsBarRectangle().contains(e.getPoint())) {
			
			if(gamePanel.getMouseStatus() == 0 && !barSnowPea.isCooling() 
					&& sun >= barSnowPea.getConsumption()) {
				//改变游戏panel的鼠标状态
				mouseStatus = 4;
				
				//减少阳光量
				sun -= barSnowPea.getConsumption();
				
				//设置为冷却
				barSnowPea.setCooling(true);
				barSnowPea.setCoolingtime(barSnowPea.getCooldowntime());
				
			}else if(gamePanel.getMouseStatus() == 4) {
				//反选
				mouseStatus = 0;
				
				//增加阳光量
				sun += barSnowPea.getConsumption();
				
				//设置为冷却好
				barSnowPea.setCooling(false);
				barSnowPea.setCoolingtime(0);
				
			}else {
				
			}
			
		}else if(barWallNut.getPlantsInPlantsBarRectangle().contains(e.getPoint())) {
			
			if(gamePanel.getMouseStatus() == 0 && !barWallNut.isCooling() 
					&& sun >= barWallNut.getConsumption()) {
				//改变游戏panel的鼠标状态
				mouseStatus = 5;
				
				//减少阳光量
				sun -= barWallNut.getConsumption();
				
				//设置为冷却
				barWallNut.setCooling(true);
				barWallNut.setCoolingtime(barWallNut.getCooldowntime());
				
			}else if(gamePanel.getMouseStatus() == 5) {
				//反选
				mouseStatus = 0;
				
				//增加阳光量
				sun += barWallNut.getConsumption();
				
				//设置为冷却好
				barWallNut.setCooling(false);
				barWallNut.setCoolingtime(0);
				
			}else {
				
			}
			
		}else if(barCherryBomb.getPlantsInPlantsBarRectangle().contains(e.getPoint())) {
			
			if(gamePanel.getMouseStatus() == 0 && !barCherryBomb.isCooling() 
					&& sun >= barCherryBomb.getConsumption()) {
				//改变游戏panel的鼠标状态
				mouseStatus = 6;
				
				//减少阳光量
				sun -= barCherryBomb.getConsumption();
				
				//设置为冷却
				barCherryBomb.setCooling(true);
				barCherryBomb.setCoolingtime(barCherryBomb.getCooldowntime());
				
			}else if(gamePanel.getMouseStatus() == 6) {
				//反选
				mouseStatus = 0;
				
				//减少阳光量
				sun += barCherryBomb.getConsumption();
				
				//设置为冷却
				barCherryBomb.setCooling(false);
				barCherryBomb.setCoolingtime(0);
			}
			
		}else {
			
		}
	}
	
	
	/**
	 * 阳光的鼠标监听
	 * @param e 鼠标事件
	 */
	public void sunClicked(MouseEvent e) {
		
		if(gamePanel.getMouseStatus() == 0) {
			if(!sunList.isEmpty()) {
				for(Sun unpickSun : sunList) {
					if(unpickSun.getRectangle().contains(e.getPoint())) {
						sun += 50;
						unpickSun.setDead(true);
					}
				}
			}
		}
	}
	
	
	/**
	 * 种植植物格子的监听 
	 * @param e 鼠标事件
	 */
	public void lineClicked(MouseEvent e) {
		
		// 遍历网格
		int i = 0;
			
		for(int height = Config.HEIGHT_LINE_ZERO; 
					height <= Config.HEIGHT_LINE_ZERO + Config.CELL_HEIGHT * (Config.PLANT_HEIGHT - 1); 
					height += Config.CELL_HEIGHT) {
			
			int j = 0;
			for(int width = Config.WIDTH_LINE_ZERO; width <= Config.WIDTH_LINE_ZERO + Config.CELL_WIDTH * (Config.PLANT_WIDTH - 1);
					width += Config.CELL_WIDTH) {
				
				//如果选中了植物且没有植物已经种植在这里
				if(new Rectangle(new Point(width, height),new Dimension(Config.CELL_WIDTH, Config.CELL_HEIGHT)).contains(e.getPoint()) && 
						cellStatus[i][j] == 0 && gamePanel.getMouseStatus() != 0) {
					try {
						if(mouseStatus == 1) {
							SunFlower sunFlower = new SunFlower(width + Config.CELL_WIDTH / 8, height + Config.CELL_HEIGHT - 80, gamePanel, i, j);
							
							//把新建的植物加入植物map
							List<Plants> sunFlowerList = plantsMap.get("sunFlower");
							sunFlowerList.add(sunFlower);
							cellStatus[i][j] = 1;
							
						}else if(mouseStatus == 2) {
							PeaShooter peaShooter = new PeaShooter(width + Config.CELL_WIDTH / 8, height + Config.CELL_HEIGHT - 80, i + 1, i, j);
							
							//把新建的植物加入植物map
							List<Plants> peaShooterList = plantsMap.get("peaShooter");
							peaShooterList.add(peaShooter);
							cellStatus[i][j] = 1;
							
						}else if(mouseStatus == 3) {
							Repeater repeater = new Repeater(width + Config.CELL_WIDTH / 8, height + Config.CELL_HEIGHT - 80, i + 1, i, j);

							//把新建的植物加入植物map
							List<Plants> repeaterList = plantsMap.get("repeater");
							repeaterList.add(repeater);
							cellStatus[i][j] = 1;
							
						}else if(mouseStatus == 4) {
							SnowPea snowPea = new SnowPea(width + Config.CELL_WIDTH / 8, height + Config.CELL_HEIGHT - 80, i + 1, i, j);

							//把新建的植物加入植物map
							List<Plants> snowPeaList = plantsMap.get("snowPea");
							snowPeaList.add(snowPea);
							cellStatus[i][j] = 1;
							
						}else if(mouseStatus == 5) {
							WallNut wallNut = new WallNut(width + Config.CELL_WIDTH / 8, height + Config.CELL_HEIGHT - 80, i, j);

							//把新建的植物加入植物map
							List<Plants> wallNutList = plantsMap.get("wallNut");
							wallNutList.add(wallNut);
							cellStatus[i][j] = 1;
							
						}else if(mouseStatus == 6) {
							CherryBomb cherryBomb = new CherryBomb(width + Config.CELL_WIDTH / 8, height + Config.CELL_HEIGHT - 80, i, j);
							
							//把新建的植物加入植物map
							List<Plants> cherryBombList = plantsMap.get("cherryBomb");
							cherryBombList.add(cherryBomb);
							cellStatus[i][j] = 1;
							
						}else {
							
						}
						
						mouseStatus = 0;
						
					}catch (Exception e1) {
						System.out.println(e.toString());
					}
				}
				j++;
			}
			i++;
		}
	}
	
	
	/**
	 * 获取植物所在的道路
	 * @param plant
	 * @return int
	 */
	public int getPlantsRoad(Plants plant) {
		int y = plant.getStartPoint().y;
		int i = 1;
		for(int height = Config.HEIGHT_LINE_ZERO; 
				height <= Config.HEIGHT_LINE_ZERO + Config.CELL_HEIGHT * Config.PLANT_HEIGHT; 
				height += Config.CELL_HEIGHT) {
			if(y > height && y < height + Config.CELL_HEIGHT) {
				break;
			}
			i++;
		}
		if(i >= 1 && i <= 5) {
			return i;
		}else {
			return 0;
		}
	}
	
	
	/**
	 * 返回植物横坐标的位置
	 * @param plant
	 * @return int
	 */
	public int getPlantsPosition(Plants plant) {
		int x = plant.getStartPoint().x;
		int i = 1;
		for(int width = Config.WIDTH_LINE_ZERO; width <= Config.WIDTH_LINE_ZERO + Config.CELL_WIDTH * (Config.PLANT_WIDTH - 1);
				width += Config.CELL_WIDTH) {
			if(x > width && x < width + Config.CELL_WIDTH) {
				break;
			}
			i++;
		}
		if(i >= 1 && i <= 9) {
			return i;
		}else {
			return 0;
		}
	}
}
