import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 1.0
 * Enum, containing MPAA ratings
 */
public enum MpaaRating implements Serializable
{
    G,
    PG,
    PG_13,
    R;

    /**
     * Method to make MpaaRating object from String object
     */
    public static MpaaRating stringToMpaaRating(String mpaaRating)
    {
        String category = mpaaRating.toUpperCase();
        switch (category)
        {
            case "G":
                return MpaaRating.G;
            case "PG":
                return MpaaRating.PG;
            case "PG_13":
                return MpaaRating.PG_13;
            case "R":
                return MpaaRating.R;
            default:
                break;
        }
        return null;
    }
}