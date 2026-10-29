import java.util.*;

public class WordFrequency 
{
    public static void main(String[] args) 
    {

        String paragraph = "java is easy and java is powerful";

        String[] words = paragraph.toLowerCase().split("\\s+");

        HashMap<String, Integer> map = new HashMap<>();

        for (String word : words) 
        {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        List<String> list = new ArrayList<>(map.keySet());

        Collections.sort(list, new Comparator<String>() 
        {
            public int compare(String a, String b) 
            {
                return map.get(b).compareTo(map.get(a));
            }
        }
    );

        System.out.println("Word Frequency:");

        for (String word : list) 
        {
            System.out.println(word + " : " + map.get(word));
        }
    }
}

