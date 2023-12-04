import java.util.Objects;

public class Town implements Comparable<Town> {
	
	private String name;
	
	public Town(String name) {
		this.name = name;
	}
	
	public Town(Town templateTown) {
		this.name = templateTown.name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(name);
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Town town = (Town) obj;
        return name.equals(town.name);
    }

	@Override
	public int compareTo(Town o) {
		return this.name.compareTo(o.name);
	}

}
