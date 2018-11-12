package godotClasses;

public class AnimationPlayer {
    public String currentAnimation;
    public void play(String animationName) {
        this.currentAnimation = animationName;
        System.out.println("Play animation: " + this.currentAnimation);
    }
}
