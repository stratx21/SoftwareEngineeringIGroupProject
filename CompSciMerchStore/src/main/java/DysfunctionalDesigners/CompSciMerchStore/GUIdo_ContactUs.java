package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JTextField;

//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class GUIdo_ContactUs extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_ContactUs.class.getName());

	//whatever, user, email, message, title
	JLabel l1, l2, l3, l4, l5;
	//user, email, message
	JTextField tf1, tf2, tf3;
	GUIdo_CButton btn1;
	
	private List<String> complaints = new ArrayList<String>();
	
	private void readJSONFile() throws Exception {
		logger.info("Importing complaints.txt");
//		File in = new File("src/main/resources/complaints.txt");
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
		Path path = Paths.get("src/main/resources", "complaints.txt");
		Charset charset = Charset.forName("ISO-8859-1");
		complaints = Files.readAllLines(path, charset);
	}
	
	private void complaintsToJSON(String complaint) {
		if(this.complaints != null) {
			logger.info("Exporting complaints with new complaint.");
//			ObjectMapper mapper = new ObjectMapper();
			
			this.complaints.add(complaint);
			
//			try {
//				mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/complaints.json"), this.complaints);
//			} catch (JsonGenerationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JsonMappingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			try {
				FileWriter writer = new FileWriter("src/main/resources/complaints.txt"); 
				for(Iterator<String> str = this.complaints.iterator(); str.hasNext();) {
				  writer.write(str.next() + "\n");
				}
				writer.close();
				logger.info("Successfully exported.");
			} catch(IOException e) {
				// TODO Auto-generated catch block
				logger.severe("ERROR: FAILED TO EXPORT COMPLAINTS: " + e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
	
	GUIdo_ContactUs(){
		super(800);
		logger.info("Switched to ContactUs panel");
		
		GUIdo_ContactUs temp = this;
		try {
			this.readJSONFile();
		} catch (Exception e1) {
			logger.severe("ERROR: FAILED TO IMPORT FILE complaints.txt: " + e1.getMessage());
			e1.printStackTrace();
		}
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		l1 = new JLabel("Contact Us");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("Enter username:");
		l3 = new JLabel("Enter email:");
		l4 = new JLabel("Enter message/complaint:");
		l5 = new JLabel("Contact Us!");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		
		btn1 = new GUIdo_CButton(750, 750, 170, 30, "Submit");
		
		l2.setBounds(100, 100, 200, 30);
		l3.setBounds(300, 100, 200, 30);
		l4.setBounds(100, 200, 200, 30);
		l5.setBounds(400, 30, 200, 30);
		
		tf1.setBounds(100, 140, 200, 30);
		tf2.setBounds(300, 140, 200, 30);
		tf3.setBounds(100, 240, 400, 100);
		
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!tf1.getText().isEmpty()) {
					for(int i = 0; i < control.getAllCustomers().size(); i++) {
						if(tf1.getText().equals(control.getAllCustomers().get(i).getUserName())) {
							String complaint = control.getAllCustomers().get(i).getComplaintPrefix() + tf3.getText();
							temp.complaintsToJSON(complaint);
							logger.info("Found user and exporting complaint: \"" + complaint + "\"");
						}
					}
				}
				JOptionPane.showMessageDialog(panel, "Complaint has been submitted!", "Accepted", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
		this.add(l4);
		this.add(l5);
		
		this.add(tf1);
		this.add(tf2);
		this.add(tf3);
		
		this.add(btn1);
	}
}
