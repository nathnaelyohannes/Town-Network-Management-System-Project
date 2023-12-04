import java.util.*;

public class TownGraphManager implements TownGraphManagerInterface{
	private Graph<Town, Road> graph;
	
	public TownGraphManager() {
		graph = new Graph<Town, Road>();
	}
	
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		if(!graph.containsVertex(new Town(town1)) || !graph.containsVertex(new Town(town2))) {
			return false;
		}
		graph.addEdge(new Town(town1), new Town(town2), weight, roadName)
;
		return true;
		}
	
	@Override
	public String getRoad(String town1, String town2) {
		Road road = graph.getEdge(new Town(town1), new Town(town2));
		return (road == null) ? null : road.getname();
	}
	
	@Override
	public boolean addTown(String V) {
		return graph.addVertex(new Town(V));
	}
	
	@Override public Town getTown(String name) {
		if(graph.containsVertex(new Town(name))) {
			return new Town(name);
		}
		return null;
	}
	
	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}
	
	@Override
	public ArrayList<String> allRoads(){
		ArrayList<String> roads = new ArrayList<>();
		for(Road road : graph.edgeSet()) {
			roads.add(road.getname());
		}
		Collections.sort(roads);
		return roads;
	}
	
	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Road r = (Road) graph.removeEdge(new Town(town1), new Town(town2), 0, road);
		return r != null;
		}
		
	@Override
	public ArrayList<String> allTowns(){
		ArrayList<String> towns = new ArrayList<>();
			for(Town town : graph.vertexSet()) {
				towns.add(town.getName());
			}
			Collections.sort(towns);
			return towns;
		}
		
	@Override
	public ArrayList<String> getPath(String town1, String town2){
			return graph.shortestPath(new Town(town1), new Town(town2));
		}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		if(!graph.containsVertex(new Town(town1)) || !graph.containsVertex(new Town(town2))) {
			return false;
		}
		return graph.containsEdge(new Town(town1), new Town(town2));
	}

	@Override
	public boolean deleteTown(String v) {
		return graph.removeVertex(new Town(v));
		
		
	}
	}
	
