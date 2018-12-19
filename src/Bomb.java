
public class Bomb extends GameObject{
	
	private int bombTime;
	private int power;
	
	public Bomb(int _xInMatrix,int _yInMatrix,int _power) {
		super(_xInMatrix*getSize(),_yInMatrix*getSize());
		bombTime=3000;
		power=_power;
		setIsCollider(true);
		setType(Type.BOMB);
	}
	
	public void explode() {
		
	}

	@Override
	public void act() {
		bombTime-=getDeltaTime();
	}
}
