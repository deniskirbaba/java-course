public class lab1
{
    public static void main(String[] args)
    {
        int i;
        int j = 0;

        /* The required number of elements in the interval [a; b] = b - a + 1. Since we only need odd, then before
         * adding one, you need to divide (b - a) by 2. */
        int[] m = new int[(19 - 7) / 2 + 1];
        for (i = 7; i <= 19; i++)
        {
            if (i % 2 == 1) m[j] = i;
            else j++;
        }

        float[] x = new float[19];
        /* We fill the array with pseudo-random numbers, converting them to the float type,
         * since the Math.random method generates numbers of type double. */
        for (i = 0; i < 19; i++) x[i] = (float) (Math.random() * 23) - 11;

        double[][] u = new double[7][9];
        for (i = 0; i < 7; i++)
        {
            for (j = 0; j < 9; j++)
            {
                switch (m[i])
                {
                    case 13 -> u[i][j] = Math.tan(Math.log(Math.pow(Math.cos(x[j]), 2)));
                    case 7, 9, 11 -> u[i][j] = Math.pow(((Math.pow(0.5 * (1.0 - x[j]), x[j]) - 0.5) / 0.25), 3);
                    default -> u[i][j] = Math.asin(Math.sin(Math.log(4.0 * (Math.abs(x[j]) / 5.0))));
                }
                System.out.printf("%6.2f", u[i][j]);
            }
        }
    }
}