# PropertyFileGenerator
This code repo contains Java Files which helps in generating property files in key value pairs for different languages

The idea is to take two files with names test.properties (for now) & test.txt and split the files using two delimiters.
test.properties file will be splitted using "=" & test.txt will be splitted using "|" and add both of these to two different maps.
Then call the method which process these maps further, for every value check if there is a value present in the key-value map.
If there is a value then take the key from test.property file & combine it with the localized value of test.txt file.
Please go through the code & let me know if i can optimize this better.
Please review the code & raise issues if i can do better.

I wrote this tool because i was exhausted doing internationalization for SAP CX (Hybris) project manually.
Feel free to email me at ```rushendra514@gmail.com``` , if i can or you can make this better.

# Example
test.properties

```
test.text.lang = Hello
```
test.txt

```
Hello|Bonjour
```
Notice that  ```Bonjour``` means ```Hello``` in french.
