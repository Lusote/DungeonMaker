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
/*		for(int i = 0; i<=dunWidth+1;i++){
			for(int j=0;j<=dunHeight+1;j++){
				if(i==0 || j==0){
					csi.print(i,j,i.toString(),CSIColor.WHITE);
				}
			}
		}*/
		for(int i=0;i<=dunWidth+1;i++){
		 	for(int j=0;j<=dunHeight+1;j++){
		 		if(i>=dunWidth+1){
		 			int aux1 = j%10;
		 			int aux2 = i%10;
		 			csi.print(i,j, Character.forDigit(aux1, 10),CSIColor.RED);
		 		}
		 		if(j>=dunHeight+1){
		 			int aux1 = j%10;
		 			int aux2 = i%10;
		 			csi.print(i,j, Character.forDigit(aux2, 10),CSIColor.RED);
		 		}
		 		if(i<=dunWidth && j<=dunHeight){	
			 		p = new Position(i,j);
			 		tileToPrint = levelToPrint.getTile(p);
			 		charToPrint = tileToPrint.getSymbol();
		 			csi.print(p.getX(),p.getY(),charToPrint,CSIColor.WHITE);
		 		}
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
					testDungeon.getLevel(0).setStairsUp();
					testDungeon.getLevel(0).setStairsDown();
			}
			if(dir.isLeftArrow()){
				testDungeon.getLevel(0).createRoom();
			}
			if(dir.isUpArrow()){				
				if(testDungeon.getLevel(0).getNumRooms() >= 6 ){

					for(int i = 0; i < testDungeon.getLevel(0).getNumRooms()-1; i++){
						Position start = testDungeon.getLevel(0).getRooms().get(i).getDoors().get(1);
						testDungeon.getLevel(0).getTile(start).setSymbol('.');
						Position end = 	testDungeon.getLevel(0).getRooms().get(i+1).getDoors().get(0);
						testDungeon.getLevel(0).getTile(end).setSymbol('.');
						ArrayList<Tile> tilesPath = new ArrayList<Tile>();
						HashSet<Position> walls = testDungeon.getLevel(0).getFloorAndWallsPosition();
						walls.remove(end);
						ArrayList<Position> path = testDungeon.getLevel(0).getPath(start, end, 4);
						if(path!= null){
							for(Position p : path){
								Tile t = testDungeon.getLevel(0).getTile(p);
								t.setSymbol('.');
								tilesPath.add(t);
							}							
							csi.cls();		
							csi.refresh();
							printDungeon(testDungeon,0,csi);
						}
					}

					Position start = testDungeon.getLevel(0).getRooms().get(testDungeon.getLevel(0).getNumRooms()-1).getDoors().get(1);
					testDungeon.getLevel(0).getTile(start).setSymbol('.');
					Position end = 	testDungeon.getLevel(0).getRooms().get(0).getDoors().get(0);
					testDungeon.getLevel(0).getTile(end).setSymbol('.');
					ArrayList<Tile> tilesPath = new ArrayList<Tile>();
					HashSet<Position> walls = testDungeon.getLevel(0).getFloorAndWallsPosition();
					walls.remove(end);
					ArrayList<Position> path = testDungeon.getLevel(0).getPath(start, end, 4);
					if(path!= null){
						for(Position p : path){
							Tile t = testDungeon.getLevel(0).getTile(p);
							t.setSymbol('.');
							tilesPath.add(t);
						}
					}
				}
				if(testDungeon.getLevel(0).getNumRooms() <6 ){
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
				while(testDungeon.getLevel(0).getNumRooms()!=6){
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
			if(dir.code == CharKey.c){
				int numRooms = testDungeon.getLevel(0).getNumRooms();
				if(numRooms == 0 ){
					testDungeon.getLevel(0).createRoom();
				}else if(numRooms > 0 && numRooms < 4 ){

					try{
					testDungeon.getLevel(0).createRoom();
					Position start = testDungeon.getLevel(0).getRooms().get(numRooms-1).getDoors().get(1);
					testDungeon.getLevel(0).getTile(start).setSymbol('.');
					Position end = 	testDungeon.getLevel(0).getRooms().get(numRooms).getDoors().get(0);
					testDungeon.getLevel(0).getTile(end).setSymbol('.');
					ArrayList<Tile> tilesPath = new ArrayList<Tile>();
					HashSet<Position> walls = testDungeon.getLevel(0).getFloorAndWallsPosition();
					walls.remove(end);
					ArrayList<Position> path = testDungeon.getLevel(0).getPath(start, end, 4);
					if(path!= null){
						for(Position p : path){
							Tile t = testDungeon.getLevel(0).getTile(p);
							t.setSymbol('.');
							tilesPath.add(t);
						}							
						csi.cls();		
						csi.refresh();
						printDungeon(testDungeon,0,csi);
					}
					}catch(Exception e){
						System.out.println("FATAL ERROR");
						testDungeon = new Dungeon();

					}


				}else if(numRooms == 4 ){

					try{


					Position start = testDungeon.getLevel(0).getRooms().get(numRooms-1).getDoors().get(1);
					testDungeon.getLevel(0).getTile(start).setSymbol('.');
					Position end = 	testDungeon.getLevel(0).getRooms().get(0).getDoors().get(0);
					testDungeon.getLevel(0).getTile(end).setSymbol('.');
					ArrayList<Tile> tilesPath = new ArrayList<Tile>();
					HashSet<Position> walls = testDungeon.getLevel(0).getFloorAndWallsPosition();
					walls.remove(end);
					ArrayList<Position> path = testDungeon.getLevel(0).getPath(start, end, 4);
					if(path!= null){
						for(Position p : path){
							Tile t = testDungeon.getLevel(0).getTile(p);
							t.setSymbol('.');
							tilesPath.add(t);
						}							
						csi.cls();		
						csi.refresh();
						printDungeon(testDungeon,0,csi);
					}
					}catch(Exception e){
						System.out.println("FATAL ERROR");
						testDungeon = new Dungeon();

					}

				}
			}
		}
		System.exit(0);
	}
 }
