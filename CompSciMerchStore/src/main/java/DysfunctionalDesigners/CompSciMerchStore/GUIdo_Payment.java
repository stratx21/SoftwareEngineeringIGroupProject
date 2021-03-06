package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class GUIdo_Payment extends GUIdo_CPanel implements ActionListener{
	private static Logger logger = Logger.getLogger(GUIdo_Payment.class.getName());
	
	GUIdo_Payment this_panel = this;
	
	/**
	 * Go to payment screen
	 * @param sale			the sale to register
	 * @param customer		the customer to check out
	 * @param current_panel	the current panel to change
	 * @param scrollpane	the scrollpane to add to
	 */
	public GUIdo_Payment(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		super();
		logger.info("Switched to Payment Screen");
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(this.getWidth(), 500));
		this.drawScreen(sale, 0, customer, current_panel, scrollpane);
  	    this.repaint();
	}
	
	/**
	 * Draw the payment screen
	 * @param sale			the sale to draw
	 * @param i				i
	 * @param customer		the customer to draw for
	 * @param current_panel	the current panel to change
	 * @param scrollpane	the scrollpane to add to
	 */
	private void drawScreen(Sale sale, int i, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		DecimalFormat df = new DecimalFormat("0.00");
		MaskFormatter card = null;
		
		try {
			card = new MaskFormatter("################");
		} catch (ParseException e) {
			logger.severe("ERROR: Caught ParseException for Card Number in Payment");
			e.printStackTrace();
		}
		MaskFormatter cvvFormat = null;
		try {
			cvvFormat = new MaskFormatter("###");
		} catch (ParseException e) {
			logger.severe("ERROR: Caught ParseException for CVC in Payment");
			e.printStackTrace();
		}
		MaskFormatter stateFormat = null;
		try {
			stateFormat = new MaskFormatter("UU");
		} catch (ParseException e) {
			logger.severe("ERROR: Caught ParseException for StateFormat in Payment");
			e.printStackTrace();
		}
		MaskFormatter zipFormat = null;
		try {
			zipFormat = new MaskFormatter("#####");
		} catch (ParseException e) {
			logger.severe("ERROR: Caught ParseException for Zip Code in Payement");
			e.printStackTrace();
		}
		
		JLabel label = new JLabel("Payment Information");
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setBounds(100, 0, 100, 100);
		label.setFont(new Font("Cambria", Font.BOLD, 34));
		
		JLabel addCard = new JLabel("Add a Card");
		addCard.setHorizontalAlignment(JLabel.LEFT);
		addCard.setSize(new Dimension(25, 10));
		addCard.setFont(new Font("Cambria", Font.BOLD, 50));
		
		JLabel cardName = new JLabel("Name on Card");
		cardName.setFont(new Font("Cambria", Font.BOLD, 12));
		JTextField nameOnCard = new JTextField();
		
		JLabel cardNum = new JLabel("Card Number");
		cardNum.setFont(new Font("Cambria", Font.BOLD, 12));
		JFormattedTextField numberOnCard = new JFormattedTextField(card);
		
		JLabel cvv = new JLabel("CVV");
		cvv.setFont(new Font("Cambria", Font.BOLD, 12));
		JFormattedTextField cvvNum = new JFormattedTextField(cvvFormat);
		cvvNum.setPreferredSize(new Dimension(40, 20));
		
		if(!customer.getPaymentInfo().isEmpty()) {
			numberOnCard.setText(customer.getPaymentInfo().get(0).getCardNumber());
			cvvNum.setText(String.valueOf(customer.getPaymentInfo().get(0).getCCV()));
		}
		
		JLabel billingaddy = new JLabel("Billing Address:");
		billingaddy.setFont(new Font("Cambria", Font.PLAIN, 16));
		JLabel bAddress1 = new JLabel("Address Line 1");
		bAddress1.setFont(new Font("Cambria", Font.BOLD, 12));
		JFormattedTextField addressLine1 = new JFormattedTextField();
		JLabel bAddress2 = new JLabel("Address Line 2");
		bAddress2.setFont(new Font("Cambria", Font.BOLD, 12));
		JFormattedTextField addressLine2 = new JFormattedTextField();
		JLabel bCity = new JLabel("City");
		bCity.setFont(new Font("Cambria", Font.BOLD, 12));
		JFormattedTextField city = new JFormattedTextField();
		JLabel bState = new JLabel("State");
		bState.setFont(new Font("Cambria", Font.BOLD, 12));
		JFormattedTextField state = new JFormattedTextField(stateFormat);
		state.setPreferredSize(new Dimension(30, 20));
		JLabel bZip = new JLabel("Zipcode");
		bZip.setFont(new Font("Cambria", Font.BOLD, 12));
		JFormattedTextField zip = new JFormattedTextField(zipFormat);
		zip.setPreferredSize(new Dimension(50, 20));
		
		if(!customer.getPaymentInfo().isEmpty()) {
			addressLine1.setText(customer.getPaymentInfo().get(0).getBillingAddress().getStreet());
			city.setText(customer.getPaymentInfo().get(0).getBillingAddress().getCity());
			state.setText(customer.getPaymentInfo().get(0).getBillingAddress().getState());
			zip.setText(String.valueOf(customer.getPaymentInfo().get(0).getBillingAddress().getZipCode()));
		}

		JLabel promoLabel = new JLabel("Promo Code:");
		promoLabel.setFont(new Font("Cambria", Font.PLAIN, 16));
		JTextField promoCode = new JTextField();
		
		// making order detail box -- reuse code in Shipping && Review/Edit Order
		JLabel orderDetails = new JLabel("Order Details");
		orderDetails.setFont(new Font("Cambria", Font.PLAIN, 28));
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
		JLabel totalCost = new JLabel("$" + df.format(sale.getTotalWithTax()));
		totalCost.setForeground(Color.RED);
		totalCost.setFont(new Font("Cambria", Font.BOLD, 14));
		
		GUIdo_CButton placeOrder = new GUIdo_CButton(0, 0, 150, 75, "Place Order");
		placeOrder.disable();
		placeOrder.setBackground(new Color(255,228,225));
		placeOrder.setHoverColor(new Color(255,192,203));
		//placeOrder.setForeground(Color.PINK);
		GUIdo_CButton addCardButton = new GUIdo_CButton(GUIdo_CButton.LEADING, GUIdo_CButton.LEADING, 25, 10, "Add Card");
		GUIdo_CButton back = new GUIdo_CButton(GUIdo_CButton.LEADING, GUIdo_CButton.LEADING, 25, 10, "Back");
		GUIdo_CButton enterPromoCode = new GUIdo_CButton(GUIdo_CButton.LEADING, GUIdo_CButton.LEADING, 25, 10, "Enter Code");
		
		addCardButton.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// save payment data
				DecimalFormat df = new DecimalFormat("0.00");
				if(!nameOnCard.getText().isEmpty() && !nameOnCard.getText().isBlank()
						&& !numberOnCard.getText().isEmpty() && !numberOnCard.getText().isBlank()
						&& !cvvNum.getText().isEmpty() && !cvvNum.getText().isBlank()
						&& !addressLine1.getText().isEmpty() && !addressLine1.getText().isBlank()
						&& !city.getText().isEmpty() && !city.getText().isEmpty()
						&& !state.getText().isEmpty() && !state.getText().isBlank()
						&& !zip.getText().isEmpty() && !zip.getText().isBlank()) {
					Address ba = new Address();
					if(!addressLine2.getText().isEmpty()) {
						ba.setStreet(addressLine1.getText() + " " + addressLine2.getText());
					}else {
						ba.setStreet(addressLine1.getText());
					}
					ba.setCity(city.getText());
					ba.setState(state.getText());
					ba.setZipCode(Integer.valueOf(zip.getText()));
					PaymentInfo card = new PaymentInfo(numberOnCard.getText(), ba, Integer.valueOf(cvvNum.getText()));
					customer.getPaymentInfo().add(card);
					placeOrder.enable();
					
					logger.info("Card Added Successfully to user " + customer.getUserName());
					sale.setPayment(new Payment(sale.getTotalWithTax(), card));
					JOptionPane.showMessageDialog(null, (Object) ("Card added successfully with amount $" + df.format(sale.getTotalWithTax()) + "!"));
				}else if(nameOnCard.getText().isEmpty() || nameOnCard.getText().isBlank()
						|| numberOnCard.getText().isEmpty() || numberOnCard.getText().isBlank()
						|| cvvNum.getText().isEmpty() || cvvNum.getText().isBlank()
						|| addressLine1.getText().isEmpty() || addressLine1.getText().isBlank()
						|| city.getText().isEmpty() || city.getText().isEmpty()
						|| state.getText().isEmpty() || state.getText().isBlank()
						|| zip.getText().isEmpty() || zip.getText().isBlank()){
					logger.info("Credit Card Information Incomplete for user " + customer.getUserName());
					JOptionPane.showMessageDialog(null, (Object) "Please enter valid card and billing address information");
				}
				
			}
		
		});
		
		placeOrder.setActionListener_clicked(new ActionListener() {
			// TODO: link to previous orders page and save to customer
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isEnabled()) {
					if(sale.finalizePayment()) {
						JOptionPane.showMessageDialog(null, (Object) "Thank you for your order!");
						customer.updatePreviousPurchases(sale);
						customer.setCart(null);
						logger.info("All Payment Information collected -- Proceeding");
						to_previousOrders(customer, current_panel, scrollpane);
					}else {
						logger.severe("ERROR: SALE NOT FINALIZED");
						JOptionPane.showMessageDialog(null, (Object) "Please enter all information"); // not appearing
						logger.info("Not all Payment Information Entered");
					}
					
				}else {
					JOptionPane.showMessageDialog(null, (Object) "Please enter all information"); // not appearing
					logger.info("Not all Payment Information Entered");
				}
				
			}
			
		});
		
		back.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				to_previous(sale, customer, current_panel, scrollpane);
			}
			
		});
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 5;
		c.weighty = 0;
		c.insets = new Insets(1, 10, 1, 10);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		this.add(back, c);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		this.add(label, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		this.add(cardName, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 2;
		this.add(nameOnCard, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 3;
		this.add(cardNum, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 3;
		this.add(numberOnCard, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 4;
		this.add(cvv, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 4;
		this.add(cvvNum, c);
		
		// add card button
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 2;
		c.gridy = 15;
		c.weighty = 1;
		this.add(addCardButton, c);
		
		// billing address section
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 6;
		this.add(billingaddy, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 7;
		this.add(bAddress1, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 7;
		this.add(addressLine1, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 8;
		this.add(bAddress2, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 8;
		this.add(addressLine2, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 9;
		this.add(bCity, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 9;
		this.add(city, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 10;
		this.add(bState, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 10;
		this.add(state, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 11;
		this.add(bZip, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 1;
		c.gridy = 11;
		this.add(zip, c);
		
		// order details box
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHEAST;
		c.weighty = 0;
		c.gridx = 3;
		c.gridy = 1;
		//c.gridwidth = 2;
		this.add(orderDetails, c);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 2;
		this.add(subtotal, c);
		c.gridx = 4;
		c.gridy = 2;
		this.add(subNum, c);
		c.gridx = 3;
		c.gridy = 3;
		this.add(estTax, c);
		c.gridx = 4;
		c.gridy = 3;
		this.add(estTaxNum, c);
		c.gridx = 3;
		c.gridy = 4;
		this.add(shipping, c);
		c.gridx = 4;
		c.gridy = 4;
		this.add(shippingCost, c);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 1;
		c.gridx = 3;
		c.gridy = 5;
		this.add(total, c);
		c.gridx = 4;
		c.gridy = 5;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(totalCost, c);
		
		enterPromoCode.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(sale.checkPromoCode(promoCode.getText())) {
					// get new sale 
					sale.applyPromoCode(promoCode.getText());
					
					JLabel newSubtotal = new JLabel("$" + df.format(sale.getTotalWithoutTax()));
					newSubtotal.setFont(new Font("Cambria", Font.PLAIN, 14));
					this_panel.remove(subNum);
					c.gridx = 4;
					c.gridy = 2;
					c.weighty = 0;
					c.anchor = GridBagConstraints.NORTHWEST;
					this_panel.add(newSubtotal, c);
					
					JLabel newTax = new JLabel("$" + df.format(sale.getEstimatedTax()));
					newTax.setFont(new Font("Cambria", Font.PLAIN, 14));
					this_panel.remove(estTaxNum);
					c.gridx = 4;
					c.gridy = 3;
					c.weighty = 0;
					c.anchor = GridBagConstraints.NORTHWEST;
					this_panel.add(newTax, c);
					
					JLabel newTotalCost = new JLabel("$" + df.format(sale.getTotalWithTax()));
					newTotalCost.setForeground(Color.RED);
					newTotalCost.setFont(new Font("Cambria", Font.BOLD, 14));
					this_panel.remove(totalCost);
					c.gridx = 4;
					c.gridy = 5;
					c.weighty = 0;
					c.anchor = GridBagConstraints.NORTHWEST;
					
					this_panel.add(newTotalCost, c);
					this_panel.repaint();
					this_panel.revalidate();
					
					JOptionPane.showMessageDialog(null, "Promo code added!");
				}else if(!sale.checkPromoCode(promoCode.getText())){
					JOptionPane.showMessageDialog(null, "Not a valid promo code!");
				}
				enterPromoCode.disable();
			}
			
		});
		
		c.gridx = 3;
		c.gridy = 6;
		c.weighty = 0;
		c.anchor = GridBagConstraints.EAST;
		this.add(promoLabel, c);
		c.gridx = 4;
		c.gridy = 6;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(promoCode, c);
		c.gridx = 4;
		c.gridy = 7;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(enterPromoCode, c);
		c.gridx = 3;
		c.gridy = 10;
		c.weighty = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(placeOrder, c);
		
	}
	
	/**
	 * Go to the previous orders page
	 * @param customer		Customer to go for
	 * @param current_panel	the current panel to go for
	 * @param scrollpane	the scrollpane to add to
	 */
	protected void to_previousOrders(Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		// TODO Auto-generated method stub
		current_panel = new GUIdo_PreviousOrders(customer);
		scrollpane.getViewport().add(current_panel);
	}

	/**
	 * Go to the shipping page
	 * @param sale				sale to go for
	 * @param customer			customer this applies to
	 * @param current_panel		The current panel to change
	 * @param scrollpane		the scrollpane to add to
	 */
	protected void to_previous(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		// TODO Auto-generated method stub
		current_panel = new GUIdo_Shipping(sale, customer, current_panel, scrollpane);
		scrollpane.getViewport().add(current_panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
