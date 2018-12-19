
public class FireWork extends GameObject{
	
	public int existTime=500;
	
	public FireWork(int _xInMatrix,int _yInMatrix) {
		super(_xInMatrix*getSize(),_yInMatrix*getSize());
		setIsCollider(false);
		setType(Type.FIREWORK);
	}
	
	@Override
	public void act() {
		existTime-=getDeltaTime();
		List<GameObject> colliders
	}
}
