package NotSoEasy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import State.StateManager;

public class NotSoEasyFrame extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private GameTable gameTable;
	private StateManager stateManager;

	private JMenuItem newGameMenuItem;
	private JMenuItem resetGameMenuItem;
	private JMenuItem undoMoveMenuItem;
	private JMenuItem redoMoveMenuItem;
	private JMenuItem exitGameMenuItem;

	private ButtonGroup cardsizeButtonGroup;
	private JCheckBoxMenuItem extraLargeCardsizeMenuItem;
	private JCheckBoxMenuItem largeCardsizeMenuItem;
	private JCheckBoxMenuItem mediumCardsizeMenuItem;
	private JCheckBoxMenuItem smallCardsizeMenuItem;
	private JCheckBoxMenuItem extraSmallCardsizeMenuItem;

	private JMenuItem aboutMenuItem;

	public NotSoEasyFrame() {
		super("Bobonne's Challenge");

		gameTable = new GameTable();
		setLayout(new GridLayout());
		getContentPane().add(gameTable);

		stateManager = gameTable.getStateManager();
		stateManager.addObserver(this);

		initMenuBar();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		mediumCardsizeMenuItem.doClick();
	}

	private void initMenuBar() {
		newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		newGameMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stateManager.init();
			}
		});

		resetGameMenuItem = new JMenuItem("Reset Game");
		resetGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		resetGameMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stateManager.reset();
			}
		});

		undoMoveMenuItem = new JMenuItem("Undo Move");
		undoMoveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		undoMoveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stateManager.undo();
			}
		});

		redoMoveMenuItem = new JMenuItem("Redo Move");
		redoMoveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				ActionEvent.CTRL_MASK));
		redoMoveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stateManager.redo();
			}
		});

		exitGameMenuItem = new JMenuItem("Exit");
		exitGameMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});

		cardsizeButtonGroup = new ButtonGroup();

		extraLargeCardsizeMenuItem = new JCheckBoxMenuItem("Extra Large");
		extraLargeCardsizeMenuItem
				.addActionListener(new CardSizeActionListener(150));

		largeCardsizeMenuItem = new JCheckBoxMenuItem("Large");
		largeCardsizeMenuItem
				.addActionListener(new CardSizeActionListener(125));

		mediumCardsizeMenuItem = new JCheckBoxMenuItem("Medium");
		mediumCardsizeMenuItem
				.addActionListener(new CardSizeActionListener(100));

		smallCardsizeMenuItem = new JCheckBoxMenuItem("Small");
		smallCardsizeMenuItem.addActionListener(new CardSizeActionListener(75));

		extraSmallCardsizeMenuItem = new JCheckBoxMenuItem("Extra Small");
		extraSmallCardsizeMenuItem
				.addActionListener(new CardSizeActionListener(50));

		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				about();
			}
		});

		// Add the 'Game' MenuItems
		JMenu gameMenu = new JMenu("Game");
		gameMenu.add(newGameMenuItem);
		gameMenu.add(resetGameMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(undoMoveMenuItem);
		gameMenu.add(redoMoveMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(exitGameMenuItem);

		// Add the 'Options' MenuItems
		JMenu optionsMenu = new JMenu("Options");
		JMenu cardsizeMenu = new JMenu("Cardsize");

		cardsizeMenu.add(extraLargeCardsizeMenuItem);
		cardsizeMenu.add(largeCardsizeMenuItem);
		cardsizeMenu.add(mediumCardsizeMenuItem);
		cardsizeMenu.add(smallCardsizeMenuItem);
		cardsizeMenu.add(extraSmallCardsizeMenuItem);

		cardsizeButtonGroup.add(extraLargeCardsizeMenuItem);
		cardsizeButtonGroup.add(largeCardsizeMenuItem);
		cardsizeButtonGroup.add(mediumCardsizeMenuItem);
		cardsizeButtonGroup.add(smallCardsizeMenuItem);
		cardsizeButtonGroup.add(extraSmallCardsizeMenuItem);

		optionsMenu.add(cardsizeMenu);

		// Add the 'Help' MenuItems
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(aboutMenuItem);

		// Add the menus to the menubar
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gameMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);

		// Add the menubar to the window
		this.setJMenuBar(menuBar);

		updateMenu();
	}

	public void updateMenu() {
		undoMoveMenuItem.setEnabled(stateManager.canUndo());
		redoMoveMenuItem.setEnabled(stateManager.canRedo());
	}

	private void about() {
		JDialog aboutDialog = new AboutDialog(this);
		aboutDialog.setVisible(true);
	}

	private void exit() {
		setVisible(false);
		System.exit(0);
	}

	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO: handle exception
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
		} catch (InstantiationException e) {
			// TODO: handle exception
		} catch (IllegalAccessException e) {
			// TODO: handle exception
		}
		NotSoEasyFrame game = new NotSoEasyFrame();
		game.setVisible(true);
	}

	@Override
	public void update(Observable stateManager, Object arg) {
		updateMenu();
	}

	private class CardSizeActionListener implements ActionListener {

		private int width;

		public CardSizeActionListener(int width) {
			this.width = width;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			gameTable.setCardWidth(width);

			// Calculate and set the new minimum size for main window
			Insets insets = getInsets();
			Dimension menuBarSize = getJMenuBar().getSize();
			Dimension minimumSize = new Dimension(insets.left
					+ gameTable.getMinimumSize().width + insets.right,
					insets.top + menuBarSize.height
							+ gameTable.getMinimumSize().height + insets.bottom);
			setMinimumSize(minimumSize);
		}
	}

}
