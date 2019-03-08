package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIdo_CButton extends JButton implements MouseListener{
	
	private boolean permanant_select=false;
	
	private int id;
	
	private boolean disabled=false;
	
	
	//stale image, hover image, clicked image
	private boolean hasIcons=false;
	
	private ImageIcon staleIcon,hoverIcon,clickedIcon;
	
	public GUIdo_CButton(int x,int y,int width, int height) {
		super.setBounds(x, y, width, height);
		this.addMouseListener(this);
	}
	
	public GUIdo_CButton(int x,int y,int width,int height, String label) {
		this(x,y,width,height);
		
	}
	
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon[] icons) {
		this(x,y,width,height);
		
		
		if(icons.length>=3) {
			this.hasIcons=true;
			this.staleIcon=icons[0];
			this.hoverIcon=icons[1];
			this.clickedIcon=icons[2];
		}
	}
	
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon stale,ImageIcon hover,ImageIcon clicked) {
		this(x,y,width,height);
		this.hasIcons=true;
		this.staleIcon=stale;
		this.hoverIcon=hover;
		this.clickedIcon=clicked;
	}
	
	public GUIdo_CButton(int x,int y,int width,int height, String label,boolean hasBorder) {
		this(x,y,width,height,label);
		
		
		if(!hasBorder)
			this.disableBorder();
	}
	
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon[] icons,boolean hasBorder) {
		this(x,y,width,height,icons);
		
		
		if(!hasBorder)
            this.disableBorder();
	}
	
	public void disableBorder() {
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void disable() {
		
	}

	public void setIcons() {
		this.hasIcons=true;
	}
	
	public void disableIcons() {
		this.hasIcons=false;
	}
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		//none?
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(this.hasIcons) {
			
		}
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(this.hasIcons) {
			
		}
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(this.hasIcons) {
			
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(this.hasIcons) {
			
		}
	}}
