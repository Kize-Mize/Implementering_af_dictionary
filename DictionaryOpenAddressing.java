public class DictionaryOpenAddressing<K, V> {

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

    public V put(K key, V value) {
        int index = hash(key);
        int start = index;

        while (table[index] != null) {
            if (table[index] != DELETED && table[index].key.equals(key)) {
                V old = table[index].value;
                table[index].value = value;
                return old;
            }

            if (table[index] == DELETED) {
                table[index] = new Entry<>(key, value);
                size++;
                return null;
            }

            index = (index + 1) % table.length;
            if (index == start) {
                return null;
            }
        }

        table[index] = new Entry<>(key, value);
        size++;
        return null;
    }

    public V get(K key) {
        int index = hash(key);
        int start = index;

        while (table[index] != null) {
            if (table[index] != DELETED && table[index].key.equals(key)) {
                return table[index].value;
            }

            index = (index + 1) % table.length;
            if (index == start) {
                break;
            }
        }

        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        int start = index;

        while (table[index] != null) {
            if (table[index] != DELETED && table[index].key.equals(key)) {
                V old = table[index].value;
                table[index] = DELETED;
                size--;
                return old;
            }

            index = (index + 1) % table.length;
            if (index == start) {
                break;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}