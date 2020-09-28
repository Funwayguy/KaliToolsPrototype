package funwayguy.kali_tools;

import com.formdev.flatlaf.FlatDarkLaf;
import funwayguy.kali_tools.plugins.ToolRegistry;
import funwayguy.kali_tools.plugins.nmap.ToolNMap;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.URLClassLoader;

public class Main
{
    public static JFrame frame;
    public static MainGui mGui;
    
    // Program entry point
    public static void main(String[] args)
    {
        // Install and set dark theme
        FlatDarkLaf.install();
        
        loadBuiltIns();
        loadPlugins();
        
        loadGlobalPrefs();
        loadPlugPrefs();
        
        mGui = new MainGui();
        frame = new JFrame("Kali Toolbox");
        frame.setLocation(new Point());
        frame.getContentPane().add(mGui.cardRoot);
        //frame.setContentPane(mGui.root_panel);
        Dimension size = new Dimension(800, 600);
        frame.setPreferredSize(size);
        frame.setMinimumSize(size);
        frame.setSize(size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        // Replaced this with something more comprehensive to load settings into every content pane
        mGui.loadCategory(0);
    }
    
    private static void loadBuiltIns()
    {
        ToolRegistry.INSTANCE.registerTool(ToolNMap.INSTANCE);
    }
    
    private static void loadPlugins()
    {
        URLClassLoader sysLoader = (URLClassLoader)URLClassLoader.getSystemClassLoader();
        PlugClassLoader plugLoader = new PlugClassLoader(sysLoader.getURLs());
        try
        {
            plugLoader.addURL(new URL(""));
        } catch(Exception ignored) {}
    }
    
    private static void loadGlobalPrefs()
    {
    }
    
    private static void loadPlugPrefs()
    {
    
    }
}
