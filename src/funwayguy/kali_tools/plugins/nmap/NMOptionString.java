package funwayguy.kali_tools.plugins.nmap;

import funwayguy.kali_tools.plugins.SimpleDocListener;

import javax.swing.*;

public class NMOptionString implements NMOption
{
    private final String name;
    private String text;
    
    public NMOptionString(String name, String def)
    {
        this.name = name;
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
        return (text == null || text.isEmpty()) ? null : text;
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
