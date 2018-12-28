import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.layout.Pane;

public class Character extends GameObject implements Movable{
	
	public static List<Character> characters=new CopyOnWriteArrayList<Character>();
	
	public static void clear() {
		for(Character c:characters) {
			c.destroy();
		}
	}
	
	private boolean isPlayer = true;//是玩家还是机器人
	private String name = "";//人物的名字,或机器人的名字
	private Dir dir=Dir.stop; //移动方向
	private	int speed=5;//移动速度
	private int health=3;//生命值
	private int maxBombNum=1;
	private int bombNum=1;//所持炸弹数量
	private int bombPower=1;//炸弹威力

	{
		setIsCollider(false);//人物不为碰撞体
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Character(String imagePath,boolean _isPlayer, String name){
		super(imagePath);
		setPlayer(_isPlayer);
		this.name = name;
		setIsCollider(false);

		setType(Type.CHARACTER);
		allObjects[getXInMatrix()][getYInMatrix()]=null;//人物不处于矩阵中（因为人物可以和炸弹还有火花重合，放在矩阵中不行。。。如果再复杂点这样处理绝对有问题）
		characters.add(this);
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public Dir getDir() {return dir;}
	
	public void setDir(Dir _dir) {dir=_dir;}
	
	public void setMaxBombNum(int _maxBombNum) {maxBombNum=_maxBombNum;}
	
	public int getMaxBombNum() {return maxBombNum;}
	
	public void setBombNum(int _bombNum) {bombNum=_bombNum;}
	
	public int getBombNum() {return bombNum;}
	
	public void setBombPower(int _bombPower) {bombPower=_bombPower;}
	
	public int getBombPower() {return bombPower;}
	
	public void setSpeed(int _speed) {if(_speed<=10) speed=_speed;}
	
	public int getSpeed() {return speed;}
	
	public void setHealth(int _health) {health=_health;}
	
	public int getHealth() {return health;}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		List<GameObject> colliders=getColliders();
		Dir preDir=dir;//人物之前所处的方向（因为人物在墙体边缘滑动时需要变换方向）
		
		double x=getX();
		double y=getY();
		int size=getSize();
		

		if((x<=0&&dir==Dir.left)||(x>=(mapSize-1)*size&&dir==Dir.right)||(y<=0&&dir==Dir.up)||(y>=(mapSize-1)*size&&dir==Dir.down)) {
			return;
		}
		
		
		//逐个判断碰撞物
		for(GameObject o:colliders) {
			if(!o.getIsCollider())//如果不是碰撞体，直接穿过
				continue;
			
			double colliderX=o.getX();
			double colliderY=o.getY();
			
			int xm=getXInMatrix();
			int ym=getYInMatrix();
			
			//分方向处理人物移动时的碰撞，如果人物处于墙体边缘，可以使人物垂直滑动
			if((colliderX-x+getSize()<=getSize()/2&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.left)) {
				if(xm!=0&&(allObjects[xm-1][ym]==null||allObjects[xm-1][ym].getType()==Type.EATABLE)) {
					dir=y>ym*size?Dir.up:Dir.down;
				}
				else
					return;
			}
			
			if(x+getSize()-colliderX<=getSize()/2&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.right) {
				if(xm!=mapSize-1&&(allObjects[xm+1][ym]==null||allObjects[xm+1][ym].getType()==Type.EATABLE)) {
					dir=y>ym*size?Dir.up:Dir.down;
				}
				else
					return;
			}
			
			if(colliderY-getY()+getSize()<=getSize()/2&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.up) {
				if(ym!=0&&(allObjects[xm][ym-1]==null||allObjects[xm][ym-1].getType()==Type.EATABLE)) {
					dir=x>xm*size?Dir.left:Dir.right;
				}
				else
					return;
			}
			
			if(getY()+getSize()-colliderY<=getSize()/2&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.down) {
				if(ym!=mapSize-1&&(allObjects[xm][ym+1]==null||allObjects[xm][ym+1].getType()==Type.EATABLE)) {
					dir=x>xm*size?Dir.left:Dir.right;
				}
				else
					return;
			}
		}
		
		//根据方向移动
		switch(dir) {
			case down:
				setY(getY()+0.5);
				break;
			case up:
				setY(getY()-0.5);
				break;
			case left:
				setX(getX()-0.5);
				break;
			case right:
				setX(getX()+0.5);
				break;
			default:
				break;
			}
		
		dir=preDir;
	}
	
	public void setBomb() {//放置炸弹
		if(bombNum>0&&allObjects[getXInMatrix()][getYInMatrix()]==null) {
			new Bomb(getXInMatrix(),getYInMatrix(),bombPower,this,"/Bomb.gif");
			bombNum-=1;
			pane.getChildren().remove(this.getImageView());
			pane.getChildren().add(this.getImageView());
		}
	}

	@Override
	public void act() {
		
		for(int i=0;i<speed;i++) {
			move();
		}
	}

	@Override
	public void destroy() {
		 characters.remove(this);
		 objectsList.remove(this);
		 GameObject.getPane().getChildren().remove(getCollisionBody());
		 GameObject.getPane().getChildren().remove(getImageView());
	}
	//判断游戏是否结束
	//游戏结束,返回1并产生画面
	public static boolean judgeGameOver() {
		if (characters.isEmpty()) {
			Pane tmpPane = Character.getPane();
			GameOver tmp = new GameOver("没有人","gameOver.jpg",tmpPane);
			Game.status = 0;
			return true;
		}
		else if (characters.size()==1) {
			Character tmpCharacter = characters.get(0);
			Pane tmpPane = Character.getPane();
			GameOver tmp = new GameOver(tmpCharacter.getName(),"gameOver.jpg",tmpPane);
			Game.status = 0;
			return true;
		}
		else {
			return false;
		}
	}
}
