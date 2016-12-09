#DIARY

##Ven 9 Dec 2016
**PROBLEM**:  when trying to launch a jenkins build, got :
Error: java.io.IOException: Cannot run program "mvn" (in directory "/Users/Shared/Jenkins/Home/jobs/hsking_server/workspace"): error=2, No such file or directory

**SOLUTION**:
http://stackoverflow.com/questions/26906972/cannot-run-program-mvn-error-2-no-such-file-or-directory

You either didn't select Maven version in Job configuration. Or you didn't configure Jenkins to install a Maven version. Or you expected to use locally installed Maven on the Slave, but it's not configured for jenkins user.

If you want to use locally installed Maven on master/slave

You must have Maven locally installed
You must be able to launch it with jenkins user
Execute sudo jenkins, and then execute mvn on your Slave to verify that jenkins user can run mvn
If that fails, you need to properly install/configure Maven
In Job configuration, for Maven Version, you must select Default. This is the setting that uses the version that's installed locally on the node
If you want to have Jenkins install Maven for you

You must go to Jenkins Global Configuration, and configure a Maven version with automatic installer (from the web).
In Job configuration, for Maven Version, you must select that particular version that you've just configured.

##Ven 9 Dec 2016
PROBLEM: installed jenkins via homebrew and via jenkins-2.35.pkg
after that, When launching the jenkins UI, he asked me to log in but had no user nor apssword.
SOLUTION: went to /Users:Shared/Jenkins/Home/config.xml and set :
...
<disableSignup>false</disableSignup>
...
then, restart jenkins :
>sudo launchctl unload /Library/LaunchDaemons/org.jenkins-ci.plist
>sudo  launchctl load /Library/LaunchDaemons/org.jenkins-ci.plist

go to http://localhost:8080
and click on "signin" icon and create an account.

##Mer 23 Nov 2016
**PROBLEM**: Had some problem generating the war and ar exploded. In the classes directory, there were
WEB-INF, META-INF, ... stuff unrelated to .class files.
SOLUTION:
in pom.xml, commented :
<build>
...
</build>
then did :
>mvn clean
>mvn install
it failed because some tests did not pass. After fixing the tests, the classes were
correctly generated.
------------------ --------------------
Fri 23 September 2016
Added the transaction Manager so that I can get rid of all the boilerplate code
in the save and update metho fo DAOs.
PROBLEM: Hibernate.initialize() no longer works since I use JPA now.
SOLUTION :I have to make an explicit call on the lazy collection in order to initialize it
(common practice is to call .size() for this purpose). In Hibernate there is a
dedicated method for this (Hibernate.initialize()), but JPA has no equivalent of that.

------------------ --------------------
Sun 18 September
PROBLEM : wanted to use HibernateDaoSupport as advised in 2009- Spirng persitence with hibernate.
However, Some people on StackOverflow says that it is no longer recommended.
http://stackoverflow.com/questions/5104765/why-is-hibernatedaosupport-not-recommended

------------------ --------------------
Fri 16 September
CONTEXT: same than below
PROBLEM:
after fetching hskword, when I do hskword.getUser(), to get the user, the user is null.

ANALYSIS : this is because, the property HSKWord.user has been defined as LAZY.

SOLUTION : We have to use Hibernate.initialize(hskword.getUser()) to initialize non-hydrated
entities or collections in the HSKWordDAO.get(String traditional, String simplified,
String pinyin) method.
For Hibernate.initialize, see :
   2013-Java persistence with hibernate chap 13
   2015-Hibernate recipes chap 5, 6, 14


------------------ --------------------
Fri 16 September
PROBLEM:
a HSKWord a a manyToOne relationship with User.
User has a oneToMany relationship with HSKList
After I save a HskWord, when trying to get it I get a :
org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON:
No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer
and no properties discovered to create BeanSerializer

ANALYSIS: when retrieving the HSkword, hibernate needs to retrieve the User which as
a hskLists property declared as lazy.

SOLUTION:

------------------ --------------------
Fri 16 September
PROBLEM:
a HSKWord a a manyToOne relationship with User.
User has a oneToMany relationship with HSKList
After I save a HskWord, when trying to get it I get a :
HttpMessageNotWritableException: Could not write JSON: could not initialize proxy -
no Session (through reference chain: HskWord["user"]->User_$$_jvstd8c_1["id"]);

ANALYSIS:
lazy loading means that when you fetch your object from the database, the nested objects
are not fetched (and may be fetched later when required). Now Jackson tries to serialize
the nested object, but fails as it finds JavassistLazyInitializer instead of normal object.
This is the error that is displayed.

