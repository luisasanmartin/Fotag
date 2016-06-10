import javax.swing.JFrame;
import javax.swing.JPanel;

import model.ImageModel;
import model.ImageCollectionModel;
import view.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import java.awt.event.KeyEvent;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Fotag");
		model.ImageCollectionModel model = new ImageCollectionModel();
		ImageCollectionView vImages = new ImageCollectionView(model, frame);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(vImages, BorderLayout.CENTER);
		frame.setSize(900, 600);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
