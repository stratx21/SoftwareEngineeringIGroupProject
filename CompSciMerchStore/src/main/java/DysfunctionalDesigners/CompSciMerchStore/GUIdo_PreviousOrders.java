package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JLabel;

public class GUIdo_PreviousOrders extends GUIdo_CPanel implements ActionListener{
	
	public Catalogue catalogue = new Catalogue(); // instance to use to search for item names by id
	
	public GUIdo_PreviousOrders(Customer customer) {
		super();
		this.setBackground(Color.WHITE);
		if(customer.getPreviousPurchases().size() < 2) {
			this.setPreferredSize(new Dimension(this.getWidth(), 500));
		}else {
			this.setPreferredSize(new Dimension(this.getWidth(), 500 + 75 * customer.getPreviousPurchases().size()));
		}
		this.drawScreen(customer);
		this.repaint();
	}
	
	public void drawScreen(Customer customer) {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		int x = 0;
		int y = 0;
		
		JLabel previousPurchases = new JLabel("Previous Purchases");
		previousPurchases.setFont(new Font("Cambria", Font.BOLD, 34));
		c.gridx = x;
		c.gridy = y;
		c.insets = new Insets(0, 10, 0 , 10);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(previousPurchases, c);
		
		for(Sale s: customer.getPreviousPurchases()) {
			JLabel orderOn = new JLabel("Order On: " + s.getDateTime());
			orderOn.setFont(new Font("Cambria", Font.BOLD, 22));
			y++;
			c.gridx = x;
			c.gridy = y;
			c.weighty = 0;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(orderOn, c);
			
			for(Map.Entry<Integer, LineItem> entry : s.getItemList().entrySet()) {
				ItemInfo item = catalogue.getItem(entry.getValue().getItemID());
				JLabel name = new JLabel(item.getDisplayName() + " x " + entry.getValue().getQuantity());
				name.setFont(new Font("Cambria", Font.PLAIN, 16));
				y++;
				c.gridy = y;
				c.anchor = GridBagConstraints.LINE_START;
				c.fill = GridBagConstraints.HORIZONTAL;
				this.add(name, c);
			}
			
			JLabel total = new JLabel("Total: $" + s.getTotalWithTax());
			total.setFont(new Font("Cambria", Font.BOLD, 16));
			total.setForeground(Color.RED);
			y++;
			c.gridy = y;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.NONE;
			//c.weighty = 0.2;
			this.add(total, c);
			y++; // increment twice to have extra space in between orders
			JLabel space = new JLabel("                                    ");
			c.gridy = y;
			this.add(space, c);
		}
		JLabel spacey = new JLabel("                  ");
		y++;
		c.gridy = y;
		c.weighty = 1;
		this.add(spacey);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