SOLUTION:
https://github.com/FasterXML/jackson-datatype-hibernate
use  jackson-datatype-hibernate to support JSON serialization and deserialization of
Hibernate specific datatypes and properties; especially lazy-loading aspects.

1) add in pom.xml :
<dependency>
  <groupId>com.fasterxml.jackson.datatype</groupId>
  <artifactId>jackson-datatype-hibernate4</artifactId>
  <version>2.5.3</version>
</dependency>

2) create a class HibernateAwareObjectMapper :
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}

3) add it as the objectmapper to be used by adding in spring-config.xml :
<mvc:annotation-driven>
        <mvc:message-converters>
            <!-- Use the HibernateAware mapper instead of the default -->
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="path.to.your.HibernateAwareObjectMapper" />
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

------------------ --------------------
Tues 13 September
PROBLEM: org.hibernate.LazyInitializationException: failed to lazily initialize a
collection of role: com.foryousoft.hsking.beans.User.hskLists, could not initialize
proxy - no Session
ANALYSIS : In order to lazy load a collection there must be an active session.
since the session is closed , it explains the error.
SOLUTION:
http://stackoverflow.com/questions/11746499/solve-failed-to-lazily-initialize-a-collection-of-role-exception
The only solution is to create a specific method that retrieves the list of hskLists
from a given user.


------------------ --------------------
Mon 12 September
PROBLEM:
cannot simultaneously fetch multiple bags: [User.hskLists, HskWord.examples]

SOLUTION:

------------------ --------------------
Sun 11 September
PROBLEM:
User user = new User("bbbb@gmail.com", "BBBBBB", "BbBbBbB");
user = webService.saveUser(user);

HskWord hskword = new HskWord(user, "a","a", "ma3", "Cheval");
hskword.setCharacterType(CharacterType.PICTOGRAM);
hskword = webService.saveHskWord(hskword);

SentenceExample example1 = new SentenceExample(hskword, "这个吗很高。");
example1 = webService.saveExample(example1);
        hskword.addSentenceExample(example1);

        hskword = webService.getHskWord("a","a", "ma3");
        logger.info("hskword: "+ hskword);

There is a oneToMany relation between HskWord and example.
Hibernate does not retrieve the example when retrieving hskword.

SOLUTION:
------------------ --------------------
Sun 11 September
PROBLEM:HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError) (through reference chain: com.foryousoft.hsking.beans.SentenceExample["hskword"]->com.foryousoft.hsking.beans.HskWord["examples"]-
 User user = new User("bbbb@gmail.com", "BBBBBB", "BbBbBbB");
 user = webService.saveUser(user);

 HskWord hskword = new HskWord(user, "a","a", "ma3", "Cheval");
 hskword.setCharacterType(CharacterType.PICTOGRAM);
 hskword = webService.saveHskWord(hskword);

 SentenceExample example1 = new SentenceExample(hskword, "这个吗很高。");
 example1 = webService.saveExample(example1);
 hskword.addSentenceExample(example1);

 hskword = webService.getHskWord("a","a", "ma3");

When getHskWord() is called, it tries to read the hskWord instance which contains a
list of SentenceExample. But every  SentenceExample contains a "hskWord" property which
itself contains a List<SentenceExample> etc.

SOLUTION:
public class HskList implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    ...
}

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="user", fetch=FetchType.EAGER)
    @JsonManagedReference
    private List<HskList> hskLists = new ArrayList();
  ...
  }

I added  JsonBackReference and @JsonManagedReference List.

------------------ --------------------
Sat 10 September
PROBLEM: 500 Server Error at org.springframework.web.client.DefaultResponseErrorHandler.handleError

SOLUTION:
When looking in the  public HskWord getHskWord() method , the found hskWord entity,
the followig sentences was written next to the examples property :
"Unable to evaluate the expression. Method threw lazyInitialisation exception.
BEFORE :
public class HskWord implements ICedictWord, Serializable {
...
  @OneToMany(mappedBy = "hskword")
    protected List<SentenceExample> examples = new ArrayList();
}
AFTER:
public class HskWord implements ICedictWord, Serializable {
...
  @OneToMany(mappedBy = "hskword", fetch=FetchType.EAGER )
    protected List<SentenceExample> examples = new ArrayList();
}

------------------ --------------------
Sat 10 September
PROBLEM : when sending chinese characters as parameters to
 RestController.getHskWord(String simplified, String traditional, String pinyin)
, the simplified & traditional string are not properly converted.
SOLUTION :
in web.xml add :
 <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
        </filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>

    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

------------------ --------------------
Sat 10 September
PROBLEM: when calling UserView method :
deleteUser: function () {

            var userToUpdate = this.model;
            var self = this;
            userToUpdate.id = userToUpdate.get("id");
            userToUpdate.destroy({
                success: function (model, response, options) {
                    self.collection.remove(model);
                },
                error: function (model, xhr, options) {
                }
            });
        }

