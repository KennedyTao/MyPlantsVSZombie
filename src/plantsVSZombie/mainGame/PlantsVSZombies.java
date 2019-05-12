package plantsVSZombie.mainGame;

import java.awt.EventQueue;

import plantsVSZombie.view.LoginFrame;

/**
 * Coding with utf-8
 * 植物大战僵尸
 * @author KamHowe
 * @version 1.00
 */
public class PlantsVSZombies {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
	}
}
