# Most Active Cookie
This is a Java program that parses a log file and returns the most active cookie for a specified day. After compiling, the program will take in input through the command line (file path and date) as shown below:

```
javac MostActiveCookie.java
java MostActiveCookie csv/cookie_log.csv -d 2018-12-09
```

The program will return the cookie that was seen in the logs the most times in the specified day via command line. For the above input, the program will return `AtY0laUfhglK3lC7`.

If multiple cookies meet the above criteria, all of those cookies will be returned. For example, `java MostActiveCookie csv/cookie_log.csv -d 2018-12-08`
will return:
```
SAZuXPGUrfbcn5UA
4sMM2LxV07bPJzwf
fbcn5UAVanZf6UtG
```
