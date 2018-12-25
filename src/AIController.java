import java.util.Random;

public class AIController{
	enum AIState{WANDERING, ATTACKING, WAITING, HIDDING};

	private final int baseTimePiece = 400;
	private int timePiece;
	private AIState curState; //record the state of AI
	private int delta; //record time
	private Character body;

	AIController(Character character){
		delta = 0;
		body = character;
		curState = AIState.WANDERING;
	}

	private void analyze(){
		;
	}

	public void act(){ //make decision here
		body.act();
		Random rand = new Random();
		delta += body.getDeltaTime();
		if (delta < baseTimePiece + rand.nextInt(200) - 100){
			return;
		}

		analyze();
		delta = 0;
		switch (rand.nextInt(7)){
			case 0:
				body.setDir(Movable.Dir.up);
				break;
			case 1:
				body.setDir(Movable.Dir.down);
				break;
			case 2:
				body.setDir(Movable.Dir.left);
				break;
			case 3:
				body.setDir(Movable.Dir.right);
				break;
			case 4:
				body.setBomb();
				break;
			default:
				body.setDir(Movable.Dir.stop);
				break;
		}
	}

	public GameObject getBody(){
		return body;
	}
}
