package funwayguy.kali_tools;

// Replaced
public class Pair<T, K>
{
    private final T a;
    private final K b;
    
    public Pair(T a, K b)
    {
        this.a = a;
        this.b = b;
    }
    
    public T getKey()
    {
        return this.a;
    }
    
    public K getValue()
    {
        return this.b;
    }
}
