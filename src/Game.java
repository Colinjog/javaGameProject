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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game extends Application{
	
	public static Pane pane=new Pane();
	public static MapGenerator mapGenerator=MapGenerator.getMapGenerator();
	
	public static Character player;
	public static Character bot1;
	public static Character bot2;
	public static Character bot3;
	
	public static AIController bot;
	
	//玩家信息
	public static Text info;
	public static Text healthInfo;
	public static Text bombInfo;
	public static Text powerInfo;
	public static Text speedInfo;
	
	public static Timeline animation;
	
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
		player2[0] = new ImageView(image=new Image("player2.png",GameObject.size,GameObject.size,false,false));
		player2[1] = new ImageView(image=new Image("left2.png",GameObject.size,GameObject.size,false,false));
		player2[2] = new ImageView(image=new Image("right2.png",GameObject.size,GameObject.size,false,false));
		player2[3] = new ImageView(image=new Image("back2.png",GameObject.size,GameObject.size,false,false));
		player3[0] = new ImageView(image=new Image("player3.png",GameObject.size,GameObject.size,false,false));
		player3[1] = new ImageView(image=new Image("left3.png",GameObject.size,GameObject.size,false,false));
		player3[2] = new ImageView(image=new Image("right3.png",GameObject.size,GameObject.size,false,false));
		player3[3] = new ImageView(image=new Image("back3.png",GameObject.size,GameObject.size,false,false));
		player4[0] = new ImageView(image=new Image("player4.png",GameObject.size,GameObject.size,false,false));
		player4[1] = new ImageView(image=new Image("left4.png",GameObject.size,GameObject.size,false,false));
		player4[2] = new ImageView(image=new Image("right4.png",GameObject.size,GameObject.size,false,false));
		player4[3] = new ImageView(image=new Image("back4.png",GameObject.size,GameObject.size,false,false));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//加载图片，初始化背景
		initImage();
		Rectangle background=new Rectangle(0,0,1000,1000);
		background.setFill(Color.GREEN);
		pane.getChildren().add(background);
		
		GameObject.setPane(pane);
		
		Button start=new Button();
		start.setLayoutX(1050);
		start.setLayoutY(500);
		start.setText("START");
		
		pane.getChildren().add(start);
		
		start.setOnMouseClicked(e->{
			mapGenerator.generateMap(pane);
			player=new Character("player1.png",true,"player1");
			bot1=new Character("player2.png",false,"player2");
			bot2=new Character("player3.png",false,"player3");
			bot3=new Character("player4.png",false,"player4");
			
			player.setX(0);
			player.setY(0);
			bot1.setX(950);
			bot1.setY(950);
			bot2.setX(950);
			bot3.setY(950);
			
			//添加玩家信息
			info=new Text(1010,50,"name:"+player.getName());
			healthInfo=new Text(1010,100,"health:"+player.getHealth());
			bombInfo=new Text(1010,150,"bomb:"+player.getBombNum()+"/"+player.getMaxBombNum());
			powerInfo=new Text(1010,200,"power:"+player.getBombPower());
			speedInfo=new Text(1010,250,"speed"+player.getSpeed());
			pane.getChildren().add(info);
			pane.getChildren().add(healthInfo);
			pane.getChildren().add(bombInfo);
			pane.getChildren().add(powerInfo);
			pane.getChildren().add(speedInfo);

			bot = new AIController(bot1);
			bot.addBody(bot2);
			bot.addBody(bot3);
			
			Game.status=1;
			
			animation.play();
			
			pane.requestFocus();
			
		});
		
		Game.status = 2;
		
		//按键处理
		Stack<KeyCode> keyStack = new Stack<KeyCode>();
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
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
				animation.stop();
			}
			bot.act();
			bot1.act();
			bot2.act();
			bot3.act();
			switch(bot1.getDir()) {
			case up:
				bot1.setImageView(Game.player2[3]);
				break;
			case down:
				bot1.setImageView(Game.player2[0]);
				break;
			case left:
				bot1.setImageView(Game.player2[1]);
				break;
			case right:
				bot1.setImageView(Game.player2[2]);
				break;
			default:
				break;
			}
			switch(bot2.getDir()) {
			case up:
				bot2.setImageView(Game.player3[3]);
				break;
			case down:
				bot2.setImageView(Game.player3[0]);
				break;
			case left:
				bot2.setImageView(Game.player3[1]);
				break;
			case right:
				bot2.setImageView(Game.player3[2]);
				break;
			default:
				break;
			}
			switch(bot3.getDir()) {
			case up:
				bot3.setImageView(Game.player4[3]);
				break;
			case down:
				bot3.setImageView(Game.player4[0]);
				break;
			case left:
				bot3.setImageView(Game.player4[1]);
				break;
			case right:
				bot3.setImageView(Game.player4[2]);
				break;
			default:
				break;
			}
			if (player != null && player.getHealth() != 0 && Game.status==1){
				switch(player.getDir()) {
				case up:
					player.setImageView(Game.player1[3]);
					break;
				case down:
					player.setImageView(Game.player1[0]);
					break;
				case left:
					player.setImageView(Game.player1[1]);
					break;
				case right:
					player.setImageView(Game.player1[2]);
					break;
				default:
					break;
				}
				player.act();
			}
			for(GameObject o:GameObject.objectsList) {
				if (o.getType()!=GameObject.Type.CHARACTER) {
					o.act();
				}
			}
			
			healthInfo.setText("health:"+player.getHealth());
			bombInfo.setText("bomb:"+player.getBombNum()+"/"+player.getMaxBombNum());
			powerInfo.setText("power:"+player.getBombPower());
			speedInfo.setText("speed"+player.getSpeed());
			
			Character.judgeGameOver();
		};
		
		animation=new Timeline(new KeyFrame(Duration.millis(20),eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		
		
		
		
		Scene scene=new Scene(pane,1300,1000);
		
		primaryStage.setTitle("Bomb It");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
