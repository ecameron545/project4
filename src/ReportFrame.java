import java.awt.BorderLayout;
import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Insets;

public class ReportFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public ReportFrame(JFrame mainFrame) {

		/* Set up the frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 251);
		this.setTitle("Trucker Information");
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);

		/* Set up the combobox and button panels */
		JPanel comboPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		contentPane.add(comboPanel, BorderLayout.NORTH);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		/* Add a back button */
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this, mainFrame));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(backButton);

		try {

			/* Connect to DB */
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/project", "root", "root");
			Statement stmt = con.createStatement();

			// GUI making the combo panel
			GridBagLayout gbl_comboPanel = new GridBagLayout();
			gbl_comboPanel.columnWidths = new int[] { 412, 0 };
			gbl_comboPanel.rowHeights = new int[] { 0, 33, 35, 0, 0 };
			gbl_comboPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_comboPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
			comboPanel.setLayout(gbl_comboPanel);

			/*
			 * Get the driverids with which to fill the combo box from the DB
			 * and put them in the truckers array
			 */
			ArrayList<String> result = new ArrayList<String>();
			ResultSet rs = stmt.executeQuery("select datedelivered from storeshipment");
			while (rs.next()) {
				result.add(rs.getString("datedelivered"));
			}
			String[] dates = new String[result.size()];
			result.toArray(dates);

			/* Make the combo box */

			JLabel lblChooseADate = new JLabel("Choose a date to see that weeks report");
			GridBagConstraints gbc_lblChooseADate = new GridBagConstraints();
			gbc_lblChooseADate.insets = new Insets(0, 0, 5, 0);
			gbc_lblChooseADate.gridx = 0;
			gbc_lblChooseADate.gridy = 0;
			comboPanel.add(lblChooseADate, gbc_lblChooseADate);
			JComboBox comboBox = new JComboBox(dates);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.gridx = 0;
			gbc_comboBox.gridy = 1;

			/* Dates */
			SqlDateModel model = new SqlDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			comboPanel.add(datePicker, gbc_comboBox);
			// comboBox.setSelectedIndex(-1); // start by displaying nothing in
			// the combo box

			/* ActionListener for when a new combo box item is selected */
			datePicker.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {

						/* Get DB connection */
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/project", "root",
								"root");
						Statement stmt = con.createStatement();

						/*
						 * Query DB for the driver and create a model from the
						 * Result Set
						 */

						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date date = (Date) datePicker.getModel().getValue();
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.DAY_OF_YEAR, 7);
						int userYear = cal.get(Calendar.YEAR);
						int userMonth = cal.get(Calendar.MONTH);
						int userDay = cal.get(Calendar.DAY_OF_MONTH);
						Date newDate = cal.getTime();
						String fullNewDate = df.format(newDate);

						Calendar currentCal = Calendar.getInstance();
						int currentYear = currentCal.get(Calendar.YEAR);
						int currentMonth = currentCal.get(Calendar.MONTH);
						int currentDay = currentCal.get(Calendar.DAY_OF_MONTH);

						ResultSet results = null;

						if ((currentYear - userYear != 0) || (currentMonth - userMonth != 0)
								|| ((currentDay - userDay > 0) || currentDay - userDay < -7)) {
							results = stmt.executeQuery(
									"select ss.datedelivered, s.name, ss.inventory, d.truckid, ss.deliverystatus "
											+ "from storeshipment ss join deliversto d on ss.deliveryid = d.deliveryid "
											+ "join store s on s.storeid = d.storeid where ss.datedelivered between "
											+ "\"" + date + "\"" + " and \"" + fullNewDate + "\"");
						} else {
							results = stmt.executeQuery(
									"select ss.datedelivered, s.name, ss.inventory, d.truckid, ss.deliverystatus "
											+ "from storeshipment ss join deliversto d on ss.deliveryid = d.deliveryid "
											+ "join store s on s.storeid = d.storeid where ss.datedelivered is null or ss.datedelivered between "
											+ "\"" + date + "\"" + " and \"" + fullNewDate + "\"");
						}

						ListTableModel model = ListTableModel.createModelFromResultSet(results);

						/* Update the table's model, border, and header */
						table.setModel(model);
						table.setGridColor(Color.BLACK);
						Border border = BorderFactory.createLineBorder(Color.black);
						table.getTableHeader().setBorder(border);

						// GUI stuff for GridBagLayout
						GridBagConstraints gbc_table_header = new GridBagConstraints();
						gbc_table_header.fill = GridBagConstraints.BOTH;
						gbc_table_header.gridx = 0;
						gbc_table_header.gridy = 2;
						gbc_table_header.gridheight = 1;
						gbc_table_header.weightx = 0.5;

						// GUI stuff for GridBagLayout
						GridBagConstraints gbc_table = new GridBagConstraints();
						gbc_table.fill = GridBagConstraints.BOTH;
						gbc_table.gridx = 0;
						gbc_table.gridy = 3;
						comboPanel.add(table.getTableHeader(), gbc_table_header);
						comboPanel.add(table, gbc_table);

						// Repaint the panel and table
						comboPanel.revalidate();
						comboPanel.repaint();
						con.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

			// Make a new blank table wihout a model. When the action listener
			// is called for the combo box,
			// this table will get a model and repaint itself
			table = new JTable();

			con.close(); // close the DB connection

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
