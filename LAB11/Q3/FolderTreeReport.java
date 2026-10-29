import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

public class FolderTreeReport 
{
    public static void main(String[] args) 
    {
        Path folder = Paths.get("sample");
        Path report = Paths.get("report.txt");

        try 
        {
            // Create a sample folder and files
            Files.createDirectories(folder.resolve("subfolder"));

            Files.write(folder.resolve("file1.txt"),"Hello Java".getBytes());

            Files.write(folder.resolve("subfolder/file2.txt"),"Java NIO".getBytes());

            try (BufferedWriter writer =Files.newBufferedWriter(report)) 
            {

                writer.write("FOLDER TREE REPORT");
                writer.newLine();
                writer.write("------------------");
                writer.newLine();

                Files.walkFileTree(folder,new SimpleFileVisitor<Path>() 
                    {
                    
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            
                    throws IOException 
                    {

                        writer.write("Name: " +folder.relativize(file));
                        writer.newLine();

                        writer.write("Size: " + attrs.size() + " bytes");
                        writer.newLine();

                        writer.write("Modified: " + Instant.ofEpochMilli(attrs.lastModifiedTime().toMillis()));
                        writer.newLine();

                        writer.newLine();

                        return FileVisitResult.CONTINUE;
                    }
                }
            );
            }

            System.out.println("Report created successfully!");
            System.out.println("Report file: report.txt");

        } 
        catch (IOException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

