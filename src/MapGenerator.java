import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.scene.layout.Pane;

public class MapGenerator {
	
	private static MapGenerator mapGenerator=new MapGenerator();
	
	private MapGenerator() {}
	
	public static MapGenerator getMapGenerator() {
		return mapGenerator;
	}
	
	public void generateMap() {
		
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
