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
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.swing.JLabel;

public class GUIdo_PastSales extends GUIdo_CPanel{
	/**
	 * Instance of the logger
	 */
	private static Logger logger = Logger.getLogger(GUIdo_PastSales.class.getName());
	
	
	/**
	 * Instance of the catalogue
	 */
	private static Catalogue catalogue = new Catalogue();
	
	/**
	 * Go to past sales frame
	 * @param vendor 	the vender to see past sales for
	 */
	public GUIdo_PastSales(Vendor vendor) {
		super();
		logger.info("Switched to Past Sales");
		this.setBackground(Color.WHITE);
		if(vendor.getPastSales().size() < 2) {
			this.setPreferredSize(new Dimension(this.getWidth(), 500));
		}else {
			this.setPreferredSize(new Dimension(this.getWidth(), 500 + 125 * vendor.getPastSales().size()));
		}
		this.drawScreen(vendor);
		this.repaint();
	}
	
	/**
	 * Draw the screen
	 * @param vendor	the vender to draw
	 */
	public void drawScreen(Vendor vendor) {
		DecimalFormat df = new DecimalFormat("0.00");
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		int x = 0;
		int y = 0;
		
		JLabel pastSales = new JLabel("Past Sales");
		pastSales.setFont(new Font("Cambria", Font.BOLD, 34));
		c.gridx = x;
		c.gridy = y;
		c.insets = new Insets(0, 10, 0 , 10);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(pastSales, c);
		
		for(Sale s: vendor.getPastSales()) {
			JLabel sale = new JLabel("Sale On: " + s.getDateTime());
			sale.setFont(new Font("Cambria", Font.BOLD, 22));
			y++;
			c.weighty = 0;
			c.gridy = y;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.NONE;
			this.add(sale, c);
			
			for(Entry<Integer, LineItem> entry : s.getItemList().entrySet()) {
				ItemInfo item = catalogue.getItem(entry.getValue().getItemID());
				JLabel name = new JLabel(item.getDisplayName() + " x " + entry.getValue().getQuantity());
				name.setFont(new Font("Cambria", Font.PLAIN, 16));
				y++;
				c.gridy = y;
				c.anchor = GridBagConstraints.LINE_START;
				c.fill = GridBagConstraints.NONE;
				this.add(name, c);
			}
			
			JLabel total = new JLabel("Total: $" + df.format(s.getTotalWithTax()));
			total.setFont(new Font("Cambria", Font.BOLD, 16));
			total.setForeground(Color.RED);
			y++;
			c.gridy = y;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.NONE;
			this.add(total, c);
			y++; // increment twice to have extra space in between orders
			JLabel space = new JLabel("                                    ");
			c.gridy = y;
			this.add(space, c);
		}
		JLabel spacey = new JLabel("                  ");
		y++;
		c.gridy = y;
		c.weighty = 6;
		this.add(spacey, c);
	}

}
