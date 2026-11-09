package homework1;

import java.util.ArrayList;
import java.util.List;

public class AnnotationDemo {
    @SuppressWarnings("unchecked")
    public void addItems() {
        List list = new ArrayList();
        list.add("Hello");
        list.add("World");
    }

    @Deprecated
    public void oldWay() {
        System.out.println("This is old way.");
    }

    public void newWay() {
        System.out.println("This is new way.");
    }
}