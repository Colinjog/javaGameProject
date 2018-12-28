import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {
	enum Type{CHARACTER,BRICK,BOMB,FIREWORK,EATABLE}//��������ͱ�ǩ
	
	protected static final int size=50;//ÿ�������Ĭ�ϴ�С
	protected static Pane pane;//���ƴ���
	
	public static Pane getPane() {return pane;}
	
	public static void setPane(Pane _pane) {pane=_pane;}//������Ҫ���ڻ�������Ĵ���
	
	public static int getSize() {return size;}
	
	protected Rectangle collisionBody=new Rectangle(0,0,size,size);//��ײ��Ϊ������
	private Type type;//��������
	private boolean isCollider=true;//�Ƿ�Ϊ��ײ��ı�ǩ
	private long timer=new Date().getTime();//����ִ��ʱ���ʱ��
	protected Image image;//��ͼ
	protected ImageView imageView;
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	private boolean isDestroyed=false;
	
	public static int mapSize=20;//��ͼ����Ĵ�С
	public static GameObject[][] allObjects=new GameObject[mapSize][mapSize];//��ͼ����
	
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
	
	public int getXInMatrix() {return ((int)collisionBody.getX()+size/2)/size;}//��ȡ�������ھ����е�X
	
	public int getYInMatrix() {return ((int)collisionBody.getY()+size/2)/size;}//��ȡ�������ھ����е�Y
	
	public Rectangle getCollisionBody() {return collisionBody;}

	public void setImage(String path){
		imageView = new ImageView(new Image(path, size, size, false, false));
		pane.getChildren().add(imageView);
		imageView.setTranslateX(this.getX() - size/4);
		imageView.setTranslateY(this.getY() - size/4);
	}

	public ImageView getImageView() {return imageView;}
	
	
	//�ж�һ�������Ƿ�����һ�����ཻ
	public boolean intersect(GameObject other) {
		return collisionBody.intersects(other.collisionBody.getBoundsInLocal());
	}
	
	//��ȡ������������ཻ����������
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
	
	//���屻�ݻ�ʱִ�е��߼�
	public abstract void destroy();
}

