package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GUIdo_ContactUs extends GUIdo_CPanel{

	//whatever, user, email, message, title
	JLabel l1, l2, l3, l4, l5;
	//user, email, message
	JTextField tf1, tf2, tf3;
	GUIdo_CButton btn1;
	
	private List<String> complaints = new ArrayList<String>();
	
	private void readJSONFile() throws Exception {
		
		File in = new File("src/main/resources/complaints.json");
		ObjectMapper mapper = new ObjectMapper();
		
		this.complaints = null;
		
		try {
			complaints = mapper.readValue(in, new TypeReference<String>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void complaintsToJSON(String complaint) {
		if(this.complaints != null) {
			ObjectMapper mapper = new ObjectMapper();
			
			this.complaints.add(complaint);
			
			try {
				mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/complaints.json"), this.complaints);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	GUIdo_ContactUs(final ActionListener al){
		super(800);
		
		GUIdo_ContactUs temp = this;
		try {
			this.readJSONFile();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated method stub
				if(!tf1.getText().isEmpty()) {
					for(int i = 0; i < control.getAllCustomers().size(); i++) {
						if(tf1.getText().equals(control.getAllCustomers().get(i).getUserName())) {
							temp.complaintsToJSON(control.getAllCustomers().get(i).getComplaintPrefix() + tf3.getText());
						}
					}
				}
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
