package route;

import java.io.Serializable;
import java.util.Scanner;

public class Location implements Serializable {
    Scanner sc;
    private int x;
    private int y;
    private int z;
    private String name;

    public Location() {
        this.sc = new Scanner(System.in);
        System.out.println("enter new location name:");
        while (this.name == null || this.name.length() > 830){
            this.name = sc.next();
        }
        System.out.println("x coordinate:");
        this.x = this.sc.nextInt();
        System.out.println("y coordinate:");
        this.y = this.sc.nextInt();
        System.out.println("z coordinate:");
        this.z = this.sc.nextInt();
    }

    public Location(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return this.name + "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getName() {
        return name;
    }
}
