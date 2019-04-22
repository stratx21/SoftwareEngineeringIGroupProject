package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIdo_Shipping extends GUIdo_CPanel implements ActionListener{
	
	public GUIdo_Shipping(Sale sale, Customer customer) {
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		drawScreen(sale, customer);
		this.repaint();
	}
	
	public void drawScreen(Sale sale, Customer customer) {
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
