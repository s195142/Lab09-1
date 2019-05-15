package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}

	public void creaGrafo(SimpleGraph<Country, DefaultEdge> grafo) {
		String sql = "SELECT c1.StateAbb as ab1, c1.CCode as cod1, c1.StateNme as n1, c2.StateAbb as a2, c2.CCode as cod2, c2.StateNme as n2 " + 
				"FROM country c1, country c2, contiguity c " + 
				"WHERE c1.CCode = c.state1no AND c2.CCode = c.state2no AND c.conttype = 1 ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int cod1 = rs.getInt("cod1");
				String abb1 = rs.getString("ab1");
				String nome1 = rs.getString("n1");
				int cod2 = rs.getInt("cod2");
				String abb2 = rs.getString("a2");
				String nome2 = rs.getString("n2");
				
				
				Country c1 = new Country(abb1, cod1, nome1);
				Country c2 = new Country(abb2, cod2, nome2);

				if(!grafo.containsVertex(c1)) {
					grafo.addVertex(c1);
				}
				
				if(!grafo.containsVertex(c2)) {
					grafo.addVertex(c2);
				}
				
				
				if(!grafo.containsEdge(c1, c2) && !grafo.containsEdge(c2,c1)) {
					grafo.addEdge(c1, c2);
				}
				
				
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
