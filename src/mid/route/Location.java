package mid.route;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Location implements Serializable {
    transient Scanner sc = new Scanner(System.in);
    private String name;
    private int x;
    private int y;
    private int z;


    public Location() {
        while (true) {
            try {
                System.out.println("enter new location name:");
                this.name = sc.nextLine();
                if (name == null || name.length() > 80 || name.isBlank()) throw new InputMismatchException();
                System.out.println("x coordinate:");
                this.x = this.sc.nextInt();
                System.out.println("y coordinate:");
                this.y = this.sc.nextInt();
                System.out.println("z coordinate:");
                this.z = this.sc.nextInt();
                return;
            } catch (InputMismatchException e) {
                System.out.println("invalid input");
            }
        }
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
