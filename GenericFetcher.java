package br.com.polygon.wm.common.db;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.polygon.wm.common.data.annotation.Column;

/**
 * 
 * @author matheus
 *
 */
public class GenericFetcher 
{
	/**
	 * fetch
	 * 
	 * @param <T>
	 * @param clazz Class&lt;?&gt;
	 * @param ps PreparedStatement
	 * @return &lt;T&gt;
	 * @throws Exception
	 */
	public static <T> T fetchOne( Class<?> clazz, PreparedStatement ps ) throws Exception
	{
		T object = (T) clazz.getDeclaredConstructor( null ).newInstance( null );
		
		ResultSet rs = ps.executeQuery();
		
		Field[] fields = clazz.getDeclaredFields();
		
		for ( Field field : fields )
		{
			field.setAccessible( true );
			
			Column column = field.getAnnotation( Column.class );
			
			String annotationValue = column.value();
			
			if ( rs.first() )
			{
				Object value = rs.getObject( annotationValue );
				
				field.set( object, value.getClass().cast( value ) );											
			}
		}	

		rs.close();

		ps.close();
		
		return object;
	}

	/**
	 * fetchMany
	 * 
	 * @param <T>
	 * @param clazz Class&lt;?&gt;
	 * @param ps PreparedStatement
	 * @return List&lt;T&gt;
	 * @throws Exception
	 */
	public static <T> List<T> fetchList( Class<?> clazz, PreparedStatement ps ) throws Exception
	{
		List<T> objectList = new ArrayList<>();
		
		T object = (T) clazz.getDeclaredConstructor( null ).newInstance( null );
		
		ResultSet rs = ps.executeQuery();
		
		Field[] fields = clazz.getDeclaredFields();
		
		do
		{
			for ( Field field : fields )
			{
				field.setAccessible( true );
				
				Column column = field.getAnnotation( Column.class );
				
				String annotationValue = column.value();
				
				if ( rs.first() )
				{
					Object value = rs.getObject( annotationValue );
					
					field.set( object, value.getClass().cast( value ) );											
				}
			}
			
			objectList.add( object );
		}
		while ( rs.next() );

		rs.close();

		ps.close();
		
		return objectList;
	}
}
