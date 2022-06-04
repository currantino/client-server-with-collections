package route;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Location implements Serializable {
    transient Scanner sc;
    private String name;
    private int x;
    private int y;
    private int z;


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

    public String show() {
        return name + '(' +
                x + ", " +
                y + ", " +
                z +
                ')';
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
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


    public String getName() {
        return name;
    }
}
