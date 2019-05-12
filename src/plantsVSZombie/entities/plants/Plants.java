package plantsVSZombie.entities.plants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import plantsVSZombie.config.Config;
import plantsVSZombie.entities.Model;

/**
 * 植物类
 * @author KamHowe
 */
public class Plants extends Model{
	
	/** 冷却时间*/
	private int cooldowntime;
	
	/** 现在的冷却剩余时间*/
	private int coolingtime;
	
	/** 是否在冷却*/
	private boolean isCooling;
	
	/** 消耗阳光量*/
	private int consumption;
	
	/** 被选择时的图片*/
	private BufferedImage selectedImage;
	
	/** 冷却时的图片*/
	private BufferedImage coolingImage;
	
	/** 是否被选中*/
	private boolean isSelected;

	/** 攻击力*/
	private int attrackPower;
	
	/** 再次触发时间间隔*/
	private int recharge;
	
	/** 距离触发所剩的时间*/
	private int rechargetime;
	
	/** 植物的x网格*/
	private int cellX;
	
	/** 植物的y网格*/
	private int cellY;
	
	/**
	 * 默认构造方法
	 */
	public Plants() {
		super();
	}

	/**
	 * 植物的构造方法
	 * @param cooldowntime 冷却时间
	 * @param isCooling 是否在冷却
	 * @param consumption 消耗阳光量
	 * @param selectedImageName 被选择时的图片的路径
	 * @param coolingImageName 冷却时的图片的路径
	 * @param imageName 植物在plant bar的静态图片的路径
	 * @param gifImageName 植物种植后的gif动图
	 * @param attrackPower 攻击力
	 * @param recharge 再次触发时间间隔
	 * @throws IOException 读写错误
	 * @throws FileNotFoundException 找不到文件 
	 */
	public Plants(int cooldowntime, boolean isCooling, int consumption, String selectedImageName,
			String coolingImageName,String imageName, String gifImageName,
			int attrackPower, int recharge, int cellX, int cellY) throws FileNotFoundException, IOException {
		super();
		this.cooldowntime = cooldowntime;
		this.isCooling = isCooling;
		this.consumption = consumption;
		
		//初始化图片
		this.selectedImage = getBufferImage(selectedImageName);
		this.coolingImage = getBufferImage(coolingImageName);
		this.setImage(getBufferImage(imageName));
		this.setGifImage(new ImageIcon(gifImageName).getImage());
		
		this.attrackPower = attrackPower;
		this.recharge = recharge;
		this.isSelected = false;
		
		this.length = 80;
		this.width = 60;
		
		this.setDead(false);
		
		this.coolingtime = this.cooldowntime;
		this.rechargetime = this.recharge;
		
		this.cellX = cellX;
		this.cellY = cellY;
	}

	public int getCooldowntime() {
		return cooldowntime;
	}

	public void setCooldowntime(int cooldowntime) {
		this.cooldowntime = cooldowntime;
	}

	public int getRecharge() {
		return recharge;
	}

	public void setRecharge(int recharge) {
		this.recharge = recharge;
	}

	public boolean isCooling() {
		return isCooling;
	}
	
	public void setCooling(boolean isCooling) {
		this.isCooling = isCooling;
	}
	
	public int getConsumption() {
		return consumption;
	}
	
	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}
	
	public BufferedImage getSelectedImage() {
		return selectedImage;
	}
	
	public void setSelectedImage(BufferedImage selectedImage) {
		this.selectedImage = selectedImage;
	}
	
	public BufferedImage getCoolingImage() {
		return coolingImage;
	}
	
	public void setCoolingImage(BufferedImage coolingImage) {
		this.coolingImage = coolingImage;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public int getAttrackPower() {
		return attrackPower;
	}
	
	public void setAttrackPower(int attrackPower) {
		this.attrackPower = attrackPower;
	}
	
	public int getCoolingtime() {
		return coolingtime;
	}

	public void setCoolingtime(int coolingtime) {
		this.coolingtime = coolingtime;
	}

	public int getRechargetime() {
		return rechargetime;
	}

	public void setRechargetime(int rechargetime) {
		this.rechargetime = rechargetime;
	}

	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	}

	/**
	 * 绘出植物在PlantBar上的图片
	 * @param x 起始x坐标
	 * @param y 起始y坐标
	 * @param status 是否冷却的状态 
	 * @param g Graphics
	 */
	public void drawPlantInBar(int x, int y, boolean isCooling, Graphics g) {
		if(isCooling == false) {
			g.drawImage(getImage(), x, y, Config.PLANTBAR_CELL_WIDTH, Config.PLANTBAR_CELL_HEIGHT, null);
			
		}else if(isCooling == true) {
			g.drawImage(coolingImage, x, y, Config.PLANTBAR_CELL_WIDTH, Config.PLANTBAR_CELL_HEIGHT, null);
		}
	}

	/**
	 * 绘出植物的gif动图
	 * @pram x 模型的初始x位置
	 * @pram y 模型的初始y位置
	 * @param panel 模型的载体
	 * @pram g Graphics
	 */
	public void drawPlant(int x, int y,JPanel panel, Graphics g) {
		if(!this.isDead()) {
			g.drawImage(getGifImage(), x, y, width, length, panel);
		}
	}
	
	/**
	 * 绘出植物被选择时的图片
	 * @param x 起始x坐标
	 * @param y 起始y坐标
	 * @param g Graphics
	 */
	public void drawSelectPlant(int x, int y, Graphics g) {
		g.drawImage(selectedImage, x, y, width, length, null);
	}
	
	
	/**
	 * 植物在plant bar被选中时的矩形
	 * @return Rectangle
	 */
	public Rectangle getPlantsInPlantsBarRectangle() {
		if(getStartPoint() == null) {
			return null;
		}
		return new Rectangle(getStartPoint(), new Dimension(Config.PLANTBAR_CELL_WIDTH, Config.PLANTBAR_CELL_HEIGHT));
	}
}
