package plantsVSZombie.controller;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import plantsVSZombie.config.GameStatus;
import plantsVSZombie.view.LoginPanel;
import plantsVSZombie.view.PlantsVSZombiesPanel;

/**
 * 操作界面管理
 * @author KamHowe
 */
public class FunctionPanelController extends MouseAdapter{
	
	public LoginPanel panel;
	
	public JFrame mainFrame;
	
	public Timer soundTimer;
	
	
	public FunctionPanelController() {
		super();
	}

	public FunctionPanelController(LoginPanel panel, JFrame mainFrame) {
		super();
		this.panel = panel;
		this.mainFrame = mainFrame;
		soundTimer = new Timer();
		panel.playSound();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		if(panel.getGameStatus() == GameStatus.FUNCTION) {
			
			if(LoginPanel.getButtonRectangle(1).contains(e.getPoint())) {
				//点击了登录
				panel.setGameStatus(GameStatus.LOGIN);
				
				//输入框可视
				panel.getUserField().setVisible(true);
				panel.getPasswdField().setVisible(true);
				
				panel.repaint();
				
			}else if(LoginPanel.getButtonRectangle(2).contains(e.getPoint())) {
				//点击了开始游戏
				
				if(panel.isLogin == false) {
					//判断有没有登录
					showMessage("请先登录");
					
				}else {
					panel.setGameStatus(GameStatus.PREGAME);
					panel.repaint();
				}
				
			}else if(LoginPanel.getButtonRectangle(3).contains(e.getPoint())) {
				//点击了注册
				panel.setGameStatus(GameStatus.RESIGTER);
				
				//输入框可视
				panel.getUserField().setVisible(true);
				panel.getPasswdField().setVisible(true);
				
				panel.repaint();
				
			}else if(LoginPanel.getButtonRectangle(4).contains(e.getPoint())) {
				//点击了rank
				if(panel.isLogin == false) {
					//判断有没有登录
					showMessage("请先登录");
					
				}else {
					panel.initReader();
					panel.setGameStatus(GameStatus.SCORE);
					panel.repaint();
				}
				
			}else {
				
			}
			
		}else if(panel.getGameStatus() == GameStatus.RESIGTER) {
			
			if(LoginPanel.getButtonRectangle(5).contains(e.getPoint())) {
				
				//返回主菜单
				panel.setGameStatus(GameStatus.FUNCTION);
				
				panel.getUserField().setText("");
				panel.getUserField().setVisible(false);
				panel.getPasswdField().setText("");
				panel.getPasswdField().setVisible(false);
				
				panel.repaint();
				
			}else if(LoginPanel.getButtonRectangle(6).contains(e.getPoint())) {
				//点击了确定
				registerHandler();
			}
			
		}else if(panel.getGameStatus() == GameStatus.LOGIN) {
			
			if(LoginPanel.getButtonRectangle(5).contains(e.getPoint())) {
				//返回主菜单
				panel.setGameStatus(GameStatus.FUNCTION);
				
				panel.getUserField().setText("");
				panel.getUserField().setVisible(false);
				panel.getPasswdField().setText("");
				panel.getPasswdField().setVisible(false);
				
				panel.repaint();
				
			}else if(LoginPanel.getButtonRectangle(6).contains(e.getPoint())) {
				//点击了确定
				loginHandler();
				
			}
			
		}else if(panel.getGameStatus() == GameStatus.SCORE) {
			//得分界面
			if(LoginPanel.getButtonRectangle(5).contains(e.getPoint())) {
				//返回主菜单
				panel.setGameStatus(GameStatus.FUNCTION);
				
				panel.repaint();
				
			}else {
				
			}
		}else if(panel.getGameStatus() == GameStatus.PREGAME) {
			
			int gameLevel = 0;
			
			//选择模式界面
			if(LoginPanel.getButtonRectangle(5).contains(e.getPoint())) {
				//返回主菜单
				panel.setGameStatus(GameStatus.FUNCTION);
				
				panel.repaint();
				
			}else if(LoginPanel.getButtonRectangle(7).contains(e.getPoint())) {
				gameLevel = 1;
				
			}else if(LoginPanel.getButtonRectangle(8).contains(e.getPoint())) {
				gameLevel = 2;
				
			}else if(LoginPanel.getButtonRectangle(9).contains(e.getPoint())) {
				gameLevel = 3;
				
			}else {
				
			}
			
			if(LoginPanel.getButtonRectangle(7).contains(e.getPoint()) || LoginPanel.getButtonRectangle(8).contains(e.getPoint())
					|| LoginPanel.getButtonRectangle(9).contains(e.getPoint())) {
				try {
					panel.setGameStatus(GameStatus.GAME);
					panel.sound.stop();

					JFrame frame = new JFrame();
					PlantsVSZombiesPanel panel = new PlantsVSZombiesPanel();
					
					GameController controller = new GameController(panel);
					panel.addMouseListener(controller);
					panel.addMouseMotionListener(controller);
					
					//设置level
					controller.setGameLever(gameLevel);
					
					//设置游戏用户
					controller.setLogginguser(this.panel.getLogingUser());
					
					controller.gameStart();
					
					Container contentPane = frame.getContentPane();
					contentPane.add(panel);
					panel.setOpaque(true);
					frame.setSize(panel.getWidth(), panel.getHeight());
					frame.setTitle("植物大战僵尸");
					
					//设置居中
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					frame.setLocation((int)(toolkit.getScreenSize().getWidth() - frame.getWidth())/2, 
								(int)(toolkit.getScreenSize().getHeight() - frame.getHeight())/2);
					
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setResizable(false);
					frame.setVisible(true);
					
					controller.playSound();
					
					if(soundTimer != null) {
						soundTimer.cancel();
					}
					mainFrame.dispose();
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		}else {
			
		}
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		
		if(panel.getGameStatus() == GameStatus.FUNCTION) {
			int x = e.getX();
			int y = e.getY();
			Point point = new Point(x, y);		
			
			panel.setMouseX(x);
			panel.setMouseY(y);
			
			int status = 0;
			
			for(int i = 1; i <= 4; i++) {
				if(LoginPanel.getButtonRectangle(i).contains(point)) {
					status = i;
				}
			}
			
			if(panel.getMouseStatus() != status) {
				panel.setMouseStatus(status);
				panel.repaint();
			}
			
		}else if(panel.getGameStatus() == GameStatus.RESIGTER || panel.getGameStatus() == GameStatus.LOGIN) {
			int x = e.getX();
			int y = e.getY();
			Point point = new Point(x, y);		
			
			panel.setMouseX(x);
			panel.setMouseY(y);
			
			int status = 0;
			
			for(int i = 5; i <= 6; i++) {
				if(LoginPanel.getButtonRectangle(i).contains(point)) {
					status = i;
				}
			}
			
			if(panel.getMouseStatus() != status) {
				panel.setMouseStatus(status);
				panel.repaint();
			}
			
		}else if(panel.getGameStatus() == GameStatus.SCORE) {
			int x = e.getX();
			int y = e.getY();
			Point point = new Point(x, y);		
			
			panel.setMouseX(x);
			panel.setMouseY(y);
			
			int status = 0;
			
			if(LoginPanel.getButtonRectangle(5).contains(point)) {
				status = 5;
			}
			
			if(panel.getMouseStatus() != status) {
				panel.setMouseStatus(status);
				panel.repaint();
			}
		}else if(panel.getGameStatus() == GameStatus.PREGAME) {
			int x = e.getX();
			int y = e.getY();
			Point point = new Point(x, y);		
			
			panel.setMouseX(x);
			panel.setMouseY(y);
			
			int status = 0;
			
			if(LoginPanel.getButtonRectangle(5).contains(point)) {
				status = 5;
			}
			
			for(int i = 7; i <= 9; i++) {
				if(LoginPanel.getButtonRectangle(i).contains(point)) {
					status = i;
				}
			}
			
			if(panel.getMouseStatus() != status) {
				panel.setMouseStatus(status);
				panel.repaint();
			}
		}
		
	}
	
	/**
	 * 判断密码是否符合标准
	 * @param passwd 需要检验的密码
	 * @return true-符合标准，false-不符合标准
	 */
	public static boolean isPasswdOk(String passwd) {
		if(passwd == null) {
			return false;
		}
		if(passwd.length() < 6 || passwd.length() > 22) {
			return false;
		}
		if(!passwd.matches("[A-Za-z0-9]+")) {
			//正则表达式判断密码格式
			return false;
		}
		return true;
	}
	
	/**
	 * 判断用户名是否符合标准
	 * @param username 需要校验的用户名
	 * @return true-符合标准，false-不符合标准
	 */
	public static boolean isUsernameOk(String username) {
		if(username == null) {
			return false;
		}
		if(!username.matches("[A-Za-z]+")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 处理注册事件
	 */
	public void registerHandler() {
		//存储录入的信息
		String username = panel.getUserField().getText().trim();
		String passwdText = new String(panel.getPasswdField().getPassword()).trim();
		
		// userisexist:判断是否已有用户
		boolean userisexist = false;
		
		//用于存储已有的登录信息
		Properties prop_login = new Properties();
		
		//存储分数
		Properties prop_score = new Properties();
		
		try{
			Reader read_login = new FileReader("files/login.txt");
			
			//获取已有的登录信息
			prop_login.load(read_login);
			
			//检测是否存在用户
			Set<String> set_login = prop_login.stringPropertyNames();
			for(String key : set_login) {
				if(key.equals(username)) {
					userisexist = true;
					break;
				}
			}
			
			//如果已经存在用户
			if(userisexist) {
				
				//判断密码是否符合输入标准
				if(isPasswdOk(passwdText)) {
					
					//重置密码
					prop_login.setProperty(username, passwdText);
					
					showMessage("密码已重置");
				}else {
					
					//密码不符合标准
					showErrorMessage("密码格式不正确");
				}
			}else {
				
				//如果用户不存在，检测用户名是否正确
				if(isUsernameOk(username)) {
					
					//检验密码是否符合标准
					if(isPasswdOk(passwdText)) {
						
						//信息正确则把新增的用户加进文件中
						prop_login.setProperty(username, passwdText);
						prop_score.setProperty(username, "0");
						
						showMessage("用户" + username + "注册成功");
					}else {
						
						//密码不符合标准
						showErrorMessage("密码格式不正确");
					}
				}else {
					
					//用户名不符合标准
					showErrorMessage("用户名格式不正确");
				}
			}
			
			//关闭读入流
			read_login.close();
			
			//调用输出流并写进文件
			Writer write_login = new FileWriter("files/login.txt");
			Writer writer_score = new FileWriter("files/score.txt");
			prop_login.store(write_login, null);
			prop_score.store(writer_score, null);
			write_login.close();
			writer_score.close();
			
			//返回主菜单
			panel.setGameStatus(GameStatus.FUNCTION);
			
			panel.getUserField().setText("");
			panel.getUserField().setVisible(false);
			panel.getPasswdField().setText("");
			panel.getPasswdField().setVisible(false);
			
			panel.repaint();
			
		} catch (FileNotFoundException e) {
			//文件找不到
			showErrorMessage("登录文件不存在");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			//其它IO错误
			showErrorMessage("读写错误");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 登录处理
	 */
	public void loginHandler() {
		//储存录入的信息
		String userText = panel.getUserField().getText().trim();
		String passwdText = new String(panel.getPasswdField().getPassword()).trim();
		
		String passwdMessage = null;
		String username = null;
		
		//找出存储的登录信息
		Properties prop = new Properties();
		
		try{
			//读取登录信息
			Reader read = new FileReader("files/login.txt");
			prop.load(read);
			
			//检测是否存在用户
			Set<String> keySet = prop.stringPropertyNames();
			for(String key : keySet) {
				if(userText.equals(key)) {
					username = key;
					passwdMessage = prop.getProperty(key);
					break;
				}
			}
			
			//出错提示
			if(passwdMessage == null) {
				
				//读取失败
				showErrorMessage("用户不存在或存储不正确");
				
			}else if(!passwdMessage.equals(passwdText)) {
				
				//输错密码
				showErrorMessage("密码错误");
				
				
			}else {
				
				//显示登录后的界面
				showMessage("登录成功");
				
				//返回主菜单
				panel.setGameStatus(GameStatus.FUNCTION);
				
				panel.getUserField().setText("");
				panel.getUserField().setVisible(false);
				panel.getPasswdField().setText("");
				panel.getPasswdField().setVisible(false);
				
				panel.setLogin(true);
				if(username != null) {
					panel.setLogingUser(username);
				}
				
			}
			
			read.close();
			
			
		} catch (FileNotFoundException e) {
			
			//找不到相应的文件
			showErrorMessage("登录文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			
			//其它IO错误
			showErrorMessage("读写错误");
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示错误信息
	 * @param message 需要显示的信息
	 */
	private static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "错误", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 显示消息
	 * @param message 需要显示的消息
	 */
	private static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "消息", JOptionPane.INFORMATION_MESSAGE);
	}
}
