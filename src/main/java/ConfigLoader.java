import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public final class ConfigLoader {

    /**
     * Credit to garbagemule's MobArena project for parts this method
     * @param plugin instance of Plugin
     * @param directory config file directory
     * @param name file name
     * @return new Config instance
     * @throws IOException
     */
    public static Config load(Plugin plugin, File directory, String name) throws IOException {
        File file = new File(directory, name);
        FileConfiguration configuration = new YamlConfiguration();

        if (!file.exists()) {
            plugin.getLogger().info("No " + name + " config-file found, creating default...");
            plugin.saveResource(name, false);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));

            int row = 0;
            String line;

            while ((line = in.readLine()) != null) {
                row++;

                if (line.indexOf('\t') != -1) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("Found tab in ").append(name).append(" config-file on line ").append(row).append(".");
                    builder.append('\n').append("NEVER use tabs! ALWAYS use spaces!");
                    builder.append('\n').append(line);
                    builder.append('\n');

                    for (int i = 0; i < line.indexOf('\t'); i++) {
                        builder.append(' ');
                    }

                    builder.append('^');
                    throw new IllegalArgumentException(builder.toString());
                }
            }

            configuration.load(file);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException("There is an error in your " + name + " config file:\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Config file " + name + " could not be created!");
        } catch (IOException e) {
            plugin.getLogger().severe("There was an error reading the " + name + " config file:\n" + e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return new Config(name, file, configuration);
    }
}