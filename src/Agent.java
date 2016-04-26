public class Agent {

    public String currentDirection;

    public Agent() {
        this.currentDirection = "Up";
    }

    public String chooseAction() {
        return this.currentDirection;
    }
}
