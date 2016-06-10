package view;

import model.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class ImageView extends JPanel {
	private ImageModel model;
	private ResizedImage image;
	private JPanel imgContainer;
	private view.StarRatings stars;
	private JPanel metadata;

	public class ResizedImage extends JPanel {
		public ResizedImage() {
			super();
			this.layoutView();
		}

		private void layoutView() {
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(model.getResizedDimension().width, 100)); // TODO
			this.setMaximumSize(this.getPreferredSize());
			this.setMinimumSize(this.getPreferredSize());
		}
		public void paint(Graphics g) {
	        g.drawImage(model.getImage(), 0, 0, model.getResizedDimension().width, model.getResizedDimension().height, null);
	    }
	}
	
	public class BigImage extends JPanel {
		private BufferedImage originalImg;
		private Dimension originalDim;
		private Dimension box;
		private Dimension scaled;
		public BigImage(BufferedImage img, Dimension dim) {
			super();
			originalImg = img;
			originalDim = dim;
			box = new Dimension(800, 600);
			this.layoutView();
		}

		private boolean needsScaling() {
			return originalDim.width <= box.width && originalDim.height <= box.height;
		}

		private void layoutView() {
			this.setBackground(Color.WHITE);
			if (needsScaling()) {
				scaled = originalDim;
			} else {
				scaled = ImageModel.getScaledDimension(originalDim, box);
			}
			
			this.setPreferredSize(scaled);
			this.setMaximumSize(this.getPreferredSize());
			this.setMinimumSize(this.getPreferredSize());
		}

		public void paint(Graphics g) {
			if (needsScaling()) {
				g.drawImage(originalImg, 0, 0, null);
			} else {
				g.drawImage(originalImg, 0, 0, scaled.width, scaled.height, null);
			}
	        
	    }
	}

	public ImageView(ImageModel aModel, boolean isGridView) {
		super();
		this.model = aModel;
		this.layoutView(isGridView);
		this.registerControllers();
		this.model.addView(new IView() {
			public void updateView() {
				repaint();
				stars.revalidate();
	        	stars.repaint();
			}
		});
		revalidate();
		repaint();
		stars.revalidate();
		stars.repaint();
	}

	private void layoutView(boolean isGridView) {
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		metadata = new JPanel();
		stars = new view.StarRatings();
		stars.setModel(model.getRating());
		JLabel path = new JLabel(model.getFileName());
		JLabel date = new JLabel(model.getDate()); 
		path.setAlignmentX(Component.LEFT_ALIGNMENT);
		date.setAlignmentX(Component.LEFT_ALIGNMENT);
		stars.setAlignmentX(Component.LEFT_ALIGNMENT);
		metadata.add(stars);
		metadata.add(date);
		metadata.add(path);

		metadata.setBackground(Color.WHITE);
		metadata.setLayout(new FlowLayout());
		metadata.setPreferredSize(new Dimension(150, 100));
		metadata.setMaximumSize(this.getPreferredSize());
		metadata.setMinimumSize(this.getPreferredSize());

		imgContainer = new JPanel();
		imgContainer.setPreferredSize(new Dimension(150, 100));
		imgContainer.setMaximumSize(this.getPreferredSize());
		imgContainer.setMinimumSize(this.getPreferredSize());
		imgContainer.setLayout(new BoxLayout(imgContainer, BoxLayout.Y_AXIS));

		image = new ResizedImage();
		imgContainer.add(image);
		imgContainer.setBackground(Color.WHITE);
		
		if (isGridView) {
			setToGridView();
		} else {
			setToListView();
		}
	}

	private void registerControllers() {
		this.stars.addStarRatingsChangedEventListener(new StarRatings.StarRatingsChangeEventListener() {
			public void StarRatingsChangeEventOccurred(StarRatings.StarRatingsChangeEvent e) {
				model.setRating(e.getModel());
			}
		});
		this.imgContainer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFrame frame = new JFrame("Fotag Image View");
				BigImage panel = new BigImage(model.getImage(), model.getOriginalDimension());
				frame.getContentPane().setLayout(new BorderLayout());
				frame.getContentPane().add(panel, BorderLayout.CENTER);
				frame.setSize(panel.getPreferredSize());
				frame.setVisible(true);
				frame.setResizable(false);
		    }
		});
	}

	public void setToGridView() {
		this.setLayout(new BorderLayout());
		this.add(imgContainer, BorderLayout.NORTH);
		this.add(metadata, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(150, 200));
		this.setMaximumSize(this.getPreferredSize());
		this.setMinimumSize(this.getPreferredSize());
	}

	public void setToListView() {
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setLayout(new BorderLayout());
		this.add(imgContainer, BorderLayout.WEST);
		this.add(metadata, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(300, 100));
		this.setMaximumSize(this.getPreferredSize());
		this.setMinimumSize(this.getPreferredSize());
	}
}
