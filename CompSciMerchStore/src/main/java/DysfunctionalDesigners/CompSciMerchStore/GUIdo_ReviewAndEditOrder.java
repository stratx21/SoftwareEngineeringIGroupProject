package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GUIdo_ReviewAndEditOrder extends GUIdo_CPanel implements ActionListener{
	
	/**
	 * This sets up the GUIdo_ReviewAndEditOrder instance 
	 */
	public GUIdo_ReviewAndEditOrder(Sale sale) {
	    super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
	    this.drawScreen(sale);
	    this.drawCart(sale);
  	    this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawRect(950, 120, 225, 250);
		g.drawLine(1025, 170, 1105, 170);
		g.drawLine(1025, 325, 1105, 325);
	}
	
	
	public void drawScreen(Sale sale) {
		DecimalFormat df2 = new DecimalFormat("0.00");
		
		// title at top of screen
		JLabel label= new JLabel("Review and Edit Order");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setSize(1300, 100);
		label.setFont(new Font("Cambria", Font.BOLD, 34));
		this.add(label);
		
		// initial subtotal box component
		JLabel od = new JLabel("Order Details");
		JLabel subtotal = null;
		if(sale.getNumItems() == 1) {
			subtotal = new JLabel("Subtotal (" + sale.getNumItems() + 
					" item):                        $" 
				    + df2.format(sale.getTotalWithoutTax()));
		}else {
			subtotal = new JLabel("Subtotal (" + sale.getNumItems() 
			       + " items):                       $" 
				   + df2.format(sale.getTotalWithoutTax()));
		}
		JLabel estTax = new JLabel("Estimated Tax:                               $" 
						+ df2.format(sale.getEstimatedTax()));
		JLabel total = new JLabel
						("Total:                                           $" 
						+ df2.format(sale.getTotalWithTax()));
		JLabel shipping = new JLabel
				("Shipping:                                       $68.00");
		
		od.setFont(new Font("Cambria", Font.ROMAN_BASELINE, 22));
		od.setHorizontalAlignment(SwingConstants.RIGHT);
		od.setSize(1125, 300);
		subtotal.setFont(new Font("Cambria", Font.PLAIN, 14));
		subtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		subtotal.setSize(1172, 425);
		estTax.setFont(new Font("Cambria", Font.PLAIN, 14));
		estTax.setHorizontalAlignment(SwingConstants.RIGHT);
		estTax.setSize(1172, 500);
		total.setFont(new Font("Cambria", Font.BOLD, 14));
		total.setHorizontalAlignment(SwingConstants.RIGHT);
		total.setSize(1173, 700);
		total.setForeground(Color.red);
		shipping.setFont(new Font("Cambria", Font.PLAIN, 14));
		shipping.setHorizontalAlignment(SwingConstants.RIGHT);
		shipping.setSize(1172, 575);
		
		this.add(subtotal);
		this.add(od);
		this.add(estTax);
		this.add(total);
		this.add(shipping);
		
		GUIdo_CButton proceed = new GUIdo_CButton(965, 415, 200, 50, "Proceed to Checkout");
		this.add(proceed);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void drawCart(Sale sale) {
		int x = 75;
	    int y = 100;
	    int w = 250;
	    int h = 250;
	    int x2 = 800;
	    int y2 = 300;
	    int yq = 450;
	    int x3 = 450;
	    int y3 = 200;
	    String[] numItems = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	    

		if(sale.getNumUniqueItems() > 0) { // CHANGE TO > 0
			for(Entry<Integer, LineItem> i : sale.getItemList().entrySet()) {
			//for(int j = 0; j < 2; j++) {
//				i.getKey();
//				Catalogue.getItem(i.getValue().getItemID());
//				Catalogue.getItem(i.getKey());
				ItemInfo currItem = Catalogue.getItem(i.getKey());
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("src/main/resources/itemimages/" + currItem.getExtendedItemID() + ".jpg"));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    JLabel pic = new JLabel(new ImageIcon(image));
			    pic.setBounds(x, y, w, h);
			    this.add(pic);
			    y += h + 100;
			    
			    // price component

			    JLabel price = new JLabel("Price: $" + sale.getItemList().get(i.getKey()).getTotalPrice());
			    price.setHorizontalAlignment(SwingConstants.CENTER);
			    price.setSize(x2, y2);
			    y2 += 2*h + 200; // unsure about constant factor multiplier to maintain continuity in position
			    price.setFont(new Font("Cambria", Font.PLAIN, 18));
			    this.add(price);
			    
			    // quantity component
			    JLabel q = new JLabel("Quantity:");
			    q.setHorizontalAlignment(SwingConstants.CENTER);
			    q.setSize(x2 - 35, yq);
			    yq += 2*h + 200;
			    q.setFont(new Font("Cambria", Font.PLAIN, 18));
			    this.add(q);
			    
			    JComboBox quantity;
			    quantity = new JComboBox(numItems);
			    //quantity.addActionListener(this);
			    quantity.setSelectedIndex(sale.getItemList().get(i.getKey()).getQuantity());
			    quantity.setBounds(x3, y3, 50, 50);
			    y3 += 350;
			    this.add(quantity);
			    
			    quantity.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox comboBox = (JComboBox) e.getSource();
						Object selected = comboBox.getSelectedItem();
						String command = e.getActionCommand();
						
						if("comboBoxChanged".equals(command)) {
		                    if(sale.editQuantity(Catalogue.getItem(i.getKey()).getItemID(), Integer.parseInt((String)selected))) {
		                    	//getRootPane().add(comboBox);
		                    	updateOrderDetails(sale);
		                    }

		                }
					}
			    	
			    });
			    
			    /*
			    JLabel quan = new JLabel();
			    JComboBox<String> quantity = new JComboBox<String>(quant);
			    quantity.setSelectedIndex(1); //sale.getItemList().get(i).getQuantity());
			    quantity.addActionListener(this);
			    quan.setFont(new Font("Cambria", Font.PLAIN, 12));
			    quan.setHorizontalAlignment(SwingConstants.CENTER);
			    quan.setBorder(BorderFactory.createEmptyBorder(600,0,0,0));
			    quan.setPreferredSize(new Dimension(400, 300));
			    this.add(quantity, BorderLayout.PAGE_START);
			    this.add(quan, BorderLayout.PAGE_END);
			    */
			}
		}
	}
	
	public void updateOrderDetails(Sale sale) {
		// update JLabels for order detail components with new data
		DecimalFormat df2 = new DecimalFormat("0.00");
		JLabel subtotal = null;
		if(sale.getNumItems() == 1) {
			subtotal = new JLabel("Subtotal (" + sale.getNumItems() + 
					" item):                        $" 
				    + df2.format(sale.getTotalWithoutTax()));
		}else {
			subtotal = new JLabel("Subtotal (" + sale.getNumItems() 
			       + " items):                       $" 
				   + df2.format(sale.getTotalWithoutTax()));
		}
		JLabel estTax = new JLabel("Estimated Tax:                               $" 
						+ df2.format(sale.getEstimatedTax()));
		JLabel total = new JLabel
						("Total:                                           $" 
						+ df2.format(sale.getTotalWithTax()));
		
		subtotal.setFont(new Font("Cambria", Font.PLAIN, 14));
		subtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		subtotal.setSize(1172, 425);
		estTax.setFont(new Font("Cambria", Font.PLAIN, 14));
		estTax.setHorizontalAlignment(SwingConstants.RIGHT);
		estTax.setSize(1172, 500);
		total.setFont(new Font("Cambria", Font.BOLD, 14));
		total.setHorizontalAlignment(SwingConstants.RIGHT);
		total.setSize(1173, 700);
		total.setForeground(Color.red);
		
		this.add(subtotal);
		this.add(estTax);
		this.add(total);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
	}

}
