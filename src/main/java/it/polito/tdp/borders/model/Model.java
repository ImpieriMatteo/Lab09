package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

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
		TreeMap<Country, Integer> map = new TreeMap<>();
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

	public Set<Country> reachableCountries1(Country country) {
		return new ConnectivityInspector<>(this.graph).connectedSetOf(country);
	}
	
	public List<Country> reachableCountries2(Country country) {
		BreadthFirstIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(this.graph, country);
		List<Country> result = new LinkedList<>();
		
		while(bfv.hasNext()) {
			result.add(bfv.next());
		}
		
		return result;
	}
	
	public List<Country> reachableCountries3(Country country) {
		List<Country> visited = new ArrayList<>();
		List<Country> toVisit = new ArrayList<>();
		
		toVisit.add(country);
		while(country!=null) {
			for(Country co : Graphs.neighborListOf(this.graph, country)) {
				if(!toVisit.contains(co) && !visited.contains(co))
					toVisit.add(co);
			}
			if(!visited.contains(country))
				visited.add(country);
			
			toVisit.remove(0);
			if(!toVisit.isEmpty())
				country = toVisit.get(0);
			else
				country = null;
		}
		
		
		
		
		
		
		
		
		for(Country c : toVisit) {
			for(Country co : Graphs.neighborListOf(this.graph, c)) {
				if(!toVisit.contains(co))
					toVisit.add(co);
			}
			if(!visited.contains(c))
				visited.add(c);
		}
		
		return visited;
	}

}
