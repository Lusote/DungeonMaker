
public class Position{

	private int i;
	private int j;

	public Position(int x, int y){
		this.i = x;
		this.j = y;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public void setX(int pos){
		this.i=pos;
	}

	public void setY(int pos){
		this.j=pos;
	}

}