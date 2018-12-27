import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FireWork extends GameObject{
	
	public int existTime=250;//火焰的存在时间
	
	public FireWork(int _xInMatrix,int _yInMatrix,String imagePath) {
		imageView = new ImageView(image = new Image(imagePath, size, size, false, false));
		imageView.setTranslateX(_xInMatrix*size);
		imageView.setTranslateY(_yInMatrix*size);
		collisionBody.setX(_xInMatrix*size);
		collisionBody.setY(_yInMatrix*size);

		pane.getChildren().add(collisionBody);
		pane.getChildren().add(imageView);
		
		objectsList.add(this);
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
		GameObject.getPane().getChildren().remove(getImageView());
		GameObject.allObjects[getXInMatrix()][getYInMatrix()]=null;
		
		objectsList.remove(this);
	}
}
