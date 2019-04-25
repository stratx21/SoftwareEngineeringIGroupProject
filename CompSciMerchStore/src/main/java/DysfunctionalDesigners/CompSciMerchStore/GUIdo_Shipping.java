package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class GUIdo_Shipping extends GUIdo_CPanel implements ActionListener{
	
	GUIdo_Shipping current_panel = this;
	
	public GUIdo_Shipping(Sale sale, Customer customer) {
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.drawScreen(sale, customer);
		this.repaint();
	}
	
	public void drawScreen(Sale sale, Customer customer) {
		MaskFormatter stateFormat = null;
		try {
			stateFormat = new MaskFormatter("UU");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MaskFormatter zipFormat = null;
		try {
			zipFormat = new MaskFormatter("#####");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GUIdo_CButton back = new GUIdo_CButton(this.getWidth(), this.getHeight(), 50, 25, "Back");
		JLabel shipInfo = new JLabel("Shipping Information");
		shipInfo.setFont(new Font("Cambria", Font.BOLD, 34));
		shipInfo.setHorizontalAlignment(JLabel.CENTER);
		JLabel shipOpt = new JLabel("Shipping Options");
		shipOpt.setFont(new Font("Cambria", Font.BOLD, 34));
		shipOpt.setHorizontalAlignment(JLabel.CENTER);
		JLabel subtotal = new JLabel("Subtotal");
		subtotal.setFont(new Font("Cambria", Font.BOLD, 34));
		subtotal.setHorizontalAlignment(JLabel.CENTER);
		JLabel addyLine1 = new JLabel("Address Line 1");
		addyLine1.setHorizontalAlignment(JLabel.CENTER);
		JLabel addyLine2 = new JLabel("Address Line 2");
		addyLine2.setHorizontalAlignment(JLabel.CENTER);
		JLabel city = new JLabel("City");
		city.setHorizontalAlignment(JLabel.CENTER);
		JLabel state = new JLabel("State");
		state.setHorizontalAlignment(JLabel.CENTER);
		JLabel zipcode = new JLabel("Zipcode");
		zipcode.setHorizontalAlignment(JLabel.CENTER);
		JLabel filler = new JLabel("dick");
		JTextField addressLine1 = new JTextField();
		addressLine1.setHorizontalAlignment(JTextField.CENTER);
		JTextField addressLine2 = new JTextField();
		addressLine2.setHorizontalAlignment(JTextField.CENTER);
		JTextField cityInput = new JTextField();
		cityInput.setHorizontalAlignment(JTextField.CENTER);
		JFormattedTextField stateInput = new JFormattedTextField(stateFormat);
		stateInput.setHorizontalAlignment(JTextField.CENTER);
		JFormattedTextField zip = new JFormattedTextField(zipFormat);
		zip.setHorizontalAlignment(JTextField.CENTER);
		
		GUIdo_CButton ptp = new GUIdo_CButton(0, 0, 200, 50, "Proceed to Payment");
		ptp.setHorizontalAlignment(JButton.CENTER);
		ptp.disable();
		JCheckBox shipOp1 = new JCheckBox("Flat Rate - $68.00");
		
		shipOp1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
					ptp.enable();
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					ptp.disable();
				}
			}
			
		});
		
		ptp.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isEnabled()) {
					// proceed to shipping!!!!!
					JOptionPane.showMessageDialog(null, "suck a dick");
				}else {
					// dialog that says to enter missing info
				}
			}
			
		});
		
		GridBagLayout gbLayout = new GridBagLayout();
		this.setLayout(gbLayout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(1, 10, 1, 10);
		//c.gridwidth = 3;
		//c.gridheight = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		c.weightx = 10;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(shipInfo, c); // col 1
		c.gridx = 1;
		c.gridy = 0;
		this.add(shipOpt, c); // col 2
		c.gridx = 2;
		c.gridy = 0;
		this.add(subtotal); // col 3
		c.gridx = 0;
		c.gridy = 1;
		this.add(addyLine1, c); // col 1
		c.gridx = 1;
		c.gridy = 1;
		//c.ipady = 40;
		this.add(shipOp1, c); // col 2
		c.gridx = 2;
		c.gridy = 10;
		c.weighty = 1;
		c.ipady = 0;
		this.add(ptp, c);	// col 3
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0;
		this.add(addressLine1, c); // col 1
		c.gridx = 0;
		c.gridy = 3;
		this.add(addyLine2, c); // col 1
		c.gridx = 0;
		c.gridy = 4;
		this.add(addressLine2, c); // col 1
		c.gridx = 0;
		c.gridy = 5;
		this.add(city, c); // col 1
		c.gridx = 0;
		c.gridy = 6;
		this.add(cityInput, c); // col 1
		c.gridx = 0;
		c.gridy = 7;
		this.add(state, c); // col 1
		c.gridx = 0;
		c.gridy = 8;
		this.add(stateInput, c); // col 1
		c.gridx = 0;
		c.gridy = 9;
		this.add(zipcode, c); // col 1
		c.gridx = 0;
		c.gridy = 10;
		this.add(zip, c); // col 1
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
