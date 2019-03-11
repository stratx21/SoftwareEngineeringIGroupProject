package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIdo_CButton extends JButton implements MouseListener{
	
	private boolean permanant_select=false;
	
	private int id;
	
	private boolean disabled=false;
	
	ActionListener onClicked=null;
	
	private String custom_action_command="CLICKED";
	
	//stale image, hover image, clicked image
	private boolean hasIcons=false;
	
	private ImageIcon staleIcon,hoverIcon,clickedIcon;
	
	/**
	 * Set up the GUIdo_CButton with x,y,width, and height as base
	 * @param x the x value to start at 
	 * @param y the y value to start at 
	 * @param width the width of the button 
	 * @param height the height of the button 
	 */
	public GUIdo_CButton(int x,int y,int width, int height) {
		super.setBounds(x, y, width, height);
		this.addMouseListener(this);
	}
	
	/**
	 * Set up the GUIdo_CButton with x,y,width,height, and a text label
	 * 
	 * @param x the x value to start at 
	 * @param y the y value to start at 
	 * @param width the width of the button 
	 * @param height the height of the button 
	 * @param text the text to put on the label
	 */
	public GUIdo_CButton(int x,int y,int width,int height, String text) {
		this(x,y,width,height);
		this.setText(text);
	}
	
	
	/**
	 * Set up the GUIdo_CButton with x,y,width,height, and icons
	 * 
	 * @param x the x value to start at 
	 * @param y the y value to start at 
	 * @param width the width of the button 
	 * @param height the height of the button 
	 * @param icons the icons to use for the button in its different states 
	 */
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon[] icons) {
		this(x,y,width,height);
		
		
		if(icons.length>=3) {
			this.hasIcons=true;
			this.staleIcon=icons[0];
			this.hoverIcon=icons[1];
			this.clickedIcon=icons[2];
		} else {
			System.err.println("ERROR: ImageIcon[] icons size provided invalid; size is " 
					+ icons.length + ", requires 3 icons");
		}
	}
	
	/**
	 * Set up the GUIdo_CButton with x,y,width,height, and icons
	 * 
	 * @param x the x value to start at 
	 * @param y the y value to start at 
	 * @param width the width of the button 
	 * @param height the height of the button 
	 * @param stale the icon used for when the button is inactive 
	 * @param hover the icon used when the mouse hovers over the button
	 * @param clicked the icon used when the mouse is clicking on the button
	 */
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon stale,ImageIcon hover,ImageIcon clicked) {
		this(x,y,width,height);
		this.hasIcons=true;
		this.staleIcon=stale;
		this.hoverIcon=hover;
		this.clickedIcon=clicked;
	}
	
	
	/**
	 * Set up the GUIdo_CButton with x,y,width,height, a text label, and an 
	 * 	option for the border
	 * 
	 * @param x the x value to start at 
	 * @param y the y value to start at 
	 * @param width the width of the button 
	 * @param height the height of the button 
	 * @param text the text to put on the label
	 * @param hasBorder a boolean telling if the button should have a border
	 */
	public GUIdo_CButton(int x,int y,int width,int height, String text,boolean hasBorder) {
		this(x,y,width,height,text);
		
		
		if(!hasBorder)
			this.disableBorder();
	}
	
	/**
	 * Set up the GUIdo_CButton with x,y,width,height, and icons
	 * 
	 * @param x the x value to start at 
	 * @param y the y value to start at 
	 * @param width the width of the button 
	 * @param height the height of the button 
	 * @param icons the icons to use for the button in its different states 
	 * @param hasBorder a boolean telling if the button should have a border
	 */
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon[] icons,boolean hasBorder) {
		this(x,y,width,height,icons);
		
		
		if(!hasBorder)
            this.disableBorder();
	}
	
	/**
	 * Styling: Disable the border for the button
	 */
	public void disableBorder() {
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	/**
	 * Disable the changes between icons of the button and
	 * 	any actions of the button
	 */
	public void disable() {
		this.disabled=true;
		if(this.hasIcons)
			this.setIcon(staleIcon);
	}
	
	/**
	 * enable the changes between icons of the button and
	 * 	any actions of the button
	 */
	public void enable() {
		this.disabled=false;
	}

	/**
	 * enable icons if the icons exist
	 */
	public void enableIcons() {
		if(  this.staleIcon   != null
		   &&this.clickedIcon != null
		   &&this.hoverIcon   != null)
			this.hasIcons=true;
		else
			System.err.println("ERROR: tried to enable icons in GUIdo_CButton "
					+ "but one or more icons does not exist");
	}
	
	/**
	 * Enable the icons and set them 
	 * @param stale the icon used for when the button is inactive 
	 * @param hover the icon used when the mouse hovers over the button
	 * @param clicked the icon used when the mouse is clicking on the button
	 */
	public void enableIcons(ImageIcon stale, ImageIcon hover,ImageIcon clicked) {
		this.staleIcon=stale;
		this.hoverIcon=hover;
		this.clickedIcon=clicked;
		this.hasIcons=true;
	}
	
	/**
	 * disable the using of icons
	 */
	public void disableIcons() {
		this.hasIcons=false;
		this.setIcon(null);//not tested
	}
	
	/**
	 * set the ActionListener to call upon when the button is clicked
	 * @param newone
	 */
	public void setActionListener_clicked(ActionListener newone) {
		this.onClicked=newone;
	}
	
	/**
	 * Set the new actionCommand to be used for the ActionListener
	 * 	when it is invoked. 
	 * 
	 * @param newActionCommand the new actionCommand String to be used
	 * 	when the ActionListener for the click is invoked
	 */
	public void setActionCommand(String newActionCommand) {
		this.custom_action_command=newActionCommand;
	}
	
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		//none?
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(hoverIcon);
			}
		}
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(staleIcon);
			}
		}
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(clickedIcon);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(staleIcon);
			}
			
			if(this.onClicked != null) {
				this.onClicked.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,custom_action_command));
			}
		}
	}

}
