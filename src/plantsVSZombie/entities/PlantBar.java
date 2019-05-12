package plantsVSZombie.entities;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import plantsVSZombie.entities.plants.CherryBomb;
import plantsVSZombie.entities.plants.PeaShooter;
import plantsVSZombie.entities.plants.Plants;
import plantsVSZombie.entities.plants.Repeater;
import plantsVSZombie.entities.plants.SnowPea;
import plantsVSZombie.entities.plants.SunFlower;
import plantsVSZombie.entities.plants.WallNut;

public class PlantBar {
	//植物栏上的植物
	public SunFlower barSunFlower;
		
	public PeaShooter barPeaShooter;
	
	public Repeater barRepeater;
		
	public SnowPea barSnowPea;
		
	public WallNut barWallNut;
		
	public CherryBomb barCherryBomb;
		
	//植物栏集合
	public List<Plants> barPlantsList;

	public PlantBar(JPanel gamePanel) throws FileNotFoundException, IOException {
		super();
		//初始化植物栏上的植物
		barSunFlower = new SunFlower(50 + 20, 8, gamePanel, -1, -1);
		barPeaShooter = new PeaShooter(50 * 2 + 20, 8, -1, -1, -1);
		barRepeater = new Repeater(50 * 3 + 20, 8, -1, -1, -1);
		barSnowPea = new SnowPea(50 * 4 + 20, 8, -1, -1, -1);
		barWallNut = new WallNut(50 * 5 + 20, 8, -1, -1);
		barCherryBomb = new CherryBomb(50 * 6 + 20, 8, -1, -1);
				
		barPlantsList = new ArrayList<>();
		barPlantsList.add(barSunFlower);
		barPlantsList.add(barPeaShooter);
		barPlantsList.add(barRepeater);
		barPlantsList.add(barSnowPea);
		barPlantsList.add(barWallNut);
		barPlantsList.add(barCherryBomb);
	}

	public List<Plants> getBarPlantsList() {
		return barPlantsList;
	}

	public void setBarPlantsList(List<Plants> barPlantsList) {
		this.barPlantsList = barPlantsList;
	}
		
	/**
	 * 画出plant bar上的植物
	 * @param plantsList bar上的植物列表
	 * @param g 像素
	 */
	public void drawBarPlants(List<Plants> plantsList, Graphics g) {
		int n = 1;
		for(Plants plant : plantsList) {
			plant.drawPlantInBar(50 * n + 20, 8, plant.isCooling(), g);
			n++;
		}
	}
	
	/**
	 * 画出选中的植物
	 * @param x 鼠标横坐标
	 * @param y 鼠标纵坐标
	 * @param type 类型
	 * @param g 像素
	 */
	public void paintSelectPaint(int mouseX, int mouseY, int type, Graphics g) {
		
		//实际坐标
		int x = mouseX - 60 / 2;
		
		int y = mouseY - 80 / 2;
		
		
		if(!barPlantsList.isEmpty()) {
			if(type == 1) {
				barSunFlower.drawSelectPlant(x, y, g);
				
			}else if(type == 2) {
				barPeaShooter.drawSelectPlant(x, type, g);
				
			}else if(type == 3) {
				barRepeater.drawSelectPlant(x, type, g);
				
			}else if(type == 4) {
				barSnowPea.drawSelectPlant(x, type, g);
				
			}else if(type == 5) {
				barWallNut.drawSelectPlant(x, type, g);
				
			}else if(type == 6) {
				barCherryBomb.drawSelectPlant(x, type, g);
				
			}else {
				
			}
			
		}
	}
	
	
}
