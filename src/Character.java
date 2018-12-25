import java.util.ArrayList;
import java.util.List;

public class Character extends GameObject implements Movable{
	
	public static List<Character> characters=new ArrayList<Character>();
	private boolean isPlayer = true;//����һ��ǻ�����
	private String name = "";//���������,������˵�����
	private Dir dir=Dir.stop; //�ƶ�����
	private	int speed=5;//�ƶ��ٶ�
	private int health=3;//����ֵ
	private int bombNum=1;//����ը������
	private int bombPower=1;//ը������

	{
		setIsCollider(false);//���ﲻΪ��ײ��
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
		allObjects[getXInMatrix()][getYInMatrix()]=null;//���ﲻ���ھ����У���Ϊ������Ժ�ը�����л��غϣ����ھ����в��С���������ٸ��ӵ�����������������⣩
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
		Dir preDir=dir;//����֮ǰ�����ķ�����Ϊ������ǽ���Ե����ʱ��Ҫ�任����
		
		double x=getX();
		double y=getY();
		int size=getSize();
		

		if((x<=0&&dir==Dir.left)||(x>=(mapSize-1)*size&&dir==Dir.right)||(y<=0&&dir==Dir.up)||(y>=(mapSize-1)*size&&dir==Dir.down)) {
			return;
		}
		
		
		//����ж���ײ��
		for(GameObject o:colliders) {
			if(!o.getIsCollider())//���������ײ�壬ֱ�Ӵ���
				continue;
			
			double colliderX=o.getX();
			double colliderY=o.getY();
			
			int xm=getXInMatrix();
			int ym=getYInMatrix();
			
			//�ַ����������ƶ�ʱ����ײ��������ﴦ��ǽ���Ե������ʹ���ﴹֱ����
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
		
		//���ݷ����ƶ�
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
	
	public void setBomb() {//����ը��
		if(bombNum>0&&allObjects[getXInMatrix()][getYInMatrix()]==null) {
			new Bomb(getXInMatrix(),getYInMatrix(),bombPower,this,"/bomb.png");
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
