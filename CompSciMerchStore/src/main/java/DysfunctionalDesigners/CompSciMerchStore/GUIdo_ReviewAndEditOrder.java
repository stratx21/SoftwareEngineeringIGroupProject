package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GUIdo_ReviewAndEditOrder extends GUIdo_CPanel{
	
	/**
	 * This sets up the GUIdo_ReviewAndEditOrder instance 
	 */
	public GUIdo_ReviewAndEditOrder() {
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
//		this.draw();
		this.repaint();
		this.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(10, 10, 1245, 100);
		
	}
	
	public void paint(Graphics g) {
		g.drawRect(900, 100, 300, 500);
//		this.draw();
		
	}
	
	public void draw() {
		// box for holding item breakdown, total, tax
		TitledBorder titled;
		Border blackline = BorderFactory.createLineBorder(Color.black);
		titled = BorderFactory.createTitledBorder(
                blackline, "Order Details", TitledBorder.CENTER, TitledBorder.TOP);
		this.setBorder(titled);
		
		// title at top of screen
		JLabel label= new JLabel("Review and Edit Order");
		label.setPreferredSize(new Dimension(150, 100));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		this.add(label);
		this.setVisible(true);
	}

}
