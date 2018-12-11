import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddDriverFrame extends JFrame {
	private JTextField fNameTextField;
	private JTextField lNameTextField;
	private JTextField birthTextField;
	private JTextField licenseTextField;

	/**
	 * Create the frame.
	 */
	public AddDriverFrame(JFrame mainFrame) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 277, 236);
		this.setTitle("Add New Driver");
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
				String fName = fNameTextField.getText();
				String lName = lNameTextField.getText();
				String licenseNo = licenseTextField.getText();
				String birthDate = birthTextField.getText();

				// make sure user has entered all required input
				if (fName.equals("") || lName.equals("") || licenseNo.equals("") || birthDate.equals(""))
					throw new UnsupportedOperationException();

				if (licenseNo.length() != 7)
					throw new UnsupportedOperationException();

				/* Update DB with new record */
				try {
					/* Get DB connection */
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/project", "root", "root");
					Statement stmt = con.createStatement();

					/* Query to update */
					stmt.executeUpdate("insert into driver " + "(driverlicenseno, driverfname, driverlname, birthdate) "
							+ "values (" + licenseNo + ", \"" + fName + "\", \"" + lName + "\", \"" + birthDate + "\")");

					con.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		/* Back Button */
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this, mainFrame));

		/* First name label and text field */
		JLabel lblFirstName = new JLabel("First Name *");
		textFieldPanel.add(lblFirstName);
		fNameTextField = new JTextField();
		fNameTextField.setColumns(10);
		textFieldPanel.add(fNameTextField);

		/* Last name label and text field */
		JLabel lblLastName = new JLabel("Last Name *");
		textFieldPanel.add(lblLastName);
		lNameTextField = new JTextField();
		lNameTextField.setColumns(10);
		textFieldPanel.add(lNameTextField);

		/* License number label and text field */
		JLabel lblLicense = new JLabel("License Number  (7 Numbers) *");
		textFieldPanel.add(lblLicense);
		licenseTextField = new JTextField();
		licenseTextField.setColumns(10);
		textFieldPanel.add(licenseTextField);

		/* Birth date label and text field */
		JLabel lblBirthDate = new JLabel("Birth Date (yyyy-mm-dd) *");
		textFieldPanel.add(lblBirthDate);
		birthTextField = new JTextField();
		birthTextField.setColumns(10);
		textFieldPanel.add(birthTextField);

		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);
		mainPanel.add(textFieldPanel);
		mainPanel.add(buttonPanel);

		getContentPane().add(mainPanel);

	}

}
