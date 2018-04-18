package alda.algorithmanalysis;

public class AlgoAnalysis {

    public static void main(String[] args) {

        // fråga 1 - konstant

        {
            int j = 99;
            int k = 0;

            for (int i = 0; i < j; i++) {
                k += j;
            }

            System.out.println(k);
        }

        // fråga 2 - N

        {

            int j = 100;

            int sum = 0;

            for (int i = 0; i < j; i++, j++)
                if (i < (j / 2))
                    sum++;
        }

        // fråga 3 - N^2

        {
            boolean done = true;
            int sum = 0;

            int j = 10;

            for (int i = 0; i < j; i++)
                for(int k = 0; k < (i * j); k++)
                        sum++;
        }
    }
}
