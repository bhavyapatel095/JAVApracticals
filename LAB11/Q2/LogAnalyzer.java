import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class LogAnalyzer 
{
    public static void main(String[] args) 
    {
        try 
        {
            Files.write(Paths.get("log1.txt"), 
            Arrays.asList("Application started","Error: File not found","User logged in"));

            Files.write(Paths.get("log2.txt"), 
            Arrays.asList("Connection successful","Error: Connection failed","Application closed"));

            int totalLines = 0;
            int keywordCount = 0;
            String keyword = "Error";

            String[] fileNames = {"log1.txt", "log2.txt"};

            for (String fileName : fileNames) 
                {
                Path path = Paths.get(fileName);

                List<String> lines = Files.readAllLines(path);

                totalLines += lines.size();

                for (String line : lines) 
                {
                    if (line.contains(keyword)) 
                    {
                        keywordCount++;
                    }
                }

                BasicFileAttributes attr =
                    Files.readAttributes(path, BasicFileAttributes.class);

                System.out.println("File: " + fileName);
                System.out.println("Size: " + attr.size() + " bytes");
                System.out.println();
            }

            System.out.println("Total lines: " + totalLines);
            System.out.println("Lines containing '" + keyword +"': " + keywordCount);

        } 
        catch (IOException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

