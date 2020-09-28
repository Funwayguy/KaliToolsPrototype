package funwayguy.kali_tools.plugins.nmap;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.swing.*;

public interface NMOption
{
    @NotNull
    String getName();
    
    @Nullable
    String appendCmd();
    
    @NotNull
    JComponent getPanel();
}
