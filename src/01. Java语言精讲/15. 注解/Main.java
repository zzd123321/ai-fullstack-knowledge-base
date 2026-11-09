import java.util.ArrayList;
import java.util.List;

//@MyMethodAnnotation
public class Main {
    public static void main(String[] args) {

    }

    @MyMethodAnnotation
    @SuppressWarnings("unused")
    public void test() {
        int unused = 10;   // 通常会有警告，此处被抑制
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void rawType() {
        List list = new ArrayList();   // 抑制 raw type 警告
        list.add("hello");
    }


}
