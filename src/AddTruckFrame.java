import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddTruckFrame extends JFrame {
	private JTextField textField;
	private JTextField textField_2;


	/**
	 * Create the frame.
	 */
	public AddTruckFrame(JFrame mainFrame) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 277, 184);
		this.setTitle("Add New Truck");
		this.setLocationRelativeTo(null);
		
		/* Button and text field panels */
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel textFieldPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));

		/* Text Field for user input */
		
		/* Submit Button */
		JButton submitButton = new JButton("Submit");
		//submitButton.addActionListener(new BackButtonListener(addFrame, mainFrame));
		
		/* Back Button */
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this, mainFrame));
		
		JLabel lblL = new JLabel("Truck Model *");
		textFieldPanel.add(lblL);
		
		textField = new JTextField();
		textField.setColumns(10);
		textFieldPanel.add(textField);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);
		mainPanel.add(textFieldPanel);
		
		JLabel lblLastName = new JLabel("Owner ID *");
		textFieldPanel.add(lblLastName);
		JTextField truckerTextField = new JTextField();
		truckerTextField.setColumns(10);
		
		
		textFieldPanel.add(truckerTextField);
		
		JLabel lblDriverId_1 = new JLabel("Driver ID *");
		textFieldPanel.add(lblDriverId_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textFieldPanel.add(textField_2);
		mainPanel.add(buttonPanel);

		getContentPane().add(mainPanel);
		
	}

}