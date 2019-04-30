package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GUIdo_CPanel extends JPanel{
	
	/**
	 * The length of the page in pixels. 
	 */
	protected int page_length = 400;
	
	/**
	 * Set up the panel with the default setup. 
	 */
	public GUIdo_CPanel() {
		//allow freedom by setting the layout to null
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setAutoscrolls(true);
	}
	
	/**
	 * Set up the panel with the page length in pixels, and set the proper size. 
	 * 
	 * @param page_length_pixels the number of pixels the page length should be. 
	 */
	public GUIdo_CPanel(int page_length_pixels) {
		this();
		this.setBackground(Color.WHITE);
		this.page_length=page_length_pixels;
		this.setPreferredSize(new Dimension(this.getWidth(), page_length_pixels));
		this.setSize(new Dimension(this.getWidth(), page_length_pixels));
	}
	
	/**
	 * Set up the panel with the page length in pixels and the width of the page 
	 * 	in pixels. 
	 * 
	 * @param width the width of the page in pixels. 
	 * @param page_length_pixels the length of the page in pixels. 
	 */
	public GUIdo_CPanel(int width,int page_length_pixels) {
		this();
		this.setBackground(Color.WHITE);
		this.page_length=page_length_pixels;
		this.setPreferredSize(new Dimension(width, page_length_pixels));
		this.setSize(new Dimension(width, page_length_pixels));
	}
}
