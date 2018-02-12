![LOGO](https://raw.githubusercontent.com/timostrating/parkingsimulator/master/blender_files/logo/logo_small.png)

This is an AWESOME parkingsimulator inspired by Rollercoaster Tycoon 2.
![gif](https://i.imgur.com/NNS8PzY.png)

## Team
* [Hilko Janssen](https://github.com/hilkojj)
* [CodeCasper](https://github.com/codecasper)
* [Timo Strating](https://github.com/timostrating) 

## Dependencies
* Gradle
* Java 8
* JRE: 1.8+ 64bit
* JVM: OpenJDK 64-Bit Server VM by JetBrains

## Documentation
[Javadoc documentation can be found here](https://timostrating.github.io/parkingsimulator/)

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

Now go to the platform / tool that you would like to use.


## Intellij idea

Press ALT + SHIFT + F9 add select edit configuration. Now add the following gradle configuration to Intellij and hit apply.

![Intellij Gradle run](https://i.imgur.com/scTH9Jw.png)

Now we are going to refresh the dependencies. Go to View -> Tool Windows -> Gradle. And now click on the first icon with the 2 spinning arrows.

![Intellij Gradle run](https://i.imgur.com/gRYdssI.png)

Now hit SHIFT + F9 and enjoy.


## Windows Powershell

Open powershell as an administrator. And run this command below to install chocolatey.

```
Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
```

Now we are going to install gradle by using the chocolatey package manager.
```
choco install gradle
```

With gradle we can run the application.
```
gradle desktop:run
```


## Linux Manjaro (arch)
Java should be installed. This is the default in manjaro.

On linux your java.home should look like something like this.
```gradle.properties
org.gradle.java.home=/usr/lib/jvm/java-8-openjdk/jre
```

Next up we can install gradle. Replace yaourt in case you run a diverend os / package manager.
``` 
yaourt -S gradle
```

With gradle we can run the application.
```
gradle desktop:run
```


## Linux Manjaro (arch)
On MAC OS your java.home should look like something like this.
```gradle.properties
org.gradle.java.home=/usr/lib/jvm/java-8-openjdk/jre
```

We are going to install gradle using brew.
``` 
brew install gradle
```

With gradle we can run the application.
```
gradle desktop:run
```
