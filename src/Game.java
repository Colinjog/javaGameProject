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
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import javafx.util.*;

import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game extends Application{
	Character player;



	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane pane=new Pane();
		
		GameObject.setPane(pane);
		
		Stack<KeyCode> keyStack = new Stack<KeyCode>();
		
		MapGenerator mapGenerator=MapGenerator.getMapGenerator();
		mapGenerator.initMap("map1", pane);
		
		player = new Character("character.png",true,"Player1");

		//
		Character bot1 = new Character("character.png", false, "Bot1");
		bot1.setHealth(100000);
		bot1.setX(100);
		bot1.setY(400);
		AIController bot = new AIController(bot1);
		
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (player == null || player.getHealth() <= 0){
					return;
				}
				// TODO Auto-generated method stub
				if(!keyStack.contains(event.getCode()))
					keyStack.push(event.getCode());
				switch(keyStack.lastElement()) {
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
				case SPACE:
					player.setBomb();
					break;
				default:
					break;
				}
			}	
		});
		
		pane.setOnKeyReleased(e->{
			keyStack.removeElement(e.getCode());
			if(keyStack.isEmpty())
				player.setDir(Movable.Dir.stop);
			else {
				switch(keyStack.lastElement()) {
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
		
		EventHandler<ActionEvent> eventHandler = e->{ //called every frame
			if (player != null && player.getHealth() != 0){
				player.act();
			}
			bot.act();
			
			for(GameObject o:GameObject.objectsList) {
				if(o!=player) {
					o.act();
				}
			}
			
			Character.judgeGameOver();
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
