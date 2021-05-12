package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> graph;
	private BordersDAO dao;
	private Map<Integer, Country> idMap;

	public Model() {
		this.idMap = new HashMap<>();
		this.dao = new BordersDAO();
		dao.loadAllCountries(idMap);
	}
	
	public void createGraph(int anno) {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		Graphs.addAllVertices(graph, dao.getVertex(anno, idMap));
		
		for(Border b : dao.getCountryPairs(anno, idMap)) {
			Graphs.addEdgeWithVertices(graph, b.getState1(), b.getState2());
		}
	}
	
	public Map<Country, Integer> getVerticesWithDegree() {
		Map<Country, Integer> map = new HashMap<>();
		for(Country c : this.graph.vertexSet()) 
			map.put(c, graph.degreeOf(c));
		
		return map;
	}
	
	public String getNoOfVertexANDEdges() {
		return "Inserted; "+this.graph.vertexSet().size()+" vertices, "+this.graph.edgeSet().size()+" edges";
	}

	public int getNoOfConnectedComponents() {
		return new ConnectivityInspector<>(this.graph).connectedSets().size();
	}

}
