package project.text.adventure.gameplay;

import project.text.adventure.gameitems.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Creates the game window, take commands from user and make sure that the user sees the changes made. When player gets game
 * over, the player can start over if they wish. If that is the case, a new set of the game rooms are created and the player
 * starts from the beginning.
 */
public class AdventureViewer implements ActionListener
{
    private JFrame frame;
    private JLabel gameLabel;
    private JTextField commandInput;
    private Command cmd = null;
    private final static int FRAME_SIZE = 450;
    private final static int FIRST_ROOM = 0;

    public AdventureViewer() {
	frame = new JFrame("Space Adventure");
	gameLabel = new JLabel();
	commandInput = new JTextField();
	restart();
    }

    public void show() {
	frame.setLayout(new BorderLayout());
	frame.getContentPane().setBackground(Color.BLACK);
	gameLabel.setForeground(Color.WHITE);
	frame.getContentPane().add(gameLabel, BorderLayout.NORTH);
	frame.getContentPane().add(commandInput, BorderLayout.SOUTH);
	commandInput.setText("Enter command");
	commandInput.selectAll();
	commandInput.addActionListener(this);

	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	frame.pack();
	frame.setSize(FRAME_SIZE, FRAME_SIZE);
	frame.setVisible(true);
    }

    public void addDescription(Room currentRoom) {
	String description = currentRoom.getDescription();
	gameLabel.setText("<html><br/>" + description + "<html/>");
    }

    public void addText(String text) {
	String previousText = gameLabel.getText();
	gameLabel.setText("<html>" + previousText + "<br/>" + text);
    }

    public void setText(String text) {
	gameLabel.setText("<html><br/>" + text + "<br/><html/>");
    }

    @Override public void actionPerformed(final ActionEvent e) {
	String command = commandInput.getText();
	setText("Entered command: " + command);
	commandInput.setText("Enter command");
	commandInput.selectAll();
	String retStr = cmd.doCommand(command);
	if (retStr.equals("exit")) {
	    askIfQuit();
	}
	else if (!retStr.isEmpty()) {
	    addText(retStr);
	}
	if (cmd.isGameWon()) {
	    commandInput.setEditable(false);
	    addText("<p style='color:#969696'>YOU WON!</p>");
	}
	if (cmd.isGameOver()) {
	    if (!askIfRestart()) {
		commandInput.setEditable(false);
		addText("<p style='color:#969696'>GAME OVER</p>");
		askIfQuit();
	    } else {
	        restart();
	    }
	}
    }

    public boolean askIfRestart() {
	Object[] options = new Object[] { "Yes", "No" };                //Customizes buttons, since they originally were in swedish
	int n = JOptionPane.showOptionDialog(frame, "You have died. \n" + "Would you like to try again?",
					     "GAME OVER",
					     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					     options[1]);
	return n != JOptionPane.NO_OPTION;
    }

    public void askIfQuit() {
	Object[] options = new Object[] { "Yes",
		"No" };                        //Customizes buttons
	int n = JOptionPane.showOptionDialog(frame, "Do you want to exit game?", "Exit game?",
					     JOptionPane.YES_NO_OPTION,
					     JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
	if (n == JOptionPane.YES_OPTION) {
	    System.exit(0);
	}
    }

    public void restart() {
	    SpaceStation spaceStation = new SpaceStation();
	    List<Room> rooms = spaceStation.getRooms();
	    Room firstRoom = rooms.get(FIRST_ROOM);
	    addDescription(firstRoom);
	    cmd = new Command(firstRoom, rooms);
    }
}
