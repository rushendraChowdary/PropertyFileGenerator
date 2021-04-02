# PropertyFileGenerator
This code repo contains Java Files which helps in generating property files in key value pairs for different languages

The idea is to take two files with names test.properties (for now) & test.txt and split the files using two delimiters.
test.properties file will be splitted using "=" & test.txt will be splitted using "|" and add both of these to two different maps.
Then call the method which process these maps further, for every value check if there is a value present in the key-value map.
If there is a value then take the key from test.property file & combine it with the localized value of test.txt file.
If it cannot find a value then it will simply add this to a notFoundList.
Please go through the code & let me know if i can optimize this better.
Please review the code & raise issues if i can do better.

I wrote this tool because i was exhausted doing internationalization for SAP CX (Hybris) project manually.
I am not saying this will take care of everything but this will do the job better.

# Example
* user has an option to enter 1 or 2 based on his/her need.
* if user enters ```1``` he / she needs to supply a property file in order to extract ```values``` from the file.
* if user enters ```2``` then he / she needs to supply two files which are shown below.  

<br />test.properties

```
test.text.lang = Hello
```
test.txt

```
Hello|Bonjour
```
Notice that  ```Bonjour``` means ```Hello``` in french.

### Final output will look something like below
```
test.text.lang=Bonjour
```

# Set up

* step 1
  <br/> ```git clone https://github.com/rushendraChowdary/PropertyFileGenerator.git```
  clone the repository or download the zip file from github

* step 2
    <br /> Create the files with names mentioned(see Line no : 18, 19) in the ```PropertyFileGenerator.java``` or create new files with name of your choice and give the names in line number 18 & 19.
    <br /> Follow the example to create new files for best results.
  
* Remember to use ```=``` for properties file & ```|```  for localized values.

*  finally run the main method, if everything goes well you will see a file with name ```file.properties``` under ```src``` folder with localized values in it.



Feel free to email me at ```rushendra514@gmail.com``` , if you have any ideas to make this tool better.

```Hack The Planet ``` :v:
