/**
 * Created by Monu on 4/26/16.
 */
public class Ghost extends Agent {

    private int scaredTimer;
    public final static int SCARED_TIME = 20;


    public Ghost () {
        this.scaredTimer = 0;
    }

    public void setScaredTimer(int scaredTime) {
        this.scaredTimer = scaredTime;
    }

    public int getScaredTimer() {
        return this.scaredTimer;
    }

    //Subtract ghost timer
}
