import java.util.ArrayList;
public class Position{

	private int i;
	private int j;

	/* Pretty simple and self-explaining class.
	*	Offers the tools for Position-management
	*	
	* TODO: 
	*	ValidPosition needs to check based on the general constant
	*
	*/

	public Position(int x, int y){
		this.i = x;
		this.j = y;
	}

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
    	if (other == this) return true;
	    if (!(other instanceof Position))return false;
	    Position another = (Position)other;
	    if ((another.getX() == this.getX()) && (another.getY() == this.getY())) return true;
	    else return false;
	}

	// Only checks map limits (in general)
	public boolean isValidPosition(){
		if( 0 <= this.getX() && this.getX() <= 79 &&
			0 <= this.getY() && this.getY() <= 24 ){
			return true;
		}
		return false;
	}

	public boolean isValidPositionForDoor(){
		if( 2 <= this.getX() && this.getX() <= 77 &&
			2 <= this.getY() && this.getY() <= 22 ){
			return true;
		}
		return false;
	}

	// Only checks for map limits(adapted for rooms=
	// We can't pick a border, because the walls will be out of the grid.
	public boolean isValidPositionForRoom(){
		boolean bol = ( 1 <= this.getX() 		 &&
							 this.getX() <= 78  &&
						1 <= this.getY() 		 && 
							 this.getY() <= 23);
		if( bol ){
			return true;
		}
		else{
			return false;
		}
	}

	public ArrayList<Position> getFourNeighbours(){
		ArrayList<Position> toReturn = new ArrayList<Position>();
		toReturn.add(this.getPositionN());
		toReturn.add(this.getPositionS());
		toReturn.add(this.getPositionE());
		toReturn.add(this.getPositionW());
		return toReturn;
	}

	public Position getPositionNW(){
		Position p = new Position(this.getX()-1, this.getY()-1);
		return p;
	}

	public Position getPositionNE(){
		Position p = new Position(this.getX()+1, this.getY()-1);
		return p;
	}

	public Position getPositionSW(){
		Position p = new Position(this.getX()-1, this.getY()+1);
		return p;
	}

	public Position getPositionSE(){
		Position p = new Position(this.getX()+1, this.getY()+1);
		return p;
	}

	public Position getPositionE(){
		Position p = new Position(this.getX()+1, this.getY());
		return p;
	}	

	public Position getPositionW(){
		Position p = new Position(this.getX()-1, this.getY());
		return p;
	}
	
	public Position getPositionN(){
		Position p = new Position(this.getX(), this.getY()-1);
		return p;
	}
	
	public Position getPositionS(){
		Position p = new Position(this.getX(), this.getY()+1);
		return p;
	}

	public int getX(){
		return this.i;
	}

	public int getY(){
		return this.j;
	}

	public void setX(int pos){
		this.i=pos;
	}

	public void setY(int pos){
		this.j=pos;
	}

}