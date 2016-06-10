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
import java.io.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ImageCollectionView extends JPanel {
	private ImageCollectionModel model;
	private JPanel imageHolder;
	private ToolbarView toolbar;
	private JFrame frame;

	private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();;

	public ImageCollectionView(ImageCollectionModel aModel, JFrame f) {
		super();
		this.model = aModel;
		this.frame = f;
		this.layoutView();
		this.registerControllers();
		this.model.addView(new IView() {
			public void updateView() {

				for (view.ImageView v : imageViews) {
					imageHolder.remove(v);
				}
				imageViews = new ArrayList<ImageView>();
				for (model.ImageModel image: model.getImages()) {
					ImageView i = new ImageView(image, model.isGridView());
					imageViews.add(i);
					imageHolder.add(i);
				}

				if (model.isGridView()) {
					imageHolder.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 50));

				} else {
					imageHolder.setLayout(new BoxLayout(imageHolder, BoxLayout.Y_AXIS));
				}
				revalidate();
				repaint();
			}
		});
	}

	private void layoutView() {
		this.setBackground(Color.WHITE);
		toolbar = new ToolbarView(this.model);
		imageHolder = new JPanel();
		imageHolder.setBackground(Color.WHITE);
		imageHolder.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 50));
		this.setLayout(new BorderLayout());
		this.add(toolbar, BorderLayout.NORTH);
		this.add(imageHolder, BorderLayout.CENTER);
		
	}

	private void registerControllers() {
		frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing( java.awt.event.WindowEvent evt ) {
	        	try {
		        	String path = "FotagModel.txt";
		        	File f = new File(path);
		        	if (!f.exists()) {
		        		f.createNewFile();
		        	}
		        	String s = model.toString();
		        	PrintWriter out = new PrintWriter(path, "UTF-8");
		        	out.println(s);
		        	out.close();
		        } catch (Exception e) {
		        	System.out.println(e);
		        }
	        	System.exit( 0 );
	        }
	    });
	}

	private class MController extends MouseInputAdapter {
	} // MController
}
