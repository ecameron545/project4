import java.awt.event.*;
import javax.swing.*;

public class BackButtonListener implements ActionListener {

	JFrame newFrame;
	JFrame mainFrame;

	BackButtonListener(JFrame newFrame, JFrame mainFrame) {
		this.newFrame = newFrame;
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		newFrame.setVisible(false);
		newFrame.dispose();
		mainFrame.setVisible(true);
	}
}
