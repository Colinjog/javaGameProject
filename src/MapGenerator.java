import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.layout.Pane;

public class MapGenerator {
	
	private static MapGenerator mapGenerator=new MapGenerator();
	
	private MapGenerator() {}
	
	public static MapGenerator getMapGenerator() {
		return mapGenerator;
	}
	
	public void generateMap(Pane pane) {
		GameObject.clear();
		Character.clear();
		GameObject.setPane(pane);
		
		Random rand=new Random();
		
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				if(i==0||i==19||i==9||i==10||j==0||j==19||j==9||j==10)
					continue;
				else {
					int tmp=rand.nextInt(100);
					if(tmp<34)
						new Brick(i,j,true,"/DestroyableBrick.png");
					else if(tmp>=34&&tmp<=67)
						new Brick(i,j,false,"/brick.png");
				}
			}
		}
		for(int i=5;i<15;i++) {
			new Brick(i,0,true,"/DestroyableBrick.png");
			new Brick(i,19,true,"/DestroyableBrick.png");
			new Brick(0,i,true,"/DestroyableBrick.png");
			new Brick(19,i,true,"/DestroyableBrick.png");
		}
	}
	
	public void initMap(String fileName, Pane pane) {
		GameObject.clear();
		Character.clear();
		GameObject.setPane(pane);
		
		File file=new File(fileName);
		try {
			Scanner input=new Scanner(file);
			
			int mapSize=input.nextInt();
			GameObject.setMapSize(mapSize);
			
			for(int i=0;i<mapSize;i++) {
				for(int j=0;j<mapSize;j++) {
					int type=input.nextInt();
					if(type==0)
						continue;
					else if(type==1)
						new Brick(j,i,true,"/DestroyableBrick.png");
					else
						new Brick(j,i,false,"/brick.png");
				}
			}
			
			input.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
