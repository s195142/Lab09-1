package it.polito.tdp.borders.model;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> grafo;

	public Model() {
	
	}

	public boolean isDigit(String anno) {
		return anno.matches("\\d{4}");
	}

	public boolean annoValido(String anno) {
		boolean valido = false;
		if(Integer.parseInt(anno)>=1816 && Integer.parseInt(anno)<=2016) {
			valido = true;
		}
		return valido;
	}

	public String calcolaConfini(String anno) {
		
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		String res = "";
		BordersDAO dao = new BordersDAO();
		dao.creaGrafo(grafo);
		
		for(Country c : grafo.vertexSet()) {
			res += c.toString() + " " + grafo.degreeOf(c) + "\n";
		}
		
		ConnectivityInspector<Country, DefaultEdge> isp = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		res += isp.connectedSets().size();
		return res;
	}

}
