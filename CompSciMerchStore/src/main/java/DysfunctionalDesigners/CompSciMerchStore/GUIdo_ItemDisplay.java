package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class GUIdo_ItemDisplay extends GUIdo_CPanel{
	
	private BufferedImage item_image = null;
	
	private ItemInfo item = null;
	
	
	
	private double image_ratio = 0;
	
	public GUIdo_ItemDisplay(ItemInfo itemToDisplay,int width/*,ActionListener done*/) {
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
		
		JLabel desc =  new JLabel(this.item.getDescription());
		desc.setFont(new Font("Calibri",Font.PLAIN,35));
		desc.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*3/20, this.getWidth()/2-this.getWidth()/10, this.getHeight()/5);
		this.add(desc);
		
		
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
