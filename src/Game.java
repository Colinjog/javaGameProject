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

import javax.xml.stream.events.Characters;

import com.sun.glass.ui.CommonDialogs.Type;

import javafx.util.*;

import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game extends Application{
	
	public static int status = 2;//0游戏结束
							//1游戏进行中
							//2游戏未开始
	public static ImageView player1[] = new ImageView[4];
	public static ImageView player2[] = new ImageView[4];
	public static ImageView player3[] = new ImageView[4];
	public static ImageView player4[] = new ImageView[4];
	private Image image;
	
	public void initImage() {
		//0 front
		//1 left
		//2 right
		//3 back
		player1[0] = new ImageView(image=new Image("player1.png",GameObject.size,GameObject.size,false,false));
		player1[1] = new ImageView(image=new Image("left.png",GameObject.size,GameObject.size,false,false));
		player1[2] = new ImageView(image=new Image("right.png",GameObject.size,GameObject.size,false,false));
		player1[3] = new ImageView(image=new Image("back.png",GameObject.size,GameObject.size,false,false));
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initImage();
		Pane pane=new Pane();
		Game.status = 1;
		GameObject.setPane(pane);
		
		Stack<KeyCode> keyStack = new Stack<KeyCode>();
		
		MapGenerator mapGenerator=MapGenerator.getMapGenerator();
		mapGenerator.generateMap(pane);
		
		Character player = new Character("/player1.png", true, "Player1");
		player.setX(0);
		player.setY(0);
		Text info=new Text(1010,50,"name:"+player.getName());
		Text healthInfo=new Text(1010,100,"health:"+player.getHealth());
		Text bombInfo=new Text(1010,150,"bomb:"+player.getBombNum()+"/"+player.getMaxBombNum());
		Text powerInfo=new Text(1010,200,"power:"+player.getBombPower());
		Text speedInfo=new Text(1010,250,"speed"+player.getSpeed());
		
		pane.getChildren().add(info);
		pane.getChildren().add(healthInfo);
		pane.getChildren().add(bombInfo);
		pane.getChildren().add(powerInfo);
		pane.getChildren().add(speedInfo);
		

		Character bot1 = new Character("player2.png", false, "Bot1");
		bot1.setX(950);
		bot1.setY(950);
		Character bot2 = new Character("player3.png", false, "Bot2");
		Character bot3 = new Character("player4.png", false, "Bot3");
		
		bot2.setX(900);
		
		bot3.setY(900);

		AIController bot = new AIController(bot1);
		bot.addBody(bot2);
		bot.addBody(bot3);
		
		
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				
				if(!keyStack.contains(event.getCode()))
					keyStack.push(event.getCode());
				switch(keyStack.lastElement()) {
				case UP:
					player.setDir(Movable.Dir.up);
					player.setImageView(Game.player1[3]);
					break;
				case DOWN:
				    player.setDir(Movable.Dir.down);
				    player.setImageView(Game.player1[0]);
					break;
				case LEFT:
					player.setDir(Movable.Dir.left);
					player.setImageView(Game.player1[1]);
					break;
				case RIGHT:
					player.setDir(Movable.Dir.right);
					player.setImageView(Game.player1[2]);
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
			if (player==null || player.getHealth()==0) {
				return ;
			}
			
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
			if (Game.status == 0){ //Game Over
				return;
			}
			bot.act();
			if (player != null && player.getHealth() != 0 && Game.status==1){
				player.act();
			}
			for(GameObject o:GameObject.objectsList) {
				if (o!=player) {
					o.act();
				}
			}
			
			healthInfo.setText("health:"+player.getHealth());
			bombInfo.setText("bomb:"+player.getBombNum()+"/"+player.getMaxBombNum());
			powerInfo.setText("power:"+player.getBombPower());
			speedInfo.setText("speed"+player.getSpeed());
			
			Character.judgeGameOver();
		};
		
		Timeline animation=new Timeline(new KeyFrame(Duration.millis(20),eventHandler));
		
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
		Scene scene=new Scene(pane,1300,1000);
		
		primaryStage.setTitle("Bomb It");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		pane.requestFocus();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
