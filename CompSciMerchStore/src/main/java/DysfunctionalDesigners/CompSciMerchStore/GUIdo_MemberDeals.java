package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUIdo_MemberDeals extends GUIdo_CPanel implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public GUIdo_MemberDeals(List<List<Integer>> everything, Customer c) {
	
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.drawScreen(everything, c);
		this.repaint();
		
		
	}
	public GUIdo_MemberDeals(List<Integer> itemIDs, Administrator c) {
		
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.drawScreen(itemIDs, c);
		this.repaint();
		
		
	}
	
	public void promptLevelChange(Customer c) {
		JOptionPane j = new JOptionPane();
		final String options[] = {"General", "Middle", "Elite", "No change"};
		
		String input = (String) JOptionPane.showInputDialog(null, "Before You Shop:",
		        "Would you like to change your member level?", JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
		
		switch(input) {
		
		case "General":
			c.setStatus(MemberLevel.GENERAL);
			break;
		case "Middle":
			c.setStatus(MemberLevel.MIDDLE);
			break;
		case "Elite":
			c.setStatus(MemberLevel.ELITE);
			break;
		default:
			//nothing to change 
		
		}
	}
	
	public void drawScreen(List<List<Integer>> items, Customer c) {
		Boolean ready = false;
		String title = "Member Deals";
		ActionListener e = null;
		List<Integer> toiterate = new ArrayList<Integer>();
		List<ItemInfo> topass = new ArrayList<ItemInfo>();
		Catalogue cat = Catalogue.getInstance();
		
		if(ready)
			promptLevelChange(c);
		
		if(c.getStatus() == MemberLevel.GENERAL) {		
			toiterate = items.get(0);	
		}
		else if(c.getStatus() == MemberLevel.MIDDLE) {			
			toiterate = items.get(1);
		}
		else if(c.getStatus() == MemberLevel.ELITE) {		
			toiterate = items.get(2);
		}
		else {
			//nah
		}
		
		for(int i = 0; i < toiterate.size(); i++) {
			topass.add(cat.getItem(toiterate.get(i)));
			System.out.println(topass.get(i).getDisplayName());
		}
		
		GUIdo_ItemCollection display = new GUIdo_ItemCollection(getWidth(),topass,title,e,c);

		display.setVisible(true);
		this.add(display);
		Graphics g;
		
//		display.paintComponent(g);
		this.repaint();
		
		
		
		
		
	}
	
	public void drawScreen(List<Integer> items, Administrator c) {
//		promptLevelChange(c);
		
	}
	
}












