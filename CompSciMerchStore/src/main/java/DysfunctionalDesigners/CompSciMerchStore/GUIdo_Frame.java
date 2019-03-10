package DysfunctionalDesigners.CompSciMerchStore;

import javax.swing.JFrame;
import javax.swing.JToolBar;

public class GUIdo_Frame extends JFrame{
	
	//JToolBar toolbar = null;
	GUIdo_CToolbar toolbar = null;
	
	GUIdo_CPanel current_panel;
	
	public GUIdo_Frame() {
		
		
		this.initialize();
		
		
	}
	
	public void initialize() {
//		toolbar = new JToolBar();
//		
//		toolbar.addSeparator();
//		
//		toolbar.add(new GUIdo_CButton(0,0,50,50,"hit this"));
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Computer Science Merchandise Store");
		this.setSize(500, 500);
		this.setResizable(true);
		this.setVisible(true);
		
		toolbar = new GUIdo_CToolbar(0,0,500,50);
		
		this.current_panel=new GUIdo_Homescreen();
		
		this.add(toolbar);
		this.add(current_panel);
		
		this.current_panel.repaint();
	}
}
