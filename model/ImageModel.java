package model;

// Note!  Nothing from the view package is imported here.
import java.util.ArrayList;
import java.util.ListIterator;
import java.time.LocalDate;
import java.awt.Dimension;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.text.SimpleDateFormat;

public class ImageModel extends Object {
	/* A list of the model's views. */
	private ArrayList<IView> views = new ArrayList<IView>();

	/* The object's data */
	private int rating; // value from 0 to 5
	private String date;
	private String fileName;
	private File file;
	private boolean isVisible = true;
	//private Dimension imgSize;

	/* The image data */
	private BufferedImage originalImg;
	private Dimension originalDim;
	private Dimension currentDim;
	private Dimension box = new Dimension(150, 100);

	private ImageCollectionModel parent;

	public ImageModel(File f, ImageCollectionModel p, int r) {
		this(f, p);
		rating = r;
	}

	public ImageModel(File f, ImageCollectionModel p) {
		rating = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		date = sdf.format(f.lastModified());
		parent = p;
		file = f;

		try {
			originalImg = ImageIO.read(file);
			originalDim = new Dimension(originalImg.getWidth(), originalImg.getHeight());
			currentDim = getScaledDimension(originalDim, box);
		} catch (IOException e) {
			System.out.println("Error opening image:");
			System.out.println(e);
		}
	}

	public BufferedImage getImage() {
		return originalImg;
	}

	public Dimension getResizedDimension() {
		return currentDim;
	}

	public Dimension getOriginalDimension() {
		return originalDim;
	}

	public String getFilePath() {
		return file.getAbsolutePath();
	}

	public String getFileName() {
		return file.getName();
	}

	public String getDate() {
		return date;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int r) {
		rating = r;
		this.updateAllViews();
		parent.ratingChanged();
	}

	public void setVisible(boolean v) {
		isVisible = v;
		// this.updateAllViews();
	}

	public static Dimension getScaledDimension(Dimension doriginal, Dimension dbox) {
    	double xScale = doriginal.width / dbox.width;
    	double yScale = doriginal.height / dbox.height;
    	if (xScale > yScale) {
    		return new Dimension(dbox.width, (int) (doriginal.height / xScale));
    	} else {
    		return new Dimension((int) (doriginal.width / yScale), dbox.height);
    	}
    }

	// public void setDimension(int w, int h) {
	// 	imgSize = new Dimension(w, h);
	// }

	// public Dimension getDimension() {
	// 	return imgSize;
	// }

	/** Add a new view of this triangle. */
	public void addView(IView view) {
		this.views.add(view);
		view.updateView();
	}

	/** Remove a view from this triangle. */
	public void removeView(IView view) {
		this.views.remove(view);
	}

	/** Update all the views that are viewing this triangle. */
	private void updateAllViews() {
		for (IView view : this.views) {
			view.updateView();
		}
	}
}