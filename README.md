# XmlMergingToolForSims4
it is a tool for merging old translation into new xml

使用方法:
1.放置三個文件夾於本程式根目錄, 分別為

	a.專門放置最新的mod文字檔案的資料夾(原文語言)
	b.專門放置舊的mod文字檔案的資料夾(原文語言,必與a相同語言)
	c.專門放置舊的mod已翻譯文字檔案的資料夾(檔案名稱﹑父資料夾結構和數量必與b相同)。*
2.建立folderCategory.txt, 並於該檔案中順序(以換行鍵("Enter")分隔)輸入1a,1b,1c的文件夾於本程式根目錄的相對路徑。
如用戶希望以寬鬆模式檢查檔案，可於此檔案第4行輸入true或1,以啟動寬鬆模式,預設為寬鬆模式。
如用戶希望以嚴格模式檢查檔案，可於此檔案第4行輸入false或0,以啟動嚴格模式。
3.開啟程式, 在本程式根目錄下會自動建立converted文件夾並產生已合併的文件。 只有曾被翻譯過的檔案會被建立。

原理:
寬鬆模式:
比較新的xml檔案和舊的xml檔案中只要textString是相同的文本。
嚴格模式:
比較新的xml檔案和舊的xml檔案中有沒有instanceID相同而且textString也是相同的文本。

如有,不論是寬鬆模式或嚴格模式。程式都會於已翻譯文件夾的檔案中尋找與舊xml相同名稱的檔案來獲取已翻譯文字。

注意事項:
1.請勿於程式完成前強制關閉程式。
2.於使用本程式前,請對3個資料夾進行備份,以防發生錯誤時影響原文件。
3.文件夾中可放置其他格式的文件,本程式只會對.xml和文件夾有動作。
4.文字檔案必需是.xml,並是sims4 mod的STBL資料格式。
如:
<?xml version="1.0" encoding="utf-8"?>
<StblData>
  <TextStringDefinitions>
    <TextStringDefinition InstanceID="0x11122233" TextString="Hi" />
  </TextStringDefinitions>
</StblData>
5.有關1a,1b和1c的詳細解釋和例子:
-XmlMergingToolForSims4<--(根目錄)
  -folderCategory.txt
  -XMT.exe
  -mod(new)<--1a
      -food System
         -noodle.xml
         -ramen.xml
      -sweet drinks System
         -soda & tea.xml
      -music System
         -popular.xml
  -mod(old)<--1b
      -food System
         -noodle.xml
      -drink System
         -soda.xml
         -tea.xml
(已翻譯mod文件夾的結構必定要跟舊文件夾相同)
  -mod(translated)<--1c
      -food System
         -noodle.xml
      -drink System
         -soda.xml
         -tea.xml
基本上譯者如果基於舊的mod原文檔案來翻譯的,照理1b和1c的檔案名稱﹑父資料夾結構和數量會完全一樣。


免責聲明:
本人對一切使用或下載本程式或任何關連所造成的損失概不負責，並且不對本程式功能作出任何種類的保證﹑陳述或協議，請用家自行承擔其風險。
並請用家自行判斷所下載之途徑(如從網頁﹑電郵)的安全性和可靠度，本人同樣對此造成的損失概不負責。

Disclaimer:
I am not responsible for any loss caused by using or downloading this program or any related, and I do not make any kind of guarantee, statement or agreement for the function of this program, please use it at your own risk.
Users are also required to validate the security and reliability of the downloading source (such as from web pages and emails), and I am also not responsible for the losses caused by it.

Guidelines:
1.Placing 3 folders under root directory of this program:
	a.Folder of the newest mod text file(original language)
	b.Folder of the old mod text file(orginal language, it must be in the same language with a.)
	c.Folder of the old and translated mod text(File name, folder structure and quantity must be the same as b.)*
2.Create folderCategory.txt, and input relative path of 1a, 1b and 1c in order in root directory of the program.
If user wish to run the program in loose mode , they can input "true"  or "1" (without "") in line 4 of folderCategory.txt,the default mode is loose mode.
If user wish to run the program in strict mode, they can input "false" or "0" (without "") in line 4 of folderCategory.txt.
3.Run program, it will create folder calls "converted" under root directory of this program. There are some merged xml files, which is merging old translation into new text file. Only files that contains translated string rows will be created in "converted".

Principles:
Loose mode:
Compare the new xml file with the old xml file as long as textString is the same text.
Strict mode:
Compare if the new xml file and the old xml file have the same instance ID and the same text string.

The program will look for files with the same name as the old xml in the translated folder to obtain the translated text.

Precautions:
1.Please do not force close the program before the program is completed.
2.Before running this program, please make a backup for 3 folders to prevent the original files from being affected when an error occurs.
3.Files in other formats is ok to be placed in the folder. This program will only act on .xml and folders.
4.Extension of text file must be .xml that stores string data in STBL format for Sims4 mod.
e.g.
<?xml version="1.0" encoding="utf-8"?>
<StblData>
  <TextStringDefinitions>
    <TextStringDefinition InstanceID="0x11122233" TextString="Hi" />
  </TextStringDefinitions>
</StblData>
5.Explanation and example for Usage 1.

-XmlMergingToolForSims4<--(root directory)
  -folderCategory.txt
  -XML.exe
  -mod(new)<--1a
      -food System
         -noodle.xml
         -ramen.xml
      -sweet drinks System
         -soda & tea.xml
      -music System
         -popular.xml
  -mod(old)<--1b
      -food System
         -noodle.xml
      -drink System
         -soda.xml
         -tea.xml
(Structure of translated mod folder must be the same with old mod folder--mod(old))
  -mod(translated)<--1c
      -food System
         -noodle.xml
      -drink System
         -soda.xml
         -tea.xml
Basically, if the translator is based on the original mod file(old mod folder), the file names, folder structure and quantity of 1b and 1c should be the same.

免責聲明:
本人對一切使用或下載本程式或任何關連所造成的損失概不負責，並且不對本程式功能作出任何種類的保證﹑陳述或協議，請用家自行承擔其風險。
並請用家自行判斷所下載之途徑(如從網頁﹑電郵)的安全性和可靠度，本人同樣對此造成的損失概不負責。

Disclaimer:
I am not responsible for any loss caused by using or downloading this program or any related, and I do not make any kind of guarantee, statement or agreement for the function of this program, please use it at your own risk.
Users are also required to validate the security and reliability of the downloading source (such as from web pages and emails), and I am also not responsible for the losses caused by it.

TeaBay--Copyright @ 2020

