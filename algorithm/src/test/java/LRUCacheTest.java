import com.syp.algorithm.LRUCacheWithCustomDoubleList;
import com.syp.algorithm.LRUCacheWithLinkedHashMap;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * @Author: SYP
 * @Date: 2020/9/2
 * @Description:
 */
public class LRUCacheTest {

    /**
     *@Author: SYP
     *@Date: 2020/9/2
     *@Description:
     * 测试使用linkedHashMap实现的LRUCache
     */
    @Test
    public void testLinkedHashMapCache(String[] args) {
        LRUCacheWithLinkedHashMap cache = new LRUCacheWithLinkedHashMap(10);
        for(int i=0; i< 20; i++){
            cache.put(i, i);
        }
    }

    /**
     *@Author: SYP
     *@Date: 2020/9/2
     *@Description:
     * 测试使用双向链表实现的LRUCache
     */
    @Test
    public  void testCustomDOubleListCache() {
        LRUCacheWithCustomDoubleList cache = new LRUCacheWithCustomDoubleList(10);
        for(int i=0; i< 20; i++){
            cache.put(i, i);
        }
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()){
            int key = sc.nextInt();
            int value = cache.get(key);
        }
    }
}
