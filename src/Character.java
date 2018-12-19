import java.util.List;

public class Character extends GameObject implements Movable{
	
	private Dir dir=Dir.stop; 
	private	int speed=5;
	private int health=3;
	private int maxBombNum=1;
	private int bombNum=maxBombNum;
	private int bombPower=1;
	
	{
		setIsCollider(false);
		setType(Type.CHARACTER);
	}
	
	Dir getDir() {return dir;}
	
	void setDir(Dir _dir) {dir=_dir;}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		List<GameObject> colliders=getColliders();
		Dir preDir=dir;
		
		double x=getX();
		double y=getY();
		int size=getSize();
		
		if((x<=0&&dir==Dir.left)||(x>=mapSize*size&&dir==Dir.right)||(y<=0&&dir==Dir.up)||(y>=mapSize*size&&dir==Dir.down)) {
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
				setY(getY()+speed);
				break;
			case up:
				setY(getY()-speed);
				break;
			case left:
				setX(getX()-speed);
				break;
			case right:
				setX(getX()+speed);
				break;
			default:
				break;
			}
		
		dir=preDir;
	}

	@Override
	public void act() {
		move();
	}

	@Override
	public void destroy() {
		 
	}

}
