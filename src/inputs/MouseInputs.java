package inputs;

import java.awt.event.*;

import gamestates.Gamestate;
import main.GamePanel;

public class MouseInputs implements MouseWheelListener, MouseListener, MouseMotionListener {

	private GamePanel gamePanel;

	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseDragged(e);
			break;
		case EDIT:
			gamePanel.getGame().getEdit().mouseDragged(e);
		default:
			break;

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case EDIT:
			gamePanel.getGame().getEdit().mouseMoved(e);
		case MENU:
			gamePanel.getGame().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseMoved(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseClicked(e);
			break;
		case EDIT:
			gamePanel.getGame().getEdit().mouseClicked(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mousePressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().mousePressed(e);
			break;
		case EDIT:
			gamePanel.getGame().getEdit().mousePressed(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().mouseReleased(e);
			break;
		case EDIT:
			gamePanel.getGame().getEdit().mouseReleased(e);
		default:
			break;

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e){
		switch (Gamestate.state) {
			case EDIT:
			{
				gamePanel.getGame().getEdit().mouseWheelMoved(e);
			}
			case PLAYING:
			case MENU:
			case OPTIONS:
			case QUIT:

			{
				break;
			}
		}
	}
}