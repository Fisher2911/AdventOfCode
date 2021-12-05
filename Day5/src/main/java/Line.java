import java.util.ArrayList;
import java.util.List;

public record Line(Pair start, Pair end) {

    public static Line fromString(final String string) {
        final String[] parts = string.split(" -> ");

        final String[] firstPairParts = parts[0].split(",");
        final String[] secondPairParts = parts[1].split(",");

        final Pair first = new Pair(Integer.parseInt(firstPairParts[0]), Integer.parseInt(firstPairParts[1]));
        final Pair second = new Pair(Integer.parseInt(secondPairParts[0]), Integer.parseInt(secondPairParts[1]));

        return new Line(first, second);
    }

    public List<Pair> walk() {
        final List<Pair> pairs = new ArrayList<>();

        final int startX = this.getStartX();
        int startZ = this.getStartZ();
        final int endX = this.getEndX();
        final int endZ = this.getEndZ();

        if (startZ != endZ && startX != endX) {
            int addXAmount = 0;
            int addZAmount = 0;

            startZ = this.start.z();

            final int xDifference = this.getEndX() - this.getStartX();

            for (int x = 0; x <= xDifference; x++) {
                final int tempX = start.x() + addXAmount;
                final int z = startZ + addZAmount;
                if (!contains(tempX, z)) {
                    continue;
                }

                final Pair pair = new Pair(tempX, z);
                pairs.add(pair);

                if (this.start.x() < this.end.x()) {
                    addXAmount++;
                } else {
                    addXAmount--;
                }

                if (this.start.z() < this.end.z()) {
                    addZAmount++;
                } else {
                    addZAmount--;
                }
            }

            return pairs;
        }

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                pairs.add(new Pair(x, z));
            }
        }

        return pairs;
    }

    private boolean contains(final int x, final int z) {
        final int startX = this.getStartX();
        final int startZ = this.getStartZ();
        final int endX = this.getEndX();
        final int endZ = this.getEndZ();

        return x >= startX && x <= endX &&
                z >= startZ && z <= endZ;
    }

    public int getStartX() {
        return Math.min(start.x(), end.x());
    }

    public int getStartZ() {
        return Math.min(start.z(), end.z());
    }

    public int getEndX() {
        return Math.max(start.x(), end.x());
    }

    public int getEndZ() {
        return Math.max(start.z(), end.z());
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
