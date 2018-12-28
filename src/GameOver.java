

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOver{

	private String imagePath;
	private String winnerName;
	private ImageView imageView;
	private Pane pane;//»æÖÆ´°¿Ú
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	} 
	public GameOver(String winner,String imagePath,Pane pane) {
		winnerName = winner;
		this.imagePath = imagePath;
		this.pane = pane;
		imageView = new ImageView(new Image(imagePath,400,200,false,false));
		imageView.setX(300);//
		imageView.setY(100);
		pane.getChildren().add(imageView);
		
		
		Text winnerText = new Text(400,300,winnerName+" win");
		winnerText.setFont(Font.font(null,FontWeight.BOLD,40));
		winnerText.setFill(Color.INDIANRED);
		pane.getChildren().add(winnerText);
		
	}
	public Pane getPane() {
		return pane;
	}
	public void setPane(Pane pane) {
		this.pane = pane;
	}

	
	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
}
