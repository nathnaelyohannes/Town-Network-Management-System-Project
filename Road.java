import java.util.Objects;

public class Road implements Comparable<Road> {
	private Town source;
	private Town destination;
	private int weight;
	private String name;

	public Road(Town source, Town destination, int weight, String name) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.name = name;
	}
	
	public Road(Town source, Town destination, String name) {
		this(source, destination, 1, name);
	}
	
	public boolean contains(Town town) {
		return source.equals(town) || destination.equals(town);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getname() {
		return name;
	}
	
	public Town getdestination() {
		return destination;
	}
	
	public Town getSource() {
		return source;
	}
	
	public int getweight() {
		return weight;
	}
	
	@Override
	public int compareTo(Road o) {
		return this.name.compareTo(o.name);
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Road road = (Road) obj;
        return weight == road.weight &&
               name.equals(road.name) &&
               ((source.equals(road.source) && destination.equals(road.destination)) ||
               (source.equals(road.destination) && destination.equals(road.source)));
    }

	
	@Override
    public int hashCode() {
        return Objects.hash(name, Math.min(source.hashCode(), destination.hashCode()), 
                            Math.max(source.hashCode(), destination.hashCode()), weight);
    }


}
