import java.util.ArrayList;

/*
*	TODO:
*		Another constructor, complete, if possible.
*		Test getNeighbours.
*		Validtile: adapt methods to Position methods
*/

public class Tile{

	private boolean isExplored;
	private boolean isOnview;
	private ArrayList<Object> thingsOnTile; 
	private int distStairsUp;
	private int distStairsDown;
	private char symbol;
	private Position pos;
	private int level;

	// Test constructor. Not really viable
	public Tile(){
		this.isExplored = false;
		this.isOnview = false;
		this.thingsOnTile = new ArrayList<Object>();
		this.distStairsDown = 0;
		this.distStairsUp = 0;
		this.symbol = 'E';
		this.pos = null;
		this.level = 0;
	}

	// The actual constructor.
	public Tile(boolean explored, boolean view, ArrayList<Object> things,
				int distDown, int distUp, char symb, Position p, int lev){
		this.isExplored = explored;
		this.isOnview = view;
		this.thingsOnTile = new ArrayList<Object>();
		this.distStairsDown = distDown;
		this.distStairsUp = distUp;
		this.symbol = symb;
		this.pos = p;
		this.level = lev;
	}

	// UNTESTED
	// Keep an eye on this
	public ArrayList<Tile> getNeighbours(Dungeon dun){
		ArrayList<Tile> n = new ArrayList<Tile>();
		int i = this.getPosition().getX();
		int j = this.getPosition().getY();
		Tile tileToAdd = new Tile();
		Position p;
		for(int altI = i-1; altI <= i+1 && altI != i ; altI++){
			for(int altJ = j-1; altJ <= j+1 && altJ != j; altJ++){
				p = new Position(i,j);
				tileToAdd = dun.getLevel(this.level).getTile(p);
				if(tileToAdd.isValidTile()){
					System.out.println("Tile.getNeighbours add: "+altI+" "+altJ);
					n.add(tileToAdd);					
				}
			}
		}
		return n;				
	}


	// Only checks for map limits(adapted for rooms)
	// We can't pick a border, because the walls will be out of the grid.
	// Maybe use the Position methods....
	public boolean isValidTileForRoom(){
		Position p = this.getPosition();
		boolean bol = ( 1 <= p.getX() 		 &&
							 p.getX() <= 78  &&
						1 <= p.getY() 		 && 
							 p.getY() <= 23);
		if( bol ){
			return true;
		}
		else{
			return false;
		}
	}

	// Only checks map limits (in general)
	// Maybe use the Position methods....
	public boolean isValidTile(){
		if( 0 <= this.getPosition().getX() && this.getPosition().getX() <= 79 &&
			0 <= this.getPosition().getY() && this.getPosition().getY()<= 24 ){
			return true;
		}
		return false;
	}

	public Position getPosition(){
		return this.pos;
	}

	public void setPosition(Position p){
		this.pos = p;
	}

	public boolean getIsExplored(){
		return this.isExplored;
	}

	public void setExplored(boolean value){
		this.isExplored = value;
	}

	public char getSymbol(){
		return this.symbol;
	}

	public void setSymbol(char givenSymbol){
		this.symbol=givenSymbol;
	}

	public boolean getIsOnView(){
		return this.isOnview;
	}

	public void setOnView(boolean value){
		this.isOnview = value;
	}

	public void putOnTile(Object thing){
		this.thingsOnTile.add(thing);
	}

	public ArrayList getThingsOnTile(){
		return this.thingsOnTile;
	}

	public boolean isTileEmpty(){
		return this.thingsOnTile.isEmpty();
	}

	public int getDistStairsUp(){
		return this.distStairsUp;
	}

	public void setDistStairsUp(int dist){
		this.distStairsUp = dist;
	}

	public int getDistStairsDown(){
		return this.distStairsDown;
	}

	public void setDistStairsDown(int dist){
		this.distStairsDown = dist;
	}

}
