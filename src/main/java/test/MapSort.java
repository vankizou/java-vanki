package test;

import java.util.*;

/**
 * Created by vanki on 2018-12-13 15:21.
 */
public class MapSort {
    public static void main(String[] args) {
        Map<Integer, A> map = new HashMap<>();

        A a1 = new A();
        a1.zgId = 1;
        a1.opens = new HashSet<Integer>() {{
            this.add(1);
            this.add(1);
            this.add(2);
        }};

        A a2 = new A();
        a2.zgId = 2;
        a2.opens = new HashSet<Integer>() {{
            this.add(1);
            this.add(1);
            this.add(2);
        }};

        A a3 = new A();
        a3.zgId = 3;
        a3.opens = new HashSet<Integer>() {{
            this.add(1);
            this.add(1);
            this.add(2);
            this.add(3);
        }};

        A a4 = new A();
        a4.zgId = 4;
        a4.opens = new HashSet<Integer>() {{
            this.add(1);
        }};

        map.put(a1.zgId, a1);
        map.put(a2.zgId, a2);
        map.put(a3.zgId, a3);
        map.put(a4.zgId, a4);

        ArrayList<A> results = new ArrayList<>(map.values());
        results.sort((o1, o2) -> {
            int c = o2.opens.size() - o1.opens.size();
            return c == 0 ? 1 : c;
        });

        System.out.println(results);
    }
}

class A {
    int zgId;
    HashSet<Integer> opens = new HashSet<>();

    @Override
    public String toString() {
        return "{\"zgId\":" + zgId + ",\"opens\":" + opens + "}";
    }
}
