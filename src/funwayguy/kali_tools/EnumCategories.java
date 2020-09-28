package funwayguy.kali_tools;

// This could probably be made modular at a later date if categories
// require more attached info such as descriptions or tooltips
public enum EnumCategories
{
    EXPLOITATION("Exploitation"),
    FORENSICS("Forensics"),
    HW_HACKING("Hardware Hacking"),
    INFO_GATHER("Info Gathering"),
    M_ACCESS("Maintain Access"),
    PASS_CRACK("Password Cracking"),
    REPORTING("Reporting"),
    REV_ENGINEER("Rev. Engineering"),
    SNIFF_SPOOF("Sniffing & Spoofing"),
    STRESS_TEST("Stress Testing"),
    VULN_ANALYSIS("Vulnerabilities"),
    WEB_APPS("Web Applications"),
    WIRELESS("Wireless"),
    OTHER("Other");
    
    public final String dispName;
    
    EnumCategories(String name)
    {
        this.dispName = name;
    }
}
