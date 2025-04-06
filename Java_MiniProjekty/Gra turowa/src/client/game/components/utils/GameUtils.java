package client.game.components.utils;

public class GameUtils {
    /**
     * Retrieves the direction represented by the given enum value.
     * @param direction the direction enum value
     * @return an array of two integers representing the direction
     */
    public static int[] getDirection(Directions direction) {
        return switch (direction) {
            case UP -> new int[]{-1, 0};
            case DOWN -> new int[]{1, 0};
            case LEFT -> new int[]{0, -1};
            case RIGHT -> new int[]{0, 1};
        };
    }
    /**
     * Checks if the given coordinates (x, y) are valid within the specified size.
     *
     * @param x    the x-coordinate
     * @param y    the y-coordinate
     * @param size the size of the map or grid
     * @return true if the coordinates are valid, false otherwise
     */
    public static boolean areCoordinatesValid(int x, int y, int size){
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}
