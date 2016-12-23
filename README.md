# Tool_Latex_Glossary
Tool for LaTeX


1.1 PROBLEM STATEMENT

  The process of marking up a word for glossary in LaTeX is a routine and deadly dull, yet beyond capabilities of any text editor or even Search and Replace operation. There is no tool to help the authors with the time-consuming task of marking up of the word for glossary. Currently the author has to go through the whole document and manually markup every single occurrences of the word, an advance user might use a combination of Find & Replace option with regular expression to get the exact word, but they have to iterate through the whole document many times to fully markup every instance of that particular word. 
 For example, assume the word to be “object”, it could be available in the document as “object”, “Object”. “objects”, “Objects” etc.  The authors spend a lot of time adding or updating the markups, especially, if the word is a plural or symbol, once this task is done, the author has to check in the glossary file if the correct markup exists, otherwise the compiler would give an error. 

 The current system which is any text-editor does not have any features to do this easily. The current system which is manually adding the tags on a text-editor lacks a good interface and necessary check to ensure that the correct markups are added.  The application will try to solve all these problems faced by the authors with the help of an easy to use interface and verifies that the correct tags are added.  


1.2 PROJECT OBJECTIVE

The main aim of this project is to create an easy to use interface tool to help the users with the time-consuming process of adding the glossary tag to their latex document.
 The users should be able to select a word from the document and glossarise the word easily using a click of a button or a mouse click. It should also detect whether the selected word is a singular, plural, uppercase, lowercase or symbol and add the right tag to it. The application should also have the options to glossarise every single instance of that word. This should be done intelligently as some words for example “object” and “object-oriented” are to be treated differently. Once the tagging is done it should give the user to save the document and glossary file separately.
  Even though the targeted audience are Researchers and engineering students, the tool should aim to provide an easy to use interface which any one can use. It should be platform independent and lightweight. 
