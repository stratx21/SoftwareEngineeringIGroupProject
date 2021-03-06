package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class GUIdo_Shipping extends GUIdo_CPanel implements ActionListener{
	private static Logger logger = Logger.getLogger(GUIdo_Shipping.class.getName());
	
	GUIdo_Shipping current_pan = this;
	
	/**
	 * Go to the shipping panel
	 * @param sale			the sale to display for
	 * @param customer		the customer that's checking out
	 * @param current_panel	the current panel to change
	 * @param scrollpane	the scollpane to add to
	 */
	public GUIdo_Shipping(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		super();
		this.setBackground(Color.WHITE);
		logger.info("Switched to Shipping Information Frame");
		this.setPreferredSize(new Dimension(this.getWidth(), 500));
		this.drawScreen(sale, customer, current_panel, scrollpane);
		this.repaint();
	}
	
	/**
	 * Draw the shipping screen
	 * @param sale			the sale to display for
	 * @param customer		the customer that's checking out
	 * @param current_panel	the current panel to change
	 * @param scrollpane	the scollpane to add to
	 */
	public void drawScreen(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		DecimalFormat df = new DecimalFormat("0.00");
		MaskFormatter stateFormat = null;
		try {
			stateFormat = new MaskFormatter("UU");
		} catch (ParseException e) {
			
			logger.severe("ERROR: Caught ParseException in Shipping");
			e.printStackTrace();
		}
		MaskFormatter zipFormat = null;
		try {
			zipFormat = new MaskFormatter("#####");
		} catch (ParseException e) {
			
			logger.severe("ERROR: Caught ParseException in Shipping");
			e.printStackTrace();
		}
		
		GUIdo_CButton back = new GUIdo_CButton(this.getWidth(), this.getHeight(), 50, 25, "Back");
		
		back.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: connect to review&edit order
				// previous_panel = new GUIdo_ReviewAndEditOrder(sale, customer);
				to_previous(sale, customer, current_panel, scrollpane);
			}
			
		});
		
		JLabel shipInfo = new JLabel("Shipping Information");
		shipInfo.setFont(new Font("Cambria", Font.BOLD, 34));
		shipInfo.setHorizontalAlignment(JLabel.CENTER);
		JLabel shipOpt = new JLabel("Shipping Options");
		shipOpt.setFont(new Font("Cambria", Font.BOLD, 34));
		shipOpt.setHorizontalAlignment(JLabel.CENTER);
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
		JTextField addressLine1 = new JTextField();
		addressLine1.setHorizontalAlignment(JTextField.CENTER);
		JTextField addressLine2 = new JTextField();
		addressLine2.setHorizontalAlignment(JTextField.CENTER);
		JTextField cityInput = new JTextField();
		cityInput.setHorizontalAlignment(JTextField.CENTER);
		JFormattedTextField stateInput = new JFormattedTextField(stateFormat);
		stateInput.setPreferredSize(new Dimension(30, 20));
		stateInput.setHorizontalAlignment(JTextField.CENTER);
		JFormattedTextField zip = new JFormattedTextField(zipFormat);
		zip.setPreferredSize(new Dimension(50, 20));
		zip.setHorizontalAlignment(JTextField.CENTER);
		
		if(customer.getShippingAddr() != null) {
			addressLine1.setText(customer.getShippingAddr().getStreet());
			cityInput.setText(customer.getShippingAddr().getCity());
			stateInput.setText(customer.getShippingAddr().getState());
			zip.setText(String.valueOf(customer.getShippingAddr().getZipCode()));
		}
		
		// making order detail box -- reuse code in Payment && Review/Edit Order
		JLabel orderDetails = new JLabel("Order Details");
		orderDetails.setFont(new Font("Cambria", Font.PLAIN, 26));
		//orderDetails.setHorizontalAlignment(JLabel.CENTER);
		JLabel subtotal = null;
		if(sale.getNumItems() == 1) {
			subtotal = new JLabel("Subtotal (" + sale.getNumItems() + " item):");
		}else {
			subtotal = new JLabel("Subtotal (" + sale.getNumItems() + " items):");
		}
		
		subtotal.setFont(new Font("Cambria", Font.PLAIN, 14));
		JLabel subNum = new JLabel("$" + df.format(sale.getTotalWithoutTax()));
		subNum.setFont(new Font("Cambria", Font.PLAIN, 14));
		JLabel estTax = new JLabel("Estimated Tax: ");
		estTax.setFont(new Font("Cambria", Font.PLAIN, 14));
		JLabel estTaxNum = new JLabel("$" + df.format(sale.getEstimatedTax()));
		estTaxNum.setFont(new Font("Cambria", Font.PLAIN, 14));
		JLabel shipping = new JLabel("Shipping:");
		shipping.setFont(new Font("Cambria", Font.PLAIN, 14));
		JLabel shippingCost = new JLabel("$" + df.format(Sale.getShipping()));
		shippingCost.setFont(new Font("Cambria", Font.PLAIN, 14));
		JLabel total = new JLabel("Total:");
		total.setFont(new Font("Cambria", Font.BOLD, 14));
		total.setForeground(Color.RED);
		JLabel totalCostps = new JLabel("$" + df.format(sale.getTotalWithTax() - Sale.getShipping()));
		totalCostps.setForeground(Color.RED);
		totalCostps.setFont(new Font("Cambria", Font.BOLD, 14));
		JLabel totalCostws = new JLabel("$" + df.format(sale.getTotalWithTax()));
		totalCostws.setFont(new Font("Cambria", Font.BOLD, 14));
		totalCostws.setForeground(Color.RED);
		
		GUIdo_CButton ptp = new GUIdo_CButton(0, 0, 200, 50, "Proceed to Payment");
		ptp.setHorizontalAlignment(JButton.CENTER);
		ptp.disable();
		JCheckBox shipOp1 = new JCheckBox("Flat Rate - $68.00");
		
		ptp.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isEnabled() && !addressLine1.getText().isEmpty() && !addressLine1.getText().isBlank()
						&& !cityInput.getText().isEmpty() && !cityInput.getText().isBlank()
						&& !stateInput.getText().isEmpty() && !stateInput.getText().isBlank()
						&& !zip.getText().isEmpty() && !zip.getText().isBlank()) {
					Address shippingAddr = new Address();
					if(!addressLine2.getText().isEmpty()) {
						shippingAddr.setStreet(addressLine1.getText() + " " + addressLine2.getText());
					}else {
						shippingAddr.setStreet(addressLine1.getText());
					}
					shippingAddr.setCity(cityInput.getText());
					shippingAddr.setState(stateInput.getText());
					shippingAddr.setZipCode(Integer.parseInt(zip.getText()));
					
					customer.setShippingAddr(shippingAddr);
					sale.setShippingAddr(shippingAddr);
					to_Payment(sale, customer, current_panel, scrollpane);
				}else if(!isEnabled() || addressLine1.getText().isEmpty() || addressLine1.getText().isBlank()
						|| cityInput.getText().isEmpty() || cityInput.getText().isBlank()
						|| stateInput.getText().isEmpty() || stateInput.getText().isBlank()
						|| zip.getText().isEmpty() || zip.getText().isBlank()){
					// dialog that says to enter missing info
					logger.info("Not all shipping information was valid");
					JOptionPane.showMessageDialog(null, "Please enter all necessary information and select a shipping option.");
				}else {
					logger.info("Shipping information was valid; proceeding to payment."); 
				}
			}
			
		});
		
		GridBagLayout gbLayout = new GridBagLayout();
		this.setLayout(gbLayout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(1, 10, 1, 10);
		c.weightx = 5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		this.add(shipInfo, c); // col 1
		c.gridx = 1;
		c.gridy = 1;
		this.add(shipOpt, c); // col 2
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHEAST;
		this.add(orderDetails, c); // col 3
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(addyLine1, c); // col 1
		c.gridx = 1;
		c.gridy = 2;
		this.add(shipOp1, c); // col 2
		c.gridx = 2;
		c.gridy = 20;
		c.weighty = 1;
		c.ipady = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		this.add(ptp, c);	// col 3
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(addressLine1, c); // col 1
		c.gridx = 0;
		c.gridy = 4;
		this.add(addyLine2, c); // col 1
		c.gridx = 0;
		c.gridy = 5;
		this.add(addressLine2, c); // col 1
		c.gridx = 0;
		c.gridy = 6;
		this.add(city, c); // col 1
		c.gridx = 0;
		c.gridy = 7;
		this.add(cityInput, c); // col 1
		c.gridx = 0;
		c.gridy = 8;
		this.add(state, c); // col 1
		c.gridx = 0;
		c.gridy = 9;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(stateInput, c); // col 1
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 10;
		this.add(zipcode, c); // col 1
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 11;
		this.add(zip, c); // col 1
		
		// back button
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		this.add(back, c);
		
		// order details section
		c.gridx = 2;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(subtotal, c);
		c.gridx = 3;
		c.gridy = 3;
		this.add(subNum, c);
		c.gridx = 2;
		c.gridy = 4;
		this.add(estTax, c);
		c.gridx = 3;
		c.gridy = 4;
		this.add(estTaxNum, c);
		c.gridx = 2;
		c.gridy = 5;
		this.add(shipping, c);
		c.gridx = 2;
		c.gridy = 6;
		this.add(total, c);
		c.gridx = 3;
		c.gridy = 6;
		this.add(totalCostps, c);	
		
		shipOp1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
					ptp.enable();
					c.gridx = 3;
					c.gridy = 5;
					current_pan.add(shippingCost, c);
					c.gridx = 3;
					c.gridy = 6;
					current_pan.remove(totalCostps);
					current_pan.add(totalCostws, c);
					current_pan.repaint();
					current_pan.revalidate();
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					ptp.disable();
				}
			}
			
		});
	}

	
	/**
	 * Go to the previous orders page
	 * @param sale			sale to go for
	 * @param customer		Customer to go for
	 * @param current_panel	the current panel to go for
	 * @param scrollpane	the scrollpane to add to
	 */
	protected void to_previous(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		// TODO Auto-generated method stub
		current_panel = new GUIdo_ReviewAndEditOrder(sale, customer, current_panel, scrollpane);
		scrollpane.getViewport().add(current_panel);
	}

	/**
	 * Go to payment screen.
	 * @param sale			sale to go for
	 * @param customer		Customer to go for
	 * @param current_panel	the current panel to go for
	 * @param scrollpane	the scrollpane to add to
	 */
	protected void to_Payment(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		// TODO Auto-generated method stub
		current_panel = new GUIdo_Payment(sale, customer, current_panel, scrollpane);
		scrollpane.getViewport().add(current_panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
