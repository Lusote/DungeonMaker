import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import java.util.*;

public class DungeonPrinter{

	public DungeonPrinter(){
		
	} 

	public static void printDungeon(Dungeon dun, int lev, ConsoleSystemInterface csi){
		int dunHeight = dun.getLevel(lev).getGridHeight();
		int dunWidth  = dun.getLevel(lev).getGridWidth();
		Level levelToPrint = dun.getLevel(lev);
		char charToPrint;
		Tile tileToPrint;
		Position p;
		for(int i=0;i<=dunWidth;i++){
		 	for(int j=0;j<=dunHeight;j++){
		 		p = new Position(i,j);
		 		tileToPrint = levelToPrint.getTile(p);
		 		charToPrint = tileToPrint.getSymbol();
		 		csi.print(p.getX(),p.getY(),charToPrint,CSIColor.WHITE);
			}
 	 	}
	}

	public static void main(String[] args){
		Dungeon testDungeon = new Dungeon();
	
		Properties text = new Properties();
		text.setProperty("fontSize","12");
		text.setProperty("font", "Courier New");
		ConsoleSystemInterface csi = null;		
		try{
			csi = new WSwingConsoleInterface("Dungeon Generator", text);
		}
		catch (ExceptionInInitializerError eiie) {
			System.out.println("*** Error: Swing Console Box cannot be initialized. Exiting...");
			eiie.printStackTrace();
			System.exit(-1);
		}
	
		boolean stop = false;
		while(!stop){
			csi.cls();
			csi.refresh();
			printDungeon(testDungeon,0,csi);

			CharKey dir = csi.inkey();
 			if(dir.code == CharKey.Q){
					stop = true;
			}
			if(dir.code == CharKey.i){
					System.out.println("BEHOLD THE WALLS!!!!");
					HashSet<Position> walls = testDungeon.getLevel(0).getFloorAndWallsPosition();
					for(Position p : walls){
						testDungeon.getLevel(0).getTile(p).setSymbol('@');
					}
			}
			if(dir.isLeftArrow()){
				testDungeon.getLevel(0).createRoom();
			}
			if(dir.isUpArrow()){				
				if(testDungeon.getLevel(0).getNumRooms() >= 2 ){
					Position start = testDungeon.getLevel(0).getRooms().get(0).getDoors().get(1);
					testDungeon.getLevel(0).getTile(start).setSymbol('.');
					Position end = 	testDungeon.getLevel(0).getRooms().get(1).getDoors().get(0);
					testDungeon.getLevel(0).getTile(end).setSymbol('.');
					ArrayList<Tile> tilesPath = new ArrayList<Tile>();
					HashSet<Position> walls = testDungeon.getLevel(0).getFloorAndWallsPosition();
					walls.remove(end);
					ArrayList<Position> path = Astar.getPath(start, end, 4, walls);
					if(path!= null){
						for(Position p : path){
							tilesPath.add(testDungeon.getLevel(0).getTile(p));
						}
						for(Tile t : tilesPath) t.setSymbol('.');
					}
				}
				if(testDungeon.getLevel(0).getNumRooms() == 1 ){
					testDungeon.getLevel(0).createRoom();
				}
				if(testDungeon.getLevel(0).getNumRooms() == 0){
					testDungeon.getLevel(0).createRoom();
				}
			}
			if(dir.isDownArrow()){
				/*for(Room r : testDungeon.getLevel(0).getRooms()){
					ArrayList<Tile> walls1 = new ArrayList<Tile>();
					for(Position p: r.getWalls()){
						walls1.add(testDungeon.getLevel(0).getTile(p));
					}
					/*for(Tile t : walls1){
						t.setSymbol('#');
					}/
					ArrayList<Tile> doorsTiles = new ArrayList<Tile>();
					for(Position p: r.getDoors()){
						doorsTiles.add(testDungeon.getLevel(0).getTile(p));
					}
				}*/
			}
			if(dir.isRightArrow()){
				while(testDungeon.getLevel(0).getNumRooms()!=13){
					testDungeon.getLevel(0).createRoom();
				}
			}
			if(dir.code == CharKey.r){
				csi.cls();
				csi.refresh();
				System.out.println("Refreshing.");
			}
			if(dir.code == CharKey.n){
				testDungeon = new Dungeon();
			}
		}
		System.exit(0);
	}
 }
