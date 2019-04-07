package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIdo_Payment extends GUIdo_CPanel implements ActionListener{
	
	public GUIdo_Payment(Sale sale) {
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.drawScreen(sale, 0);
  	    this.repaint();
	}
	
	

	private void drawScreen(Sale sale, int i) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
