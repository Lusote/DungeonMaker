import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import java.util.Properties;
import java.util.Random;

public class DungeonPrinter{

    static Random randomGenerator = new Random();

	public DungeonPrinter(){
		
	} 

	public static int printRandomRoom(Dungeon maz){
		int randomIndexULX = randomGenerator.nextInt(78)+1;
		int randomIndexULY = randomGenerator.nextInt(23)+1;
		int randomIndexBRX = randomIndexULX + randomGenerator.nextInt(6)+1;		
		int randomIndexBRY = randomIndexULY  + randomGenerator.nextInt(6)+1;
		Position uL = new Position(randomIndexULX,randomIndexULY);
		Position bR = new Position(randomIndexBRX, randomIndexBRY);
		Position bL = new Position(randomIndexULX, randomIndexBRY);
		Position uR = new Position(randomIndexBRX, randomIndexULY);

			Room r = maz.getCurrentLevel().createRoom(
						maz.getCurrentLevel().getTile(uL),
						maz.getCurrentLevel().getTile(uR),
						maz.getCurrentLevel().getTile(bL),
						maz.getCurrentLevel().getTile(bR)
					);
		return 1;
	}

	public static void printDungeon(Dungeon dun, int lev, ConsoleSystemInterface csi){
		int dunHeight = dun.getLevel(lev).getGridHeight();
		int dunWidth  = dun.getLevel(lev).getGridWidth();
		Level levelToPrint = dun.getLevel(lev);
		char charToPrint;
		Tile tileToPrint;
		Position p;
		 for(int i=0;i<dunWidth;i++){
		 	for(int j=0;j<dunHeight;j++){
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