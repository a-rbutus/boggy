
public class Tile {
	
	public Tile(String tile_char) {
		super();
		this.tile_value = tile_char;
	}

	String tile_value;

	public String get_tile_value() {
		return tile_value;
	}

	public void setTile_char(String tile_char) {
		this.tile_value = tile_char;
	}


	@Override
	public String toString() {
		return "Tile [tile_value=" + tile_value + "]";
	}

}
