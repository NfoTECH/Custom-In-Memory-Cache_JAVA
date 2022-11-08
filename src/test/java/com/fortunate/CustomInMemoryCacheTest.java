package com.fortunate;

/**
 * @author fortunate
 * How to Create a Simple In Memory Cache in Java (Lightweight Cache)
 */
public class CustomInMemoryCacheTest {
    public static void main(String[] args) throws InterruptedException {
        CustomInMemoryCacheTest customCache = new CustomInMemoryCacheTest();

        print("\n\n==========Test1: testAddRemoveObjects ==========");
        customCache.testAddRemoveObjects();

        print("\n\n==========Test2: testExpiredCacheObjects ==========");
        customCache.testExpiredCacheObjects();

        print("\n\n==========Test3: testObjectsCleanupTime ==========");
        customCache.testObjectsCleanupTime();
    }
    private void testAddRemoveObjects() {
        // Test with timeToLive = 200 seconds
        // timerInterval = 500 seconds
        // maxItems = 6
        CustomInMemoryCache<String, String> cache = new CustomInMemoryCache<String, String>(200, 500, 6);
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
        cache.put("Microsoft", "Microsoft");
        cache.put("Crunchify", "Crunchify");
        cache.put("Facebook", "Facebook");

        print("6 Cache Object Added.. cache.size(): " + cache.size());
        cache.remove("IBM");
        print("One object removed.. cache.size(): " + cache.size());
        cache.put("Twitter", "Twitter");
        cache.put("SAP", "SAP");
        print("Two objects Added but reached maxItems.. cache.size(): " + cache.size());
    }

    private static void print(String s) {

        System.out.println(s);
    }

    private void testExpiredCacheObjects() throws InterruptedException {
        // Test with timeToLive = 1 second
        // timerInterval = 1 second
        // maxItems = 10
        CustomInMemoryCache<String, String> cache = new CustomInMemoryCache<String, String>(1, 1, 10);
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");

        // Adding 3 seconds sleep. Both above objects will be removed from
        // Cache because of timeToLiveInSeconds value
        Thread.sleep(3000);

        print("Two objects are added but reached timeToLive. cache.size(): " + cache.size());
    }
    private void testObjectsCleanupTime() throws InterruptedException {
        int size = 500000;
        // Test with timeToLiveInSeconds = 100 seconds
        // timerIntervalInSeconds = 100 seconds
        // maxItems = 500000
        CustomInMemoryCache<String, String> cache = new CustomInMemoryCache<String, String>(100, 100, 500000);
        for (int i = 0; i < size; i++) {

            // toString(): Returns a String object representing the specified integer.
            // The argument is converted to signed decimal representation and returned as a string,
            // exactly as if the argument and radix 10 were given as arguments to the toString(int, int) method.
            String value = Integer.toString(i);
            cache.put(value, value);
        }
        Thread.sleep(200);
        long start = System.currentTimeMillis();
        cache.cleanup();
        double finish = (double) (System.currentTimeMillis() - start) / 1000.0;

        print("Cleanup times for " + size + " objects are " + finish + " s");
    }
}
