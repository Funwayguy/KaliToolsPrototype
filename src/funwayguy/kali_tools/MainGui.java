package funwayguy.kali_tools;

import funwayguy.kali_tools.plugins.ToolDef;
import funwayguy.kali_tools.plugins.ToolRegistry;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGui
{
    public JPanel cardRoot;
    private JPanel pgMain;
    private JPanel pgPrefs;
    private JPanel panelRight;
    private JTabbedPane toolTabs;
    private JScrollPane tabDesc;
    private JScrollPane tabOptions;
    private JScrollPane tabConsole;
    private JButton btnLaunch;
    private JButton btnUpdate;
    private JPanel panelLeft;
    private JButton btnMenu;
    private JComboBox<String> catCombo;
    private JScrollPane toolScroll;
    private JPanel toolSidebar;
    private JPanel innerPanel;
    private JTabbedPane prefTabs;
    private JScrollPane scrollGen;
    private JScrollPane scrollPlug;
    private JButton btnSave;
    private JButton btnCancel;
    public JTextArea txtConsole;
    private JPanel infoPanelRoot;
    private JLabel toolIconBox;
    private JTextArea toolTitleBox;
    private JTextArea toolDescBox;
    private JPanel optionsPanel;
    private JButton btnSaveLog;
    
    private List<ToolDef> toolList = Collections.emptyList();
    private ToolDef selTool;
    
    public MainGui() // Fires after GUI has been created so variables above should be accessible safely
    {
        catCombo.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) loadCategory(catCombo.getSelectedIndex());
        });
        
        catCombo.removeAllItems();
        catCombo.addItem("All");
        for(EnumCategories cat : EnumCategories.values())
        {
            catCombo.addItem(cat.dispName);
        }
        
        btnMenu.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                openPrefs();
            }
        });
        toolTabs.addChangeListener(e -> changeTab(toolTabs.getSelectedIndex()));
        
        btnSave.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // TODO: Save Preference changes
                CardLayout cards = (CardLayout)cardRoot.getLayout();
                cards.show(cardRoot, "CardMain");
            }
        });
        
        btnCancel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                CardLayout cards = (CardLayout)cardRoot.getLayout();
                cards.show(cardRoot, "CardMain");
            }
        });
        
        btnLaunch.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                startStopTool();
            }
        });
        btnSaveLog.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFileChooser fileChoose = new JFileChooser();
                fileChoose.setDialogTitle("Save Log");
                fileChoose.addChoosableFileFilter(new FileNameExtensionFilter("Log File (*.txt, *.log)", "log", "txt"));
                if(fileChoose.showDialog(Main.frame, "Save") == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChoose.getSelectedFile();
                    if(!file.getName().endsWith(".log") && !file.getName().endsWith(".txt")) file = new File(file.toString() + ".log");
                    try(FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw))
                    {
                        bw.write(txtConsole.getText());
                    } catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
    
    public void loadCategory(int index)
    {
        if(index < 0 || index >= catCombo.getItemCount())
        {
            System.out.println("ERROR: Tried to access invalid category at index " + index);
            return;
        }
    
        List<ToolDef> tmpList = ToolRegistry.INSTANCE.getAllTools();
        
        if(index != 0)
        {
            EnumCategories cat = EnumCategories.values()[index - 1];
            tmpList = new ArrayList<>(tmpList);
            tmpList.removeIf((obj) -> (obj.getCategory() != cat));
        }
        
        toolList = tmpList;
        
        System.out.println("Loading category: " + catCombo.getItemAt(index));
        toolSidebar.removeAll();
        for(int i = 0; i < tmpList.size(); i++)
        {
            ToolDef tool = tmpList.get(i);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            JLabel lbl = new JLabel("Ico " + i);
            ImageIcon icon = tool.getIcon();
            if(icon != null)
            {
                Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
                lbl.setIcon(new ImageIcon(img));
            }
            Dimension dim = new Dimension(32, 32);
            lbl.setSize(dim);
            lbl.setMinimumSize(dim);
            lbl.setMaximumSize(dim);
            lbl.setPreferredSize(dim);
            //btn.setBounds(0, i * 64, 64, 64);
            lbl.setVisible(true);
            toolSidebar.add(lbl, gbc);
            
            /*GridBagConstraints*/ gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            JButton btn = new JButton(tool.getName());
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setSize(200, 32);
            btn.setVisible(true);
            
            final int toolID = i;
            btn.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    selectTool(toolID);
                }
            });
            toolSidebar.add(btn, gbc);
        }
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = toolList.size();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1D;
        gbc.weighty = 1D;
        Dimension fillMin = new Dimension(0, 0);
        Dimension fillMax = new Dimension(32, 32);
        toolSidebar.add( new Box.Filler(fillMin, fillMin, fillMax), gbc);
        
        toolSidebar.validate();
        toolScroll.validate();
        toolSidebar.repaint();
        toolScroll.repaint();
    }
    
    public void selectTool(int toolID)
    {
        if(toolList == null || toolID < 0 || toolID >= toolList.size()) return;
        selTool = toolList.get(toolID);
        
        optionsPanel.removeAll();
        selTool.buildOptions(optionsPanel);
        optionsPanel.validate();
        tabOptions.validate();
        optionsPanel.repaint();
        tabOptions.repaint();
        
        toolTitleBox.setText(selTool.getName() + "\nVersion: " + selTool.getVersion());
        toolDescBox.setText(selTool.getDesc());
        toolDescBox.setLineWrap(true);
        toolDescBox.setWrapStyleWord(true);
        
        ImageIcon icon = selTool.getIcon();
        if(icon != null)
        {
            Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_FAST);
            toolIconBox.setIcon(new ImageIcon(img));
        }
        
        System.out.println("Selected tool with index " + toolID);
    }
    
    public void changeTab(int index)
    {
        System.out.println("Opening tool tab " + toolTabs.getSelectedIndex());
    }
    
    public void openPrefs()
    {
        System.out.println("Opening menu...");
        // TODO: Load prefs into GUI
        CardLayout cards = (CardLayout)cardRoot.getLayout();
        cards.show(cardRoot, "CardPrefs");
    }
    
    public void startStopTool()
    {
        System.out.println("Launching tools...");
        if(selTool != null) selTool.startTool();
        toolTabs.setSelectedIndex(2); // Make this safer later...
    }
}
