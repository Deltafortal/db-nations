package org.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

	
	public static void main(String[] args) {
		
		//Declaring variables
		final String URL = "jdbc:mysql://localhost:3306/nations";
		final String USER = "root";
		final String PASSWORD = "root";
		String searchedTerm = "";
		Scanner in = new Scanner(System.in);
		
		
		System.out.println("Inserisci la tua ricerca");
		searchedTerm = in.nextLine();
		
		//Main Logic
		
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
			
			
			String sql = ""
						+	" SELECT "
						+		" countries.name AS name, "
						+		" country_id AS id, "
						+		" regions.name AS region_name, "
						+		" continents.name AS continent_name "
						+	" FROM "
						+		" countries "
						+	" JOIN "
						+		" regions ON countries.region_id = regions.region_id"
						+	" JOIN "
						+		" continents ON regions.continent_id = continents.continent_id "
						+	" WHERE "
						+		" countries.name LIKE ?"
						+	" ORDER BY "
						+		" countries.name;";
			
		
			
				try(PreparedStatement ps = conn.prepareStatement(sql)) {
					
					ps.setString(1, "%" + searchedTerm + "%");
					
					try(ResultSet rs = ps.executeQuery()) {
						
						// While 
						while (rs.next()) {
							
							String name = rs.getString(1);
							String id = rs.getString(2);
							String region = rs.getString(3);
							String continent = rs.getString(4);
							
							System.out.println("[" + id + "]" + " | Name: " + name + " | Region: " + region + " | Continent: " + continent);
							
						}
						
					}
					
				}
			
				System.out.println("Inserisci un id ");
				String insertedId = in.nextLine();
				int id = Integer.parseInt(insertedId);
				
				sql = ""
					+	" SELECT "
					+		" languages.language AS language, "
					+ 		" country_stats.year AS year, "
					+ 		" country_stats.population AS population, "
					+ 		" country_stats.gdp AS gdp "
					+	" FROM "
					+		"  countries "
					+	" JOIN "
					+		" regions ON countries.region_id = regions.region_id "
					+	" JOIN "
					+		" continents ON regions.continent_id = continents.continent_id "
					+	" JOIN "
					+		" country_languages ON country_languages.country_id = continents.continent_id "
					+	" JOIN "
					+		" languages ON languages.language_id = country_languages.language_id "
					+	" JOIN "
					+		" country_stats ON country_stats.country_id = countries.country_id "
					+	" WHERE "
					+		" countries.country_id = ?"
					+		" AND country_stats.year = (SELECT MAX(year) FROM country_stats WHERE country_id = countries.country_id );";
				
				
				
				try(PreparedStatement ps = conn.prepareStatement(sql)) {
					
					ps.setInt(1, id );
					
					try(ResultSet rs = ps.executeQuery()) {
						
						// While 
						while (rs.next()) {
							
							String languages = rs.getString(1);
							String year = rs.getString(2);
							String population = rs.getString(3);
							String gdp = rs.getString(4);
							
							
							System.out.println("Lingue: " + languages + " | " +" Anno: " + year );
							System.out.println("Population: " + population + " | " + "gdp: " + gdp);
							
						}
						
					}
					
				}
				
				
				
		} catch (Exception e) {
			
			System.out.println("DB Error: " + e.getMessage());
			
		}
		
		
		
		in.close();
		
	}
	
}
