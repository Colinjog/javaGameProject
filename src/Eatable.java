import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Eatable extends GameObject{
	
	enum Function{HEALTH,BOMB,POWER,SPEED}
	
	private Function func;
	
	public Eatable(int xInMatrix,int yInMatrix,String imagePath) {
		super(xInMatrix*getSize(),yInMatrix*getSize(),imagePath);
		setType(GameObject.Type.EATABLE);
		
		this.setIsCollider(false);
		
		//将道具缩小并且置于格子中间（只要后面没有什么道具移动或者碰撞功能什么的这样写应该没有问题）
		getCollisionBody().setX(getX()+12.5);
		getCollisionBody().setY(getY()+12.5);
		getCollisionBody().setWidth(25);
		getCollisionBody().setHeight(25);
		
		//随机生成道具种类
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
		
		if(allObjects[getXInMatrix()][getYInMatrix()]==null||allObjects[getXInMatrix()][getYInMatrix()].getType()!=Type.EATABLE)
			destroy();
		
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
		GameObject.getPane().getChildren().remove(getImageView());
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
		
		objectsList.remove(this);
	}

}
