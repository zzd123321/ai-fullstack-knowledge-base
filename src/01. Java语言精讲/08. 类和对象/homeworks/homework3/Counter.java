package homework3;

class Counter {
    private int instanceCount = 0;
    public static int totalCount = 0;

    public Counter() {
        instanceCount++;
        totalCount++;
        System.out.println("创建对象 #" + totalCount);
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public static int getTotalCount() {
        return totalCount;
    }
}