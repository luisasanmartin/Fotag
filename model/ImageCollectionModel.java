package model;

// Note!  Nothing from the view package is imported here.
import java.util.ArrayList;
import java.util.ListIterator;
import java.time.LocalDate;
import java.awt.Dimension;
import java.io.*;

public class ImageCollectionModel extends Object {
	/* A list of the model's views. */
	private ArrayList<IView> views = new ArrayList<IView>();

	private ArrayList<ImageModel> allImages = new ArrayList<ImageModel>();
	private ArrayList<ImageModel> imagesToDisplay = new ArrayList<ImageModel>();

	private int minRating = 0;
	private boolean isGridView = true;

	private Dimension imageBox = new Dimension(150, 100);
	
	// Override the default construtor, making it private.
	public ImageCollectionModel() {
		loadFromFile();
		setMinRating(0);
		this.updateAllViews();
	}

	public void loadFromFile() {
		String path = "FotagModel.txt";
    	//File file = new File(path);
		//ArrayList<String> input = new ArrayList<String>();
		int numImages = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line = reader.readLine();
			String filePath, rating;
			numImages = Integer.parseInt(line);
			for (int i = 0; i < numImages; i++) {
				filePath = reader.readLine();
				rating = reader.readLine();
				File inFile = new File(filePath);
				ImageModel newImage = new ImageModel(inFile, this, Integer.parseInt(rating));
				allImages.add(newImage);
				imagesToDisplay.add(newImage);
			}
			this.updateAllViews();
		} catch (IOException x) {
			System.out.println("error");
		}
	}

	public ArrayList<ImageModel> getImages() {
		return imagesToDisplay;
	}

	public void addImage(File f) {
		ImageModel newImage = new ImageModel(f, this);
		allImages.add(newImage);
		imagesToDisplay.add(newImage);
		this.updateAllViews();
	}

	public void setMinRating(int r) {
		minRating = r;
		imagesToDisplay = new ArrayList<ImageModel>();
		for (ImageModel i: allImages) {
			if (i.getRating() >= minRating) {
				imagesToDisplay.add(i);
			}
		}
		this.updateAllViews();
	}

	public void setAsGridView() {
		isGridView = true;
		this.updateAllViews();
	}

	public void setAsListView() {
		isGridView = false;
		this.updateAllViews();
	}

	public boolean isGridView() {
		return isGridView;
	}

	public boolean isListView() {
		return !isGridView;
	}

	public void ratingChanged() {
		if (minRating != 0) {
			imagesToDisplay = new ArrayList<ImageModel>();
			for (ImageModel i: allImages) {
				if (i.getRating() >= minRating) {
					imagesToDisplay.add(i);
				}
			}
			this.updateAllViews();
		}
	}

	public String toString() {
		String retVal = "";
		retVal += allImages.size() + "\n";
		for (ImageModel i: allImages) {
			retVal += i.getFilePath() + "\n";
			retVal += i.getRating() + "\n";
		}
		return retVal;
	}

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