import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Enum, containing genres
 */
public enum MovieGenre implements Serializable {
    WESTERN,
    COMEDY,
    MUSICAL,
    ADVENTURE,
    THRILLER;

    /**
     * Method to make Genre object from String object
     */
    public static MovieGenre stringToGenre(String genre) {
        String category = genre.toUpperCase();
        switch (category) {
            case "WESTERN":
                return MovieGenre.WESTERN;
            case "COMEDY":
                return MovieGenre.COMEDY;
            case "MUSICAL":
                return MovieGenre.MUSICAL;
            case "ADVENTURE":
                return MovieGenre.ADVENTURE;
            case "THRILLER":
                return MovieGenre.THRILLER;
            default:
                break;
        }
        return null;
    }
}