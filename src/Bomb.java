
public class Bomb extends GameObject{
	
	private int bombTime;
	private int power;
	private Character setter;
	
	public Bomb(int _xInMatrix,int _yInMatrix,int _power, Character _setter) {
		super(_xInMatrix*getSize(),_yInMatrix*getSize());
		bombTime=2000;
		power=_power;
		setter=_setter;
		setIsCollider(true);
		setType(Type.BOMB);
	}
	
	public void explode() {
		Brick tmpBrick;
		Bomb tmpBomb;
		
		destroy();
		
		new FireWork(getXInMatrix(),getYInMatrix());
		
		for(int i=1;i<=power&&getXInMatrix()+i<getMapSize();i++) {
			GameObject o=allObjects[getXInMatrix()+i][getYInMatrix()];
			if(o==null)
				new FireWork(getXInMatrix()+i,getYInMatrix());
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
					new FireWork(getXInMatrix()+i,getYInMatrix());
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i);
				}
				else if (o.getType()==Type.CHARACTER) {
					Character tmpCharacter;
					o.destroy();
					//characterÀ¿Õˆ
				}
			}
		}
		
		for(int i=1;i<=power&&getXInMatrix()-i>=0;i++) {
			GameObject o=allObjects[getXInMatrix()-i][getYInMatrix()];
			if(o==null)
				new FireWork(getXInMatrix()-i,getYInMatrix());
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
					new FireWork(getXInMatrix()-i,getYInMatrix());
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i);
				}//≈–∂œo «≤ª «character
				else if (o.getType()==Type.CHARACTER) {
					o.destroy();
					//characterÀ¿Õˆ
				}
			}
		}
		
		for(int i=1;i<=power&&getYInMatrix()+i<getMapSize();i++) {
			GameObject o=allObjects[getXInMatrix()][getYInMatrix()+i];
			if(o==null)
				new FireWork(getXInMatrix(),getYInMatrix()+i);
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
					new FireWork(getXInMatrix(),getYInMatrix()+i);
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i);
				}
				else if (o.getType()==Type.CHARACTER) {
					o.destroy();
					//characterÀ¿Õˆ
				}
			}
		}
		
		for(int i=1;i<=power&&getYInMatrix()-i>=0;i++) {
			GameObject o=allObjects[getXInMatrix()][getYInMatrix()-i];
			if(o==null)
				new FireWork(getXInMatrix(),getYInMatrix()-i);
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
					new FireWork(getXInMatrix(),getYInMatrix()-i);
				}
				else if(o.getType()==Type.EATABLE) {
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i);
				}
				else if (o.getType()==Type.CHARACTER) {
					o.destroy();
					//characterÀ¿Õˆ
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
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
		setter.setBombNum(setter.getBombNum()+1);
	}
}
