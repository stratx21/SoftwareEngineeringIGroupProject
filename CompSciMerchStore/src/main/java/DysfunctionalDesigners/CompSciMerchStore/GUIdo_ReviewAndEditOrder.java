package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class GUIdo_ReviewAndEditOrder extends GUIdo_CPanel implements ActionListener{
	private static Logger logger = Logger.getLogger(GUIdo_ReviewAndEditOrder.class.getName());
	
	/**
	 * This sets up the GUIdo_ReviewAndEditOrder instance 
	 */
	public GUIdo_ReviewAndEditOrder(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
	    super();
	    this.setBackground(Color.WHITE);
	    logger.info("Switched to Review and Edit Order");
	    if(sale.getNumUniqueItems() > 0) {
	    	this.setPreferredSize(new Dimension(this.getWidth(), 1000 * (sale.getNumUniqueItems())));
	    }else {
	    	this.setPreferredSize(new Dimension(this.getWidth(), 500));
	    }
		
	    this.drawScreen(sale, 0, customer, current_panel, scrollpane);
  	    this.repaint();
	}
	
	/**
	 * this function draws the frame for the review and edit order screen. 
	 * @param sale the sale to be displayed
	 * @param num num
	 * @param customer the customer the sale belongs to
	 * @param current_panel to set it
	 * @param scrollpane to add it
	 */
	
	public void drawScreen(Sale sale, int num, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		DecimalFormat df2 = new DecimalFormat("0.00");
		if(num == 1) {
			this.removeAll(); // redraw components
			this.setLayout(null);
		}
		
		this.drawCart(sale, customer, current_panel, scrollpane);
		// title at top of screen
		JLabel label= new JLabel("Review and Edit Order");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setSize(1300, 100);
		label.setFont(new Font("Cambria", Font.BOLD, 34));
		this.add(label);
		
		GUIdo_CButton proceed = new GUIdo_CButton(965, 415, 200, 50, "Proceed to Checkout");
		proceed.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				to_shipping(sale, customer, current_panel, scrollpane);
			}
			
		});
		this.add(proceed);
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
		shipping.setFont(new Font("Cambria", Font.PLAIN, 14));
		shipping.setHorizontalAlignment(SwingConstants.RIGHT);
		shipping.setSize(1172, 575);
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
		this.add(od);
		this.add(estTax);
		this.add(total);
		logger.info("Order total was calculated for user " + customer.getUserName() + ": " + total);
		this.add(shipping);
		
		subtotal.repaint();
		estTax.repaint();
		total.repaint();
		this.repaint();
	}
	
	/**
	 * function goes from the order page to the shipping page
	 * @param sale sale to be displayed
	 * @param customer customer used
	 * @param current_panel frame to be set
	 * @param scrollpane scrollpane to add the frame to
	 */
	protected void to_shipping(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		// TODO Auto-generated method stub
		current_panel = new GUIdo_Shipping(sale, customer, current_panel, scrollpane);
		scrollpane.getViewport().add(current_panel);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void drawCart(Sale sale, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		int x = 75;
	    int y = 100;
	    int w = 250;
	    int h = 250;
	    int x2 = 800;
	    int y2 = 300;
	    int yq = 450;
	    int x3 = 450;
	    int y3 = 200;
	    //String[] numItems = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	    DecimalFormat df2 = new DecimalFormat("0.00");
	    
		if(sale.getNumUniqueItems() > 0) { // CHANGE TO > 0
			for(Entry<Integer, LineItem> i : sale.getItemList().entrySet()) {
			//for(int j = 0; j < 2; j++) {
//				i.getKey();
//				Catalogue.getItem(i.getValue().getItemID());
//				Catalogue.getItem(i.getKey());
				ItemInfo currItem = Catalogue.getInstance().getItem(i.getKey());
				BufferedImage image = null;
				JLabel pic = null;
				try {
					image = ImageIO.read(new File(App.resourceTarget + "itemimages/" + currItem.getExtendedItemID() + ".jpg"));
					pic = new JLabel(new ImageIcon(image));
					
					pic.setBounds(x, y, w, h);
					this.add(pic);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			    y += h + 100;
			    
			    // price component

			    JLabel price = new JLabel("Price: $" + df2.format(sale.getItemList().get(i.getKey()).getTotalPrice()));
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
			    
			    //int amount = sale.getItemList().get(currItem.getItemID()).getQuantity();
			    String[] numItems = new String[11];
			    for(int j = 0; j <= currItem.getStock() && j <= 10; j++) {
					numItems[j] = String.valueOf(j);
				}
			    JComboBox quantity;
			    quantity = new JComboBox(numItems);
			    //quantity.setLayout(new GridLayout(0, 1));
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
						
	                    if(sale.editQuantity(Catalogue.getInstance().getItem(i.getKey()).getItemID(), Integer.parseInt((String)selected))) {
	                    	drawScreen(sale, 1, customer, current_panel, scrollpane);
	                    	quantity.updateUI();
	                    }
					}
			    });
			    
			    try {
			    	 pic.repaint();
			    }
			    catch(NullPointerException e) {
			    	logger.severe("ERROR: NULLPOINTEREXCEPTION CAUGHT IN REVIEW/EDIT ORDER");
			    	e.printStackTrace();
			    }
			   
			    price.repaint();
			    q.repaint();
			    //quantity.revalidate();
			    quantity.repaint();
			    quantity.validate();
			    this.repaint();
			   
			    
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
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}
