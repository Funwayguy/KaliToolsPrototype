package funwayguy.kali_tools.plugins.nmap;

import javax.swing.*;

public class NMOptionNull implements NMOption
{
    private final String name;
    
    public NMOptionNull(String name)
    {
        this.name = name;
    }
    
    @Override
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public String appendCmd()
    {
        return null;
    }
    
    @Override
    public JComponent getPanel()
    {
        return null;
    }
}
