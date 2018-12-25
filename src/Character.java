import java.util.ArrayList;
import java.util.List;

public class Character extends GameObject implements Movable{
	
	public static List<Character> characters=new ArrayList<Character>();//储存所有人物的列表
	

	private boolean isPlayer = true;
	private Dir dir=Dir.stop; //移动方向
	private	int speed=5;//移动速度
	private int health=3;//生命值
	private int bombNum=1;//所持炸弹数量
	private int bombPower=1;//炸弹威力

	
	{
		setIsCollider(false);//人物不为碰撞体
	}
	private String _imagePath;
	
	public Character(String imagePath){
		super(imagePath);
		_imagePath = imagePath;
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
	
	public void setBombNum(int _bombNum) {bombNum=_bombNum;}
	
	public int getBombNum() {return bombNum;}
	
	public void setBombPower(int _bombPower) {bombPower=_bombPower;}
	
	public int getBombPower() {return bombPower;}
	
	public void setSpeed(int _speed) {speed=_speed;}
	
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
		

		//判断边界
		if((x<=0&&dir==Dir.left)||(x>=mapSize*size-size&&dir==Dir.right)||(y<=0&&dir==Dir.up)||(y>=mapSize*size-size&&dir==Dir.down)) {

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
			if((colliderX-x+getSize()==0&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.left)) {
				if(xm!=0&&(allObjects[xm-1][ym]==null||allObjects[xm-1][ym].getType()==Type.EATABLE)) {
					dir=y>ym*size?Dir.up:Dir.down;
				}
				else
					return;
			}
			
			if(colliderX-x-getSize()==0&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.right) {
				if(xm!=mapSize-1&&(allObjects[xm+1][ym]==null||allObjects[xm+1][ym].getType()==Type.EATABLE)) {
					dir=y>ym*size?Dir.up:Dir.down;
				}
				else
					return;
			}
			
			if(colliderY-getY()+getSize()==0&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.up) {
				if(ym!=0&&allObjects[xm][ym-1]==null||(allObjects[xm][ym-1].getType()==Type.EATABLE)) {
					dir=x>xm*size?Dir.left:Dir.right;
				}
				else
					return;
			}
			
			if(colliderY-getY()-getSize()==0&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.down) {
				if(ym!=mapSize-1&&allObjects[xm][ym+1]==null||(allObjects[xm][ym+1].getType()==Type.EATABLE)) {
					dir=x>xm*size?Dir.left:Dir.right;
				}
				else
					return;
			}
		}
		
		//根据方向移动
		switch(dir) {
			case down:
				setY(getY()+1);
				break;
			case up:
				setY(getY()-1);
				break;
			case left:
				setX(getX()-1);
				break;
			case right:
				setX(getX()+1);
				break;
			default:
				break;
			}
		
		dir=preDir;
	}
	
	public void setBomb() {//放置炸弹
		if(bombNum>0&&allObjects[getXInMatrix()][getYInMatrix()]==null) {
			new Bomb(getXInMatrix(),getYInMatrix(),bombPower,this,_imagePath);
			bombNum-=1;
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
		 
	}

}
