import java.util.Dictionary;
import java.util.Enumeration;

public class dictionaryOpenAddressing<K,V> implements Dictionary<K,V> {

    private entry<K,V>[] table;
    private int size;
    private final entry<K,V> deleted;

    public dictionaryOpenAddressing() {
        table = (entry<K, V>[]) new entry[11];
        size = 0;
        deleted = new entry<>(null,null);
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Enumeration<K> keys() {
        return null;
    }

    @Override
    public Enumeration<V> elements() {
        return null;
    }


    @Override
    public V get(K key) {
        int index = hash(key);
        int start = index;

        while (table[index] != null) {
            if (table[index] != deleted && table[index].key.equals(key)) {
                return table[index].value;
            }
            index = (index + 1) % table.length;

            if (index == start) {
                break;
            }
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);

        while (table[index] != null && table[index] != deleted) {
            if (table[index].key.equals(key)) {
                V oldValue = table[index].value;
                table[index].value = value;
                return oldValue;
            }
            index = (index + 1) % table.length;
        }

        table[index] = new entry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }


}
