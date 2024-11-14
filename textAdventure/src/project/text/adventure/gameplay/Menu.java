package project.text.adventure.gameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Creates a start menu, from which one can either start the game or exit it.
 */
public class Menu extends JComponent
{
    private JFrame menuFrame = new JFrame("Space adventure");
    private final ImageIcon img = new ImageIcon((ClassLoader.getSystemResource("galaxyWithTitle.jpg")));
    private static final int FRAME_SIZE = 450;

    public void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	img.paintIcon(this, g, 0, 0);
    }

    public void startMenuFrame() {
	menuFrame.setLayout(new BorderLayout());
	menuFrame.add(new Menu(), BorderLayout.CENTER);
	menuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	menuFrame.setSize(FRAME_SIZE, FRAME_SIZE);
	JLabel label = new JLabel(img);
	label.setLayout( new FlowLayout() );
	JButton start = new JButton("Start game");
	JButton exit = new JButton("Exit game");
	label.add(start);
	label.add(exit);
	start.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(ActionEvent e)
	    {
		    AdventureViewer startGame = new AdventureViewer();
		    startGame.show();
		    menuFrame.dispose();
	    }
	});
	exit.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	    }
	});
	menuFrame.add(label);
	menuFrame.setVisible(true);
    }
}

