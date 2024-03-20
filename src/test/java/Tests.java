import com.iceolive.util.StringUtil;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalTime;

public class Tests {
    @Test
    public void test1(){
        Time a = StringUtil.parse("1899-12-31 08:30:00", "yyyy-MM-dd HH:mm:ss", Time.class);
        System.out.println(a);
        Time a1= StringUtil.parse("08:30:00", "HH:mm:ss", Time.class);
        System.out.println(a1);
        LocalTime a2 = StringUtil.parse("1899-12-31 08:30:01", "yyyy-MM-dd HH:mm:ss", LocalTime.class);
        System.out.println(a2);
        LocalTime a3= StringUtil.parse("08:30:02", "HH:mm:ss", LocalTime.class);
        System.out.println(a3);
        System.out.println(StringUtil.format(a, "HH:mm"));
        System.out.println(StringUtil.format(a1, "HH:mm:ss"));
        System.out.println(StringUtil.format(a2, "HH:mm"));
        System.out.println(StringUtil.format(a3, "HH:mm:ss"));
    }
}
