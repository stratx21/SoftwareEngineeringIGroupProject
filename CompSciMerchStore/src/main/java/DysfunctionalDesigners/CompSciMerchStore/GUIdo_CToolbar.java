package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIdo_CToolbar extends GUIdo_CPanel{
	
	private JTextField searchBar;
	
	public GUIdo_CToolbar(int x, int y, int width, int height, final ActionListener done) {
//		super();
		//general setup:
		this.setSize(width, height);
//		this.width=width;
//		this.height=height;
		this.setBackground(Color.PINK);
		
		//home button:
		
		GUIdo_CButton home_button = new GUIdo_CButton(x,y,height,height,"HOME");
		home_button.setActionCommand("home");
		home_button.setActionListener_clicked(done);
		home_button.setBackground(Color.YELLOW);
		
		GUIdo_CButton wishlist = new GUIdo_CButton(x+width-height*3,y,height,height,"WISH");
		wishlist.setActionCommand("wishlist");
		wishlist.setActionListener_clicked(done);
		wishlist.setBackground(Color.YELLOW);
		
		GUIdo_CButton login = new GUIdo_CButton(x+width-height*2,y,height*2,height,"LOGIN");
		login.setActionCommand("login");
		login.setActionListener_clicked(done);
		login.setBackground(Color.cyan);
		
		
		//search bar:
		
		searchBar = new JTextField("search");
		
		searchBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: implement search algorithm here:
				System.out.println("SEARCH invoked with string \"" + searchBar.getText() + "\"");
				
				//use searchBar.getText() to get the String of text entered to search
				
				ActionEvent forPerformed = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,"search");
				forPerformed.setSource(searchBar.getText());
				
				done.actionPerformed(forPerformed);
			}
		});
		searchBar.setSize(width-height*4, height/2);
		searchBar.setLocation(height, height/4);
		
		searchBar.setBackground(new Color(255,102,167));
		
		
		this.add(home_button);
		this.add(searchBar);
		this.add(wishlist);
		this.add(login);
		
		home_button.repaint();
		
//		this.setVisible(true);
		
		this.repaint();
	}
	
}
