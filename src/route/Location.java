package route;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Location implements Serializable {
    private int x;
    private int y;
    private int z;
    private String name;
    transient Scanner sc;


    public Location() {
        sc = new Scanner(System.in);
        System.out.println("enter new location name:");
        while (name == null || name.length() > 830 || name.length() < 1) {
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

    @Override
    public String toString() {
        return this.name + "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return x == location.x && y == location.y && z == location.z && name.equals(location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
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
