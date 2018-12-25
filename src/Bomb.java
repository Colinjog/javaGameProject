
public class Bomb extends GameObject{
	
	private int bombTime;
	private int power;
	private Character setter;
	String _imagePath;
	
	public Bomb(int _xInMatrix,int _yInMatrix,int _power, Character _setter,String imagePath) {
		super(_xInMatrix*getSize(),_yInMatrix*getSize(),imagePath);
		_imagePath = imagePath;
		bombTime=2000;
		power=_power;
		setter=_setter;
		setIsCollider(true);
		setType(Type.BOMB);
	}
	
	public void explode() {
		Brick tmpBrick;
		Bomb tmpBomb;
		Character tmpCharacter;
		destroy();
		
		new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
		
		for(int i=1;i<=power&&getXInMatrix()+i<getMapSize();i++) {
			GameObject o=allObjects[getXInMatrix()+i][getYInMatrix()];
			if(o==null)
				new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
			else {
				if(o.getType()==Type.BRICK) {
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
					new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
				}
				else if (o.getType()==Type.CHARACTER) {
					tmpCharacter = (Character)o;
					int health = tmpCharacter.getHealth();
					if (tmpCharacter.isPlayer()) {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							//玩家被炸弹击中,但玩家还有生命
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//游戏结束
						}
					}
					else {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//电脑玩家被炸弹击中,但电脑玩家还有生命
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
					}
				}
			}
		}
		
		for(int i=1;i<=power&&getXInMatrix()-i>=0;i++) {
			GameObject o=allObjects[getXInMatrix()-i][getYInMatrix()];
			if(o==null)
				new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
			else {
				if(o.getType()==Type.BRICK) {
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
					new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
				}
				else if (o.getType()==Type.CHARACTER) {
					tmpCharacter = (Character)o;
					int health = tmpCharacter.getHealth();
					if (tmpCharacter.isPlayer()) {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							//玩家被炸弹击中,但玩家还有生命
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//游戏结束
						}
					}
					else {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//电脑玩家被炸弹击中,但电脑玩家还有生命
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
					}
				}
			}
		}
		
		for(int i=1;i<=power&&getYInMatrix()+i<getMapSize();i++) {
			GameObject o=allObjects[getXInMatrix()][getYInMatrix()+i];
			if(o==null)
				new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
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
				else if (o.getType()==Type.CHARACTER) {
					tmpCharacter = (Character)o;
					int health = tmpCharacter.getHealth();
					if (tmpCharacter.isPlayer()) {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							//玩家被炸弹击中,但玩家还有生命
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//游戏结束
						}
					}
					else {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//电脑玩家被炸弹击中,但电脑玩家还有生命
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
					}
				}
			}
		}
		
		for(int i=1;i<=power&&getYInMatrix()-i>=0;i++) {
			GameObject o=allObjects[getXInMatrix()][getYInMatrix()-i];
			if(o==null)
				new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
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
					new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
				}
				else if (o.getType()==Type.CHARACTER) {
					tmpCharacter = (Character)o;
					int health = tmpCharacter.getHealth();
					if (tmpCharacter.isPlayer()) {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							//玩家被炸弹击中,但玩家还有生命
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//游戏结束
						}
					}
					else {
						if (health>1) {
							health--;
							tmpCharacter.setHealth(health);
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
							//电脑玩家被炸弹击中,但电脑玩家还有生命
						}
						else {
							o.destroy();
							new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
						}
					}
				}
			}
		}
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
		setter.setBombNum(setter.getBombNum()+1);
	}
}
