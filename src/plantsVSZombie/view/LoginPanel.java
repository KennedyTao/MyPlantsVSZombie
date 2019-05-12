package plantsVSZombie.view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import plantsVSZombie.config.Config;
import plantsVSZombie.config.GameStatus;

/**
 * 主功能界面
 * @author KamHowe
 */
public class LoginPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	//按钮的宽度
	public static final int  buttonWidth = 300;
	
	//按钮的高度
	public static final int buttonHeight = 100;
	
	//背景图片
	public BufferedImage background;
	
	//登录图片
	public BufferedImage chooseLoginImage;
	public BufferedImage unchooseLoginImage;
	
	//注册图片
	public BufferedImage chooseRegisterImage;
	public BufferedImage unchooseRegisterImage;
	
	//开始游戏图片
	public BufferedImage chooseStartImage;
	public BufferedImage unchooseStartImage;
	
	//排行榜图片
	public BufferedImage chooseRankImage;
	public BufferedImage unchooseRankImage;
	
	//主界面图片
	public BufferedImage chooseMainImage;
	public BufferedImage unchooseMainImage;
	
	//确定图片
	public BufferedImage chooseConfirmImage;
	public BufferedImage unChooseConfirmImage;
	
	//账户图片
	public BufferedImage userImage;
	
	//密码图片
	public BufferedImage passwdImage;
	
	//easy图片
	public BufferedImage chooseEasyImage;
	public BufferedImage unchooseEasyImage;
	
	//normal图片
	public BufferedImage chooseNormalImage;
	public BufferedImage unchooseNormalImage;
	
	//hard图片
	public BufferedImage chooseHardImage;
	public BufferedImage unchooseHardImage;
	
	//游戏状态
	public GameStatus gameStatus;
	
	//鼠标X
	public int mouseX;
	
	//鼠标Y
	public int mouseY;
	
	//判断是否已登录
	public boolean isLogin;
	
	//输入账户的文本框
	public JTextField userField;
	
	//输入密码的文本框
	public JPasswordField passwdField;
	
	//已登录的一个用户
	public String logingUser;
	
	//存储成绩
	public Properties propScore;
	
	//写成绩
	public Writer scoreWriter;
	
	public Reader scoreReader;
	
	//记录排序的成绩
	public List<Map.Entry<String, Integer>> list;
	
	//map
	public Map<String, Integer> map;
	
	public Timer soundTimer;
	
	public AudioClip sound;
	
	/**
	 * 鼠标状态，0表示正常，1表示在login上，2表示在start上，3表示在register上，4表示在rank上
	 * 5表示在main上，6表示在confirm上,7表示在easy上，8表示在normal上,9表示在hard上
	 */
	public int mouseStatus;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		try {
			//初始化各种图片
			background = ImageIO.read(new FileInputStream("images/Panels/panel_1.jpg"));
			
			chooseLoginImage = ImageIO.read(new FileInputStream("images/Panels/login_1.png"));
			unchooseLoginImage = ImageIO.read(new FileInputStream("images/Panels/login_2.png"));
			
			chooseRegisterImage = ImageIO.read(new FileInputStream("images/Panels/register.png"));
			unchooseRegisterImage = ImageIO.read(new FileInputStream("images/Panels/register_2.png"));
			
			chooseRankImage = ImageIO.read(new FileInputStream("images/Panels/rank_1.png"));
			unchooseRankImage = ImageIO.read(new FileInputStream("images/Panels/rank_2.png"));
			
			chooseStartImage = ImageIO.read(new FileInputStream("images/Panels/start_1.png"));
			unchooseStartImage = ImageIO.read(new FileInputStream("images/Panels/start_2.png"));
			
			chooseMainImage = ImageIO.read(new FileInputStream("images/Panels/main_1.png"));
			unchooseMainImage = ImageIO.read(new FileInputStream("images/Panels/main_2.png"));
			
			chooseConfirmImage = ImageIO.read(new FileInputStream("images/Panels/confirm_1.png"));
			unChooseConfirmImage = ImageIO.read(new FileInputStream("images/Panels/confirm_2.png"));
			
			userImage = ImageIO.read(new FileInputStream("images/Panels/user.png"));
			passwdImage = ImageIO.read(new FileInputStream("images/Panels/passwd.png"));
			
			chooseEasyImage = ImageIO.read(new FileInputStream("images/Panels/chooseEasy.png"));
			unchooseEasyImage = ImageIO.read(new FileInputStream("images/Panels/unchooseEasy.png"));
			
			chooseNormalImage = ImageIO.read(new FileInputStream("images/Panels/chooseNormal.png"));
			unchooseNormalImage = ImageIO.read(new FileInputStream("images/Panels/unchooseNormal.png"));
			
			chooseHardImage = ImageIO.read(new FileInputStream("images/Panels/chooseHard.png"));
			unchooseHardImage = ImageIO.read(new FileInputStream("images/Panels/unchooseHard.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setSize(Config.WINDOWS_WIDTH, Config.WINDOWS_HEIGHT);
		
		gameStatus = GameStatus.FUNCTION;
		
		mouseX = 0;
		mouseY = 0;
		
		mouseStatus = 0;
		isLogin = false;
		logingUser = null;
		
		userField = new JTextField();
		userField.setBounds(Config.CELL_WIDTH * 6 + buttonWidth / 2, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2);
		userField.setBackground(Color.BLACK);
		userField.setFont(new Font("宋体", Font.BOLD, 16));
		userField.setForeground(Color.WHITE);
		add(userField);
		userField.setVisible(false);
		
		passwdField = new JPasswordField();
		passwdField.setBounds(Config.CELL_WIDTH * 6 + buttonWidth / 2, Config.CELL_HEIGHT * 3 / 2, buttonWidth / 2, buttonHeight / 2);
		passwdField.setBackground(Color.BLACK);
		passwdField.setFont(new Font("宋体", Font.BOLD, 16));
		passwdField.setForeground(Color.WHITE);
		add(passwdField);
		passwdField.setVisible(false);
		
		//初始化map
		map = new HashMap<>();
		soundTimer = new Timer();
		
		initReader();
		
	}
	
	
	/**
	 * 播放背景音乐
	 */
	public void playSound() {
		File soundFile = new File("music/welcome.wav");
		try {
			URL url = soundFile.toURI().toURL();
			sound = Applet.newAudioClip(url);
			sound.loop();
				
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}


	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}


	public int getMouseX() {
		return mouseX;
	}


	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}


	public int getMouseY() {
		return mouseY;
	}


	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}


	public int getMouseStatus() {
		return mouseStatus;
	}


	public void setMouseStatus(int mouseStatus) {
		this.mouseStatus = mouseStatus;
	}

	public boolean isLogin() {
		return isLogin;
	}


	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	
	public JTextField getUserField() {
		return userField;
	}


	public void setUserField(JTextField userField) {
		this.userField = userField;
	}


	public JPasswordField getPasswdField() {
		return passwdField;
	}


	public void setPasswdField(JPasswordField passwdField) {
		this.passwdField = passwdField;
	}


	public String getLogingUser() {
		return logingUser;
	}


	public void setLogingUser(String logingUser) {
		this.logingUser = logingUser;
	}


	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, Config.WINDOWS_WIDTH, Config.WINDOWS_HEIGHT, this);
		
		if(gameStatus == GameStatus.FUNCTION) {
			paintMainFuction(g);
			
		}else if(gameStatus == GameStatus.RESIGTER || gameStatus == GameStatus.LOGIN) {
			paintLogin(g);
			
		}else if(gameStatus == GameStatus.SCORE) {
			paintRank(g);
		}else if(gameStatus == GameStatus.PREGAME) {
			paintPreGame(g);
		}
	}
	
	/**
	 * 根据状态画出按钮
	 * @param g 像素
	 */
	public void paintMainFuction(Graphics g) {
		drawButton(mouseStatus == 1 ? chooseLoginImage : unchooseLoginImage, 
				Config.CELL_WIDTH, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, g);
		
		drawButton(mouseStatus == 2 ? chooseStartImage : unchooseStartImage, 
				Config.CELL_WIDTH * 2 + buttonWidth, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, g);
		
		drawButton(mouseStatus == 3 ? chooseRegisterImage : unchooseRegisterImage, Config.CELL_WIDTH * 5, Config.CELL_HEIGHT / 2, g);
		
		drawButton(mouseStatus == 4 ? chooseRankImage : unchooseRankImage, Config.CELL_WIDTH * 6 + buttonWidth, Config.CELL_HEIGHT / 2, g);
		
	}
	
	/**
	 * 画出一个按钮
	 * @param buttonImage
	 * @param x 按钮起始x坐标
	 * @param y 按钮起始y坐标
	 * @param g 像素
	 */
	public void drawButton(BufferedImage buttonImage, int x, int y, Graphics g) {
		g.drawImage(buttonImage, x, y, buttonWidth, buttonHeight, this);
	}
	
	/**
	 * 返回button所在的矩形
	 * @param button 1表示login, 2表示start, 3表示register, 4表示rank
	 * @return Rectangle
	 */
	public static Rectangle getButtonRectangle(int button) {
		if(button == 1) {
			return new Rectangle(Config.CELL_WIDTH, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, buttonWidth, buttonHeight);
			
		}else if(button == 2) {
			return new Rectangle(Config.CELL_WIDTH * 2 + buttonWidth, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2,
					buttonWidth, buttonHeight);
			
		}else if(button == 3) {
			return new Rectangle( Config.CELL_WIDTH * 5, Config.CELL_HEIGHT / 2, buttonWidth, buttonHeight);
			
		}else if(button == 4) {
			return new Rectangle(Config.CELL_WIDTH * 6 + buttonWidth, Config.CELL_HEIGHT / 2, buttonWidth, buttonHeight);
			
		}else if(button == 5) {
			return new Rectangle(Config.CELL_WIDTH, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, buttonWidth, buttonHeight);
			
		}else if(button == 6) {
			return new Rectangle(Config.CELL_WIDTH * 2 + buttonWidth, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, buttonWidth, buttonHeight);
			
		}else if(button == 7) {
			return new Rectangle(Config.CELL_WIDTH * 6, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2);
			
		}else if(button == 8) {
			return new Rectangle(Config.CELL_WIDTH * 8, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2);
			
		}else if(button == 9) {
			return new Rectangle(Config.CELL_WIDTH * 10, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2);
			
		}else {
			return null;
		}
	}
	
	/**
	 * 画出注册和登录的面板
	 * @param g 像素
	 */
	public void paintLogin(Graphics g) {
		drawButton(mouseStatus == 5 ? chooseMainImage : unchooseMainImage, Config.CELL_WIDTH, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, g);
		
		drawButton(mouseStatus == 6 ? chooseConfirmImage : unChooseConfirmImage,
				Config.CELL_WIDTH * 2 + buttonWidth, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, g);
		
		g.drawImage(userImage, Config.CELL_WIDTH * 5, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2, this);
		
		g.drawImage(passwdImage, Config.CELL_WIDTH * 5, Config.CELL_HEIGHT + buttonHeight / 2,
				buttonWidth / 2, buttonHeight / 2, this);
	}
	
	/**
	 * 画出得分界面
	 * @param g 像素
	 */
	public void paintRank(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
		
		int index = 1;
		int userScore = -1;
		int userRank = 0;

		//要先init！
		if(list != null) {
			
			if(!list.isEmpty()) {
				
				for(Map.Entry<String, Integer> entry : list) {
					
					if(entry.getKey().equals(logingUser)) {
						userScore = entry.getValue();
						userRank = index;
					}
					
					if(index > 3) {
						continue;
					}
					
					g.drawString("Rank " + index + " : " + entry.getKey() + " --- " + entry.getValue(), Config.CELL_WIDTH * 5, 
							 Config.CELL_HEIGHT / 2 * index);
					index++;
				}
			}
			if(userScore != -1) {
				g.drawLine(Config.CELL_WIDTH * 5, Config.CELL_HEIGHT / 2 * 4 - Config.CELL_HEIGHT / 4 , 
						Config.CELL_WIDTH * 13, Config.CELL_HEIGHT / 2 * 4 - Config.CELL_HEIGHT / 4);
				g.drawString("Rank " + userRank + " : " + logingUser + " --- " + userScore, Config.CELL_WIDTH * 5, 
						 Config.CELL_HEIGHT / 2 * 5 - Config.CELL_HEIGHT / 4);
			}
		}
		
		drawButton(mouseStatus == 5 ? chooseMainImage : unchooseMainImage, Config.CELL_WIDTH, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, g);
		
	}
	
	
	/**
	 * 写入一个成绩，并更新读写器
	 * @param newScore
	 */
	public void rewriteScore(int newScore) {
		initReader();
		
		for(String key : propScore.stringPropertyNames()) {
			
			if(key.equals(logingUser) && logingUser != null) {
				newScore += Integer.parseInt((String)propScore.get(key));
			}
		}
		
		if(propScore != null) {
			if(logingUser != null) {
				propScore.setProperty(logingUser, String.valueOf(newScore));
			}
		}
		
		try {
			scoreWriter = new FileWriter("files/score.txt");
			propScore.store(scoreWriter, null);
			scoreWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 初始化propScore
	 */
	public void initReader() {
		propScore = new Properties();
		
		try {
			scoreReader = new FileReader("files/score.txt");
			propScore.load(scoreReader);
			scoreReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!propScore.isEmpty()) {
			
			for(String key : propScore.stringPropertyNames()) {
				map.put(key, Integer.parseInt(propScore.getProperty(key)));
			}
		}
		
		list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		
		//对list排序
		Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {  
			
			  public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {  
	                Integer one = o1.getValue();  
	                Integer two = o2.getValue();  
	                return two.compareTo(one);  
			  }  
	              
		});  
		 
	}
	
	/**
	 * 绘出难度选择界面
	 * @param g 像素
	 */
	public void paintPreGame(Graphics g) {
		//主界面按钮
		drawButton(mouseStatus == 5 ? chooseMainImage : unchooseMainImage, Config.CELL_WIDTH, Config.WINDOWS_HEIGHT - Config.CELL_HEIGHT * 2, g);
		
		g.drawImage(mouseStatus == 7 ? chooseEasyImage : unchooseEasyImage, 
				Config.CELL_WIDTH * 6, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2, this);
		
		g.drawImage(mouseStatus == 8 ? chooseNormalImage : unchooseNormalImage, 
				Config.CELL_WIDTH * 8, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2, this);
		
		g.drawImage(mouseStatus == 9 ? chooseHardImage : unchooseHardImage, 
				Config.CELL_WIDTH * 10, Config.CELL_HEIGHT / 2, buttonWidth / 2, buttonHeight / 2, this);
	}
}
