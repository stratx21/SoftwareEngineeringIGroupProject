package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class GUIdo_ContactUs extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_ContactUs.class.getName());

	//whatever, user, email, message, title
	JLabel title, getUN, getEmail, message, l5;
	//user, email, message
	JTextField username, email;
	JTextArea messageBody;
	GUIdo_CButton btn1;
	
	private List<String> complaints = new ArrayList<String>();
	
	private void readJSONFile() throws Exception {
		logger.info("Importing complaints.txt");
//		File in = new File(App.resourceTarget + "complaints.txt");
//		ObjectMapper mapper = new ObjectMapper();
//		
//		this.complaints = null;
//		
//		try {
//			complaints = mapper.readValue(in, new TypeReference<String>(){});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		BufferedReader in = new BufferedReader(new FileReader(new File()));
		Path path = Paths.get(App.resourceTarget, "complaints.txt");
		Charset charset = Charset.forName("ISO-8859-1");
		complaints = Files.readAllLines(path, charset);
	}
	
	private void complaintsToJSON(String complaint) {
		if(this.complaints != null) {
			logger.info("Exporting complaints with new complaint.");
//			ObjectMapper mapper = new ObjectMapper();
			
			this.complaints.add(complaint);
			

			try {
				FileWriter writer = new FileWriter(App.resourceTarget + "complaints.txt"); 
				for(Iterator<String> str = this.complaints.iterator(); str.hasNext();) {
				  writer.write(str.next() + "\n");
				}
				writer.close();
				logger.info("Successfully exported.");
			} catch(IOException e) {
				
				logger.severe("ERROR: FAILED TO EXPORT COMPLAINTS: " + e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
	
	GUIdo_ContactUs(){
		super(800);
		logger.info("Switched to ContactUs panel");
		this.setBackground(Color.WHITE);
		GUIdo_ContactUs temp = this;
		try {
			this.readJSONFile();
		} catch (Exception e1) {
			logger.severe("ERROR: FAILED TO IMPORT FILE " + App.resourceTarget + "complaints.txt: " + e1.getMessage());
			e1.printStackTrace();
		}
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		title = new JLabel("Contact Us");
		
		title.setFont(new Font("Cambria", Font.BOLD, 34));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		//title.setBounds(100, 0, 100, 100);
		getUN = new JLabel("Enter username:");
		getUN.setFont(new Font("Cambria",Font.PLAIN,20));
		getEmail = new JLabel("Enter email:");
		getEmail.setFont(new Font("Cambria",Font.PLAIN, 20));
		message = new JLabel("Enter message/complaint:");
		message.setFont(new Font("Cambria", Font.PLAIN, 20));
		//l5 = new JLabel("Contact Us!");
		
		username = new JTextField();
		email = new JTextField();
		messageBody = new JTextArea();
		
		btn1 = new GUIdo_CButton(750, 750, 170, 30, "Submit");
//		
//		getUN.setBounds(100, 100, 200, 30);
//		getEmail.setBounds(300, 100, 200, 30);
//		message.setBounds(100, 200, 200, 30);
		
		
		
		username.setPreferredSize(new Dimension(10,50));
		email.setPreferredSize(new Dimension(10,70));
		messageBody.setPreferredSize(new Dimension(500,500));
		
		
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Customer> allCust = control.getAllCustomers();
				if(!username.getText().isEmpty()) {
					for(int i = 0; i < allCust.size(); i++) {
						if(username.getText().equals(allCust.get(i).getUserName())) {
							String complaint = allCust.get(i).getComplaintPrefix() + messageBody.getText();
							temp.complaintsToJSON(complaint);
							logger.info("Found user and exporting complaint: \"" + complaint + "\"");
						}
					}
				}
				
				JOptionPane.showMessageDialog(panel, "Complaint has been submitted!", "Accepted", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		messageBody.setEditable(true);
		messageBody.setLineWrap(true);
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 0;
		this.add(title,c);
		c.gridy = 1;
		this.add(getUN,c);
		c.gridy = 3;
		this.add(getEmail,c);
		c.gridy = 5;
		this.add(message,c);
		
		c.gridy = 2;
		c.gridx = 0;
		this.add(username,c);
		c.gridy = 4;
		this.add(email,c);
		c.gridy = 6;
		this.add(messageBody,c);
		c.gridx = 0;
		c.gridy = 7;
		this.add(btn1,c);
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
	}
}
