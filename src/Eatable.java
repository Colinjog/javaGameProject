import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Eatable extends GameObject{
	
	enum Function{HEALTH,BOMB,POWER,SPEED}
	
	private Function func;
	
	public Eatable(Function _func) {
		func=_func;
	}
	
	public Eatable(int xInMatrix,int yInMatrix) {
		super(xInMatrix*getSize(),yInMatrix*getSize());
		setType(GameObject.Type.EATABLE);
		
		this.setIsCollider(false);
		
		getCollisionBody().setX(getX()+12.5);
		getCollisionBody().setY(getY()+12.5);
		getCollisionBody().setWidth(25);
		getCollisionBody().setHeight(25);
		
		Random rand=new Random();
		switch(rand.nextInt(4)) {
		case 0:
			func=Function.HEALTH;
			break;
		case 1:
			func=Function.BOMB;
			break;
		case 2:
			func=Function.POWER;
			break;
		case 3:
			func=Function.SPEED;
			break;
		}
	}
	
	public Function getFunc() {return func;}
	
	@Override
	public void act() {
		// TODO Auto-generated method stub
		for(Character c:Character.characters) {
			if(intersect(c)) {
				
				System.out.println(getFunc());
				
				switch(func) {
				case HEALTH:
					c.setHealth(c.getHealth()+1);
					break;
				case BOMB:
					c.setBombNum(c.getBombNum()+1);
					break;
				case POWER:
					c.setBombPower(c.getBombPower()+1);
				case SPEED:
					c.setSpeed(c.getSpeed()+1);
					break;
				}
				this.destroy();
				break;
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		GameObject.getPane().getChildren().remove(getCollisionBody());
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
	}

}
