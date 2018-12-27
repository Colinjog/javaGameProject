import java.util.Random;

public class Brick extends GameObject{
	

	private boolean isDestroyable;//砖块是否可摧毁

	
	public Brick(int xInMatrix, int yInMatrix,boolean _isDestroyable,String imagePath) {
		super(xInMatrix*getSize(),yInMatrix*getSize(),imagePath);
		setIsCollider(true);
		setType(Type.BRICK);
		isDestroyable=_isDestroyable;
	}
	
	public boolean getIsDestroyable() {return isDestroyable;}
	
	@Override
	public void act() {
		// TODO Auto-generated method stub
		if(allObjects[getXInMatrix()][getYInMatrix()]==null||allObjects[getXInMatrix()][getYInMatrix()].getType()!=Type.BRICK)
			destroy();
	}

	@Override
	public void destroy() { 
		GameObject.getPane().getChildren().remove(getCollisionBody());
		GameObject.getPane().getChildren().remove(getImageView());
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
		
		//砖块被摧毁时，在该位置有三分之一的概率生成随机道具
		Random rand=new Random();

		//int isSet=rand.nextInt()%4;
		//if(isSet==0)
			new Eatable(getXInMatrix(),getYInMatrix(),"eatable.png");
		
		objectsList.remove(this);
	}
}
