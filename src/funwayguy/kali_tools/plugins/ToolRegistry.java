package funwayguy.kali_tools.plugins;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class ToolRegistry
{
    public static final ToolRegistry INSTANCE = new ToolRegistry();
    
    private final TreeMap<String,ToolDef> REG_TOOLS = new TreeMap<>();
    
    // ID must be file/folder safe
    public void registerTool(@NotNull ToolDef def)
    {
        if(REG_TOOLS.containsKey(def.getToolID()) || REG_TOOLS.containsValue(def))
        {
            throw new IllegalArgumentException("Cannot register duplicate tool or ID");
        }
        
        REG_TOOLS.put(def.getToolID(), def);
    }
    
    @Nullable
    public ToolDef getTool(String idName)
    {
        return INSTANCE.getTool(idName);
    }
    
    public List<ToolDef> getAllTools()
    {
        return Collections.unmodifiableList(new ArrayList<>(REG_TOOLS.values()));
    }
}
