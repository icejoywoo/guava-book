package bbejeck.guava.chapter4;


import com.google.common.collect.HashBasedTable;
import org.junit.Before;
import org.junit.Test;


import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 4/11/13
 * Time: 9:44 PM
 */
public class TableTest {

    private HashBasedTable<Integer,Integer,String> table = HashBasedTable.create();

    @Before
    public void setUp(){
        table.put(1,1,"Rook");
        table.put(1,2,"Knight");
        table.put(1,3,"Bishop");
        table.put(2,1,"Lateral");
        table.put(2,2,"L-Shape");
        table.put(2,3,"Diagonal");
    }

    @Test
    public void testHashBasedTablePut(){
         assertThat(table.get(1,2),is("Knight"));
         assertThat(table.get(5,5),is(nullValue()));
    }

    @Test
    public void testGetColumnView(){
        Map<Integer,String> columnMap = table.column(1);
        assertThat(columnMap.get(1),is("Rook"));
        assertThat(columnMap.get(2),is("Lateral"));
        columnMap.put(2,"Changed");
        assertThat(table.get(2,1),is("Changed"));
        columnMap.put(3,"Added Value");
        assertThat(table.get(3,1),is("Added Value"));
    }

    @Test
    public void testGetRowView(){
        Map<Integer,String> rowMap = table.row(1);
        assertThat(rowMap.get(1),is("Rook"));
        assertThat(rowMap.get(2),is("Knight"));
        assertThat(rowMap.get(3),is("Bishop"));

        rowMap.put(4,"King");
        assertThat(table.get(1,4),is("King"));
        assertThat(table.column(4).get(1),is("King"));
    }

    @Test
    public void testTableOperations(){
        table.contains(1,1);
        table.containsColumn(2);
        table.containsRow(1);
        table.containsValue("Rook");

    }
}
