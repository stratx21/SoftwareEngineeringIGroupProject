package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class GUIdo_CPanel extends JPanel{
	
	private int page_length = 400;
	
	public GUIdo_CPanel() {
		this.setLayout(null);
		this.setAutoscrolls(true);
	}
	
	public GUIdo_CPanel(int page_length_pixels) {
		this();
		this.page_length=page_length_pixels;
		this.setPreferredSize(new Dimension(this.getWidth(), page_length_pixels));
	}
}
