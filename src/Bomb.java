
public class Bomb extends GameObject{
	
	private int bombTime;//爆炸事件
	private int power;//炸弹威力
	private Character setter;//放置炸弹的人物
	
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
		
		destroy();//先在矩阵以及Pane中删除该对象，否则之后创建的新对象会替换掉矩阵中的该对象，图片就留在Pane上去不掉了。。。
		
		new FireWork(getXInMatrix(),getYInMatrix());//在炸弹位置创建火焰
		
		
		//分别从四个方向判断爆炸逻辑
		for(int i=1;i<=power&&getXInMatrix()+i<getMapSize();i++) {
			GameObject o=allObjects[getXInMatrix()+i][getYInMatrix()];
			if(o==null)//如果该位置没有
				new FireWork(getXInMatrix()+i,getYInMatrix());
			else {
				if(o.getType()==Type.BRICK) {//如果是砖块，这停止判断
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
					new FireWork(getXInMatrix()+i,getYInMatrix());
				}
				else if(o.getType()==Type.EATABLE) {//如果是道具，摧毁
					o.destroy();
					new FireWork(getXInMatrix(),getYInMatrix()+i);
				}
				else if (o.getType()==Type.CHARACTER) {
					Character tmpCharacter;
					o.destroy();
					//character死亡
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
				}//判断o是不是character
				else if (o.getType()==Type.CHARACTER) {
					o.destroy();
					//character死亡
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
					//character死亡
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
					//character死亡
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
		setter.setBombNum(setter.getBombNum()+1);//炸弹被摧毁时，放置者拥有的炸弹数量+1
	}
}
