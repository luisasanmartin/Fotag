package view;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;




public class StarRatings extends JPanel {
	private static int MAX_RATING = 5;
	private int rating = 0;
	private ImageIcon iStar;
	private ImageIcon iStarSelected;
	private ImageIcon iX;
	private JButton stars[];
	private JButton clear;

	public StarRatings() {
		super();
		this.layoutView();
		this.registerControllers();
		repaint();
	}

	public void setModel(int r) {
		rating = r;
		changeIcons(r);
		StarRatingsChangeEvent evt = new StarRatingsChangeEvent(this, rating);
		fireStarRatingsChangedEvent(evt);
	}

	public int getModel() {
		return rating;
	}

	private void layoutView() {
		this.setBackground(Color.WHITE);
		this.setLayout(new GridLayout(1, 6));
		iStar = new ImageIcon(getClass().getResource("whiteStar.jpg"));
		iStarSelected = new ImageIcon(getClass().getResource("yellowStar.jpg"));
		iX = new ImageIcon(getClass().getResource("blackX.jpg"));
		stars = new JButton[MAX_RATING];
		
		clear = new JButton();
		clear.setIcon(iX);
		clear.setBorderPainted(false); 
        clear.setContentAreaFilled(false); 
        clear.setFocusPainted(false); 
        clear.setOpaque(false);
        clear.setPreferredSize(new Dimension(20, 20));
        clear.setMaximumSize(clear.getPreferredSize());
        clear.setMinimumSize(clear.getPreferredSize());
		this.add(clear);
		for (int i = 0; i < MAX_RATING; i++) {
			stars[i] = new JButton();
			stars[i].setIcon(iStar);
			stars[i].setBorderPainted(false); 
	        stars[i].setContentAreaFilled(false); 
	        stars[i].setFocusPainted(false); 
	        stars[i].setOpaque(false);
	        stars[i].setPreferredSize(new Dimension(20, 20));
	        stars[i].setMaximumSize(stars[i].getPreferredSize());
	        stars[i].setMinimumSize(stars[i].getPreferredSize());
			this.add(stars[i]);
		}
		this.setVisible(true);
		this.validate();
	}

	private void registerControllers() {
		
		for (int i = 0; i < MAX_RATING; i++) {
			MouseInputListener mil = new MController(i + 1);
			stars[i].addMouseListener(mil);
			stars[i].addMouseMotionListener(mil);
		}
		MouseInputListener mil = new MController(0);
		clear.addMouseMotionListener(mil);
		clear.addMouseListener(mil);
	}

	private void changeIcons(int newRating) {
		for (int i = 0; i < newRating; i++) {
			stars[i].setIcon(iStarSelected);
		}
		for (int i = newRating; i < MAX_RATING; i++) {
			stars[i].setIcon(iStar);
		}
	}

	private class MController extends MouseInputAdapter {
		int val;
		public MController(int i) {
			val = i;
		}

		public void mouseEntered(MouseEvent e) {
			changeIcons(val);
		}

		public void mouseExited(MouseEvent e) {
			changeIcons(rating);
		}

		public void mouseReleased(MouseEvent e) {
			setModel(val);
		}
	} // MController


	class StarRatingsChangeEvent extends EventObject {
		private int newRating;
		public StarRatingsChangeEvent(Object source, int r) {
			super(source);
			newRating = r;
		}
		public Integer getModel() {
			return newRating;
		}
	}

	public interface StarRatingsChangeEventListener extends EventListener {
		public void StarRatingsChangeEventOccurred(StarRatingsChangeEvent evt);
	}

	protected EventListenerList listenerList = new EventListenerList();

	public void addStarRatingsChangedEventListener(StarRatingsChangeEventListener listener) {
		listenerList.add(StarRatingsChangeEventListener.class, listener);
	}

	public void removeStarRatingsChangedEventListener(StarRatingsChangeEventListener listener) {
		listenerList.remove(StarRatingsChangeEventListener.class, listener);
	}

	void fireStarRatingsChangedEvent(StarRatingsChangeEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i+2) {
			if (listeners[i] == StarRatingsChangeEventListener.class) {
				((StarRatingsChangeEventListener) listeners[i+1]).StarRatingsChangeEventOccurred(evt);
			}
		}
	}

}