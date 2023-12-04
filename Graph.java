import java.util.*;

public class Graph<V, E> implements GraphInterface<V,E> {
	
	private Map<V,Map<V,E>> adjacencyMap;
	private Map<V,Integer> shortestDistances;
	private Map<V,V> predecessors;
	
	public Graph() {
		this.adjacencyMap = new HashMap<>();
		this.shortestDistances = new HashMap<>();
		this.predecessors = new HashMap<>();
	}
	
	@Override 
	public boolean addVertex(V v) {
		if(v == null) {
			throw new NullPointerException("The vertex cannot be null.");
		}
		if(!adjacencyMap.containsKey(v)) {
			adjacencyMap.put(v,new HashMap<>());
			return true;
		}
		return false;
	}
	
	@Override
	public E addEdge(V sourceVertex, V destinationVertex, int weight, String description) {
        if (!adjacencyMap.containsKey(sourceVertex) || !adjacencyMap.containsKey(destinationVertex)) {
            throw new IllegalArgumentException("Source or destination vertex not found in the graph.");
        }
        E newEdge = (E) new Road((Town) sourceVertex, (Town) destinationVertex, weight, description);
        adjacencyMap.get(sourceVertex).put(destinationVertex, newEdge);
        
        
        E reverseEdge = (E) new Road((Town) destinationVertex, (Town) sourceVertex, weight, description);
        adjacencyMap.get(destinationVertex).put(sourceVertex, reverseEdge);
        return newEdge;
    }
	
	@Override
	public boolean containsVertex(V v) {
		if(v == null) return false;
		return adjacencyMap.containsKey(v);
	}
	
	@Override
	public boolean containsEdge(V sourceVertex, V destinationVertex) {
		if(sourceVertex == null || destinationVertex == null) {
			return false;
		}
		return adjacencyMap.containsKey(sourceVertex) && adjacencyMap.get(sourceVertex).containsKey(destinationVertex);
	}
	
	@Override
	public Set<E> edgesOf(V vertex){
		if(vertex == null) {
			throw new NullPointerException("Vertex cannot be null.");
		}
		if(!adjacencyMap.containsKey(vertex)) {
			throw new IllegalArgumentException("Vertex not found.");
		}
		return new HashSet<>(adjacencyMap.get(vertex).values());
	}
	
	@Override
	public E removeEdge(V sourceVertex, V destinationVertex, int weight, String description) {
		if(!adjacencyMap.containsKey(sourceVertex)) return null;
		Map<V,E> edges = adjacencyMap.get(sourceVertex);
		E edge = edges.get(destinationVertex);
		if(edge != null && edgeMatches(edge, weight, description)) {
			edges.remove(destinationVertex);
			return edge;
		}
		return null;
	}
	
	@Override
	public boolean removeVertex(V v) {
		if(v == null || !adjacencyMap.containsKey(v)) return false;
		adjacencyMap.values().forEach(map -> map.remove(v));
		adjacencyMap.remove(v);
		return true;
	}
	
	@Override
	public Set<V> vertexSet(){
		return new HashSet<>(adjacencyMap.keySet());
	}
	
	public ArrayList<String> shortestPath(V sourceVertex, V destinationVertex){
		dijkstraShortestPath(sourceVertex);
		return buildPath(predecessors, sourceVertex, destinationVertex);
	}
	
	public void dijkstraShortestPath(V sourceVertex) {
		shortestDistances.clear();
		predecessors.clear();
		Set<V> visited = new HashSet<>();
		
		for(V vertex : adjacencyMap.keySet()) {
			shortestDistances.put(vertex, Integer.MAX_VALUE);
		}
		
		shortestDistances.put(sourceVertex, 0);
		
		PriorityQueue<V> queue = new PriorityQueue<>(Comparator.comparing(shortestDistances::get));
		queue.add(sourceVertex);
		
		while(!queue.isEmpty()) {
			V currentVertex = queue.poll();
			visited.add(currentVertex);
			
			for(Map.Entry<V, E> neighborEntry : adjacencyMap.get(currentVertex).entrySet()){
				V neighbor = neighborEntry.getKey();
				if(!visited.contains(neighbor)) {
					int edgeWeight = getEdgeWeight(neighborEntry.getValue());
					int newDistance = shortestDistances.get(currentVertex) + edgeWeight;
					
					if(newDistance < shortestDistances.get(neighbor)) {
						shortestDistances.put(neighbor, newDistance);
						predecessors.put(neighbor, currentVertex);
						queue.add(neighbor);
					}
				}
			}
		}
	}
	
	private boolean edgeMatches(E edge, int weight, String description) {
		if(edge instanceof Road) {
			Road road = (Road) edge;
			return road.getweight() == weight && road.getname().equals(description);
		}
		return false;
	}
	
	private int getEdgeWeight(E edge) {
		if(edge instanceof Road) {
			return ((Road)edge).getweight(); 
		}
		return 0;
	}
	
	private ArrayList<String> buildPath(Map<V,V> predecessors, V sourceVertex, V destinationVertex){
		LinkedList<V> path = new LinkedList<>();
		V step = destinationVertex;
		
		if(predecessors.get(step) == null) return new ArrayList<>();
		
		path.add(step);
		while(!step.equals(sourceVertex)) {
			step = predecessors.get(step);
			path.add(step);
		}
		
		Collections.reverse(path);
		
		ArrayList<String> result = new ArrayList<>();
		for(int i=0; i<path.size() - 1; i++) {
			V startVertex = path.get(i);
			V endVertex = path.get(i + 1);
			E edge = adjacencyMap.get(startVertex).get(endVertex);
			String edgeDescription = "via" + edge.toString() + "to";
			int weight = getEdgeWeight(edge);
			result.add(startVertex + " " + edgeDescription + endVertex + " " + weight);
		}
		
		return result;
	}
	
	public class Edge<T>{
		private T weight;
		private String description;
		
		public Edge(T weight, String description) {
			this.weight = weight;
			this.description = description;
		}
	}

	@Override
	public E getEdge(V sourceVertex, V destinationVertex) {
	    if (sourceVertex == null || destinationVertex == null) {
	        return null;
	    }

	    Map<V, E> edges = adjacencyMap.get(sourceVertex);
	    if (edges != null) {
	        return edges.get(destinationVertex);
	    }
	    return null;
	}
	
	@Override
	public Set<E> edgeSet() {
		Set<E> allEdges = new HashSet<>();
		for(Map<V,E> edges : adjacencyMap.values()) {
			allEdges.addAll(edges.values());
		}
		return allEdges;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
