
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/*
change folderCategory.txt with new folder, old folder and translated old folder
for sims4 mod text update
search any text w/ the same instanceID and English text string for each new text file in all old text file
text file must be .xml
*/

public class Testing {
    final static String se =  File.separator;
    long lasting = System.currentTimeMillis();
    static boolean isLoose = true;
    static String pathPrefix = "";
    static String NewestFolder = "";//newest mod folder prefix
    static int NewestGRPCount = 0;
    static String[] NewestGRP = new String[100000];//all newest mod xml files, it must be in the same dir w/ this proj, but under newest mod folder
    
    static String OldFolder = "";//old untranslated mod folder prefix
    static String translatedFolder = "";//old translated mod folder prefix
    static int OldGRPCount = 0;
    static String[] OldGRP = new String[100000];//all old untranslated mod xml files, it must be in the same dir w/ this proj, but it is under old mode folder n there must be the same file in translated folder
    
    //final static String[] OldGRP = {};
    public static void main(String[] args){
        try{
        Path currentRelativePath = Paths.get("");
        String currentPath = currentRelativePath.toAbsolutePath().toString();
        pathPrefix = currentPath+se;
        set3Folders();
        setGRPs();
        int count = 0;
        //[x][0] = translated row instanceID, [x][1] = translated row instanceID,[x][2] = original row instanceID
        //if it is in lenient mode, [x][0] n [x][2] will be different
        String[][] update = new String[1000000][3];
        boolean needUpdate = false;
        int updateSize = 0;
        for(int i=0;i<NewestGRPCount;i++){
          updateSize = 0;
          needUpdate = false;
          File f = new File(pathPrefix+NewestFolder+NewestGRP[i]);//"SOL Drama System\\KS - SOL Drama System.xml"
          //File fo = new File(pathPrefix+"SOL\\SOL Drama System\\KS - SOL Drama System.xml");
          System.out.println("Current New File is "+NewestGRP[i]);
          SAXReader reader = new SAXReader();
          Document doc = reader.read(f);
          Element root = doc.getRootElement();
          for (Iterator<Element> its = root.elementIterator(); its.hasNext();) {
                Element definiGrp = its.next();
                for (Iterator<Element> it = definiGrp.elementIterator("TextStringDefinition"); it.hasNext();) {
                    Element element = it.next();
                    Attribute instanceID = element.attribute("InstanceID");
                    Attribute attribute = element.attribute("TextString");
                    
                    for(int n=0;n<OldGRPCount;n++){
                        
                        File fO = new File(pathPrefix+OldFolder+OldGRP[n]);//same file w/ fTr, but not in the same folder
                        
                        SAXReader readerO = new SAXReader();
                        Document docO = readerO.read(fO);
                        Element rootO = docO.getRootElement();
                        //run old xml
                        for (Iterator<Element> itOs = rootO.elementIterator(); itOs.hasNext();) {
                            Element definiOGrp = itOs.next();
                            for (Iterator<Element> itO = definiOGrp.elementIterator("TextStringDefinition"); itO.hasNext();) {
                                Element elementO = itO.next();
                                Attribute instanceIDO = elementO.attribute("InstanceID");
                                Attribute attributeO = elementO.attribute("TextString");
                                // if id and attribute text are the same in strict mode
                                boolean idCompare ;
                                if(isLoose){
                                    idCompare = true;
                                }else{
                                    idCompare = instanceID.getValue().equals(instanceIDO.getValue());
                                }
                                if(idCompare  &&attribute.getValue().equals(attributeO.getValue())){
                                    //run translated xml
                                    File fTr = new File(pathPrefix+translatedFolder+OldGRP[n]);
                                    SAXReader readerTr = new SAXReader();
                                    Document docTr = readerTr.read(fTr);
                                    Element rootTr = docTr.getRootElement();
                                    for (Iterator<Element> itTrs = rootTr.elementIterator(); itTrs.hasNext();) {
                                        Element definiTrGrp = itTrs.next();
                                        for (Iterator<Element> itTr = definiTrGrp.elementIterator("TextStringDefinition"); itTr.hasNext();) {
                                            Element elementTr = itTr.next();
                                            Attribute instanceIDTr = elementTr.attribute("InstanceID");
                                            Attribute attributeTr = elementTr.attribute("TextString");
                                            if(instanceIDO.getValue().equals(instanceIDTr.getValue())){
                                                needUpdate = true;
                                                update[updateSize][0] = instanceIDTr.getValue();
                                                update[updateSize][1] = attributeTr.getValue();
                                                update[updateSize][2] = instanceID.getValue();
                                                System.out.println(""+instanceID.getValue()+" To " + instanceIDTr.getValue()+"");
                                                System.out.println("'"+attribute.getValue()+"' To '" + attributeTr.getValue()+"'");
                                                System.out.println(update[updateSize][0]+" and " + update[updateSize][1]);
                                                updateSize++;
                                            }
                                        }

                                    }               
                                    count++;
                                    continue;
                                }
                            }
                        }
                    }
                }
                if(needUpdate){
                    needUpdate = false;
                    updateXML(update,updateSize,f,NewestGRP[i]);
                    update = new String[1000000][3];
                }
          }
        }
           System.out.println("Total "+count+" is translated");
           showMessageDialog(null, "Completed. There are "+count+" rows merged into files.");
        } catch (NullPointerException e) {
          e.printStackTrace();
           showMessageDialog(null, 
                   "Your job does not finished correctly. Please check if 3 folder paths in folderCategory.txt is correct.\n"
                           + "你的工作未能正常完成,請檢查folderCategory.txt中的3個資料夾的相對路徑是否正確。"
                   ,"Error Occurs",JOptionPane.ERROR_MESSAGE);
        }catch (Exception e) {
          e.printStackTrace();
           showMessageDialog(null, "Your job does not finished correctly. "+e.toString(),"Error Occurs",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void updateXML(String[][] list,int size,File f,String fileName)throws Exception{
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        //createXML
        Document document = DocumentHelper.createDocument();
        Element base = document.addElement("StblData");
        Element TextStringDefinitions = base.addElement("TextStringDefinitions");
        String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +"<StblData>\n" +
"  <TextStringDefinitions>";
        for (Iterator<Element> its = root.elementIterator(); its.hasNext();) {
            Element definiGrp = its.next();
            xmlStr += "<TextStringDefinition ";
            for (Iterator<Element> it = definiGrp.elementIterator("TextStringDefinition"); it.hasNext();) {
                Element element = it.next();
                Attribute instanceID = element.attribute("InstanceID");
                Attribute attribute = element.attribute("TextString");
                boolean found = false;
                for(int i =0;i<size;i++){
                    if(list[i][1]!=null){
                    }
                    if(instanceID.getValue().equals(list[i][2])){
                        xmlStr += "InstanceID=\""+list[i][2]+"\" TextString=\""+list[i][1]+"\"";
                        TextStringDefinitions = createElement(TextStringDefinitions,list[i][2],list[i][1]);
                        found = true;
                        break;
                    }else{
                    }
                }
                if(!found){
                    xmlStr += "InstanceID=\""+instanceID.getValue()+"\" TextString=\""+attribute.getValue()+"\"";
                    TextStringDefinitions = createElement(TextStringDefinitions,instanceID.getValue(),attribute.getValue());
                }
                xmlStr += " />";
            }
            xmlStr += " />";
        }
        xmlStr += "</TextStringDefinitions>\n" +
"</StblData>";
        //Document document = DocumentHelper.parseText(xmlStr);
        Path currentRelativePath = Paths.get("");
        String currentPath = currentRelativePath.toAbsolutePath().toString();
        String tempPath = currentPath+se+"converted"+fileName;
        File file = new File(tempPath);
        //file.getParentFile().getParentFile().mkdirs();
        file.getParentFile().mkdirs();
        file.createNewFile();
        //FileWriter out = new FileWriter(file);//FileWriter is problematic
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        //XMLWriter writer = new XMLWriter(new FileOutputStream(file));
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
        //document.write(out);
        //out.close();
        writer.write(document);
        writer.close();
    }
    public static Element createElement(Element TextStringDefinitions,String ID,String str){
        Element  temp = TextStringDefinitions.addElement("TextStringDefinition")
                .addAttribute("InstanceID", ID)
                .addAttribute("TextString", str);
        return TextStringDefinitions;
    }
    public static void set3Folders() throws Exception {
            String name = "folderCategory.txt";
            File filename = new File(name); // 要讀取以上路徑的input。txt檔案
            InputStreamReader reader = new InputStreamReader( new FileInputStream(filename)); // 建立一個輸入流物件reader
            BufferedReader br = new BufferedReader(reader); // 建立一個物件，它把檔案內容轉成計算機能讀懂的語言
            String line = "";
            String[] folderCat = new String[4];
            int folderSize = 0;
            line = br.readLine();
            while (line != null) {
                folderCat[folderSize] = line;
                folderSize++;
                line = br.readLine(); // 一次讀入一行資料
            }
            NewestFolder = folderCat[0];
            OldFolder = folderCat[1];
            translatedFolder = folderCat[2];
            if(folderCat[3]!=null){
                isLoose = (folderCat[3].equalsIgnoreCase("0")||folderCat[3].equalsIgnoreCase("false")?false:true);
            }
    }
    public static void setGRPs()throws Exception{
            File f = new File(pathPrefix+NewestFolder);
            setGRP(f,"",1);
            f = new File(pathPrefix+OldFolder);
            setGRP(f,"",2);
            f = new File(pathPrefix+translatedFolder);
            setGRP(f,"",3);
    }
    public static void setGRP(File f,String parent,int type) throws Exception{
            File[] files = f.listFiles();

            // Get the names of the files by using the .getName() method
            for (int i = 0; i < files.length; i++) {
                if(files[i].isDirectory()){
                    setGRP(files[i],parent+se+files[i].getName(),type);
                }else if(files[i].getName().endsWith(".xml")){
                    switch(type){
                        case 1:
                            NewestGRP[NewestGRPCount] = parent+se+files[i].getName();
                            NewestGRPCount++;
                            break;
                        case 2:
                            OldGRP[OldGRPCount] = parent+se+files[i].getName();
                            OldGRPCount++;
                            break;
                        case 3:
                            break;
                        default:
                    }
                }
            }
    }
}
