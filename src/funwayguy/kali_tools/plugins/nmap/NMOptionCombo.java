package funwayguy.kali_tools.plugins.nmap;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import funwayguy.kali_tools.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class NMOptionCombo implements NMOption
{
    private final String name;
    private int selected;
    private List<Pair<String, String>> comboOps = new ArrayList<>();
    
    public NMOptionCombo(String name, int def)
    {
        this.name = name;
        this.selected = def;
    }
    
    public void addOption(@NotNull String opName, @Nullable String value)
    {
        comboOps.add(new Pair<>(opName, value));
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    @Override
    public String appendCmd()
    {
        if(selected < 0 || selected >= comboOps.size()) return null;
        return comboOps.get(selected).getValue();
    }
    
    @Override
    public JComponent getPanel()
    {
        JComboBox<String> combo = new JComboBox<>();
        for(Pair<String, String> entry : comboOps)
        {
            combo.addItem(entry.getKey());
        }
        combo.addItemListener(e -> selected = combo.getSelectedIndex());
        if(selected >= 0 && selected < combo.getItemCount()) combo.setSelectedIndex(selected);
        return combo;
    }
}
