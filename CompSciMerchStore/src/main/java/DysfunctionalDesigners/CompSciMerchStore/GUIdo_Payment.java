package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JLabel;

public class GUIdo_Payment extends GUIdo_CPanel implements ActionListener{
	
	public GUIdo_Payment(Sale sale) {
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.drawScreen(sale, 0);
  	    this.repaint();
	}
	

	private void drawScreen(Sale sale, int i) {
		// TODO Auto-generated method stub
		DecimalFormat df2 = new DecimalFormat("0.00");
		JLabel label = new JLabel("Payment Information");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("Cambria", Font.BOLD, 34));
		this.add(label);
		
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
