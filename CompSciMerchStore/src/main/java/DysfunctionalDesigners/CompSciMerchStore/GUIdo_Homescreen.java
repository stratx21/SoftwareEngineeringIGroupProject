package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIdo_Homescreen extends GUIdo_CPanel{
	
	/**
	 * This sets up the GUIdo_Homescreen instance 
	 */
	public GUIdo_Homescreen(){
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.repaint();
		
		GUIdo_CButton trybutton = new GUIdo_CButton(75,75,75,75,"TRY");
		trybutton.setActionListener_clicked(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("TRY!!!!!!");
			}
		});
		trybutton.setBackground(Color.GREEN);
		
		this.add(trybutton);
		
//		this.setVisible(true);
	}
	
	/**
	 * Draw the graphical representation of this panel.
	 * 
	 * @param g the java.awt.Graphics instance to use to draw things on this
	 * 	instance of GUIdo_Homescreen
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(50, 50, 100, 1000);
	}

}
