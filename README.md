![LOGO](https://raw.githubusercontent.com/timostrating/parkingsimulator/master/blender_files/logo/logo_small.png)

This is an AWESOME parkingsimulator inspired by Rollercoaster Tycoon 2.
<br><br>
![gif](https://i.imgur.com/NNS8PzY.png)

## Dependencies
* Gradle
* Java 8
* JRE: 1.8+ 64bit
* JVM: OpenJDK 64-Bit Server VM by JetBrains

## Installation

gradle.properties
```gradle.properties
org.gradle.java.home=__URL_TO_YOUR_JAVA_HOME__
```

Replace this with the url of your jdk installation. For example: if you have jdk version 1.8.0_73 installed on Windows.
WARNING: Java 9 not supported!

```gradle.properties
org.gradle.java.home=C:\\Program Files\\Java\\jdk1.8.0_73
```

Press ALT + SHIFT + F9 add select edit configuration. Now add the following gradle configuration to Intellij and hit apply.

![Intellij Gradle run](https://i.imgur.com/scTH9Jw.png)

Now we are going to refresh the dependencies. Go to View -> Tool Windows -> Gradle. And now click on the first icon with the 2 spinning arrows.

![Intellij Gradle run](https://i.imgur.com/gRYdssI.png)

Now hit SHIFT + F9 and enjoy.



