package utils;

public class Coord {
    public int x = 0;
    public int y = 0;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Coord)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Coord c = (Coord) o;

        // Compare the data members and return accordingly
        return this.x == c.x && this.y == c.y;
    }

    public long distanceTo(Coord dest) {
        return Math.abs(this.x - dest.x) + Math.abs(this.y - dest.y);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append(this.x)
                .append(", ")
                .append(this.y)
                .append("]")
                .toString();
    }

}
