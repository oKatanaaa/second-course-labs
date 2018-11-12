package godotClasses;

public class KinematicBody2D {
    public final AnimationPlayer anim = new AnimationPlayer();

    protected void moveAndSlide(Vector2 moveDir){
        System.out.println(moveDir);
    }
}
