import java.util.Random;

public class AIController{

	private final int bombScore = -10000,
					fireworkScore = -10000,
					brickScore = 500,
					eatScore = 5000;


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
		bodies[index] = null;
		bodies[index] = bodies[--numOfBodies];
		bodies[numOfBodies] = null;
	}

	private void getValueMap(int now){
		int[][] tempValueMap = new int[mapSize][mapSize];
		for (int i=0;i<mapSize;++i){
			for (int j=0;j<mapSize;++j){
				if (GameObject.allObjects[i][j] == null){ //empty block
					continue;
				}
				GameObject.Type blockType = GameObject.allObjects[i][j].getType();
				if (blockType == GameObject.Type.BOMB){
					tempValueMap[i][j] += bombScore;
					int bombPower = ((Bomb)(GameObject.allObjects[i][j])).getPower();
					;
				}else if (blockType == GameObject.Type.FIREWORK){
					tempValueMap[i][j] += fireworkScore;
				}else if (blockType == GameObject.Type.BRICK){
					;
				}else if (blockType == GameObject.Type.EATABLE){
					tempValueMap[i][j] += eatScore;
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
		if (valueMap[x][y]%100 != 0){ //set bomb
			bodies[index].setBomb();
		}else{
			bodies[index].setDir(dirMap[x][y]);
			bodies[index].act();
		}
	}

	public void act(){ //make decisions here
		for (int i=0;i<numOfBodies;++i){
			if (bodies[i] != null && bodies[i].getHealth() > 0){ //still alive
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
