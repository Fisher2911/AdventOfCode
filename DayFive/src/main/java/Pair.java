import java.util.Objects;

public record Pair(int x, int z) {

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pair pair = (Pair) o;
        return x == pair.x && z == pair.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }
}
