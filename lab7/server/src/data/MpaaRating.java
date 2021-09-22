package data;

import java.io.Serializable;

/**
 * @author Denis Kirbaba
 * @version 1.2
 * An enum class that represents a MPAA rating of movie.
 */

public enum MpaaRating implements Serializable
{
    G,
    PG,
    PG_13,
    R;

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