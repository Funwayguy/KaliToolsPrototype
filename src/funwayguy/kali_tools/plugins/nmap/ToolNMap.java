package funwayguy.kali_tools.plugins.nmap;

import funwayguy.kali_tools.EnumCategories;
import funwayguy.kali_tools.Main;
import funwayguy.kali_tools.plugins.ToolDef;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToolNMap implements ToolDef
{
    public static ToolNMap INSTANCE = new ToolNMap();
    
    private final ImageIcon icon = new ImageIcon("nmap_icon.png");//"C:/Users/Funwayguy/Desktop/nmap_icon.png");
    
    private final NMOptionString opExec;
    private final NMOptionString opTarget;
    
    private final List<NMOption> options = new ArrayList<>();
    
    private ToolNMap()
    {
        // Build options list
        options.add(new NMOptionNull("-=- Scanning -=-"));
        
        opExec = new NMOptionString("Executable", "C:/Program Files (x86)/Nmap/nmap.exe");
        opTarget = new NMOptionString("Target", "127.0.0.1");
        
        NMOptionCombo tcpCombo = new NMOptionCombo("TCP scans", 0);
        tcpCombo.addOption("None", null);
        tcpCombo.addOption("ACK (-sA)", "-sA");
        tcpCombo.addOption("FIN (-sA)", "-sF");
        tcpCombo.addOption("Maimon (-sM)", "-sM");
        tcpCombo.addOption("SYN (-SN)", "-sN");
        tcpCombo.addOption("Connect (-sT)", "-sT");
        tcpCombo.addOption("Window (-sW)", "-sW");
        tcpCombo.addOption("Xmas Tree (-sX)", "-sX");
        options.add(tcpCombo);
        
        NMOptionCombo noTcpCombo = new NMOptionCombo("Non-TCP scans", 0);
        noTcpCombo.addOption("None", null);
        noTcpCombo.addOption("UDP (-sU)", "-sU");
        noTcpCombo.addOption("IP Protocol (-sO)", "-sO");
        noTcpCombo.addOption("List (-sL)", "-sL");
        noTcpCombo.addOption("No Port Scan (-sn)", "-sn");
        noTcpCombo.addOption("SCTP INIT Port Scan (-sY)", "-sY");
        noTcpCombo.addOption("SCTP cookie-echo Port Scan (-sZ)", "-sZ");
        options.add(noTcpCombo);
        
        NMOptionCombo timeCombo = new NMOptionCombo("Timing Template", 5);
        timeCombo.addOption("None", null);
        timeCombo.addOption("Paranoid (-T0)", "-T0");
        timeCombo.addOption("Sneaky (-T1)", "-T1");
        timeCombo.addOption("Polite (-T2)", "-T2");
        timeCombo.addOption("Normal (-T3)", "-T3");
        timeCombo.addOption("Agressive (-T4)", "-T4");
        timeCombo.addOption("Insane (-T5)", "-T5");
        options.add(timeCombo);
        
        options.add(new NMOptionBool("Enable Agressive Options (-A)", "-A", true));
        options.add(new NMOptionBool("OS Detection (-O)", "-O", false));
        options.add(new NMOptionBool("Version Detection (-sV)", "-sV", false));
        options.add(new NMOptionStringArg("Idle Scan (-sl)", "-sl", ""));
        options.add(new NMOptionStringArg("FTP Bounce (-b)", "-b", ""));
        options.add(new NMOptionBool("IPv6 Support (-6)", "-6", false));
        
        options.add(new NMOptionBool("Disable reverse DNS (-n)", "-n", false));
        
        options.add(new NMOptionNull("- Ping -"));
        
        options.add(new NMOptionBool("Don't Ping Before Scan (-Pn)", "-Pn", false));
        options.add(new NMOptionBool("ICMP Ping (-PE)", "-PE", false));
        options.add(new NMOptionBool("ICMP Timestamp (-PP)", "-PP", false));
        options.add(new NMOptionBool("ICMP Netmask (-PM)", "-PM", false));
        options.add(new NMOptionStringArg("ACK Ping (-PA)", "-PA", ""));
        options.add(new NMOptionStringArg("SYN Ping (-PS)", "-PS", ""));
        options.add(new NMOptionStringArg("UDP Probe (-PU)", "-PU", ""));
        options.add(new NMOptionStringArg("IPProto Probe (-PO)", "-PO", ""));
        options.add(new NMOptionStringArg("SCTP INIT Probe (-PY)", "-PY", ""));
        
        options.add(new NMOptionNull("- Target -"));
        
        options.add(new NMOptionStringArg("Excluded hosts/networks (--exclude)", "--exclude", ""));
        options.add(new NMOptionStringArg("Exclusion File (--excludefile)", "--excludefile", ""));
        options.add(new NMOptionStringArg("Targets File (-iL)", "-iL", ""));
        options.add(new NMOptionStringArg("Scan Random (-iR)", "-iR", ""));
        options.add(new NMOptionStringArg("Ports to Scan (-p)", "-p", ""));
        options.add(new NMOptionBool("Fast Scan (-F)", "-F", false));
        
        options.add(new NMOptionNull("- Other -"));
        
        NMOptionCombo verbCombo = new NMOptionCombo("Verbosity", 1);
        verbCombo.addOption("0", null);
        String verbSuffix = "-v";
        for(int i = 1; i <= 10; i++)
        {
            verbCombo.addOption("" + i, verbSuffix);
            verbSuffix += " -v";
        }
        options.add(verbCombo);
    }
    
    @Override
    public String getToolID()
    {
        return "nmap";
    }
    
    @Override
    public String getVersion()
    {
        return "0.0.1";
    }
    
    @Override
    public String getName()
    {
        return "NMap";
    }
    
    @Override
    public String getDesc()
    {
        return "Nmap (\"Network Mapper\") is a free and open source (license) utility for network discovery and security auditing. " +
                "Many systems and network administrators also find it useful for tasks such as network inventory, managing service upgrade schedules, and monitoring host or service uptime. " +
                "Nmap uses raw IP packets in novel ways to determine what hosts are available on the network, what services (application name and version) those hosts are offering, " +
                "what operating systems (and OS versions) they are running, what type of packet filters/firewalls are in use, and dozens of other characteristics. " +
                "It was designed to rapidly scan large networks, but works fine against single hosts.";
    }
    
    @Override
    public EnumCategories getCategory()
    {
        return EnumCategories.VULN_ANALYSIS;
    }
    
    @Override
    public ImageIcon getIcon()
    {
        return icon;
    }
    
    @Override
    public void buildOptions(JPanel panel)
    {
        // EXECUTABLE LOCATION
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0.5D;
        gbc.weighty = 0D;
        gbc.insets = new Insets(2, 4, 2, 4);
        
        JLabel lbl = new JLabel(opExec.getName());
        lbl.setPreferredSize(new Dimension(1, 24));
        //lbl.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(lbl, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0.5D;
        gbc.weighty = 0D;
        gbc.insets = new Insets(2, 4, 2, 4);
        
        panel.add(opExec.getPanel(), gbc);
        
        // TARGET ADDRESS
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0.5D;
        gbc.weighty = 0D;
        gbc.insets = new Insets(2, 4, 2, 4);
        
        lbl = new JLabel(opTarget.getName());
        lbl.setPreferredSize(new Dimension(1, 24));
        panel.add(lbl, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0.5D;
        gbc.weighty = 0D;
        gbc.insets = new Insets(2, 4, 2, 4);
        
        panel.add(opTarget.getPanel(), gbc);
        
        for(int i = 0; i < options.size(); i++)
        {
            NMOption op = options.get(i);
            JComponent opPanel = op.getPanel();
            
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = i + 2;
            gbc.gridheight = 1;
            gbc.gridwidth = opPanel == null ? 2 : 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.weightx = 0.5D;
            gbc.weighty = 0D;
            gbc.insets = new Insets(2, 4, 2, 4);
            
            lbl = new JLabel(op.getName());
            lbl.setPreferredSize(new Dimension(1, 24));
            panel.add(lbl, gbc);
            
            if(opPanel != null)
            {
                gbc = new GridBagConstraints();
                gbc.gridx = 1;
                gbc.gridy = i + 2;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.weightx = 0.5D;
                gbc.weighty = 0D;
                gbc.insets = new Insets(2, 4, 2, 4);
    
                panel.add(op.getPanel(), gbc);
            } else
            {
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = options.size() + 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0D;
        gbc.weighty = 1D;
        Dimension fillMin = new Dimension(0, 0);
        Dimension fillMax = new Dimension(32, 32);
        panel.add( new Box.Filler(fillMin, fillMin, fillMax), gbc);
        
        System.out.println("Built options tab with " + options.size() + " entries");
    }
    
    @Override
    public JPanel[] getMiscPages()
    {
        return new JPanel[0];
    }
    
    @Override
    public void startTool()
    {
        try
        {
            StringBuilder sb = new StringBuilder();
            Iterator<NMOption> iter = options.iterator();
            while(iter.hasNext())
            {
                NMOption op = iter.next();
                String com = op.appendCmd();
                if(com != null)
                {
                    sb.append(com);
                    if(iter.hasNext()) sb.append(" ");
                }
            }
            // "-T4 -A -v 127.0.0.1"
            String runCommand = "\"" + opExec.appendCmd() + "\" " + sb.toString() + " " + opTarget.appendCmd();
            final Process p = Runtime.getRuntime().exec(runCommand);
            final JTextArea jta = Main.mGui.txtConsole;
            jta.setText("> " + runCommand + "\n");
            
            new Thread(() -> {
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
        
                try
                {
                    while((line = input.readLine()) != null)
                        jta.append(line + "\n");
                } catch(IOException e)
                {
                    e.printStackTrace();
                }
            }).start();
        } catch(Exception e)
        {
            System.out.println("Error launching tool!");
            e.printStackTrace();
        }
    }
    
    @Override
    public void runUpdate()
    {
    }
}
