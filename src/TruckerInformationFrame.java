import java.awt.BorderLayout;
import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Insets;

public class TruckerInformationFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public TruckerInformationFrame(JFrame mainFrame) {

		/* Set up the frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 176);
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
			gbl_comboPanel.rowHeights = new int[] { 0, 52, 35, 0 };
			gbl_comboPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_comboPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
			comboPanel.setLayout(gbl_comboPanel);

			
			/* Get the driverids with which to fill the combo box from the DB and put them in the truckers array*/
			ArrayList<Integer> result = new ArrayList<Integer>();
			ResultSet rs = stmt.executeQuery("select driverid from driver");
			while (rs.next()) {
				result.add(rs.getInt("driverid"));
			}
			Integer[] truckers = new Integer[result.size()];
			
			
			result.sort(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					if (o1 < o2)
						return -1;
					if(o1 >= o2)
						return 1;
					return 0;
				}				
			});
			
			result.toArray(truckers);
			
			
			/* Make the combo box */
			
			JLabel lblChooseADriver = new JLabel("Choose a driver ");
			GridBagConstraints gbc_lblChooseADriver = new GridBagConstraints();
			gbc_lblChooseADriver.insets = new Insets(0, 0, 5, 0);
			gbc_lblChooseADriver.gridx = 0;
			gbc_lblChooseADriver.gridy = 0;
			comboPanel.add(lblChooseADriver, gbc_lblChooseADriver);
			JComboBox comboBox = new JComboBox(truckers);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.gridx = 0;
			gbc_comboBox.gridy = 1;
			comboPanel.add(comboBox, gbc_comboBox);
			comboBox.setSelectedIndex(-1); // start by displaying nothing in the combo box
			
			/* ActionListener for when a new combo box item is selected */
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					
						/* Get DB connection */
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/project", "root", "root");
						Statement stmt = con.createStatement();

						/* Query DB for the driver and create a model from the Result Set */
						JComboBox cb = (JComboBox) e.getSource();
						ResultSet results = stmt.executeQuery("select * from driver where driverid = " + cb.getSelectedItem().toString());
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

			
			// Make a new blank table wihout a model. When the action listener is called for the combo box,
			// this table will get a model and repaint itself
			table = new JTable();
			
			
			con.close(); // close the DB connection
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
