import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {
	enum Type{CHARACTER,BRICK,BOMB,FIREWORK}//物体的类型标签
	
	private static final int size=50;//每个物体的默认大小
	private static Pane pane;//绘制窗口
	
	public static Pane getPane() {return pane;}
	
	public static void setPane(Pane _pane) {pane=_pane;}//设置需要用于绘制物体的窗口
	
	public static int getSize() {return size;}
	
	private Rectangle collisionBody=new Rectangle(0,0,size,size);
	private Type type;//物体种类
	private boolean isCollider=true;
	private long timer=new Date().getTime();//代码执行时间计时器
	
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
	
	//返回上一次执行该函数间隔的时间（如果第一次执行，则返回距该对象被创建时间隔的时间）
	public int getDeltaTime() {
		long now=new Date().getTime();
		int delta=(int) (now-timer);
		timer=now;
		return delta;
	}
	
	public abstract void act();//物体每一帧将要执行的动作
	
	public abstract void destroy();
}
