import java.util.Random;

class Node{
	public int x,y,last,step,score;
	Node(int _x,int _y,int _last,int _step){
		x=_x;
		y=_y;
		last=_last;
		step=_step;
	}
}

public class AIController{

	private final int bombScore = -10000,
					fireworkScore = -10000,
					brickScore = 103,
					eatScore = 2000,
					killScore = 103;
	private final double stepScore = -0.1;


	private static int numOfBodies = 0, mapSize, tick, dirResult;

	private static Character[] bodies;
	private static Movable.Dir dirMap[][], lastDir;
	private static int[][] valueMap;

	AIController(){
		lastDir = Movable.Dir.stop;
		bodies = new Character[8];
		mapSize = GameObject.mapSize;
	}

	AIController(Character character){
		lastDir = Movable.Dir.stop;
		bodies = new Character[8];
		addBody(character);
		mapSize = GameObject.mapSize;
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
				int nowx = x+dx[i]*k, nowy = y+dy[i]*k;
				if (nowx < mapSize && nowx >= 0 && nowy < mapSize && nowy >= 0){
					map[nowx][nowy] += delta;
				}
			}
		}
	}

	private void getValueMap(){
		valueMap = new int[mapSize][mapSize];
		for (int i=0;i<GameObject.objectsList.size();++i){
			GameObject temp = GameObject.objectsList.get(i);
			int x = temp.getXInMatrix(), y = temp.getYInMatrix();

			GameObject.Type blockType = temp.getType();
			if (blockType == null){
				continue;
			}

			if (blockType == GameObject.Type.BOMB){
				valueMap[x][y] += bombScore;
				int bombPower = ((Bomb)(temp)).getPower();
				updateInEveryDir(valueMap, x, y, bombPower, bombScore);
			}else if (blockType == GameObject.Type.FIREWORK){
				valueMap[x][y] += fireworkScore;
			}else if (blockType == GameObject.Type.BRICK){
				updateInEveryDir(valueMap, x, y, 1, brickScore);
			}else if (blockType == GameObject.Type.EATABLE){
				valueMap[x][y] += eatScore;
			}
		}
	}

	private void getDirMap(int index){ //using bfs
		dirMap = new Movable.Dir[mapSize][mapSize];
		int head=1, tail=2, k=0, listLen=(mapSize+1)*(mapSize+1);
		int[] dx={1,0,-1,0}, dy={0,1,0,-1};
		int[][] tempMap = new int[mapSize][mapSize];
		int[][] deltaMap = new int[mapSize][mapSize];
		Node[] list = new Node[(mapSize+1) * (mapSize+1)];
		boolean[][] used = new boolean[mapSize][mapSize];

		//initialize the tempMap
		for (int i=0;i<mapSize;++i){
			for (int j=0;j<mapSize;++j){
				tempMap[i][j] = valueMap[i][j];
				deltaMap[i][j] = Math.abs(i-bodies[index].getXInMatrix());
				deltaMap[i][j] += Math.abs(j-bodies[index].getYInMatrix());
				deltaMap[i][j]  = (int)(deltaMap[i][j] * stepScore);
			}
		}

		for (int i=0;i<GameObject.objectsList.size();++i){
			GameObject temp = GameObject.objectsList.get(i);
			int x = temp.getXInMatrix(), y = temp.getYInMatrix();

			if (temp.getType() == GameObject.Type.CHARACTER){
				if (x != bodies[index].getXInMatrix() || y != bodies[index].getYInMatrix()){
					deltaMap[x][y] += killScore;
					updateInEveryDir(deltaMap, x, y, bodies[index].getBombPower(), killScore);
				}
			}
		}

		k = head;
		list[head] = new Node(bodies[index].getXInMatrix(), bodies[index].getYInMatrix(), 0, 0);
		while (head != tail){
			Node temp = list[head];
			if (tempMap[list[k].x][list[k].y] < tempMap[temp.x][temp.y]){
				k = head;
			}
			for (int i=0;i<4;++i){
				int nowx = temp.x+dx[i], nowy = temp.y+dy[i];
				if (nowx >= 0 && nowy >= 0 && nowx < mapSize && nowy < mapSize){
					if (GameObject.allObjects[nowx][nowy] != null){
						GameObject.Type tempType= GameObject.allObjects[nowx][nowy].getType();
						if (tempType == GameObject.Type.BRICK){ //skip the blocks
							continue;
						}else if (tempType == GameObject.Type.BOMB){
							if (nowx != bodies[index].getXInMatrix() || nowy != bodies[index].getYInMatrix()){
								continue;
							}
						}
					}
					if (!used[nowx][nowy] || tempMap[nowx][nowy] < valueMap[nowx][nowy] + deltaMap[nowx][nowy]){
						used[nowx][nowy] = true;
						tempMap[nowx][nowy] = valueMap[nowx][nowy] + deltaMap[nowx][nowy];
						list[tail] = new Node(nowx, nowy, head, temp.step+1);
						tail = (tail+1)%listLen;
					}
				}
			}
			head = (head+1)%listLen;
		}

		/********test code begin*******
		System.out.println("now in " + bodies[index].getXInMatrix() + " " + bodies[index].getYInMatrix());
		System.out.println("going to " + list[k].x +" "+ list[k].y);
		for (int i=0;i<mapSize;++i){
			System.out.print(i + "\t");
			for (int j=0;j<mapSize;++j){
				System.out.print(tempMap[j][i] + "\t");
			}
			System.out.println();
		}
		for (int i=0;i<mapSize;++i){
			System.out.print(i + " ");
			for (int j=0;j<mapSize;++j){
				System.out.print(dirMap[j][i] + "\t");
			}
			System.out.println();
		}
		/********test code end*******/

		while (list[k].last != 0){
			int x = list[k].x, y = list[k].y;
			k=list[k].last;
			if (list[k].x != x){
				if (list[k].x > x){
					dirMap[list[k].x][list[k].y] = Movable.Dir.left;
				}else{
					dirMap[list[k].x][list[k].y] = Movable.Dir.right;
				}
			}else{
				if (list[k].y > y){
					dirMap[list[k].x][list[k].y] = Movable.Dir.up;
				}else{
					dirMap[list[k].x][list[k].y] = Movable.Dir.down;
				}
			}
		}
		dirResult = tempMap[bodies[index].getXInMatrix()][bodies[index].getYInMatrix()];
	}

	private void makeAction(int index){
		int x = bodies[index].getXInMatrix(), y = bodies[index].getYInMatrix();
		if (dirResult%10 != 0){ //set bomb
			bodies[index].setBomb();
		}
		if (dirMap[x][y] == null){
			dirMap[x][y] = Movable.Dir.stop;
		}
		if (tick%10 == 0){ //slow down the AI
			bodies[index].act();
			bodies[index].setDir(lastDir = dirMap[x][y]);
		}
	}

	public void act(){ //make decisions here
		++tick;
		getValueMap();
		for (int i=0;i<numOfBodies;++i){
			if (bodies[i] != null && bodies[i].getHealth() > 0){ //still alive
				getDirMap(i);
				makeAction(i);
			}else{
				deleteBody(i);
				--i;
			}
		}
	}
}
