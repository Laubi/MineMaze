package me.Laubi.MineMaze.Addons;

import java.util.Iterator;

/**
 *
 * @author Laubi
 */
public interface AddonCollector<C>{
    public C getEntry(String alias);
    public C getRandomEntry();
    
    public void registerMethods(Class<?> clazz);
    
    public int size();
    public Iterator<C> iterator();
    
}
