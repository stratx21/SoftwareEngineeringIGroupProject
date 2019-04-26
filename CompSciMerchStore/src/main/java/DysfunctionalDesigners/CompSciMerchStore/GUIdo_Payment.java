package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class GUIdo_Payment extends GUIdo_CPanel implements ActionListener{
	private static Logger logger = Logger.getLogger(GUIdo_Payment.class.getName());
	public GUIdo_Payment(Sale sale, Customer customer) {
		super();
		logger.info("Switched to Payment Screen");
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.drawScreen(sale, 0, customer);
  	    this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawRect(950, 120, 225, 250);
		g.drawLine(1025, 170, 1105, 170);
		g.drawLine(1025, 325, 1105, 325);
	}
	

	private void drawScreen(Sale sale, int i, Customer customer) {
		this.setLayout(null);
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
		JTextField nameOnCard = new JTextField();
		
		JLabel cardNum = new JLabel("Card Number");
		JFormattedTextField numberOnCard = new JFormattedTextField(card);
		
		JLabel cvv = new JLabel("CVV");
		JFormattedTextField cvvNum = new JFormattedTextField(cvvFormat);
		
		JLabel bAddress1 = new JLabel("Address Line 1");
		JFormattedTextField addressLine1 = new JFormattedTextField();
		JLabel bCity = new JLabel("City");
		JFormattedTextField city = new JFormattedTextField();
		JLabel bState = new JLabel("State");
		JFormattedTextField state = new JFormattedTextField(stateFormat);
		JLabel bZip = new JLabel("Zipcode");
		JFormattedTextField zip = new JFormattedTextField(zipFormat);
		
		GUIdo_CButton placeOrder = new GUIdo_CButton(this.getWidth() / 4, this.getHeight() / 5, 150, 50, "Place Order");
		GUIdo_CButton addCardButton = new GUIdo_CButton(GUIdo_CButton.LEADING, GUIdo_CButton.LEADING, 25, 10, "Add Card");
		
		addCardButton.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// save payment data
				Address ba = new Address();
				ba.setStreet(addressLine1.getText());
				ba.setCity(city.getText());
				ba.setState(state.getText());
				ba.setZipCode(Integer.valueOf(zip.getText()));
				PaymentInfo card = new PaymentInfo(nameOnCard.getText(), ba, Integer.valueOf(cvvNum.getText()));
				customer.getPaymentInfo().add(card);
			}
		
		});
		
		placeOrder.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isEnabled()) {
					
					JOptionPane.showMessageDialog(null, (Object) "Thank you for your order!");
					logger.info("All Payment Information collected -- Proceeding");
				}else {
					JOptionPane.showMessageDialog(null, (Object) "Please enter all information"); // not appearing
					logger.info("Not all Payment Information Entered");
				}
				
			}
			
		});
		
		this.add(placeOrder);
		
		GridBagLayout gbl = new GridBagLayout();
		//gbl.removeLayoutComponent(label);
		this.setLayout(gbl);
		this.add(label);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(1, 10, 1, 10);
		c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		//cardName.setHorizontalTextPosition(this.getWidth() / 20);
		this.add(cardName, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		c.gridx = 1;
		c.gridy = 0;
		this.add(nameOnCard, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		c.gridx = 0;
		c.gridy = 1;
		this.add(cardNum, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		c.gridx = 1;
		c.gridy = 1;
		this.add(numberOnCard, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		c.gridx = 0;
		c.gridy = 2;
		this.add(cvv, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		c.gridx = 1;
		c.gridy = 2;
		this.add(cvvNum, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		c.gridx = 3;
		c.gridy = 3;
		this.add(addCardButton, c);
		
	
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
