import java.util.*;

public class Leaderboard 
{
    public static void main(String[] args) 
    {

        Map<String, Integer> scores = new HashMap<>();

        scores.put("Bhavya", 95);
        scores.put("Rahul", 88);
        scores.put("Jay", 95);
        scores.put("Het", 78);
        scores.put("Fenil", 88);

        List<Map.Entry<String, Integer>> players =new ArrayList<>(scores.entrySet());

        Collections.sort(players, new Comparator<Map.Entry<String, Integer>>() 
        {
            public int compare(Map.Entry<String, Integer> p1,Map.Entry<String, Integer> p2) 
            {
                int result = p2.getValue().compareTo(p1.getValue());

                if (result == 0) 
                {
                    return p1.getKey().compareTo(p2.getKey());
                }

                return result;
            }
        }
    );

        int topN = 3;

        System.out.println("Top " + topN + " Players:");
        System.out.println("-------------------");

        for (int i = 0; i < Math.min(topN, players.size()); i++) 
        {
            Map.Entry<String, Integer> player = players.get(i);

            System.out.println(player.getKey() + " - " + player.getValue());
        }
    }
}

