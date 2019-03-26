package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class GUIdo_CToolbar extends GUIdo_CPanel{
	
	public boolean getButtons_disabled() {
		return buttons_disabled;
	}

	private JTextField searchBar;
	
	private boolean buttons_disabled = false;
	
	private ArrayList<GUIdo_CButton> buttons = new ArrayList<>();
	
	public GUIdo_CToolbar(int x, int y, int width, int height, final ActionListener done) {
//		super();
		//general setup:
		this.setSize(width, height);
//		this.width=width;
//		this.height=height;
		this.setBackground(Color.PINK);
		
		//home button:
		
		GUIdo_CButton cart = new GUIdo_CButton (width- height* 4,y , height, height, "CART" ) ;
		cart. setActionCommand("cart");
		cart. setActionListener_clicked( done);
		cart.setBackground(Color. YELLOW);
		
		GUIdo_CButton home_button= null;
		try  {
			home_button  =  new GUIdo_CButton(x,y,height,height,
					new ImageIcon(ImageIO.read(new File("src/main/resources/Toolbar/home (1).png"))),
					new ImageIcon(ImageIO.read(new File("src/main/resources/Toolbar/home (2).png"))),
					new ImageIcon(ImageIO.read(new File("src/main/resources/Toolbar/home (3).png"))));
		} catch(Exception e) {
			System.out.println("ERROR setting up home button initialization");
			e.printStackTrace();
		}
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
		searchBar.setSize(width-height*5, height/2);
		searchBar.setLocation(height, height/4);
		
		searchBar.setBackground(new Color(255,102,167));
		
		
		this.add(home_button);
		this.add(searchBar);
		this.add(wishlist);
		this.add(login);
		this.add(cart);
		
		buttons.add(home_button);
		buttons.add(wishlist);
		buttons.add(login);
		buttons.add(cart);
		
		home_button.repaint();
		
		this.repaint();
	}
	
	
	public void disable_all_buttons() {
		for(GUIdo_CButton button : buttons) {
			button.disable();
		}
		buttons_disabled=true;
	}
	
	public void enable_all_buttons() {
		for(GUIdo_CButton button : buttons) {
			button.enable();
		}
		buttons_disabled=false;
	}
	
}
