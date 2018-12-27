import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class Bomb extends GameObject{
	

	private int bombTime;//��ը�¼�
	private int power;//ը������
	private Character setter;//����ը��������

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
		boolean isExist = false;//ը����λ���Ƿ�����
		//�ж��˸�ը��ͬһ��λ��
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
						//��ұ�ը������,����һ�������
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						
					}
					else {
						tmpCharacter.destroy();
						tmpCharacter.setHealth(0);
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						Pane tmpPane = Bomb.getPane();
						Character.characters.remove(tmpCharacter);//��characters��ɾ��������character
						tmpCharacter = null;//ɾ��character
					}
				}
				else {
					if (health>1) {
						health--;
						tmpCharacter.setHealth(health);
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						//������ұ�ը������,��������һ�������
					}
					else {
						tmpCharacter.setHealth(0);
						tmpCharacter.destroy();
						Character.characters.remove(tmpCharacter);//��characters��ɾ��������character
						new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);
						tmpCharacter = null;//ɾ��character
					}
				}
			}
		}
		destroy();//���ھ����Լ�Pane��ɾ���ö��󣬷���֮�󴴽����¶�����滻�������еĸö���ͼƬ������Pane��ȥ�����ˡ���
		if (!isExist) {
			new FireWork(getXInMatrix(),getYInMatrix(),_imagePath);//��ը��λ�ô�������
		}
		
		//�ֱ���ĸ������жϱ�ը�߼�
		//�ҷ�
		for(int i=1;i<=power&&getXInMatrix()+i<getMapSize();i++) {//i=0��Ϊ��Ҫ�ж��Ƿ�ը����characterͬλ��
			GameObject o=allObjects[getXInMatrix()+i][getYInMatrix()];

			if(o==null)//�����λ��û��
			{
				
				boolean temp = false;//�ж��Ƿ��Ѿ�����firework
				//�ж��Ƿ��������ҷ�
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
								//��ұ�ը������,����һ�������
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								tmpCharacter1.setHealth(0);
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								Pane tmpPane = Bomb.getPane();
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								tmpCharacter1 = null;//ɾ��character
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								//������ұ�ը������,��������һ�������
							}
							else {
								tmpCharacter1.setHealth(0);
								tmpCharacter1.destroy();
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
								tmpCharacter1 = null;//ɾ��character
							}
						}
					}
				}
				if (!temp) {
					new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
				}
				
		}
			else {
				if(o.getType()==Type.BRICK) {//�����ש�飬��ֹͣ�ж�
					tmpBrick=(Brick)o;
					if(tmpBrick.getIsDestroyable()) {
						tmpBrick.destroy();
					}
					break;
				}
				else if(o.getType()==Type.BOMB) {//�����ը����������
					tmpBomb=(Bomb)o;
					tmpBomb.explode();
				}
				else if(o.getType()==Type.FIREWORK) {//�����λ�����ǻ��棬��ɾ�����棨��ֹͼƬ���������ٴ����µĻ���
					o.destroy();
					new FireWork(getXInMatrix()+i,getYInMatrix(),_imagePath);
				}
				else if(o.getType()==Type.EATABLE) {//����ǵ��ߣ��ݻ�
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
				
				boolean temp = false;//�ж��Ƿ��Ѿ�����firework
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
								//��ұ�ը������,����һ�������
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								Pane tmpPane = Bomb.getPane();
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								tmpCharacter1 = null;//ɾ��character
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								//������ұ�ը������,��������һ�������
							}
							else {
								tmpCharacter1.setHealth(0);
								tmpCharacter1.destroy();
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);
								tmpCharacter1 = null;//ɾ��character
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
				}//�ж�o�ǲ���character
				else if (o.getType()==Type.CHARACTER) {
					o.destroy();
					//character����
					new FireWork(getXInMatrix()-i,getYInMatrix(),_imagePath);

				}
				else {
					
					}
			}
			
		
		
		for(int i=1;i<=power&&getYInMatrix()+i<getMapSize();i++) {
			GameObject o=allObjects[getXInMatrix()][getYInMatrix()+i];
			if(o==null)
			{
				
				boolean temp = false;//�ж��Ƿ��Ѿ�����firework
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
								//��ұ�ը������,����һ�������
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								tmpCharacter1 = null;//ɾ��character
								
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								//������ұ�ը������,��������һ�������
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()+i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								tmpCharacter1 = null;//ɾ��character
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
				
				boolean temp = false;//�ж��Ƿ��Ѿ�����firework
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
								//��ұ�ը������,����һ�������
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								tmpCharacter1 = null;//ɾ��character
							}
						}
						else {
							if (health>1) {
								health--;
								tmpCharacter1.setHealth(health);
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								//������ұ�ը������,��������һ�������
							}
							else {
								tmpCharacter1.destroy();
								new FireWork(getXInMatrix(),getYInMatrix()-i,_imagePath);
								tmpCharacter1.setHealth(0);
								Character.characters.remove(tmpCharacter1);//��characters��ɾ��������character
								tmpCharacter1 = null;//ɾ��character
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
		setter.setBombNum(setter.getBombNum()+1);//ը�����ݻ�ʱ��������ӵ�е�ը������+1
	}
	
}
