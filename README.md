# WikipediaHarvester
###### For saving wikipedia articles as plain text.
```
Current Version: 1.0.0.3
```
### Customize language:
There are only a few things that need to be changed so that it will work with your prefered language:
```
WikipediaHarvester.java >>> String wikistring = ww.reline(ww.clean(ww.getstring(line, "de")));
    Replace "de" with your preference -> en.wikipedia.org -> "en";

NumberToText.java >>> numerals
    Replace the corresponding numerals with those of the selected language. Carefull, it may not work for every language.
```

### Usage:
```
1. Build jar & execute
2. The program should download a file containing all titles of the wikipedia articles (~35MB/ ~ 2.500.000 titles)
   (Only if no input.txt exist. You can create one with your own selection)
2b. Those titles get extracted to input.ini
3. The program should get the content of every wikipedia article, remove everything besides the content, convert numbers to text, reformating the text and save it to a file.
   This may take a while (~94h)
4. Done
```