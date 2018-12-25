import java.util.ArrayList;
import java.util.List;

public class Character extends GameObject implements Movable{
	
	public static List<Character> characters=new ArrayList<Character>();
	
	private Dir dir=Dir.stop; 
	boolean isPlayer = true;
	private	int speed=5;
	private int health=3;
	private int bombNum=1;
	private int bombPower=1;
	
	{
		setIsCollider(false);
		setType(Type.CHARACTER);
		allObjects[getXInMatrix()][getYInMatrix()]=null;
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
		Dir preDir=dir;
		
		double x=getX();
		double y=getY();
		int size=getSize();
		
		if((x<=0&&dir==Dir.left)||(x>=(mapSize-1)*size&&dir==Dir.right)||(y<=0&&dir==Dir.up)||((y>=(mapSize-1) * size) &&dir==Dir.down)) {
			return;
		}
		
		for(GameObject o:colliders) {
			if(!o.getIsCollider())
				continue;
			
			double colliderX=o.getX();
			double colliderY=o.getY();
			
			/*if(colliderX-x+getSize()==0&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.left||
				colliderX-x-getSize()==0&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.right||
				colliderY-getY()+getSize()==0&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.up||
				colliderY-getY()-getSize()==0&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.down)
					return;*/
			
			int xm=getXInMatrix();
			int ym=getYInMatrix();
			
			if((colliderX-x+getSize()==0&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.left)) {
				if(xm!=0&&allObjects[xm-1][ym]==null) {
					dir=y>ym*size?Dir.up:Dir.down;
				}
				else
					return;
			}
			
			if(colliderX-x-getSize()==0&&y- colliderY<getSize()&& colliderY-y<getSize()&&dir==Dir.right) {
				if(xm!=mapSize-1&&allObjects[xm+1][ym]==null) {
					dir=y>ym*size?Dir.up:Dir.down;
				}
				else
					return;
			}
			
			if(colliderY-getY()+getSize()==0&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.up) {
				if(ym!=0&&allObjects[xm][ym-1]==null) {
					dir=x>xm*size?Dir.left:Dir.right;
				}
				else
					return;
			}
			
			if(colliderY-getY()-getSize()==0&&x-colliderX<getSize()&&colliderX-x<getSize()&&dir==Dir.down) {
				if(ym!=mapSize-1&&allObjects[xm][ym+1]==null) {
					dir=x>xm*size?Dir.left:Dir.right;
				}
				else
					return;
			}
		}
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
	
	public void setBomb() {
		if(bombNum>0&&allObjects[getXInMatrix()][getYInMatrix()]==null) {
			new Bomb(getXInMatrix(),getYInMatrix(),bombPower,this);
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
