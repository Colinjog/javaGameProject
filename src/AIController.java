import java.util.Random;

public class AIController{

	private static int minValue = -10000;
	private static int numOfBodies = 0;
	private static int mapSize;

	private static Character[] bodies;
	private static Movable.Dir[][] dirMap;
	private static int[][] valueMap;

	AIController(){
		bodies = new Character[8];
		mapSize = GameObject.mapSize;
		valueMap = new int[mapSize][mapSize];
		dirMap = new Movable.Dir[mapSize][mapSize];
	}

	AIController(Character character){
		bodies = new Character[8];
		addBody(character);
		mapSize = GameObject.mapSize;
		valueMap = new int[mapSize][mapSize];
		dirMap = new Movable.Dir[mapSize][mapSize];
	}

	public void addBody(Character character){
		bodies[numOfBodies++] = character;
	}

	private void deleteBody(int index){
		bodies[index] = bodies[numOfBodies-1];
		bodies[numOfBodies] = null;
		--numOfBodies;
	}

	private void getValueMap(int now){
		int[][] tempValueMap = new int[mapSize][mapSize];
		for (int i=0;i<mapSize;++i){
			for (int j=0;j<mapSize;++j){
				if (GameObject.allObjects[i][j].getType() == GameObject.Type.BOMB){
					;
				}else if (GameObject.allObjects[i][j].getType() == GameObject.Type.BRICK){
					;
				}else if (GameObject.allObjects[i][j].getType() == GameObject.Type.EATABLE){
					;
				}
			}
		}
		valueMap = tempValueMap;
	}

	private void getDirMap(int index){
		Movable.Dir[][] tempDirMap = new Movable.Dir[mapSize][mapSize];
	}

	private void makeAction(int index){
		int x = bodies[index].getXInMatrix(), y = bodies[index].getYInMatrix();
		if (valueMap[x][y]%100==1){ //set bomb
			bodies[index].setBomb();
		}else{
			bodies[index].setDir(dirMap[x][y]);
			bodies[index].act();
		}
	}

	public void act(){ //make decisions here
		for (int i=0;i<numOfBodies;++i){
			if (bodies[i].getHealth() > 0){ //still alive
				/*getValueMap(i);
				getDirMap(i);
				makeAction(i);*/
			}else{
				deleteBody(i);
				--i;
			}
		}
	}
}
