package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	
	private Connection connection;
	private Scanner scanner;
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public Patient(Connection connection,Scanner scanner)
	{
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void addPatient() {
		System.out.print("Enter the Id: ");
		int id = scanner.nextInt();
		System.out.print("Enter Patient Name: ");
		String name = scanner.next();
		System.out.print("Enter Patient Age: ");
		int age = scanner.nextInt();
		System.out.print("Enter Patient Gender: ");
		String gender = scanner.next();
		
		try {
			String query = "INSERT INTO patients(id,name,age,gender) VALUES(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2,name);
			preparedStatement.setInt(3, age);
			preparedStatement.setString(4,gender);
			int affectedRows = preparedStatement.executeUpdate();
			
			if(affectedRows > 0) {
				System.out.println("Patient Added Successfully");
			}
			else {
				System.out.println("Failed to add the Patient!!");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void viewPatients() {
		String query = "SELECT * FROM patients";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+------------+---------------------+---------+----------+");
			System.out.println("| Patient Id | Name                | Age     | Gender   |");
			System.out.println("+------------+---------------------+---------+----------+");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				System.out.printf("|%-12s|%-20s|%-9s|%-11s|\n", id, name, age, gender);
				System.out.println("+------------+---------------------+---------+----------+");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id) {
		String query = "SELECT * FROM patients WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
