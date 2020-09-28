package funwayguy.kali_tools.plugins.nmap;

import javax.swing.*;

public class NMOptionBool implements NMOption
{
    private final String name;
    private final String opt;
    private boolean state;
    
    public NMOptionBool(String name, String opt, boolean def)
    {
        this.name = name;
        this.opt = opt;
        this.state = def;
    }
    
    @Override
    public String getName()
    {
        return this.name;
    }
    
    public String appendCmd()
    {
        return state ? opt : null;
    }
    
    @Override
    public JComponent getPanel()
    {
        JCheckBox chkBox = new JCheckBox();
        chkBox.setSelected(state);
        chkBox.addActionListener(e -> state = chkBox.isSelected());
        return chkBox;
    }
}
