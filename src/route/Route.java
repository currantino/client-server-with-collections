package route;

import server.data.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class Route implements Serializable {

    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Location from;
    private Location to;
    private Float distance;
    transient Scanner sc;


    /**
     * конструктор, используемый при добавлении нового элемента в коллекцию через командную строку
     */
    public Route() {
        sc = new Scanner(System.in);
        while (this.name == null || this.name.equals("")) {
            System.out.println("enter new route name: ");
            this.name = sc.next();
        }
        while (this.distance == null || this.distance <= 0) {
            System.out.println("enter new route distance:");
            this.distance = sc.nextFloat();
        }
        this.coordinates = new Coordinates();
        this.from = new Location();
        this.to = new Location();
        this.creationDate = LocalDateTime.now().withNano(0);
        Data.generateAndSetId(this);
    }

    /**
     * Конструктор, используемый при добавлении нового элемента в коллекцию из файла
     */

    public Route(int id, String name, Float distance, int xCoordinate, Float yCoordinate, String fromName, int fromX, int fromY, int fromZ, String toName, int toX, int toY, int toZ) {
        this.name = name;
        this.distance = distance;
        this.from = new Location(fromName, fromX, fromY, fromZ);
        this.to = new Location(toName, toX, toY, toZ);
        this.creationDate = LocalDateTime.now().withNano(0);
        this.id = id;
        this.coordinates = new Coordinates(xCoordinate, yCoordinate);
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String toString() {
        return "Server.route.Route" + "(" + this.id + ")" + " from " + this.from + " to " + this.to + " created: " + this.creationDate;
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

    public String getName() {
        return name;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }


    public Location getFrom() {
        return from;
    }


    public Location getTo() {
        return to;
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
}
