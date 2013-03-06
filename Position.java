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

	public Position getPositionRight(){
		Position p = new Position(this.getX()+1, this.getY());
		return p;
	}	

	public Position getPositionLeft(){
		Position p = new Position(this.getX()-1, this.getY());
		return p;
	}
	
	public Position getPositionOver(){
		Position p = new Position(this.getX(), this.getY()-1);
		return p;
	}
	
	public Position getPositionUnder(){
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