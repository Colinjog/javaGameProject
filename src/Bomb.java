import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class Bomb extends GameObject{
	

	private int bombTime;//爆炸事件
	private int power;//炸弹威力
	private Character setter;//放置炸弹的人物

	String _imagePath;//path of firework image
	
	public Bomb(int _xInMatrix,int _yInMatrix,int _power, Character _setter,String imagePath) {
		super(_xInMatrix*getSize(),_yInMatrix*getSize(),imagePath);
		_imagePath = "/firework.png";
		bombTime=2000;
		power=_power;
		setter=_setter;
		setIsCollider(true);
		setType(Type.BOMB);
	}

	public int getPower(){
		return this.power;
	}
	
	public void explode() {
		Brick tmpBrick;
		Bomb tmpBomb;
		boolean isExist = false;//炸弹的位置是否有人
		//判断人跟炸弹同一个位置
		for (Character tmpCharacter: Character.characters) {
			int x = tmpCharacter.getXInMatrix();
			int y = tmpCharacter.getYInMatrix();
			if (x==this.getXInMatrix() && y==this.getYInMatrix()) {
				isExist = true;
				int health = tmpCharacter.getHealth();
				System.out.println(health);
				if (tmpCharacter.isPlayer()) {
					if (health>1) {
						health--;
						tmpCharacter.setHealth(health);
						//玩家被炸弹击中,但玩家还有生命
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						
					}
					else {
						tmpCharacter.destroy();
						tmpCharacter.setHealth(0);
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						Pane tmpPane = Bomb.getPane();
						Character.characters.remove(tmpCharacter);//从characters中删除死亡的character
						tmpCharacter = null;//删除character
					}
				}
				else {
					if (health>1) {
						health--;
						tmpCharacter.setHealth(health);
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						//电脑玩家被炸弹击中,但电脑玩家还有生命
					}
					else {
						tmpCharacter.setHealth(0);
						tmpCharacter.destroy();
						Character.characters.remove(tmpCharacter);//从characters中删除死亡的character
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						tmpCharacter = null;//删除character
					}
				}
			}
		}
		destroy();//先在矩阵以及Pane中删除该对象，否则之后创建的新对象会替换掉矩阵中的该对象，图片就留在Pane上去不掉了。。
		if (!isExist) {
			new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);//在炸弹位置创建火焰
		}
		
		//分别从四个方向判断爆炸逻辑
		//右方
		for(int i=1;i<=power&&getXInMatrix()+i<getMapSize();i++) {//i=0因为需要判断是否炸弹跟character同位置
			GameObject o=allObjects[getXInMatrix()+i][getYInMatrix()];

			if(o==null)//如果该位置没有
			{
				
				boolean temp = false;//判断是否已经产生firework
				//判断是否有人在右方
				for (Character tmpCharacter1:  Character.characters) {
					int x = tmpCharacter1.getXInMatrix();
					int y = tmpCharacter1.getYInMatrix();
					if ((x==(i+this.getXInMatrix()))&&(y==this.getYInMatrix())) {
						temp = true;
						int health = tmpCharacter1.getHealth();
						System.out.println(health);
						if (tmpCharacter1.isPlayer()) {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								//玩家被炸弹击中,但玩家还有生命
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								tmpCharacter1.setHealth(0);
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								Pane tmpPane = Bomb.getPane();
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								tmpCharacter1 = null;//删除character
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								//电脑玩家被炸弹击中,但电脑玩家还有生命
							}
							else {
								tmpCharacter1.setHealth(0);
								tmpCharacter1.destroy();
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								tmpCharacter1 = null;//删除character
							}
						}
					}
				}
				if (!temp) {
					new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
				}
				
		}
			else {
				if(o.getType()==Type.BRICK) {//如果是砖块，则停止判断
					tmpBrick=(Brick)o;
					if(tmpBrick.getIsDestroyable()) {
						tmpBrick.destroy();
					}
					break;
				}
				else if(o.getType()==Type.BOMB) {//如果是炸弹，则引爆
					tmpBomb=(Bomb)o;
					tmpBomb.explode();
				}
				else if(o.getType()==Type.FIREWORK) {//如果该位置上是火焰，先删除火焰（防止图片遗留），再创建新的火焰
					o.destroy();
					new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
				}
				else if(o.getType()==Type.EATABLE) {//如果是道具，摧毁
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
				}
				else {
					
				}
				
			}
		}
		
		for(int i=1;i<=power&&getXInMatrix()-i>=0;i++) {
			GameObject o=allObjects[getXInMatrix()-i][getYInMatrix()];
			if(o==null)
			{
				
				boolean temp = false;//判断是否已经产生firework
				for (Character tmpCharacter1:  Character.characters) {
					int x = tmpCharacter1.getXInMatrix();
					int y = tmpCharacter1.getYInMatrix();
					if ((x==(this.getXInMatrix()-i))&&(y==this.getYInMatrix())) {
						temp = true;
						int health = tmpCharacter1.getHealth();
						System.out.println(health);
						if (tmpCharacter1.isPlayer()) {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								//玩家被炸弹击中,但玩家还有生命
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								Pane tmpPane = Bomb.getPane();
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								tmpCharacter1 = null;//删除character
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								//电脑玩家被炸弹击中,但电脑玩家还有生命
							}
							else {
								tmpCharacter1.setHealth(0);
								tmpCharacter1.destroy();
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								tmpCharacter1 = null;//删除character
							}
						}
					}
				}
				if (!temp) {
					new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
				}
			}
			else if(o.getType()==Type.BRICK) {
					tmpBrick=(Brick)o;
					if(tmpBrick.getIsDestroyable()) {
						tmpBrick.destroy();
					}
					break;

				}
				else if(o.getType()==Type.BOMB) {
					tmpBomb=(Bomb)o;
					tmpBomb.explode();
				}
				else if(o.getType()==Type.FIREWORK) {
					o.destroy();
					new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
				}//判断o是不是character
				else if (o.getType()==Type.CHARACTER) {
					o.destroy();
					//character死亡
					new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);

				}
				else {
					
					}
			}
			
		
		
		for(int i=1;i<=power&&getYInMatrix()+i<getMapSize();i++) {
			GameObject o=allObjects[getXInMatrix()][getYInMatrix()+i];
			if(o==null)
			{
				
				boolean temp = false;//判断是否已经产生firework
				for (Character tmpCharacter1:  Character.characters) {
					int x = tmpCharacter1.getXInMatrix();
					int y = tmpCharacter1.getYInMatrix();
					if ((x==this.getXInMatrix())&&(y==(this.getYInMatrix()+i))) {
						temp = true;
						int health = tmpCharacter1.getHealth();
						System.out.println(health);
						if (tmpCharacter1.isPlayer()) {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								//玩家被炸弹击中,但玩家还有生命
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								tmpCharacter1 = null;//删除character
								
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								//电脑玩家被炸弹击中,但电脑玩家还有生命
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								tmpCharacter1 = null;//删除character
							}
						}
					}
				}
				if (!temp) {
					new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
				}
			}
			else {
				if(o.getType()==Type.BRICK) {
					tmpBrick=(Brick)o;
					if(tmpBrick.getIsDestroyable())
						tmpBrick.destroy();
					break;

				}
				else if(o.getType()==Type.BOMB) {
					tmpBomb=(Bomb)o;
					tmpBomb.explode();
				}
				else if(o.getType()==Type.FIREWORK) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
				}
				else {
					
				
					}
			}
		}
		
		for(int i=1;i<=power&&getYInMatrix()-i>=0;i++) {
			GameObject o=allObjects[getXInMatrix()][getYInMatrix()-i];
			if(o==null)
			{
				
				boolean temp = false;//判断是否已经产生firework
				for (Character tmpCharacter1:  Character.characters) {
					int x = tmpCharacter1.getXInMatrix();
					int y = tmpCharacter1.getYInMatrix();
					if ((x==this.getXInMatrix())&&(y==(this.getYInMatrix()-i))) {
						temp = true;
						int health = tmpCharacter1.getHealth();
						System.out.println(health);
						if (tmpCharacter1.isPlayer()) {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								//玩家被炸弹击中,但玩家还有生命
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								tmpCharacter1 = null;//删除character
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								//电脑玩家被炸弹击中,但电脑玩家还有生命
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//从characters中删除死亡的character
								tmpCharacter1 = null;//删除character
							}
						}
					}
				}
				if (!temp) {
					new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
				}
			}
			else {
				if(o.getType()==Type.BRICK) {
					tmpBrick=(Brick)o;
					if(tmpBrick.getIsDestroyable()) 
						tmpBrick.destroy();
					break;
				}
				else if(o.getType()==Type.BOMB) {
					tmpBomb=(Bomb)o;
					tmpBomb.explode();
				}
				else if(o.getType()==Type.FIREWORK) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
				}
				else {
					
				}
			}
		}
		Character.judgeGameOver();
	}

	@Override
	public void act() {
		bombTime-=getDeltaTime();
		if(bombTime<=0)
			explode();
	}

	@Override
	public void destroy() {
		GameObject.getPane().getChildren().remove(getCollisionBody());
		GameObject.getPane().getChildren().remove(getImageView());
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
		setter.setBombNum(setter.getBombNum()+1);//炸弹被摧毁时，放置者拥有的炸弹数量+1
	}
	
}
