package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class GUIdo_ItemDisplay extends GUIdo_CPanel{
	
	private BufferedImage item_image = null;
	
	private ItemInfo item = null;
	
	private int quantity_chosen = 1;
	
	private double image_ratio = 0;
	
	public GUIdo_ItemDisplay(ItemInfo itemToDisplay,int width, Sale cart,ActionListener done) {
		super(1500);
		
		this.setSize(new Dimension(width,1500));
		
		this.item = itemToDisplay;
		try {
			this.item_image = ImageIO.read(new File("src/main/resources/itemimages/"+ this.item.getExtendedItemID() + ".jpg"));
		} catch(Exception e) {
			System.out.println("Error getting image for item in GUIdo_ItemDisplay");
			e.printStackTrace();
		}
		System.out.println("width = " + this.getWidth());
		this.image_ratio = (this.item_image.getWidth()*1.0/this.item_image.getHeight());
		
		JLabel name =  new JLabel(this.item.getDisplayName());
		name.setFont(new Font("Calibri",Font.PLAIN,55));
		name.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*1/10, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(name);
		
		JLabel price =  new JLabel("$"+new DecimalFormat("#.00").format(this.item.getPrice()));
		price.setFont(new Font("Calibri",Font.PLAIN,35));
		price.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*5/35, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(price);
		
		JLabel stock = new JLabel("Only " + this.item.getStock() + " left! Get it before it runs out!");
		stock.setBounds(price.getX(), price.getY()+35, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(stock);
		
		JLabel desc =  new JLabel("Item Description:\n"+this.item.getDescription());
		desc.setFont(new Font("Calibri",Font.PLAIN,35));
		desc.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*3/20, this.getWidth()/2-this.getWidth()/10, this.getHeight()/5);
		this.add(desc);
		
		
		JLabel quantityis = new JLabel("Quantity: ");
		quantityis.setFont(new Font("Calibri",Font.PLAIN,20));
		quantityis.setBounds(desc.getX(), desc.getY()+desc.getHeight()+5, 88, 25);
		this.add(quantityis);
		
		ArrayList<String> options = new ArrayList<>();
		
		for(int i = 1; i <= item.getStock() && i < 10; i++) {
			options.add(i+"");
		}
		
		
		JComboBox quantity = new JComboBox(options.toArray());
	    quantity.setSelectedIndex(0);
	    quantity.setBounds(quantityis.getX()+quantityis.getWidth()+5,quantityis.getY(), 50, 25);
	    
	    
	    quantity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quantity_chosen = Integer.parseInt((String)(((JComboBox)e.getSource()).getSelectedItem()));
				
			}
	    	
	    });
	    
	    this.add(quantity);
		
		GUIdo_CButton addtocart = new GUIdo_CButton(quantity.getX()+quantity.getWidth()+5 ,desc.getY()+desc.getHeight()+5, 250, 50, "add to cart");
		addtocart.setActionCommand("add_to_cart");
		addtocart.setActionListener_clicked(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cart.addItem(quantity_chosen, item.getItemID());
				System.out.println("CART SADFGDNF quant : " + cart.getNumItems());
				done.actionPerformed(new ActionEvent(cart,ActionEvent.ACTION_PERFORMED,"item_added"));
			}
		});
		addtocart.setBackground(Color.PINK);
		this.add(addtocart);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		int width = (int)(this.getWidth()/2*this.image_ratio);
		int height = this.getWidth()/2;
		
		if(width > this.getWidth()/2) {
			height *= (width*1.0/(this.getWidth()/2));
			width=this.getWidth()/2;
		}
		
		g.drawImage(this.item_image, 
				this.getWidth()*1/10, this.getHeight()*1/10, width, height, null);
		
		
		
		
		
		
	}
	
}
