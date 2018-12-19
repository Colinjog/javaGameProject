import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.Panel;
import java.util.Timer;
import javafx.util.*;

import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Game extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane pane=new Pane();
		
		GameObject.setPane(pane);
		
		Character player=new Character();
		Brick brick1=new Brick(10,10,true);
		
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				switch(event.getCode()) {
				case UP:
					player.setDir(Movable.Dir.up);
					break;
				case DOWN:
					player.setDir(Movable.Dir.down);
					break;
				case LEFT:
					player.setDir(Movable.Dir.left);
					break;
				case RIGHT:
					player.setDir(Movable.Dir.right);
					break;
				default:
					break;
				}
			}	
		});
		
		pane.setOnKeyReleased(e->{
			switch(e.getCode()) {
			case DOWN:
				if(player.getDir()==Movable.Dir.down)
					player.setDir(Movable.Dir.stop);
				break;
			case UP:
				if(player.getDir()==Movable.Dir.up)
					player.setDir(Movable.Dir.stop);
				break;
			case LEFT:
				if(player.getDir()==Movable.Dir.left)
					player.setDir(Movable.Dir.stop);
				break;
			case RIGHT:
				if(player.getDir()==Movable.Dir.right)
					player.setDir(Movable.Dir.stop);
				break;
			default:
				break;
			}
		});
		
		EventHandler<ActionEvent> eventHandler = e->{
			player.move();
		};
		
		Timeline animation=new Timeline(new KeyFrame(Duration.millis(20),eventHandler));
		
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
		Scene scene=new Scene(pane,1000,1000);
		
		primaryStage.setTitle("Bomb It");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		pane.requestFocus();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
