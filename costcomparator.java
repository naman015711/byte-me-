package byteme.bytemeap4;
import java.util.*;
public class costcomparator implements Comparator<items>{
    public int compare(items i1 , items i2){
        if (i1.item_cost == i2.item_cost)
            return 0;
        else if (i1.item_cost > i2.item_cost)
            return 1;
        else
            return -1;

    }

}
