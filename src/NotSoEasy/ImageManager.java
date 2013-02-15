package NotSoEasy;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Card.Card;
import Card.Card.Rank;
import Card.Card.Suit;

public class ImageManager {

	private static ImageManager instance;
	
	private HashMap<String, BufferedImage> images;
	
	private ImageManager() {
		images = new HashMap<>();
		
		loadImages();
	}
	
	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}
	
	private void loadImages() {
		String basePath = "/Resources/";
		
		URL coverURL = Card.class.getResource(basePath + "NewCover.png");
		URL backgroundURL = Card.class.getResource(basePath + "Background.jpg");
		URL silenzioURL = ImageManager.class.getResource(basePath + "Silenzio.png");
		try {
			images.put("Silenzio", ImageIO.read(silenzioURL));
			images.put("Background", ImageIO.read(backgroundURL));
			images.put("Cover", makeRoundedCorner(ImageIO.read(coverURL), 9, new Color(120, 37, 40)));
		}
		catch (Exception e) {
			System.err.println("Error while loading image!");
			e.printStackTrace();
		}
		
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				String cardPath = basePath + rank.name() + "_of_" + suit.name() + ".png";
				URL cardURL = Card.class.getResource(cardPath);
				try {
					BufferedImage roundedCard = makeRoundedCorner(ImageIO.read(cardURL), 9, new Color(155, 161, 157));
					images.put(rank.name() + " of " + suit.name(), roundedCard);
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: Something when image can't be loaded
				}
			}
		}
	}


	private static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius, Color borderColor) {
	    int w = image.getWidth();
	    int h = image.getHeight();
	    BufferedImage output = new BufferedImage(w+1, h+1, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = output.createGraphics();

	    // This is what we want, but it only does hard-clipping, i.e. aliasing
	    // g2.setClip(new RoundRectangle2D ...)

	    // so instead fake soft-clipping by first drawing the desired clip shape
	    // in fully opaque white with antialiasing enabled...
	    g2.setComposite(AlphaComposite.Src);
	    g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

	    // ... then compositing the image on top,
	    // using the white shape from above as alpha source
	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);
	    
	    g2.setComposite(AlphaComposite.Src);
	    g2.setColor(borderColor);
	    g2.draw(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

	    g2.dispose();

	    return output;
	}
	
	public BufferedImage getImage(String name) {
		return images.get(name);
	}
}
