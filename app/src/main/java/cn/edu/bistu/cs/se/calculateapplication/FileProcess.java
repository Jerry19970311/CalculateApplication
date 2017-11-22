package cn.edu.bistu.cs.se.calculateapplication;//import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;
import java.util.Stack;

/**
 * Created by XZL on 2017/2/23.
 */
public class FileProcess {
    public static String readToString(String filePath, String encoding){
        File file=new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long fileLength=file.length();
        byte[] fileContent=new byte[Long.valueOf(fileLength).intValue()];
        try{
            FileInputStream in=new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(fileContent,encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.err.println("本系统不支持文件格式"+encoding);
            return null;
        }
    }
    public static ArrayList readToString2(String filePath) {
        FileReader fr= null;
        try {
            fr = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        ArrayList<String> s=new ArrayList<String>();
        String st = null;
        do{
            try {
                st=br.readLine();
                if(st!=null)
                    s.add(st);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (st!=null);
        return s;
    }
    public static void writeStringContentToFile(String filePath,String fileContent){
        /*File f=new File(filePath);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        FileWriter fw;
        try{
            fw=new FileWriter(filePath);
            fw.write(fileContent);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //另一种文件的读写方法，用到了BufferedReader类。
    public static ArrayList readFile2Array(String filePath,String st) throws IOException{
        ArrayList wordList=new ArrayList();
        File inputFile=new File(filePath);
        inputFile.createNewFile();
        FileReader fr=new FileReader(inputFile);
        BufferedReader br=new BufferedReader(fr);
        String s;
        while ((s=br.readLine())!=null){
            String[] words=s.split(st);
            for(int i=0;i<words.length;i++){
                if(!wordList.contains(words[i])){
                    wordList.add(words[i]);
                }
            }
        }
        fr.close();
        return wordList;
    }
    public static void writeWordList2File(ArrayList wordList,String filePath){
        int length=wordList.size();
        String str=readToString(filePath,"UTF-8");
        for(int i=0;i<length;i++){
            str=str+wordList.get(i)+"\n";
        }
        writeStringContentToFile(filePath,str);
    }
    //排序算法：将双词按照词频进行顺序排序，利用快速排序的非递归算法(详见quickSort2方法)。
    public static void sortHTable(ArrayList<String> wordList,Hashtable h){
        int length=wordList.size();
        int[] value=new int[length];
        for(int i=0;i<length;i++){
            value[i]=(int)h.get(wordList.get(i));
        }
        quickSort2(value,0,length-1,wordList);
    }

    //利用栈实现快速排序的非递归算法，其中，partition方法扔引用递归算法中的partition方法。
    private static void quickSort2(int[] list,int first,int last,ArrayList<String> wordlist){
        Stack<Integer> t=new Stack<Integer>();
        if(first<last){
            int p=partition(list,first,last,wordlist,0);
            if(p-1>first){
                t.push(first);
                t.push(p-1);
            }
            if(p+1<last){
                t.push(p+1);
                t.push(last);
            }
            while (!t.isEmpty()){
                int p1=t.pop();
                int p2=t.pop();
                p=partition(list,Math.min(p1,p2),Math.max(p1,p2),wordlist,0);
                if(p-1>Math.min(p1,p2)){
                    t.push(Math.min(p1,p2));
                    t.push(p-1);
                }
                if(p+1<Math.max(p1,p2)){
                    t.push(p+1);
                    t.push(Math.max(p1,p2));
                }
            }
        }
    }
    /*private static int partition2(short[] list,int first,int last,ArrayList<String> wordlist){
        //System.out.println("2     first="+first+"        last="+last);
        short pivot=list[first];
        String pivots=wordlist.get(first);
        int low=first+1;
        int high=last;
        while (high>low) {
            while (low <= high && list[low] <= pivot) {
                low++;
            }
            while (low <= high && list[high] >= pivot) {
                high--;
            }
            if (low < high) {
                short temp = list[high];
                list[high] = list[low];
                list[low] = temp;
                String temps = wordlist.get(high);
                wordlist.set(high, wordlist.get(low));
                wordlist.set(low, temps);
            }
        }
        while (high>first&&list[high]>=pivot){
            high--;
        }
        if(pivot>list[high]){
            list[first]=list[high];
            list[high]=pivot;
            wordlist.set(first,wordlist.get(high));
            wordlist.set(high,pivots);
            return high;
        }else {
            return first;
        }
    }*/

    //排序：利用快速排序法将词表中的词按照字典从小到大排序。
    //改进方面：可以解决当头字母相同的时候比较后一个字母的问题。
    public static void sortWordList(ArrayList<String> wordList){
        sortWordList(wordList,0,0,wordList.size());
    }
    //注：wordList待处理表、sort排序参考字符的位置、setJ待排序区间的头位置、length排序区间长度。
    private static void sortWordList(ArrayList<String> wordList,int sort,int setJ,int length){
        //以排序区间的长度定义一个存储参考字符的整形数组。
        //例如：第一次排序时，排序区间长度为整个表，如果排序后出现相同字符，则产生第二次排序，则此次所取的均为每个单词的第2个字母，排序区间长度为上一个字母相同的单词个数。
        int[] list=new int[length];
        boolean isThrow=false;
        //字符串长度大多数时候不一定相等，如果碰到上一个sort已到了较短字符串的最末位置，本次提取字符的时候会造成溢出，抛出StringIndexOutOfBoundsException异常。
        //处理方法：将该字符串与本区间的第一个字符串对调，同时将参考字符数组长度减1，然后把之前录入的字符重新录入。
        for(int i=0;i<length;i++){
            try {
                list[i] = wordList.get(i + setJ).charAt(sort);
            }catch (StringIndexOutOfBoundsException e){
                isThrow=true;
                String temp;
                temp=wordList.get(i+setJ);
                wordList.set(i+setJ,wordList.get(setJ));
                wordList.set(setJ,temp);
                int[] temps=list;
                length--;
                list=new int[length];
                for(int h=0;h<i;h++){
                    list[h]=wordList.get(h+setJ+1).charAt(sort);
                }
            }
        }
        //如果其中存在长度不足的字符串，则已通过异常处理调到最前面，因此无需参与下面的排序，须将头位置前移一位。
        if(isThrow){
            setJ++;
        }
        //进行快速排序，需要注意的是，如果有第2次排序或更多次排序，则数组和ArrayList中的待处理区间就会产生固定的差
        //（即上一次排序后相同字母的单词中，第一个单词的位置可能不为0），因此须引入setJ变量消除这一偏差。
        quickSort(list,0,list.length-1,wordList,setJ);
        //排序完毕后进行检查，是否出现了相同的字母。
        //变量l：用于存储相同字母的单词数量，初设值1。
        int l=1;
        //变量k：用于存储上一次排序的区间的最长位。
        int k=length+setJ;
        for(int j=setJ;j<k;){
            if((j+l<k)&&(wordList.get(j).charAt(sort)==wordList.get(j+l).charAt(sort))){
                l++;
            }else{
                if(l!=1){
                    sortWordList(wordList,sort+1,j,l);
                }
                j=j+l;
                l=1;
            }
        }
    }
    private static void quickSort(int[] list,int first,int last,ArrayList<String> wordlist,int setJ){
        if(last>first){
            int pivotIndex=partition(list,first,last,wordlist,setJ);
            //System.out.println(first+"              "+pivotIndex);
            quickSort(list,first,pivotIndex-1,wordlist,setJ);
            quickSort(list,pivotIndex+1,last,wordlist,setJ);
        }
    }
    private static int partition(int[] list,int first,int last,ArrayList<String> wordlist,int setJ){
        //System.out.println("2     first="+first+"        last="+last);
        int pivot=list[first];
        String pivots=wordlist.get(setJ+first);
        int low=first+1;
        int high=last;
        while (high>low) {
            while (low <= high && list[low] <= pivot) {
                low++;
            }
            while (low <= high && list[high] >= pivot) {
                high--;
            }
            if (low < high) {
                int temp = list[high];
                list[high] = list[low];
                list[low] = temp;
                String temps = wordlist.get(setJ+high);
                wordlist.set(high+setJ, wordlist.get(setJ+low));
                wordlist.set(low+setJ, temps);
            }
        }
        while (high>first&&list[high]>=pivot){
            high--;
        }
        if(pivot>list[high]){
            list[first]=list[high];
            list[high]=pivot;
            list[high]=pivot;
            wordlist.set(first+setJ,wordlist.get(high+setJ));
            wordlist.set(high+setJ,pivots);
            return high;
        }else {
            return first;
        }
    }
    //readFile2HashTable方法：利用Hashtable类统计词频。
    //Hashtable类是一种哈希表，表中的元素为键或值，可实现由键映射到对应的值的功能。
    //为了成功地在哈希表中存储和获取对象，用作键的对象必须实现Map接口中的equals方法以及重载Object类中的clone方法。
    //该类中的两个参数：initialCapacity（初始容量）和loadFactor（加载因子）
    //initialCapacity：表示哈希表的容量，默认值为11.
    //loadFactor：表示哈希表的加载因子，即限定键的数量占据哈希表容量的比值，如果超出这个比值，则自动调用rehash方法进行扩容，默认值为0.75。
    public static Hashtable readFile2HashTable(String filePath){
        //创建一个由字符串映射到整形变量的哈希表，设定初始容量2000和加载因子0.75。
        Hashtable<String,Integer> wordFreqHashTable=new Hashtable<String,Integer>(2000,0.75f);
        ArrayList a=new ArrayList();
        //把将要读取的文件以其路径创建一个File对象。
        File inputfile=new File(filePath);
        File[] farray=inputfile.listFiles();
        FileReader fr;
        for(int j=0;j<farray.length;j++) {
            try {
                //创建读取字符文件的便捷类，然后以此创建缓冲字符输入流。
                fr = new FileReader(farray[j]);
                BufferedReader br = new BufferedReader(fr);
                String s;
                try {
                    //循环，对每一行以空格进行分词，存入String数组。
                    while ((s = br.readLine()) != null) {
                        String[] words = s.split(" ");
                        //？循环，对数组内的每一个字符串先后与哈希表对照，如果哈希表已有该字符串，则对该串所映射的值加1，如果没有，则添加此键，并把映射值设为1。
                        for (int i = 0; i < words.length; i++) {
                            if (wordFreqHashTable.containsKey(words[i])) {
                                int freq = Integer.parseInt(wordFreqHashTable.get(words[i]).toString());
                                wordFreqHashTable.put(words[i], freq + 1);
                            } else {
                                wordFreqHashTable.put(words[i], 1);
                                a.add(words[i]);
                            }
                        }
                    }
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //返回此哈希表。
        }
        return wordFreqHashTable;
    }

    //获取指定路径中的所有文件内的双词。最后将双词及其词频以哈希表的形式返回。
    public static Hashtable getdoubleWord(String filePath){
        //利用ArrayList存放所有双词。
        ArrayList<String> wordlist=new ArrayList<String>();
        //利用Hashtable存放所有双词及其词频。
        Hashtable wordlists=new Hashtable();
        //文件类及其有关的类的作用，同上readFile2HashTable方法。
        File inputfile=new File(filePath);
        File[] farray=inputfile.listFiles();
        FileReader fr;
        //other变量初始值为空，可存放每个文件的最后一个单词，与下一个文件的第一个单词构成双词。
        String other=null;
        //读取所有文件。
        for(int j=0;j<farray.length;j++){
            try {
                fr = new FileReader(farray[j]);
                BufferedReader br = new BufferedReader(fr);
                String s;
                String[] str=null;
                try {
                    while ((s = br.readLine()) != null) {
                        //System.out.println(s);
                        str = s.split(" ");

                        //思路1：双词在经过分词后的字符串数组中的表现为位置相邻。
                        //思路2：每个词的词性标记都以反斜杠开头，因此可以通过反斜杠对该词的拆分，然后取前面的部分。
                        for (int k = 0; k < str.length; k++) {
                            //如果other变量不为空，则说明上一个文件留有单词，可直接将此次所读取文件的第一个单词与之配对构成双词，然后直接读取下一个单词。
                            if (other != null) {
                                String ad = other + " " + str[0].split("/")[0];
                                wordlist.add(ad);
                                other = null;
                                continue;
                            }
                            //System.out.println(str.length+"                        "+k);
                            try {
                                //正常获取双词。
                                String a = str[k].split("/")[0] + " " + str[k + 1].split("/")[0];
                                wordlist.add(a);
                            }
                            //异常处理：如果抛出ArrayIndexOutOfBoundsException就说明，已经读到最后一个单词，因此，下地址的读取会造成溢出。
                            //此时就将这一单词存入other变量，参与到下一个文件的双词获取中。
                                catch(ArrayIndexOutOfBoundsException e){
                                    other = str[k].split("/")[0];
                                }
                        }

                        //s=s.replaceAll("[ ,，。-（）：:_；…？！“”]"," ");
                        //s=s.replaceAll("[ ,，。-（）：:_；…？！“”]"," ");
                        /*s=s.replaceAll("[ ！]"," ");
                        str=s.split(" ");
                        int pathJ=1;
                        for(int k=0;k<str.length;k++){
                            if(str[k].split("/")[0]==""){
                                continue;
                            }
                            if(other!=null){
                                String ad=other+" "+str[k].split("/")[0];
                                wordlist.add(ad);
                                other=null;
                                continue;
                            }
                            for(;k+pathJ<str.length;){
                                if(str[k+pathJ].split("/")[0]!=""){
                                    String a=str[k].split("/")[0]+" "+str[k+pathJ].split("/")[0];
                                    wordlist.add(a);
                                    break;
                                }
                                pathJ++;
                                if(k+pathJ==str.length){
                                    other=str[k];
                                }
                            }
                            if(other!=null){
                                break;
                            }
                        }*/
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        //统计词频，算法与readFile2HashTable方法同。
        int wlength=wordlist.size();
        for (int i = 0; i < wlength; i++) {
            if (wordlists.containsKey(wordlist.get(i))) {
                int freq = Integer.parseInt(wordlists.get(wordlist.get(i)).toString());
                wordlists.put(wordlist.get(i), (int)(freq + 1));
            } else {
                wordlists.put(wordlist.get(i), (int)1);
            }
        }
        return wordlists;
    }
}

