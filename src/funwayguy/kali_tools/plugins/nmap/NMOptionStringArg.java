package funwayguy.kali_tools.plugins.nmap;

import funwayguy.kali_tools.plugins.SimpleDocListener;

import javax.swing.*;

public class NMOptionStringArg implements NMOption
{
    private final String name;
    private final String opt;
    private String text;
    
    public NMOptionStringArg(String name, String opt, String def)
    {
        this.name = name;
        this.opt = opt;
        this.text = def;
    }
    
    @Override
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public String appendCmd()
    {
        return (text == null || text.isEmpty()) ? null : (opt + " " + text);
    }
    
    @Override
    public JComponent getPanel()
    {
        JTextField txtField = new JTextField();
        txtField.setText(text);
        txtField.getDocument().addDocumentListener((SimpleDocListener)e -> text = txtField.getText());
        return txtField;
    }
}
