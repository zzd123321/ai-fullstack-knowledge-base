public class OrderedPair<K, V> implements Pair<K, V> {
    private K key;
    private V value;

    public OrderedPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() { return key; }

    @Override
    public V getValue() { return value; }

    public static void main(String[] args) {
        Pair<String, Integer> pair1 = new OrderedPair<>("age", 25);
        System.out.println(pair1.getKey() + " = " + pair1.getValue());

        Pair<String, String> pair2 = new OrderedPair<>("name", "Alice");
        System.out.println(pair2.getKey() + " = " + pair2.getValue());
    }
}