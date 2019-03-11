package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIdo_CToolbar extends GUIdo_CPanel{
	
	private int x,y;
	private int width, height;
	
	private JTextField searchBar;
	
	public GUIdo_CToolbar(int x, int y, int width, int height, final ActionListener done) {
//		super();
		//general setup:
		this.setSize(width, height);
		this.width=width;
		this.height=height;
		this.setBackground(Color.PINK);
		
		//home button:
		
		GUIdo_CButton home_button = new GUIdo_CButton(x+height/10,y+height/10,height*8/10,height*8/10,"HOME");
		home_button.setActionListener_clicked(done);
		home_button.setBackground(Color.YELLOW);
		
		
		//search bar:
		
		searchBar = new JTextField("search");
		
		searchBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: implement search algorithm here:
				System.out.println("SEARCH invoked with string \"" + searchBar.getText() + "\"");
				
				//use searchBar.getText() to get the String of text entered to search
				
				
				done.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,"search"));
			}
		});
		searchBar.setSize(width-height, height/2);
		searchBar.setLocation(height, height/4);
		
		searchBar.setBackground(new Color(255,102,167));
		
		
		this.add(home_button);
		this.add(searchBar);
		
		home_button.repaint();
		
//		this.setVisible(true);
		
		this.repaint();
	}
	
}
