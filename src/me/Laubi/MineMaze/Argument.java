package me.Laubi.MineMaze;

/**
 *
 * @author Laubi
 */
public class Argument {
    private String key;
    private String value;

    public Argument(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Argument(String key) {
        this(key, null);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void appendValue(String value){
        this.value = this.value==null?value : this.value + value;
    }
}