REstController.java :
 @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable Long userId) {

        logger.info("Deleting user");
        User user = userDao.findUser(userId);
        userDao.delete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

it always call the error() method.
see : https://myshittycode.com/2013/11/12/backbone-model-destroy-always-call-error-callback-function/

SOLUTION :
REstController.java :
 @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable Long userId) {

        logger.info("Deleting user");
        User user = userDao.findUser(userId);
        userDao.delete(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


------------------ --------------------
Wednesday 7 September

PROBLEM: When launching server, the schema database is not created.
SOLUTION:
added
<prop key="hibernate.hbm2ddl.auto">create-drop</prop>
in spring-config.xml
------------------ --------------------

Differences between hibernate.cfg.xml and persistence.xml ?

If you are using Hibernate's proprietary API, you'll need the hibernate.cfg.xml.
If you are using JPA i.e. Hibernate EntityManager, you'll need the persistence.xml.

------------------ --------------------
Monday 5 September

PROBLEM: when calling save() method from backbone client, even though the response
 is 200, the error() method is invoked when the request returns to the client.

SOLUTION:
https://myshittycode.com/2013/11/12/backbone-model-destroy-always-call-error-callback-function/
Reason : The reason is because Backbone expects a JSON payload from the web
service call. In the example above, we return a 200 OK but without a JSON
payload. Thus, Backbone will treat this as an error and invoke the error
callback function instead of success callback function regardless of the
HTTP status.
BEFORE:
userModel.save({}, {
                success: function (model, response, options) {
                    console.log('User saved to server (id:' + model.get('id'));
                },
                error: function (model, response, options) {
                    console.log('Error saving user (status:' + response.status);
                }
            });

AFTER:
userModel.save({}, {
                contentType : 'application/json',
                dataType : 'text',
                success: function (model, response, options) {
                    console.log('User saved to server (id:' + model.get('id'));
                },
                error: function (model, response, options) {
                    console.log('Error saving user (status:' + response.status);
                    console.log( 'id:' + model.get('id') );
                }
});
With this approach, we accept the data as text from the server.

------------------ --------------------
Monday 5 September
PROBLEM: when saving with rest a User bean on the js client,
the callback functions error() and success were not called:
SOLUTION:
BEFORE:
userModel.save({
                success: function (e) {
                    console.log('User saved to server');
                },
                error: function (e) {
                    console.log('Error saving user');
                }
            });
AFTER:
// ------------|------------
userModel.save({}, {
                success: function (e) {
                    console.log('User saved to server');
                },
                error: function (e) {
                    console.log('Error saving user');
                }
            });

------------------ --------------------
Saturday 3 September
PROBLEM: BackBone. the View is not updated after the The model is updated with a list of Users
SOLUTION: The model is updated after the the call to the fetch() method has returned results
asynchronously. The 'change' event is not trigerred when the callback method (defined in fetch())
is called. This is the 'reset' method which is called (on condition that the fetch method is called
like this :
 usersModel.fetch({
       ...
        reset: true
    });
    So, to catch the event in the View, I have to add in the View.initialize() method :
 initialize: function() {
           ...
            this.model.on("reset", this.render, this);// to update the view after the fetch has completed
            this.model.on("add", this.render, this);// to pudate the view after a user has been added
 }

------------------ --------------------
Friday 2 September
Installed Backbone using the site :
https://spring.io/guides/gs/consuming-rest-backbone/

------------------ --------------------
Sat 27 August
org.hibernate.MappingException: Could not determine type for:
public class HSList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    ...
}
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="user")
    private List<HSList> hsLists = new ArrayList();
    ...
}

SOLUTION:
The only solution I found was to add : "implements Serializable".
 It should work without "implements Serializable" though. However,
 I tried a lot of things without any success. It is perhaps created by spring-MVC.

public class HSList implements Serializable {
    ...
}
public class User implements Serializable {
    ...
}

"implements Serializable" corrige l'erreur. Tried a lot of things without any success. It is perhaps created to spring-MVC.


------------------ --------------------
Fri 26 August
PROBLEM: org.h2.jdbc.JdbcSQLException: NULL not allowed for column "ID"; SQL statement:
insert into USER (id, age, city,
SOLUTION:

------------------ --------------------
Thu 25 August
PROBLEM: the schema is not created by Hibernate. Hibernate does not seem to start
& the hibernate.cfg.xml file is not used.
SOLUTION: the persistence.xml file must be located in webapp/META-INF AND the client
that calls the Persistence.createEntityManagerFactory() must be launched so that the
the aforementioned method be called.