package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class GUIdo_Payment extends GUIdo_CPanel implements ActionListener{
	
	public GUIdo_Payment(Sale sale, Customer customer) {
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.drawScreen(sale, 0, customer);
  	    this.repaint();
	}
	

	private void drawScreen(Sale sale, int i, Customer customer) {
		// TODO Auto-generated method stub
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
//		DecimalFormat df2 = new DecimalFormat("0.00");
		MaskFormatter card = null;
		try {
			card = new MaskFormatter("################");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MaskFormatter cvvFormat = null;
		try {
			cvvFormat = new MaskFormatter("###");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel label = new JLabel("Payment Information");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("Cambria", Font.BOLD, 34));
		this.add(label);
		
		JLabel addCard = new JLabel("Add a Card");
		addCard.setHorizontalAlignment(JLabel.LEFT);
		addCard.setFont(new Font("Cambria", Font.BOLD, 28));
		
		JLabel cardName = new JLabel("Name on Card");
		JTextField nameOnCard = new JTextField();
		
		JLabel cardNum = new JLabel("Card Number");
		JFormattedTextField numberOnCard = new JFormattedTextField(card);
		
		JLabel cvv = new JLabel("CVV");
		JFormattedTextField cvvNum = new JFormattedTextField(cvvFormat);
		
		GUIdo_CButton addCardButton = new GUIdo_CButton(GUIdo_CButton.LEADING, GUIdo_CButton.LEADING, 25, 10, "Add Card");
		
		addCardButton.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// save payment data
				Address ba = new Address();
				PaymentInfo card = new PaymentInfo(nameOnCard.getText(), ba, Integer.valueOf(cvvNum.getText()));
				
			}
		
		});
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(cardName)
						.addComponent(nameOnCard))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(cardNum)
						.addComponent(numberOnCard))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(cvv)
						.addComponent(cvvNum))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(addCardButton))
		);
		
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
