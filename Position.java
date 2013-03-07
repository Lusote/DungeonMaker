public class Position{

	private int i;
	private int j;

	public Position(int x, int y){
		this.i = x;
		this.j = y;
	}

	// Only checks map limits
	public boolean isValidPosition(){
		if( 0 <= this.getX() && this.getX() <= 79 &&
			0 <= this.getY() && this.getY() <= 24 ){
			return true;
		}
		return false;
	}

	// Only checks for map limits
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

	public Position getPositionNW(){
		Position p = new Position(this.getX()-1, this.getY()-1);
		return p;
	}

	public Position getPositionNE(){
		Position p = new Position(this.getX()-1, this.getY()+1);
		return p;
	}

	public Position getPositionSW(){
		Position p = new Position(this.getX()+1, this.getY()-1);
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