public class DictionaryOpenAddressing<K, V> implements Dictionary<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] table;
    private Entry<K, V> DELETED;
    private int size;

    @SuppressWarnings("unchecked")
    public DictionaryOpenAddressing() {
        table = (Entry<K, V>[]) new Entry[10];
        DELETED = new Entry<>(null, null);
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);

        while (table[index] != null && table[index] != DELETED) {

            if (table[index].key.equals(key)) {
                V old = table[index].value;
                table[index].value = value;
                return old;
            }

            index = (index + 1) % table.length;
        }

        table[index] = new Entry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        int index = hash(key);

        while (table[index] != null) {

            if (table[index] != DELETED && table[index].key.equals(key)) {
                return table[index].value;
            }

            index = (index + 1) % table.length;
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);

        while (table[index] != null) {

            if (table[index] != DELETED && table[index].key.equals(key)) {
                V old = table[index].value;
                table[index] = DELETED;
                size--;
                return old;
            }

            index = (index + 1) % table.length;
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}