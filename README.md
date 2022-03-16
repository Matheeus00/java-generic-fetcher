# Java Generic Fetcher
Repository destinated to show a Java Generic Fetcher made by me


## How to use?
1. Create a Java Data Class, and put above the Fields a Column annotation that represents his column in Database;

*Ex: User.java*
```java
public class User
{
  @Column( "id" )
  private Long id;

  @Column( "name" )
  private String name;  

  @Column( "password" )
  private String password;
  
  // Getters and Setters
}
```

2. Use the GenericFetcher.java

*Ex: UserController.java*
```java
public class UserController
{
  public List<User> getUsers() throws Exception
    {
    	Connection connection = Database.getInstance().getConnection();
    	
    	String sql = Users.select; // select * from user;
    	
    	PreparedStatement ps = connection.prepareStatement( sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
                                                                 ResultSet.CONCUR_READ_ONLY );
    	
    	List<User> users = GenericFetcher.fetchList( User.class, ps );
    	
    	ps.close();
    	
    	return users;
    }
}
```
