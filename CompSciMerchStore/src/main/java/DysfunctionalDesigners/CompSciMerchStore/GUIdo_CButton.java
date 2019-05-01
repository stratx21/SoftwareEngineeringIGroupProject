package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIdo_CButton extends JButton implements MouseListener{
	
	//NOTE:: addActionListener(ActionListener) inherited will only add an additional
	//ActionListener instance and will not flow with the ActionCommand for the clicks
	//managed by the child CButton. 
	
	/**
	 * The Logger instance used to log events. 
	 * 
	 */
	private static Logger logger = Logger.getLogger(GUIdo_CButton.class.getName());
	

	/**
	 * The boolean value telling if this instance of CButton is disabled. 
	 * 
	 */
	private boolean disabled=false;
	
	/**
	 * The ActionListener instance that is used when this CButton is clicked. 
	 */
	ActionListener onClicked=null;
	
	/**
	 * The color used for when this button is pressed/clicked with the mouse. 
	 */
	private Color pressedColor = null;
	
	/**
	 * The color that is used for when the cursor hovers over this instance of CButton. 
	 */
	private Color hoverColor = null;
	
	/**
	 * The default color that is used as a singular color for this instance of CButton: 
	 */
	private Color defaultColor = null;
	
	/**
	 * The custom action command that is used for when the ActionListener is invoked by 
	 *  this instance of CButton being clicked; it will be passed with the ActionEvent
	 *  that is created and passed up through the ActionListener call. 
	 */
	private String custom_action_command="CLICKED";
	
	/**
	 * This boolean tells if this instance of CButton is using icons instead of plain
	 *  text and/or colors. 
	 */
	private boolean hasIcons=false;
	
	/**
	 * This is the icon that is used when no actions are happening with the mouse. 
	 */
	private ImageIcon staleIcon;
	
	/**
	 * The icon that is used when the cursor hovers over this instance of Cbutton. 
	 */
	private ImageIcon hoverIcon;
	
	/**
	 * The icon that is used when this instance of CButton is clicked, to signal
	 *  to the user that the button is active and is being clicked. 
	 */
	private ImageIcon clickedIcon;
	
	/**
	 * The item that can be used to represent the data that this
	 *  button is holding for dependencies. 
	 */
	private Object data_from_holding;
	
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
			this.staleIcon=new ImageIcon(getScaledImage(icons[0].getImage(),width,height));
			this.hoverIcon=new ImageIcon(getScaledImage(icons[1].getImage(),width,height));
			this.clickedIcon=new ImageIcon(getScaledImage(icons[2].getImage(),width,height));
		} else {
			logger.severe("ERROR: ImageIcon[] icons size provided invalid; size is " 
					+ icons.length + ", requires 3 icons");
		}
		this.repaint();
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
		this.staleIcon=new ImageIcon(getScaledImage(stale.getImage(),width,height));
		this.hoverIcon=new ImageIcon(getScaledImage(hover.getImage(),width,height));
		this.clickedIcon=new ImageIcon(getScaledImage(clicked.getImage(),width,height));
		this.repaint();
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
		
		this.repaint();
	}
	
	private Image getScaledImage(Image source, int width, int height) {
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = resized.createGraphics();

	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(source, 0, 0, width, height, null);
	    g.dispose();

	    return resized;
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
		else {
			logger.severe("ERROR: tried to enable icons in GUIdo_CButton "
					+ "but one or more icons does not exist");
		}
			
	}
	
	/**
	 * Enable the icons and set them 
	 * @param stale the icon used for when the button is inactive 
	 * @param hover the icon used when the mouse hovers over the button
	 * @param clicked the icon used when the mouse is clicking on the button 
	 */
	public void enableIcons(ImageIcon stale, ImageIcon hover,ImageIcon clicked) {
		this.staleIcon=new ImageIcon(getScaledImage(stale.getImage(),this.getWidth(),this.getHeight()));
		this.hoverIcon=new ImageIcon(getScaledImage(hover.getImage(),this.getWidth(),this.getHeight()));
		this.clickedIcon=new ImageIcon(getScaledImage(clicked.getImage(),this.getWidth(),this.getHeight()));
		this.hasIcons=true;
		this.setIcon(this.staleIcon);
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
	
	public void setPressedColor(Color newcolor) {
		this.pressedColor = newcolor;
	}
	
	public void setHoverColor(Color newcolor) {
		this.hoverColor=newcolor;
	}
	
	public void setDefaultColor(Color newcolor) {
		this.defaultColor=newcolor;
	}
	
//	@Override
//	protected void paintComponent(Graphics g) {
//		if(!this.disabled && getModel().isPressed() && this.pressedColor != null) {
//			g.setColor(this.pressedColor);
//		} else if(!this.disabled && getModel().isRollover() && this.hoverColor != null) {
//			g.setColor(this.hoverColor);
//		} else {
//			g.setColor(this.getBackground());
//		}
//		
//		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
//		super.paintComponent(g);
//	}
	
	/**
	 * This overrides the function used for setting the background
	 *  so that the color can be managed manually by the CButton to
	 *  store the default color for the stale state as well. 
	 *  
	 *  @param newcolor the new Color instance that will be used to 
	 *   set the background of this isntance of CButton. 
	 */
	@Override
	public void setBackground(Color newcolor) {
		this.defaultColor=newcolor;
		super.setBackground(newcolor);
	}
	
	/**
	 * The overrriden function from the MouseListener that 
	 *  is invoked when the mouse clicks on the CButton. 
	 */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		//none?
	}

	/**
	 * This is the overrriden function from the MouseListener that is invoked
	 *  when the cursor enters the span of the CButton. 
	 *  
	 */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(hoverIcon);
			} else if(hoverColor != null){
				super.setBackground(this.hoverColor);
			}
		}
	}

	/**
	 * This is the overriden function from the MouseListener that is invoked 
	 *  when the cursor exits the span of the CButton. 
	 *  
	 */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(staleIcon);
			} else if(defaultColor != null){
				super.setBackground(this.defaultColor);
			}
		}
	}

	/**
	 * This is the overriden function from the MouseListener that is invoked
	 *  when the cursor begins a clicking action on the CButton, but before
	 *  the mouse is released. 
	 *  
	 */
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(clickedIcon);
			} else if(pressedColor != null){
				super.setBackground(this.pressedColor);
			}
		}
	}

	/**
	 * This is the overriden function from the MouseListener that is invoked
	 *  when the cursor ends the clicking action on the CButton, so then
	 *  the click is completed and the actions of the buttons will be invoked. 
	 *  
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!this.disabled) {
			if(this.hasIcons) {
				this.setIcon(staleIcon);
			} else if(defaultColor != null){
				super.setBackground(this.defaultColor);
			}
			
			if(this.onClicked != null) {
				this.onClicked.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,custom_action_command));
			}
		}
	}

	/**
	 * This will return the data that the CButton is holding for any 
	 *  purposes that the user may have. 
	 * 
	 * @return the object that the programmer gave to the CButton for
	 *  it to hold. 
	 */
	public Object getData_from_holding() {
		return data_from_holding;
	}

	/**
	 * This sets the data for the CButton to hold for the programmer
	 *  to be able to identify the CButton or use the data later. 
	 * 
	 * @param data_from_holding the data that the programmer wants
	 *  the CButton instance to hold. 
	 */
	public void setData_from_holding(Object data_from_holding) {
		this.data_from_holding = data_from_holding;
	}

}
