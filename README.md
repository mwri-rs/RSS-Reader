# RSS Reader Project
# Overview:
This project is an RSS Reader written in  java .RSS or Really Simple Syndication, encompasses XML files that are continuously updated by computers. Using our program, RSS Reader, we can extract and receive the latest information from websites that we want in a readable format. This information includes headlines, summaries, links, and relevant content from the websites.
# Note 1:
Most websites have RSS, but please note that internet access is required to run this program.
# General Structure of the Program:
In this program, the user stores a list of their favorite website addresses in a file named data.txt, and any changes made by the user to this list are saved at the end of the program execution. The information stored in our file includes the website's name, website address, and its RSS feed address, separated by a semicolon.
# Note 2:
If a website title contains a semicolon, we may encounter issues. Therefore, our default assumption is that the websites we work with do not have semicolons in their titles.
Meanwhile, the program provides user 3 options which are explained briefly below:
# Options:
# 1) Add URL:
This option allows the user to add desired websites. for using this option the user must enter the number 2 in the terminal (following the predetermined order of the program). Upon entering this number, a message "Please enter website URL to add:" will be displayed where the user can enter the website address.
After entering the website address, two outcomes are possible:
1. If the entered website address did not exist in the file previously, it will be easily added to the file.
2. If the website address already existed in the file, the user will receive a message indicating that the website address already exists in the file.

# 2) Show Updates:
If the user wants to view recent content of a specific website, they need to enter the number 1 in the terminal following the predetermined order of the program. Upon entering this number, a message in the format "Show updates for:" followed by the names of all websites added by the user (not removed) will be displayed. The user can enter the number of the desired website in the terminal to access recent content along with a summary and links. (Entering the number 0, as indicated by All websites, will display recent content, summaries, and links to all websites).
If the number -1 is entered in the terminal, the program will return to the guide including the same 3 options.

# 3) Remove URL:
To remove a website from the list, the user should use this option. By entering the number 3 in the terminal, the user will receive a message from the program stating "Please enter website URL to remove:". The user must then enter the URL of the website they wish to remove. Two outcomes are possible:
1. If the website address existed in the file, it will be successfully removed, and the program will display a message saying "Removed + website address + successfully".
2. If the website address did not previously exist in the file, the user will receive the message "Couldn’t find + website address".
