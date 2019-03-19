import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	//���嵥������
	private static Integer n=0;
	//�洢����,����
	private static Map<String,Integer> map = new TreeMap<String,Integer>();
	/**
	* ��ȡ����
	* @param fileNamePath �ļ�����
	*/
	public void read(String fileNamePath){
		try {
			BufferedReader br = new BufferedReader(new FileReader("/src/test/source.txt"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			br.close();
			//����
			Pattern p = Pattern.compile("[a-zA-Z]+");
			String words = sb.toString();
			Matcher matcher = p.matcher(words);
			int times=0;
			while(matcher.find()){
				String word = matcher.group();
				n++;
				if(map.containsKey(word)){
					times = map.get(word);
					// ����map��keyͳ������
					map.put(word, times+1);
				}else{
					map.put(word, 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* �����߳�
	* @param fileNamePath
	* @throws Exception
	*/
	public void creatThread(String fileNamePath) throws Exception{
		Thread thread = new Thread(){
			public void run(){
				read(fileNamePath);
			}
		};
		thread.start();
		thread.join();
	}
	/**
	* ���򷽷�
	* @throws IOException
	*/
	public void comparatorToWord() throws IOException{
		//��ͳ�ƽ�������
		List<Entry<String,Integer>> list = new ArrayList<Entry<String,Integer>>(map.entrySet());
		Comparator<Entry<String,Integer>> com = new Comparator<Entry<String,Integer>>(){
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return (o2.getValue().compareTo(o1.getValue()));
			}
		};
		Collections.sort(list,com);
		// д�����ļ�
		BufferedWriter bw = new BufferedWriter(new FileWriter("/src/result.txt"));
		for(Entry<String,Integer> e:list){
			System.out.println("����  "+e.getKey()+",����  "+e.getValue());
			bw.write("����  "+e.getKey()+",����  "+e.getValue());
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	public static void main(String[] args) throws IOException {
		// ��ȡD�̵��ļ�
		File file = new File("/src");
		Main r = new Main();
		if(file.isDirectory()){
			File[] files = file.listFiles();
			// �����ļ�����
			String match = "\\w+.txt";
			for(int i=0;i<files.length;i++){
				String fileName = files[i].getName();
				if(fileName.matches(match)){
					try {
						System.out.println(fileName);
						r.creatThread("D:\\"+fileName);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			r.comparatorToWord();		
		}
	}
}
