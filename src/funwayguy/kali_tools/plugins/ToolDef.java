package funwayguy.kali_tools.plugins;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import funwayguy.kali_tools.EnumCategories;

import javax.swing.*;

// Kali Tool Definition
public interface ToolDef
{
    @NotNull
    String getToolID();
    @NotNull
    String getVersion();
    @NotNull
    String getName();
    @NotNull
    String getDesc();
    @NotNull
    EnumCategories getCategory();
    @Nullable
    ImageIcon getIcon();
    
    @Nullable
    void buildOptions(JPanel panel);
    
    @Nullable
    JPanel[] getMiscPages();
    
    void startTool();
    void runUpdate();
}
