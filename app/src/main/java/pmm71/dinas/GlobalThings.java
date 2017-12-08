package pmm71.dinas;

/**
 * Created by St-Ex on 08.12.2017.
 */


public class GlobalThings {
    public float density;
    //перевод px в dp. Так удобно, что просто памагити.
    static int pxFromDp(double dp, float density) {
        return (int) (dp * density);
    }
}
