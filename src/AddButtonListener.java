import java.awt.event.*;
import javax.swing.*;

public class AddButtonListener implements ActionListener {

	static JFrame mainFrame;

	AddButtonListener(JFrame frame) {
		mainFrame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame addFrame = new AddDriverFrame(mainFrame);
		addFrame.setVisible(true);
		mainFrame.setVisible(false);
	}
}
