import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddStoreFrame extends JFrame {
	private JTextField nameTextField;
	private JTextField stateTextField;
	private JTextField cityTextField;
	private JTextField streetTextField;
	private JTextField deliveryTextField;

	/**
	 * Create the frame.
	 */
	public AddStoreFrame(JFrame mainFrame) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 277, 276);
		this.setTitle("Add New Store");
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
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String state = stateTextField.getText();
				String city = cityTextField.getText();
				String street = streetTextField.getText();
				String deliveryPerWeek = deliveryTextField.getText();

				// make sure user has entered all required input
				if (name.equals("") || state.equals("") || city.equals("") || street.equals(""))
					throw new UnsupportedOperationException();

				/* Update DB with new record */
				try {
					/* Get DB connection */
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/project", "root", "root");
					Statement stmt = con.createStatement();
					
					/* Query to update, deliveryperweek is optional */
					if(deliveryPerWeek.equals(""))
						stmt.executeUpdate("insert into store " + "(name, state, city, street) "
								+ "values (\"" + name + "\", \"" + state + "\", \"" + city + "\", \"" + street + "\")");
					else
						stmt.executeUpdate("insert into store " + "(name, state, city, street, deliveryperweek) "
								+ "values (\"" + name + "\", \"" + state + "\", \"" + city + "\", \"" 
											 + street + "\", \"" + Integer.parseInt(deliveryPerWeek) +"\")");

					con.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		/* Back Button */
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this, mainFrame));
		
		JLabel lblName = new JLabel("Name *");
		textFieldPanel.add(lblName);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		textFieldPanel.add(nameTextField);
		
		JLabel lblState = new JLabel("State *");
		textFieldPanel.add(lblState);
		
		stateTextField = new JTextField();
		stateTextField.setColumns(10);
		textFieldPanel.add(stateTextField);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);
		mainPanel.add(textFieldPanel);
		
		JLabel lblCity = new JLabel("City *");
		textFieldPanel.add(lblCity);
		cityTextField = new JTextField();
		cityTextField.setColumns(10);
		
		
		textFieldPanel.add(cityTextField);
		
		JLabel lblStreet = new JLabel("Street *");
		textFieldPanel.add(lblStreet);
		
		streetTextField = new JTextField();
		streetTextField.setColumns(10);
		textFieldPanel.add(streetTextField);
		
		JLabel lblDelivery = new JLabel("Deliveries Per Week");
		textFieldPanel.add(lblDelivery);
		
		deliveryTextField = new JTextField();
		deliveryTextField.setColumns(10);
		textFieldPanel.add(deliveryTextField);
		mainPanel.add(buttonPanel);

		getContentPane().add(mainPanel);
		
	}

}