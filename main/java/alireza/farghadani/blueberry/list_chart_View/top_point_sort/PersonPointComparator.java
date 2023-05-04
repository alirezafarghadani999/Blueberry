package alireza.farghadani.blueberry.list_chart_View.top_point_sort;

import java.util.Comparator;

public class PersonPointComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Float.compare(p2.getPoint(), p1.getPoint());
    }
}