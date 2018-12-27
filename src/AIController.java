import java.util.Random;

public class AIController{

	private final int bombScore = -10000,
					fireworkScore = -10000,
					brickScore = 1000,
					eatScore = 2000;


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

	private void updateInEveryDir(int[][] map,int x,int y,int step,int delta){
		int[] dx = {0,0,1,-1}, dy = {1,-1,0,0};
		for (int k=1;k<=step;++k){
			for (int i=0;i<4;++i){
				int nowx = x+dx[i]*k, nowy = y+dx[i]*k;
				if (nowx < mapSize && nowx >= 0 && nowy < mapSize && nowy >= 0){
					map[nowx][nowy] += delta;
				}
			}
		}
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
					updateInEveryDir(tempValueMap, i, j, bombPower, bombScore/2);
				}else if (blockType == GameObject.Type.FIREWORK){
					tempValueMap[i][j] += fireworkScore;
				}else if (blockType == GameObject.Type.BRICK){
					tempValueMap[i][j] += brickScore;
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
			if (dirMap[x][y] == null){
				bodies[index].setDir(Movable.Dir.stop);
			}else{
				bodies[index].setDir(dirMap[x][y]);
			}
			bodies[index].act();
		}
	}

	public void act(){ //make decisions here
		for (int i=0;i<numOfBodies;++i){
			if (bodies[i] != null && bodies[i].getHealth() > 0){ //still alive
				getValueMap(i);
				getDirMap(i);
				makeAction(i);
			}else{
				deleteBody(i);
				--i;
			}
		}
	}
}
