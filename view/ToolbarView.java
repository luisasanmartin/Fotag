package view;

import model.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ToolbarView extends JPanel {
	private ImageCollectionModel model;
	private view.StarRatings stars;
	private JLabel sortBy;
	private JRadioButton  tbGrid;
	private JLabel lList;
	private JRadioButton  tbList;
	private JLabel lGrid;
	private JButton open;
	private JFileChooser fc;

	public ToolbarView(ImageCollectionModel aModel) {
		super();
		this.model = aModel;
		this.layoutView();
		this.registerControllers();
		this.model.addView(new IView() {
			public void updateView() {
				stars.revalidate();
				stars.repaint();
				repaint();
			}
		});
	}

	private void layoutView() {
		/* Sort by : stars */
		this.setBackground(Color.WHITE);
		stars = new view.StarRatings();
		stars.setModel(0);
		sortBy = new JLabel("Sort by: ");

		JPanel sorting = new JPanel();
		sorting.setBackground(Color.WHITE);
		sorting.add(sortBy);
		sorting.add(stars);

		/* View display list or grid */
		lList = new JLabel("List: ");
		lGrid = new JLabel("Grid: ");
		tbList = new JRadioButton();
		tbGrid = new JRadioButton();

		ButtonGroup group = new ButtonGroup();
		group.add(tbList);
		group.add(tbGrid);
		tbList.setOpaque(false);
		tbGrid.setOpaque(false);

		tbGrid.setSelected(true);

		lList.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lGrid.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tbList.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tbGrid.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JPanel viewContainer = new JPanel();
		viewContainer.setBackground(Color.WHITE);
		JPanel listContainer = new JPanel();
		listContainer.setBackground(Color.WHITE);
		JPanel gridContainer = new JPanel();
		gridContainer.setBackground(Color.WHITE);

		listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.X_AXIS));
		listContainer.add(lList);
		listContainer.add(tbList);
		listContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

		gridContainer.setLayout(new BoxLayout(gridContainer, BoxLayout.X_AXIS));
		gridContainer.add(lGrid);
		gridContainer.add(tbGrid);
		gridContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.Y_AXIS));
		viewContainer.add(gridContainer);
		viewContainer.add(listContainer);
		
		/* Open new file */
		open = new JButton("Open new image");
		fc = new JFileChooser();

		/* Put it on the panel */
		this.setLayout(new BorderLayout());
		this.add(viewContainer, BorderLayout.WEST);
		this.add(sorting, BorderLayout.CENTER);
		this.add(open, BorderLayout.EAST);
		
	}

	private void registerControllers() {
		this.stars.addStarRatingsChangedEventListener(new StarRatings.StarRatingsChangeEventListener() {
			public void StarRatingsChangeEventOccurred(StarRatings.StarRatingsChangeEvent e) {
				model.setMinRating(e.getModel());
			}
		});
		this.tbList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setAsListView();
		    }
		});
		this.tbGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setAsGridView();
		    }
		});
		this.open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    model.addImage(file);
                }
		    }
		});
	}

	private class MController extends MouseInputAdapter {
	} // MController
}
