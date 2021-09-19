import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hyc.report.util.SqlUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class test {

    @Test
    public void test() {
/*
        String sql = "select t.c,c.a,t.a from test t,hyc c,tea t";
        //获取from的开始索引
        int from = sql.indexOf("from");
        //以from末尾字符的索引开始截取所有主SQL的字段长度即为from后面的一个或多个表名
        String substring = sql.substring(from + 5, sql.length());
        System.out.println(substring);
        List<String> tableList = new ArrayList<>();
        if (substring.contains(" ")) {
            String[] tableArray = substring.split(",");
            for(int i = 0 ;i< tableArray.length;i++) {
                String table = tableArray[i].substring(0, tableArray[i].lastIndexOf(" "));
                System.out.println(table);
                tableList.add(table);
            }
            System.out.println(tableList);
        }
*/
        List<String> tableNameBySql = SqlUtil.getTableNameBySql("select * from testHYCDEPT");
        System.out.println(tableNameBySql.get(0));
    }

    @Test
    public void test2(){
        String str = "[{field_value=select * from testHYCDEPT, field_name=主SQL},{field_value=where id = 1, field_name=从SQL}]";

        System.out.println(str);

    }


}
