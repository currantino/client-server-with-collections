package mid.route;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Route implements Serializable {

    transient Scanner sc = new Scanner(System.in);

    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Location from;
    private Location to;
    private Float distance;


    /**
     * конструктор, используемый при добавлении нового элемента в коллекцию через командную строку
     */
    public Route() {
        while (true) {
            try {
                System.out.println("enter new route name: ");
                this.name = sc.nextLine();
                if (name == null || name.isBlank() || name.length() > 80) throw new InputMismatchException();
                System.out.println("enter new route distance:");
                this.distance = sc.nextFloat();
                if (distance <= 0) throw new InputMismatchException();
                this.from = new Location();
                this.to = new Location();
                this.creationDate = LocalDateTime.now().withNano(0);
                return;
            } catch (InputMismatchException e) {
                System.out.println("invalid input");
            }
        }
    }

    /**
     * конструктор, используемый при добавлении элемента, полученного от клиента
     */
    public Route(String name, Float distance, int xCoordinate, Float yCoordinate, String fromName, int fromX, int fromY, int fromZ, String toName, int toX, int toY, int toZ, LocalDateTime creationDate) {
        this.name = name;
        this.distance = distance;
        this.from = new Location(fromName, fromX, fromY, fromZ);
        this.to = new Location(toName, toX, toY, toZ);
        this.coordinates = new Coordinates(xCoordinate, yCoordinate);
        this.creationDate = creationDate;
    }

    /**
     * конструктор, используемый при добавлении элемента, полученного из БД
     */
    public Route(int id, String name, Float distance, String fromName, int fromX, int fromY, int fromZ, String toName, int toX, int toY, int toZ, LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.from = new Location(fromName, fromX, fromY, fromZ);
        this.to = new Location(toName, toX, toY, toZ);
        this.creationDate = creationDate;
    }


    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && name.equals(route.name) && coordinates.equals(route.coordinates) && Objects.equals(creationDate, route.creationDate) && from.equals(route.from) && to.equals(route.to) && distance.equals(route.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, from, to, distance);
    }

    public String show() {
        return "Route" + "(" + id + ")" + " from " + from.show() + " to " + to.show() + " created: " + this.creationDate;
    }


    public Float getDistance() {
        return this.distance;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

}
