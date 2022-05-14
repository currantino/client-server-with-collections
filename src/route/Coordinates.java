package route;

import java.io.Serializable;
import java.util.Scanner;

public class Coordinates implements Serializable {
    private int x;
    private float y;
    public Coordinates() {
        Scanner sc = new Scanner(System.in);
        System.out.println("x coordinate: ");
        this.x = sc.nextInt();
        while(this.x <= -181){
            System.out.println("incorrect\nx coordinate: ");
            this.x = sc.nextInt();
        }
        System.out.println("y coordinate: ");
        this.y = sc.nextFloat();
    }

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}