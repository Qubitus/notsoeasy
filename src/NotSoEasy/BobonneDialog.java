package NotSoEasy;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BobonneDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public BobonneDialog(Component parent, String title, String message) {
		setLayout(new FlowLayout());

		ImageIcon icon = new ImageIcon(ImageManager.getInstance().getImage("Bobonne"));
	    JLabel label = new JLabel(icon);
	    label.setMinimumSize(new Dimension(icon.getIconHeight(), icon.getIconWidth()));
	    label.setAlignmentX(0.5f);
	    add(label);

	    JPanel textPanel = new JPanel(new GridLayout(2,1));
	    JLabel bobonneLabel = new JLabel("Bobonne says: ");
	    bobonneLabel.setFont(new Font("Serif", Font.BOLD, 16));
	    JLabel textLabel = new JLabel(message);
	    textLabel.setFont(new Font("Serif", Font.PLAIN, 13));
	    textPanel.add(bobonneLabel);
	    textPanel.add(textLabel);
	    add(textPanel);

	    setResizable(false);
	    setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle(title);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    
	    pack();
	    setLocationRelativeTo(parent);
	}
	
}
