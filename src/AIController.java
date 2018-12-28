import java.util.Random;
import java.util.Date;

class Node{
	public int x,y,last;
	Node(int _x,int _y,int _last){
		x=_x;
		y=_y;
		last=_last;
	}
}

public class AIController{

	private final int bombScore = -100000,
					fireworkScore = -50000,
					brickScore = 103,
					eatScore = 2000,
					killScore = 203;
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
		for (int k=1;k<=step;++k){
			if (x+k < mapSize){
				map[x+k][y] += delta;
			}
			if (x-k >= 0){
				map[x-k][y] += delta;
			}
			if (y+k < mapSize){
				map[x][y+k] += delta;
			}
			if (y-k >= 0){
				map[x][y-k] += delta;
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
			}else if (blockType == GameObject.Type.EATABLE){
				valueMap[x][y] += eatScore;
			}
		}
	}

	private void getDirMap(int index){ //using bfs
		dirMap = new Movable.Dir[mapSize][mapSize];
		int bodyx = bodies[index].getXInMatrix(), bodyy = bodies[index].getYInMatrix();
		int head=1, tail=2, k=1, listLen=(mapSize+1)*(mapSize+1), targetX = bodyx, targetY = bodyy;
		int[] dx={1,0,-1,0}, dy={0,1,0,-1};
		int[][] tempMap = new int[mapSize][mapSize];
		Node[] list = new Node[(mapSize+1)*(mapSize+1)];
		boolean[][] used = new boolean[mapSize][mapSize];

		//initialize the tempMap
		for (int i=0;i<GameObject.objectsList.size();++i){
			GameObject temp = GameObject.objectsList.get(i);
			int x = temp.getXInMatrix(), y = temp.getYInMatrix();
			GameObject.Type tempType = temp.getType();

			if (tempType == GameObject.Type.CHARACTER && (x != bodyx || y != bodyy)){
				tempMap[x][y] += killScore;
				updateInEveryDir(tempMap, x, y, bodies[index].getBombPower(), killScore);
			}else if (tempType == GameObject.Type.BRICK && ((Brick)temp).getIsDestroyable()){
				updateInEveryDir(tempMap, x, y, bodies[index].getBombPower(), brickScore);
			}
		}
		for (int i=0;i<mapSize;++i){
			for (int j=0;j<mapSize;++j){
				int steps = Math.abs(bodyx-i)+Math.abs(bodyy-j);
				tempMap[i][j] += valueMap[i][j] + (int)(stepScore * steps);
			}
		}

		list[head] = new Node(bodyx, bodyy, 0);
		while (head < tail){
			Node temp = list[head];
			if (tempMap[targetX][targetY] < tempMap[temp.x][temp.y]){
				targetX = temp.x;
				targetY = temp.y;
				k=head;
			}
			for (int i=0;i<4;++i){
				int nowx = temp.x+dx[i], nowy = temp.y+dy[i];
				if (nowx >= 0 && nowy >= 0 && nowx < mapSize && nowy < mapSize){
					if (GameObject.allObjects[nowx][nowy] != null){
						GameObject.Type tempType= GameObject.allObjects[nowx][nowy].getType();
						if (tempType == GameObject.Type.BRICK){ //skip the blocks
							continue;
						}else if (tempType == GameObject.Type.BOMB){
							if (nowx != bodyx || nowy != bodyy){
								continue;
							}
						}
					}
					if (!used[nowx][nowy]){
						used[nowx][nowy] = true;
						list[tail++] = new Node(nowx, nowy, head);
					}
				}
			}
			++head;
		}

		/********test code begin*******
		System.out.println("now in " + bodies[index].getXInMatrix() + " " + bodies[index].getYInMatrix());
		System.out.println("going to " + list[k].x + " " + list[k].y);
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
		dirResult = tempMap[bodyx][bodyy];
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
