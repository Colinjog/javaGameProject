
public class FireWork extends GameObject{
	
	public int existTime=250;
	
	public FireWork(int _xInMatrix,int _yInMatrix) {
		super(_xInMatrix*getSize(),_yInMatrix*getSize());
		setIsCollider(false);
		setType(Type.FIREWORK);
	}
	
	@Override
	public void act() {
		existTime-=getDeltaTime();
		if(existTime<=0)
			destroy();
	}

	@Override
	public void destroy() { 
		GameObject.getPane().getChildren().remove(getCollisionBody());
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
	}
}
