import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class InformationButtonListener implements ActionListener {

	JFrame mainFrame;
	
	InformationButtonListener(JFrame mainFrame){
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame truckerFrame = null;
		truckerFrame = new TruckerInformationFrame(mainFrame);
		truckerFrame.setVisible(true);
		mainFrame.setVisible(false);
	}

}
