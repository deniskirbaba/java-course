import java.io.Serializable;

public enum MovieGenre implements Serializable
{
    WESTERN,
    COMEDY,
    MUSICAL,
    ADVENTURE,
    THRILLER;

    public static MovieGenre stringToGenre(String genre)
    {
        String category = genre.toUpperCase();
        switch (category)
        {
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