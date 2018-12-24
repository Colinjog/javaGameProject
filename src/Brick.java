import java.util.Random;

public class Brick extends GameObject{
	
	private boolean isDestroyable;
	
	public Brick(int xInMatrix, int yInMatrix,boolean _isDestroyable) {
		super(xInMatrix*getSize(),yInMatrix*getSize());
		setIsCollider(true);
		setType(Type.BRICK);
		isDestroyable=_isDestroyable;
	}
	
	public boolean getIsDestroyable() {return isDestroyable;}
	
	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() { 
		GameObject.getPane().getChildren().remove(getCollisionBody());
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
		Random rand=new Random();
		//int isSet=rand.nextInt()%4;
		//if(isSet==0)
			new Eatable(getXInMatrix(),getYInMatrix());
	}
}
