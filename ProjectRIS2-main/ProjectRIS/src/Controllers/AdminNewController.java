package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DatabaseConnection;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminNewController {

	//orders
	@FXML
	private TextField OrderPatientName;
	@FXML
	private TextField OrderReferralMD;
	@FXML
	private TextField OrderModalityNeeded;
	@FXML
	private TextArea OrderNotes;
	
	@FXML 
	private Button addNewPatientButton;
	@FXML
	private TextField PatientFirstName;
	@FXML 
	private TextField PatientLastName;
	@FXML 
	private TextField PatientRace;
	@FXML
	private TextField PatientEthnicity;
	@FXML
	private DatePicker DOB;
	@FXML
	private TextField sex;
	@FXML
	private TextField phoneNumber;
	@FXML
	private TextField emailAddress;
	@FXML
	private TextArea patientNotes;
	
	public void userLogOut(ActionEvent event) throws IOException {
		
		Main m = new Main();
		
		m.changeScene("../Views/Login.fxml");
	}
	
	public void HomeButton(ActionEvent event) throws IOException{
		
		Main m = new Main();
		m.changeScene("../Views/AdminReferrals.fxml");
	}
	
	public void newOrderButton(ActionEvent event) throws IOException{
		
		Main m = new Main();
		m.changeScene("../Views/AdminNewOrder.fxml");
	}
	
	
	public void addNewOrder(ActionEvent event) throws IOException {
		  Connection conn = null;
	      Statement stmt = null;
	      
	      
	      try {
	          try {
	             Class.forName("com.mysql.cj.jdbc.Driver");
	          } catch (Exception e) {
	             System.out.println(e);
	       }
	       conn = DatabaseConnection.getConnection();
	       stmt = (Statement) conn.createStatement();
	       int patientid=0;
	       int referralMD_id=0;
	       int modality=0;
	       
	       String patientName = OrderPatientName.getText();
	       String[] name = patientName.split(" ");
	       String firstName = name[0];
	       String lastName = name[1];
	       
	       String myQuery = "select * from patients where first_name=\'"+firstName+"\' and last_name=\'"+lastName+"\';";
	       		//+ "//' //and dob=\'"+DOB.getText()+"\';";;
	       
	       ResultSet rs = stmt.executeQuery(myQuery);
	       while(rs.next())
	       {
	    	   patientid=rs.getInt("patient_id");	
	       }
	       String refDoc = OrderReferralMD.getText();
	       String myQuery2 = "select * from users where full_name=\'"+ refDoc+"\';";
      
	      rs = stmt.executeQuery(myQuery2);
	      while(rs.next())
	      {
	   	   referralMD_id=rs.getInt("user_id");
	   	   //System.out.println("doc is "+referralMD_id);	
	      }
	       
	       
	      String modal = OrderModalityNeeded.getText();
	       String myQuery3 = "select * from modalities where name=\'"+ modal+"\';";
	       //System.out.println(myQuery3);
     
	      rs = stmt.executeQuery(myQuery3);
	      while(rs.next())
	      {
	   	   modality=rs.getInt("modality_id");
	   	   //System.out.println("modal is "+modality);	
	      }
	       
	       String query1 = "INSERT INTO orders(patient, referral_md, modality, notes) VALUES("+patientid+", "+referralMD_id+", "+modality+", '"+OrderNotes.getText()+"');";
	       //System.out.println(query1);
	       stmt.executeUpdate(query1);
	       } catch (SQLException excep) {
	          excep.printStackTrace();
	       } catch (Exception excep) {
	          excep.printStackTrace();
	       } finally {
	          try {
	             if (stmt != null)
	                conn.close();
	          } catch (SQLException se) {}
	          try {
	             if (conn != null)
	                conn.close();
	          } catch (SQLException se) {
	             se.printStackTrace();
	          }  
	       }
	      HomeButton(event);
	      }
	
	public void addNewPatient(ActionEvent event) throws IOException {
		
		 Connection conn = null;
	      Statement stmt = null;
	      
	      
	      try {
	          try {
	             Class.forName("com.mysql.cj.jdbc.Driver");
	          } catch (Exception e) {
	             System.out.println(e);
	       }
	       conn = DatabaseConnection.getConnection();
	       stmt = (Statement) conn.createStatement();
	       
	       String query1 = "INSERT INTO patients(first_name,last_name,dob,sex,race,ethnicity,email_address,phone_number,patientNotes) VALUES  ('"+PatientFirstName.getText()+"','"+PatientLastName.getText()+"', '"+DOB.getValue()+"' ,'"+sex.getText()+"', '"+PatientRace.getText()+"','" +PatientEthnicity.getText()+"','" + emailAddress.getText()+"','" + phoneNumber.getText()+"','" + patientNotes.getText()+"');";
	       //System.out.println(query1);
	       stmt.executeUpdate(query1);
	       
	       } catch (SQLException excep) {
	          excep.printStackTrace();
	       } catch (Exception excep) {
	          excep.printStackTrace();
	       } finally {
	          try {
	             if (stmt != null)
	                conn.close();
	          } catch (SQLException se) {}
	          try {
	             if (conn != null)
	                conn.close();
	          } catch (SQLException se) {
	             se.printStackTrace();
	          }  
	       }
	       HomeButton(event);
	    }
}
