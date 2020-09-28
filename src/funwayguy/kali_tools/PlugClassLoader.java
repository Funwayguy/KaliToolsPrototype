package funwayguy.kali_tools;

import java.net.URL;
import java.net.URLClassLoader;

public class PlugClassLoader extends URLClassLoader
{
    public PlugClassLoader(URL[] urls)
    {
        super(urls);
    }
    
    @Override // Purely to make this accessible for adding plugin JARs
    public void addURL(URL url)
    {
        super.addURL(url);
    }
}
