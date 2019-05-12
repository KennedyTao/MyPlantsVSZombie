package plantsVSZombie.view;

import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JFrame;

import plantsVSZombie.controller.FunctionPanelController;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public LoginPanel loginPanel;
	
	/**
	 * 构造方法
	 */
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("植物大战僵尸");
		
		loginPanel = new LoginPanel();
		
		FunctionPanelController controller = new FunctionPanelController(loginPanel, this);
		
		Container contentPane = getContentPane();
		contentPane.add(loginPanel);
		loginPanel.setOpaque(false);
		loginPanel.setLayout(null);
		
		loginPanel.addMouseListener(controller);
		loginPanel.addMouseMotionListener(controller);
		
		setSize(loginPanel.getWidth(), loginPanel.getHeight());
		setResizable(false);
		
		//设置居中
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setLocation((int)(toolkit.getScreenSize().getWidth() - getWidth())/2, 
					(int)(toolkit.getScreenSize().getHeight() - getHeight())/2);
		
//		setUndecorated(true);
	}
	
	public LoginPanel getLoginPanel() {
		return loginPanel;
	}

}
