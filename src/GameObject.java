import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {
	enum Type{CHARACTER,BRICK,BOMB,FIREWORK,EATABLE}//物体的类型标签
	
	protected static final int size=50;//每个物体的默认大小
	protected static Pane pane;//绘制窗口
	
	public static Pane getPane() {return pane;}
	
	public static void setPane(Pane _pane) {pane=_pane;}//设置需要用于绘制物体的窗口
	
	public static int getSize() {return size;}
	
	protected Rectangle collisionBody=new Rectangle(0,0,size,size);//碰撞体为正方形
	private Type type;//物体种类
	private boolean isCollider=true;//是否为碰撞体的标签
	private long timer=new Date().getTime();//代码执行时间计时器
	protected Image image;//贴图
	protected ImageView imageView;
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	private boolean isDestroyed=false;
	
	public static int mapSize=20;//地图矩阵的大小
	public static GameObject[][] allObjects=new GameObject[mapSize][mapSize];//地图矩阵
	
	public static List<GameObject> objectsList=new CopyOnWriteArrayList<GameObject>();
	
	public static void clear() {
		objectsList.clear();
		for(int i=0;i<mapSize;i++) {
			for(int j=0;j<mapSize;j++) {
				if(allObjects[i][j]!=null)
					allObjects[i][j].destroy();
			}
		}
	}
	
	public static int getMapSize() {return mapSize;}
	
	public static void setMapSize(int _mapSize) {
		mapSize=_mapSize;
		allObjects=new GameObject[mapSize][mapSize];
	}
	
	public GameObject() {
		collisionBody.setVisible(false);
		
		objectsList.add(this);
	}

	public GameObject(String imagePath) {
		collisionBody.setVisible(false);
		imageView = new ImageView(image = new Image(imagePath, size, size, false, false));
		allObjects[getXInMatrix()][getYInMatrix()]=this;
		pane.getChildren().add(collisionBody);
		pane.getChildren().add(imageView);
		objectsList.add(this);
	}
	
	public GameObject(double _x,double _y,String imagePath) {
		collisionBody.setVisible(false);
		if (imagePath != "empty"){
			imageView = new ImageView(image = new Image(imagePath, size, size, false, false));
			pane.getChildren().add(imageView);
			imageView.setTranslateX(_x);
			imageView.setTranslateY(_y);
		}
		collisionBody.setX(_x);
		collisionBody.setY(_y);
		allObjects[getXInMatrix()][getYInMatrix()]=this;
		pane.getChildren().add(collisionBody);
		
		objectsList.add(this);
	}
	
	public boolean isDestroyed() {return isDestroyed;}
	
	public void setDestroyed() {isDestroyed=true;}
	
	public void setIsCollider(boolean _isCollider) {isCollider=_isCollider;}
	
	public boolean getIsCollider() {return isCollider;}
	
	public void setType(Type _type) {type=_type;}
	
	public Type getType() {return type;}
	
	public double getX() {return collisionBody.getX();}
	
	public void setX(double _x) {collisionBody.setX(_x);imageView.setTranslateX(_x);}
	
	public double getY() {return collisionBody.getY();}
	
	public void setY(double _y) {collisionBody.setY(_y);imageView.setTranslateY(_y);}
	
	public int getXInMatrix() {return ((int)collisionBody.getX()+size/2)/size;}//获取该物体在矩阵中的X
	
	public int getYInMatrix() {return ((int)collisionBody.getY()+size/2)/size;}//获取该物体在矩阵中的Y
	
	public Rectangle getCollisionBody() {return collisionBody;}

	public void setImage(String path){
		imageView = new ImageView(new Image(path, size, size, false, false));
		pane.getChildren().add(imageView);
		imageView.setTranslateX(this.getX() - size/4);
		imageView.setTranslateY(this.getY() - size/4);
	}

	public ImageView getImageView() {return imageView;}
	
	
	//判断一个物体是否与另一物体相交
	public boolean intersect(GameObject other) {
		return collisionBody.intersects(other.collisionBody.getBoundsInLocal());
	}
	
	//获取所有与该物体相交的其他物体
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
	
	//物体被摧毁时执行的逻辑
	public abstract void destroy();
}

