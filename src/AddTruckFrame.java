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

public class AddTruckFrame extends JFrame {
	private JTextField truckModelTextField;
	private JTextField ownerTextField;
	private JTextField driverTextField;

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
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String model = truckModelTextField.getText();
				String owner = ownerTextField.getText();
				String driver = driverTextField.getText();

				// make sure user has entered all required input
				if (model.equals("") || owner.equals("") || driver.equals(""))
					throw new UnsupportedOperationException();

				/* Update DB with new record */
				try {
					/* Get DB connection */
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/project", "root", "root");
					Statement stmt = con.createStatement();

					/* Query to update, deliveryperweek is optional */

					stmt.executeUpdate("insert into truck " + "(truckmodel, ownsdriverid, drivesdriverid) "
							+ "values (\"" + model + "\", \"" + Integer.parseInt(owner) + "\", \"" + Integer.parseInt(driver) + "\")");

					con.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		/* Back Button */
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this, mainFrame));

		JLabel lblTruckModel = new JLabel("Truck Model *");
		textFieldPanel.add(lblTruckModel);

		truckModelTextField = new JTextField();
		truckModelTextField.setColumns(10);
		textFieldPanel.add(truckModelTextField);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);
		mainPanel.add(textFieldPanel);

		JLabel lblOwner = new JLabel("Owner ID *");
		textFieldPanel.add(lblOwner);
		ownerTextField = new JTextField();
		ownerTextField.setColumns(10);

		textFieldPanel.add(ownerTextField);

		JLabel lblDriver = new JLabel("Driver ID *");
		textFieldPanel.add(lblDriver);

		driverTextField = new JTextField();
		driverTextField.setColumns(10);
		textFieldPanel.add(driverTextField);
		mainPanel.add(buttonPanel);

		getContentPane().add(mainPanel);

	}

}