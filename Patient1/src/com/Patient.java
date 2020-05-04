package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/new1", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readPatient() {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			output = "<table border=\"1\"><th>Patient Name</th><th>Age</th><th>NIC</th><th>Contact No</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from patient";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String UserID = Integer.toString(rs.getInt("userID"));
				String patientName = rs.getString("Name");
				String age = Integer.toString(rs.getInt("age"));
				String nic = rs.getString("nic");
				String phoneNo = Integer.toString(rs.getInt("PhoneNo"));
				// Add into the html table
				output += "<tr><td>" + patientName + "</td>";
				output += "<td>" + age + "</td>";
				output += "<td>" + nic + "</td>";
				output += "<td>" + phoneNo + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button'value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button'value='Remove'class='btnRemove btn btn-danger' data-userID='"
						+ UserID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the patient details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertPatient(String Name, String age, String nic, String phoneNo) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into patient(`userID`,`Name`,`age`,`nic`,`phoneNo`)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Name);
			preparedStmt.setInt(3, Integer.parseInt(age));
			preparedStmt.setString(4, nic);
			preparedStmt.setInt(5, Integer.parseInt(phoneNo));
			// execute the statement
			preparedStmt.execute();
			con.close();
			// output = "Inserted successfully";
			String newPatient = readPatient();
			output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Patient details.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePatient(String ID, String name, String age, String nic, String pNo) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE patient SET Name=?,age=?,nic=?,phoneNo=? WHERE userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setString(1, name);
			preparedStmt.setInt(2, Integer.parseInt(age));
			preparedStmt.setString(3, nic);
			preparedStmt.setInt(4, Integer.parseInt(pNo));
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			// output = "Updated successfully";
			String newPatient = readPatient();
			output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Patient details.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePatient(String userID) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from patient where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			// output = "Deleted successfully";
			String newPatient = readPatient();
			output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the Patient details.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
