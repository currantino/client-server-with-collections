package mid.route;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class Route implements Serializable {

    transient Scanner sc;
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
        sc = new Scanner(System.in);
        while (this.name == null || this.name.equals("")) {
            System.out.println("enter new route name: ");
            this.name = sc.nextLine();
        }
        while (this.distance == null || this.distance <= 0) {
            System.out.println("enter new route distance:");
            this.distance = sc.nextFloat();
        }
        this.coordinates = new Coordinates();
        this.from = new Location();
        this.to = new Location();
        this.creationDate = LocalDateTime.now().withNano(0);
    }

    /**
     * Конструктор, используемый при добавлении нового элемента в коллекцию из файла
     */

    public Route(String name, Float distance, int xCoordinate, Float yCoordinate, String fromName, int fromX, int fromY, int fromZ, String toName, int toX, int toY, int toZ) {
        this.name = name;
        this.distance = distance;
        this.from = new Location(fromName, fromX, fromY, fromZ);
        this.to = new Location(toName, toX, toY, toZ);
        this.creationDate = LocalDateTime.now().withNano(0);
        this.coordinates = new Coordinates(xCoordinate, yCoordinate);
    }

    public Route(String name, Float distance, int xCoordinate, Float yCoordinate, String fromName, int fromX, int fromY, int fromZ, String toName, int toX, int toY, int toZ, LocalDateTime creationDate) {
        this.name = name;
        this.distance = distance;
        this.from = new Location(fromName, fromX, fromY, fromZ);
        this.to = new Location(toName, toX, toY, toZ);
        this.creationDate = LocalDateTime.now().withNano(0);
        this.coordinates = new Coordinates(xCoordinate, yCoordinate);
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

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

}
