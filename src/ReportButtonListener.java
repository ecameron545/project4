import java.awt.event.*;
import javax.swing.*;

public class ReportButtonListener implements ActionListener {

	static JFrame mainFrame;

	ReportButtonListener(JFrame frame) {
		mainFrame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame reportFrame = new ReportFrame(mainFrame);
		reportFrame.setVisible(true);
		mainFrame.setVisible(false);
	}
}