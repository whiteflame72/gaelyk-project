You need Groovy support in Eclipse!

Setup
A two steps process. First run
Run http://localhost:8888/createUsers.groovy
and then remove admin and user1 from ShiroUsers entity via datastore viewer.
Lastly, run http://localhost:8888/createUsers.groovy again and you will see the settings (in SysPrefs entity) for 
your customization specific to your site (i.e. change the properties accordingly via the datastore viewer) like -
1. Change all replace_me values to your own values
2. Remove any duplicate SysPrefs entity (there should be only one at any given time)

Sample Settings -

	ID/Name	 app1	 appuri1	 email1	 favicon1	 googleanalytics1	 headerimg1	 headerimg2	 password1	 phone1	 recaptchaprivatekey1	 recaptchapublickey1	 title1
	id=8001	 NLM Service	 /videos	 admin@gmail.com	 http://www.favicon.cc/?action=download_copy&file_id=224194	 UA-29427604-1	 http://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/US-NationalLibraryOfMedicine-Logo.svg/140px-US-NationalLibraryOfMedicine-Logo.svg.png	 http://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Entrance_to_the_National_Library_of_Medicine%2C_October_9%2C_2008.jpg/220px-Entrance_to_the_National_Library_of_Medicine%2C_October_9%2C_2008.jpg	 f????o???34	 7034375049	 6LcrcM4SAAAAAP4w4EtX0oUp-JVYwLPqOZ5vES8U	 6LcrcM4SAAAAAA32Eafd7PgjrlV0gD_elGli5eD0	 NLM

For Google Analytics account, please visit http://www.google.com/recaptcha to apply for the unique ID related to your app's domain.

Good Gaelyk Introduction
http://www.slideshare.net/glaforge/gaelyk-paris-ggug-2011-guillaume-laforge

Gaelyk Template
http://globalgateway.wordpress.com/2010/10/21/simple-crud-with-gaelyk/

CRUD
August 30th, 2011
New Gaelyk plugin: Easy Datastore Service Plugin â€” CRUD for Gaelyk

VladimÃ­r OranÃ½ has released a new plugin for Gaelyk â€” the Easy Datastore Service (EasyDS) Plugin, which â€œsimplifies basic CRUD operation with entities and also provides some closure based valiation.â€� This is as far as I know the first attempt at adding such simplified, Grails-style CRUD to Gaelyk (and the plugin is in fact inspired by GORM). The source is up at GitHub, and there are code examples and explanations in the README.

https://github.com/musketyr/gaelyk-easyds-plugin/blob/master/README.md#readme
http://blog.groovymag.com/page/2/


Gaelyk Scaffolding
http://www.objectpartners.com/2010/06/29/usage-patterns-for-gaelyk/

To install it -

1. Install https://github.com/bmuschko/gradle-gaelyk-plugin first by adding the following line into build.gradle's dependencies attribute:
        classpath 'bmuschko:gradle-gaelyk-plugin:0.3'

2. gradle gaelykInstallPlugin -Pplugin=scaffolding

3. Download http://cloud.github.com/downloads/bmuschko/gradle-gaelyk-plugin/gradle-gaelyk-plugin-0.3.jar and put it into war/WEB-INF/lib

Issues

1. http://code.google.com/p/googleappengine/issues/detail?id=6928

-Dappengine.user.timezone=UTC

2. java.lang.RuntimeException: java.io.NotSerializableException

You need to make sure the class implements Serializable interface!
Due to the same, to make CSRFGuard works properly in GAEJ SDK 1.7 properly, CSRFGuard has been changed
to implement Serializable interface (source pulled from https://github.com/esheri3/OWASP-CSRFGuard).

3. "groovy.lang.MissingPropertyException: No such property: title1 for class: SimpleTemplateScript7"

Your setup is not proper. There is some servlet that you need to invoke to set properties like title1 etc.

Database Setup

. Create the sbr database e.g.
/usr/local/mysql/bin/mysqladmin -uroot -proot create sbr

. Run the two scripts
create-tables.sql
create-test-tables.sql

. Create a test user (case-sensitive!!!) in the database e.g.
/usr/local/mysql/bin/mysql -uroot
CREATE USER 'jen1'@'localhost' IDENTIFIED BY 'jen1';

Optional

SET PASSWORD FOR root@localhost = PASSWORD('newpassword');

References

https://blogs.oracle.com/randystuph/entry/injecting_jndi_datasources_for_junit