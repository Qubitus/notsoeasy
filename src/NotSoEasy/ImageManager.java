package NotSoEasy;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import Card.Card;
import Card.Card.Rank;
import Card.Card.Suit;

/**
 * An image manager implementation class
 * 
 * @author Pyck Nicolas
 * 
 */
public class ImageManager {

	private static ImageManager instance;

	private HashMap<String, BufferedImage> images;

	/**
	 * Private ImageManager constructor.
	 */
	private ImageManager() {
		images = new HashMap<>();

		loadImages();
	}

	/**
	 * Gets the Singleton instance object.
	 * 
	 * @return The Singleton instance.
	 */
	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}

	/**
	 * Loads all necessary images.
	 */
	private void loadImages() {
		Color coverRoundColor = new Color(120, 37, 40);
		Color cardRoundColor = new Color(155, 161, 157);

		Map<String, Color> imageEntries = new HashMap<>();
		imageEntries.put("Background.jpg", null);
		imageEntries.put("Bobonne.png", null);
		imageEntries.put("Silenzio.png", null);
		imageEntries.put("Cover.png", coverRoundColor);

		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				imageEntries.put(rank.name() + " of " + suit.name() + ".png",
						cardRoundColor);
			}
		}

		// Load the images using the information from imageEntries
		String basePath = "/Resources/";
		for (Entry<String, Color> imageEntry : imageEntries.entrySet()) {
			URL imageURL = Card.class.getResource(basePath
					+ imageEntry.getKey());
			try {
				BufferedImage image;
				if (imageEntry.getValue() != null)
					image = makeRoundedCorner(ImageIO.read(imageURL),
							imageEntry.getValue());
				else
					image = ImageIO.read(imageURL);
				images.put(imageEntry.getKey().split("\\.")[0], image);
			} catch (IOException e) {
				System.err.println("Error while loading image: "
						+ imageURL.toString());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gives a specified BufferedImage rounded corners.
	 * 
	 * @param image
	 *            The BufferedImage to be manipulated.
	 * @param borderColor
	 *            The Color of the rounded border.
	 * @return The manipulated BufferedImage.
	 */
	private static BufferedImage makeRoundedCorner(BufferedImage image,
			Color borderColor) {
		int cornerRadius = 9;
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage output = new BufferedImage(w + 1, h + 1,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = output.createGraphics();

		// This is what we want, but it only does hard-clipping, i.e. aliasing
		// g2.setClip(new RoundRectangle2D ...)

		// so instead fake soft-clipping by first drawing the desired clip shape
		// in fully opaque white with antialiasing enabled...
		g2.setComposite(AlphaComposite.Src);
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius,
				cornerRadius));

		// ... then compositing the image on top,
		// using the white shape from above as alpha source
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(image, 0, 0, null);

		g2.setComposite(AlphaComposite.Src);
		g2.setColor(borderColor);
		g2.draw(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius,
				cornerRadius));

		g2.dispose();

		return output;
	}

	/**
	 * Returns the BufferedImage with the specified name.
	 * 
	 * @param name
	 *            The name of the BufferedImage to be retrieved.
	 * @return The retrieved BufferedImage.
	 */
	public BufferedImage getImage(String name) {
		return images.get(name);
	}
}
