import javax.swing.*;
import java.awt.BorderLayout;

class MainFrame{
	
	public static JFrame setup(){
		
		/* Create the main frame */
		JFrame frame = new JFrame("Trucks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 128);
		frame.setLocationRelativeTo(null);
		
		/* Panel to hold buttons */
		JPanel buttonPanel = new JPanel();
		
		/* Add buttons to the button panel */
		JButton truckerButton = new JButton("Trucker Information");
		truckerButton.addActionListener(new InformationButtonListener(frame));
		buttonPanel.add(truckerButton);
		
		JButton reportButton = new JButton("Weekly Report");
		reportButton.addActionListener(new ReportButtonListener(frame));
		buttonPanel.add(reportButton);
		
		JButton addButton = new JButton("Add New");
		addButton.addActionListener(new AddButtonListener(frame));
		buttonPanel.add(addButton);
		
		/* Add button panel to the main frame */
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		return frame;
	}

	public static void main(String args[]) {
		JFrame mainFrame = setup();
		
		mainFrame.setVisible(true);
	}
}