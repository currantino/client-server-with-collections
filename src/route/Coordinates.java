package route;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Coordinates implements Serializable {
    transient Scanner sc;
    private int x;
    private float y;

    public Coordinates() {
        sc = new Scanner(System.in);
        System.out.println("x coordinate: ");
        this.x = sc.nextInt();
        while (this.x <= -181) {
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

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return x == that.x && Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}