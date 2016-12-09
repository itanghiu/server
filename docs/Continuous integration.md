INSTALLING JENKINS

1.download jenkins-2.35.pkg at :
https://jenkins.io/content/thank-you-downloading-os-x-installer/
2.Go to /Users:Shared/Jenkins/Home/config.xml and set :
  ...
  <disableSignup>false</disableSignup>
  ...
3.restart jenkins :
  >sudo launchctl unload /Library/LaunchDaemons/org.jenkins-ci.plist
  >sudo  launchctl load /Library/LaunchDaemons/org.jenkins-ci.plist
  
4.go to http://localhost:8080 and click on "signin" icon and create an account.

RUNNING **JENKINS**

>java -jar /Applications/jenkins.jar

To have launchd start jenkins now and restart at login:
  >brew services start jenkins
  
if you don't want/need a background service you can just run:
  >jenkins
  
Jenkins initial setup is required. An admin user has been created and a password generated.
Please use the following password to proceed to installation:
  681979226f8246b4a130a467afc87a0f
  
This may also be found at: /Users/i-tang/.jenkins/secrets/initialAdminPassword
  
java -jar jenkins.war --httpPort=8081 --ajp13Port=-1