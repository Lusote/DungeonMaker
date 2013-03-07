import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import java.util.Properties;
import java.util.Random;

public class DungeonPrinter{

	static int randomPosHeight;
	static int randomPosWidth;
	static int randomRoomHeight; 
	static int randomRoomWidth;
    static Random randomGenerator = new Random();

	public DungeonPrinter(){
		
	} 

	public static void printRandomRoom(Dungeon dun){
		randomPosHeight  = randomGenerator.nextInt(78)+1;
		randomPosWidth   = randomGenerator.nextInt(23)+1;
		randomRoomHeight = randomPosHeight + randomGenerator.nextInt(6)+1;
		randomRoomWidth  = randomPosWidth  + randomGenerator.nextInt(6)+1;
		Position pUpLeft = new Position(randomPosHeight,randomPosWidth);
		Position pDownRight = new Position(randomRoomHeight, randomRoomWidth);
		
		dun.getCurrentLevel().createRoom(pUpLeft, pDownRight);
		
		System.out.println("Hueco: "+randomPosHeight+", "+randomPosWidth);
	}

	public static void printDungeon(Dungeon dun, int lev, ConsoleSystemInterface csi){
		int dunHeight = dun.getLevel(lev).getGridHeight();
		int dunWidth  = dun.getLevel(lev).getGridWidth();
		Level levelToPrint = dun.getLevel(lev);
		char charToPrint;
		Tile tileToPrint;
		Position p;
		 for(int i=0;i<dunHeight;i++){
		 	for(int j=0;j<dunWidth;j++){
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
			if(dir.isLeftArrow()){
				printRandomRoom(testDungeon);
			}
			if(dir.code == CharKey.r){
				csi.cls();
				csi.refresh();
				System.out.println("Refreshing.");
			}
		}
		System.exit(0);
	}
 }