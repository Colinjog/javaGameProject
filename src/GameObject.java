import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {
	enum Type{CHARACTER,BRICK,BOMB,FIREWORK}//��������ͱ�ǩ
	
	private static final int size=50;//ÿ�������Ĭ�ϴ�С
	private static Pane pane;//���ƴ���
	
	public static Pane getPane() {return pane;}
	
	public static void setPane(Pane _pane) {pane=_pane;}//������Ҫ���ڻ�������Ĵ���
	
	public static int getSize() {return size;}
	
	private Rectangle collisionBody=new Rectangle(0,0,size,size);
	private Type type;//��������
	private boolean isCollider=true;
	private long timer=new Date().getTime();//����ִ��ʱ���ʱ��
	
	public static int mapSize=20;
	public static GameObject[][] allObjects=new GameObject[mapSize][mapSize];
	
	public static void setMapSize(int _mapSize) {
		mapSize=_mapSize;
		allObjects=new GameObject[mapSize][mapSize];
	}
	
	public GameObject() {
		allObjects[getXInMatrix()][getYInMatrix()]=this;
		pane.getChildren().add(collisionBody);
	}
	
	public GameObject(double _x,double _y) {
		collisionBody.setX(_x);
		collisionBody.setY(_y);
		allObjects[getXInMatrix()][getYInMatrix()]=this;
		pane.getChildren().add(collisionBody);
	}
	
	public void setIsCollider(boolean _isCollider) {isCollider=_isCollider;}
	
	public boolean getIsCollider() {return isCollider;}
	
	public void setType(Type _type) {type=_type;}
	
	public Type getType() {return type;}
	
	public double getX() {return collisionBody.getX();}
	
	public void setX(double _x) {collisionBody.setX(_x);;}
	
	public double getY() {return collisionBody.getY();}
	
	public void setY(double _y) {collisionBody.setY(_y);;}
	
	public int getXInMatrix() {return ((int)collisionBody.getX()+size/2)/size;}
	
	public int getYInMatrix() {return ((int)collisionBody.getY()+size/2)/size;}
	
	public Rectangle getCollisionBody() {return collisionBody;}
	
	public boolean intersect(GameObject other) {
		return collisionBody.intersects(other.collisionBody.getBoundsInLocal());
	}
	
	public List<GameObject> getColliders(){
		List<GameObject> colliders=new ArrayList<GameObject>();
		for(int i=0;i<mapSize;i++) {
			for(int j=0;j<mapSize;j++) {
				if(allObjects[i][j]!=null)
					if(this.intersect(allObjects[i][j]))
						colliders.add(allObjects[i][j]);
			}
		}
		return colliders;
	}
	
	//������һ��ִ�иú��������ʱ�䣨�����һ��ִ�У��򷵻ؾ�ö��󱻴���ʱ�����ʱ�䣩
	public int getDeltaTime() {
		long now=new Date().getTime();
		int delta=(int) (now-timer);
		timer=now;
		return delta;
	}
	
	public abstract void act();//����ÿһ֡��Ҫִ�еĶ���
	
	public abstract void destroy();
}
