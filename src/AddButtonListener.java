import java.awt.event.*;
import javax.swing.*;

public class AddButtonListener implements ActionListener {

	static JFrame mainFrame;

	AddButtonListener(JFrame frame) {
		mainFrame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		JFrame addFrame = null;
		
		if (source.getText().equals("Add Driver")) {
			addFrame = new AddDriverFrame(mainFrame);
		} else if (source.getText().equals("Add Truck")) {
			addFrame = new AddTruckFrame(mainFrame);
		} else {
			addFrame = new AddStoreFrame(mainFrame);
		}

		addFrame.setVisible(true);
		mainFrame.setVisible(false);
	}
}
