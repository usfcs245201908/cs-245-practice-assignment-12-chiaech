public class Hashtable {
    int size;
    HashNode[] bucket;
    double LOAD_THRESHOLD = (float) 0.5;
    int entries = 0;

    class HashNode {
        String key;
        String value;
        HashNode next;

        public HashNode(String key, String value){
            this.key = key;
            this.value = value;
            next = null;
        }
    }

    Hashtable(){
        size = 10000;
        bucket = new HashNode[size];
        entries = 0;
    }

    // Returns “true” if a key/value object pair (with the key matching the argument and any value).
    boolean containsKey(String key) {
        return bucket[getHash(key)] != null;
    }


    // Returns the value associated with the key which is passed as an argument; returns ​null​
    // if no key/value pair is contained by theHashtable instance.
    String get(String key){
        HashNode head = bucket[getHash(key)];

        while(head != null){
            if (head.key == key){
                return head.value;
            } else {
                head = head.next;
            }
        }
        return null;
    }

    // Adds the key/value pair into the Hashtable instance. If there is an existing key/value
    // pair, the Hashtable instance replaces the stored value with the argument value.
    void put (String key, String value){
        HashNode head = bucket[getHash(key)];
        if (head == null){
            bucket[getHash(key)] = new HashNode(key, value);
        } else {
            while (head != null) {
                if (head.key.equals(key)) {
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            HashNode node = new HashNode(key, value);
            node.next = bucket[getHash(key)];
            bucket[getHash(key)] = node;
        }
        ++entries;
        if ((entries * 1.0) / size >= LOAD_THRESHOLD){
            increaseBucketSize();
        }
    }

    // Removes the key/value pair from the Hashtable instance and returns the value associated
    // with the key to the caller. Throws an Exception instance if the key is not present in
    // the Hashtable instance.
    String remove(String key) throws Exception {
        HashNode head = bucket[getHash(key)];

        // if null, then done
        if (head != null) {
            if (head.key.equals(key)) {
                bucket[getHash(key)] = head.next;
                --entries;
                return head.value;
            } else {
                HashNode prev = head;
                HashNode curr;

                while (head.next != null) {
                    curr = head.next;
                    if (curr != null && curr.key == key) {
                        head.next = curr.next;
                        --entries;
                        return curr.value;
                    }
                }
                return head.next.value;
            }
        } else {
            throw new Exception();
        }
    }

    int getHash(String key){
        return Math.abs(key.hashCode() % size);
    }
    private void increaseBucketSize(){
        HashNode[] temp = bucket;
        size = size *2;
        bucket = new HashNode[size];
        entries = 0;
        for(HashNode head : temp) {
            while(head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }
}
