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
	
	public GUIdo_CButton(int x,int y,int width, int height) {
		super.setBounds(x, y, width, height);
		this.addMouseListener(this);
	}
	
	public GUIdo_CButton(int x,int y,int width,int height, String label) {
		this(x,y,width,height);
		
	}
	
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon icon) {
		this(x,y,width,height);
		
	}
	
	public GUIdo_CButton(int x,int y,int width,int height, String label,boolean hasBorder) {
		this(x,y,width,height);
		
		
		if(!hasBorder)
			this.disableBorder();
	}
	
	public GUIdo_CButton(int x,int y,int width,int height,ImageIcon icon,boolean hasBorder) {
		this(x,y,width,height);
		
		
		if(!hasBorder)
            this.disableBorder();
	}
	
	public void disableBorder() {
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void disable() {
		
	}

	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}}
