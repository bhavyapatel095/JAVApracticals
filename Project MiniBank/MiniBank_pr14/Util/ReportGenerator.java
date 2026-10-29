package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {
    private ReportGenerator() {
    }

    public static String generate(Path logsFolder, Path reportFile)
            throws IOException {
        long deposits = 0;
        long withdrawals = 0;
        StringBuilder report =
                new StringBuilder("MiniBank End-of-Day Report\n");

        if (Files.exists(logsFolder)) {
            try (DirectoryStream<Path> files =
                         Files.newDirectoryStream(logsFolder, "*.log")) {
                for (Path file : files) {
                    BasicFileAttributes attributes = Files.readAttributes(
                            file, BasicFileAttributes.class
                    );

                    if (attributes.size() == 0) {
                        continue;
                    }

                    report.append("\nFile: ").append(file.getFileName())
                            .append(" | Size: ").append(attributes.size())
                            .append(" bytes | Modified: ")
                            .append(attributes.lastModifiedTime()).append("\n");

                    try (BufferedReader reader = Files.newBufferedReader(file)) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.trim().split("\\s+");
                            if (parts.length < 2) {
                                continue;
                            }

                            try {
                                long amount =
                                        Long.parseLong(parts[parts.length - 1]);

                                if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                    deposits += amount;
                                } else if (parts[0].equalsIgnoreCase("WITHDRAW")) {
                                    withdrawals += amount;
                                }
                            } catch (NumberFormatException ignored) {
                                // Ignore malformed transaction lines.
                            }
                        }
                    }
                }
            }
        }

        report.append("\nTotal deposits: Rs. ").append(deposits)
                .append("\nTotal withdrawals: Rs. ").append(withdrawals)
                .append("\n");

        Path parent = reportFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(
                reportFile,
                report.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        return report.toString();
    }
}
