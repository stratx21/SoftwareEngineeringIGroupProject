package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class GUIdo_AddReview extends GUIdo_CPanel implements ActionListener{
	private static Logger logger = Logger.getLogger(GUIdo_AddReview.class.getName());
	
	public GUIdo_AddReview(ItemInfo item, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane ) {
		this.setPreferredSize(new Dimension(this.getWidth(), 500));
		this.drawScreen(item, customer, current_panel, scrollpane);
		this.repaint();
	}
	
	public void drawScreen(ItemInfo item, Customer customer, GUIdo_CPanel current_panel, JScrollPane scrollpane) {
		MaskFormatter reviewFormat = null;
		try {
			reviewFormat = new MaskFormatter("#");
		} catch (ParseException e) {
			
			logger.severe("ERROR: Caught ParseException in AddReview");
			e.printStackTrace();
		}
		
		JLabel addReviewTitle = new JLabel("Add a Review For: " + item.getDisplayName());
		addReviewTitle.setFont(new Font("Cambria", Font.BOLD, 34));
		JLabel numStars = new JLabel("Number of Stars (1-5):");
		numStars.setFont(new Font("Cambria", Font.PLAIN, 16));
		JFormattedTextField numberOfStars = new JFormattedTextField(reviewFormat);
		numberOfStars.setPreferredSize(new Dimension(30, 20));
//		JLabel reviewTitle = new JLabel("Review Title:");
//		reviewTitle.setFont(new Font("Cambria", Font.PLAIN, 16));
//		JTextField reviewTitleField = new JTextField();
//		reviewTitleField.setPreferredSize(new Dimension(15, 20));
		JLabel review = new JLabel("Add Review Here:");
		review.setFont(new Font("Cambria", Font.PLAIN, 16));
		JTextArea reviewField = new JTextArea(20, 10);
		reviewField.setLineWrap(true);
		reviewField.setEditable(true);
		reviewField.setWrapStyleWord(true);
		reviewField.setMaximumSize(new Dimension(30,30));
		reviewField.setPreferredSize(new Dimension(100, 100));
		GUIdo_CButton addReview = new GUIdo_CButton(0, 0, 100, 50, "Add Review");
		
		addReview.setActionListener_clicked(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String description = reviewField.getText();
				int rating = Integer.valueOf(numberOfStars.getText());
				Review review = new Review(description, customer.getUserID(), rating);
				item.addReview(review);
				
				// TODO: link to ItemDisplay panel
			}
			
		});
		
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 10, 0, 10);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		this.add(addReviewTitle, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(numStars, c);
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(numberOfStars, c);
//		c.gridx = 0;
//		c.gridy = 6;
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.NORTHWEST;
//		this.add(reviewTitleField, c);
//		c.gridx = 0;
//		c.gridy = 7;
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.NORTHWEST;			
//		this.add(reviewTitle, c);
		c.gridx = 0;
		c.gridy = 8;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(review, c);
		c.gridx = 0;
		c.gridy = 9;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(reviewField, c);
		c.gridx = 2;
		c.gridy = 12;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 1;
		this.add(addReview, c);
	}
	
//	public void to_itemDisplay(GUIdo_CPanel current_panel, JScrollPane scrollpane ) {
//		
//	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
