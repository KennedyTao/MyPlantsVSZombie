package plantsVSZombie.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import plantsVSZombie.config.Config;
import plantsVSZombie.config.GameStatus;
import plantsVSZombie.entities.Bullet;
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

public class PlantsVSZombiesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/** 游戏背景图*/
	public BufferedImage imageBackground;
	
	/** 植物栏图*/
	public BufferedImage imagePlantsBar;
	
	/** 僵尸胜利图*/
	public BufferedImage imageZombieWin;
	
	/** 奖杯图片*/
	public BufferedImage winImage;
	
	/** 当前的游戏状态*/
	public GameStatus gameStatus;
	
	/** 当前游戏的分数*/
	public int gameScore;
	
	//植物栏上的植物
	public SunFlower barSunFlower;
	
	public PeaShooter barPeaShooter;
	
	public Repeater barRepeater;
	
	public SnowPea barSnowPea;
	
	public WallNut barWallNut;
	
	public CherryBomb barCherryBomb;
	
	/** 记录每个植物格子的状态*/
	public int[][] cellStatus;
	
	/** 
	 * 	鼠标的状态，0表示正常状态，1-6 分别表示鼠标
	 *  点击了向日葵，豌豆，双豌豆，雪花豌豆，坚果，炸弹
	 */
	public int mouseStatus;
	
	/** 判断需不需要paint选择的植物*/
	public boolean isPaintSelectPlant;
	
	/** 鼠标的x坐标*/
	public int mouseX;
	
	/** 鼠标的y坐标*/
	public int mouseY;
	
	/** 植物栏的植物*/
	public PlantBar plantBar;
	
	/** 各种植物集合*/
	public Map<String, List<Plants>> plantsMap;
		
	/** 子弹集合,int 记录子弹所在的道路*/
	public Map<Integer, List<Bullet>> bulletMap;
		
	/** 僵尸集合,int 表示僵尸走的道路*/
	public Map<Integer, List<Zombie>> zombieTeam;
		
	/** 阳光量*/
	public int sun;
		
	/** 阳光集合*/
	public List<Sun> sunList;
	
	/**
	 * 默认构造方法
	 */
	public PlantsVSZombiesPanel() {
		
		//设定大小
		setSize(Config.WINDOWS_WIDTH, Config.WINDOWS_HEIGHT);
		mouseStatus = 0;
		cellStatus = new int[5][9];
		isPaintSelectPlant = false;
		
		//初始化模型
		initModels();
		
		cellStatus = new int[5][9];
		
		//初始化网格状态
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 9; j++) {
				cellStatus[i][j] = 0;
			}
		}
		
		//加载各种图片
		try {
			plantBar = new PlantBar(this);
			imageBackground = ImageIO.read(new FileInputStream("images/OtherModels/Background/MainBackground.jpg"));
			imagePlantsBar = ImageIO.read(new FileInputStream("images/OtherModels/Background/plantsBar_2.png"));
			imageZombieWin = ImageIO.read(new FileInputStream("images/OtherModels/ZombieWin.png"));
			winImage = ImageIO.read(new FileInputStream("images/Panels/win_1.png"));
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
//			e.printStackTrace();
		}
		
	}

	public int getMouseStatus() {
		return mouseStatus;
	}

	public void setMouseStatus(int mouseStatus) {
		this.mouseStatus = mouseStatus;
	}

	public int[][] getCellStatus() {
		return cellStatus;
	}

	public void setCellStatus(int[][] cellStatus) {
		this.cellStatus = cellStatus;
	}

	public boolean isPaintSelectPlant() {
		return isPaintSelectPlant;
	}

	public void setPaintSelectPlant(boolean isPaintSelectPlant) {
		this.isPaintSelectPlant = isPaintSelectPlant;
	}

	public PlantBar getPlantBar() {
		return plantBar;
	}

	public void setPlantBar(PlantBar plantBar) {
		this.plantBar = plantBar;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getGameScore() {
		return gameScore;
	}

	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	@Override
	public void paintComponent(Graphics g) {
		
		if(gameStatus == GameStatus.GAME) {
			drawMainGame(g);
			
		}else if(gameStatus == GameStatus.GAMEOVER) {
			g.drawImage(imageZombieWin, 0, 0, Config.WINDOWS_WIDTH, Config.WINDOWS_HEIGHT, this);
			
		}else if(gameStatus == GameStatus.GAMEWIN){
			g.drawImage(winImage, Config.WINDOWS_WIDTH / 4, Config.WINDOWS_HEIGHT / 4, 
					Config.WINDOWS_WIDTH / 2, Config.WINDOWS_HEIGHT / 2, this);
		}else {
			
		}
		
	}
	
	/**
	 * 初始化植物的数据结构
	 */
	public void initModels() {
		//初始化植物集合
		plantsMap = new HashMap<>();
				
		plantsMap.put("sunFlower", Collections.synchronizedList(new ArrayList<>()));
		plantsMap.put("peaShooter", Collections.synchronizedList(new ArrayList<>()));
		plantsMap.put("repeater", Collections.synchronizedList(new ArrayList<>()));
		plantsMap.put("snowPea", Collections.synchronizedList(new ArrayList<>()));
		plantsMap.put("wallNut", Collections.synchronizedList(new ArrayList<>()));
		plantsMap.put("cherryBomb", Collections.synchronizedList(new ArrayList<>()));
				
		//初始化子弹集合
		bulletMap = new HashMap<>();
				
		bulletMap.put(1, Collections.synchronizedList(new ArrayList<>()));
		bulletMap.put(2, Collections.synchronizedList(new ArrayList<>()));
		bulletMap.put(3, Collections.synchronizedList(new ArrayList<>()));
		bulletMap.put(4, Collections.synchronizedList(new ArrayList<>()));
		bulletMap.put(5, Collections.synchronizedList(new ArrayList<>()));
				
		//初始化僵尸集合
		zombieTeam = new HashMap<>();
				
		zombieTeam.put(1, Collections.synchronizedList(new ArrayList<>()));
		zombieTeam.put(2, Collections.synchronizedList(new ArrayList<>()));
		zombieTeam.put(3, Collections.synchronizedList(new ArrayList<>()));
		zombieTeam.put(4, Collections.synchronizedList(new ArrayList<>()));
		zombieTeam.put(5, Collections.synchronizedList(new ArrayList<>()));
				
		//初始化阳光量
		sun = 150;
		
		//初始化阳光集合
		sunList = Collections.synchronizedList(new ArrayList<>());
		
	}
	
	
	
	public Map<String, List<Plants>> getPlantsMap() {
		return plantsMap;
	}

	public void setPlantsMap(Map<String, List<Plants>> plantsMap) {
		this.plantsMap = plantsMap;
	}

	public Map<Integer, List<Bullet>> getBulletMap() {
		return bulletMap;
	}

	public void setBulletMap(Map<Integer, List<Bullet>> bulletMap) {
		this.bulletMap = bulletMap;
	}

	public Map<Integer, List<Zombie>> getZombieTeam() {
		return zombieTeam;
	}

	public void setZombieTeam(Map<Integer, List<Zombie>> zombieTeam) {
		this.zombieTeam = zombieTeam;
	}

	public int getSun() {
		return sun;
	}

	public void setSun(int sun) {
		this.sun = sun;
	}

	public List<Sun> getSunList() {
		return sunList;
	}

	public void setSunList(List<Sun> sunList) {
		this.sunList = sunList;
	}

	/**
	 * 随鼠标移动画出植物
	 * @param g 像素
	 */
	public void paintSelectPlant(Graphics g) {
		if(this.isPaintSelectPlant == true && mouseStatus != 0) {
			plantBar.paintSelectPaint(mouseX, mouseY, mouseStatus, g);
		}
	}
	
	/**
	 * 接收要绘出的模型
	 * @param plantsMap 植物模型
	 * @param bulletMap 子弹模型
	 * @param zombieTeam 僵尸模型
	 * @param sunList 阳光模型
	 * @param sunAmount 阳光计数器
	 * @param plantsBarList 植物栏
	 * @param g 像素
	 */
	public void paintModels(Map<String, List<Plants>> plantsMap, Map<Integer, List<Bullet>> bulletMap,
			Map<Integer, List<Zombie>> zombieTeam, List<Sun> sunList, int sunAmount, List<Plants> plantsBarList, Graphics g) {
		
		Set<String> plantsSet = plantsMap.keySet();
		
		//画出植物
		plantsSet.forEach((key) -> {
			List<Plants> plantList = plantsMap.get(key);
			
			if(!plantList.isEmpty()) {
				
				plantList.forEach((plant) -> {
					plant.drawPlant(plant.getStartPoint().x, plant.getStartPoint().y, this, g);
				});
			}
		});
		
		Set<Integer> zombieSet = zombieTeam.keySet();
		
		//画出僵尸
		zombieSet.forEach((key) -> {
			List<Zombie> zombieList = zombieTeam.get(key);
			
			if(!zombieList.isEmpty()) {
				
				zombieList.forEach((zombie) -> {
					zombie.drawMe(this, g);
				});
			}
		});
		
		Set<Integer> bulletSet = bulletMap.keySet();
		
		//画出子弹
		bulletSet.forEach((key) -> {
			List<Bullet> bulletList = bulletMap.get(key);
			
			if(!bulletList.isEmpty()) {
				
				bulletList.forEach((bullet) -> {
					bullet.draw(this, g);
				});
			}
		});
		
		//画出阳光
		if(!sunList.isEmpty()) {
			sunList.forEach((sun) -> {
				sun.draw(this, g);
			});
		}
		
		//画出阳光值
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		g.drawString(sunAmount + "" , 18, 83);
		
		//画出plant bar
		int i = 1;
		for(Plants plant : plantsBarList) {
			plant.drawPlantInBar(50 * i + 20, 8, plant.isCooling(), g);
			i++;
		};
	}
	
	/**
	 * 绘出被选中的植物
	 * @param g 像素
	 */
	public void paintSelectedPlant(Graphics g) {
		
		if(mouseStatus != 0) {
			for(int i = 1; i <= 6; i++) {
				if(mouseStatus == i) {
					plantBar.getBarPlantsList().get(i - 1).drawSelectPlant(mouseX - plantBar.getBarPlantsList().get(i - 1).getWidth() / 2,
							mouseY - plantBar.getBarPlantsList().get(i - 1).getLength() / 2, g);
				}else {
					
				}
			}
		}
	}
	
	/**
	 * 绘出游戏主界面
	 * @param g 像素
	 */
	public void drawMainGame(Graphics g) {
		//画出背景
		g.drawImage(imageBackground, 0, 0, this);
				
		//画出辅助线
		for(int i = Config.HEIGHT_LINE_ZERO; i <= Config.HEIGHT_LINE_ZERO + Config.CELL_HEIGHT * Config.PLANT_HEIGHT; i+=Config.CELL_HEIGHT) {
			g.setColor(Color.blue);
			g.drawLine(Config.WIDTH_LINE_ZERO, i, Config.WIDTH_LINE_ZERO + Config.PLANT_WIDTH * Config.CELL_WIDTH, i);
		}
				
		for(int j = Config.WIDTH_LINE_ZERO; j <=Config.WIDTH_LINE_ZERO + Config.CELL_WIDTH * Config.PLANT_WIDTH; j+=Config.CELL_WIDTH) {
			g.setColor(Color.RED);
			g.drawLine(j, Config.HEIGHT_LINE_ZERO, j, Config.HEIGHT_LINE_ZERO + Config.CELL_HEIGHT * Config.PLANT_HEIGHT);
		}
				
		//画出植物栏(其中阳光显示占一栏)
		g.drawImage(imagePlantsBar, 0, 0, 50 * 8 + 10, 90, this);
				
		//画出阳光值(默认150) 
		g.setColor(Color.RED);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		g.drawString(""+sun, 18, 83);
				
		//画出各种模型
		paintModels(plantsMap, bulletMap, zombieTeam, sunList, sun, plantBar.getBarPlantsList(), g);
				
		//画出被选中的模型
		paintSelectedPlant(g);
	}
}
