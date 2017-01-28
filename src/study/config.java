package study;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class config {
	
	public String fTestDocPath = new String("");
	public List<String> fTestDocSetKeys = new ArrayList<String>();
	public List<List<String>> fTestDocSetPatterns = new ArrayList<List<String>>();
	public List<String> fTestDocNameReplaces = new ArrayList<String>();	
	
	public void read(String filePath) {
		try{
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String str = null;
			boolean isTestDocPath = false;
			boolean isTestDocSetPattern = false;
			boolean isTestDocNameReplace = false;
			String key = new String("");
			List<String> pattern = null;
			while((str = br.readLine()) != null){
				
				// コンフィグ内の見出しを検索し、フラグを立てて各データの読み込みモードにする
				if(str.equals("[testDocPath]")) {
					isTestDocPath = true;
					str = br.readLine();
				} else if(str.equals("[testDocSetPattern]")) {
					isTestDocSetPattern = true;
					str = br.readLine();
				} else if(str.equals("[testDocNameReplace]")) {
					isTestDocNameReplace = true;
					str = br.readLine();
				} else if(str.equals("")) {
					// 改行のみの行で読み込みモードをリセット
					isTestDocPath = false;
					isTestDocSetPattern = false;
					isTestDocNameReplace = false;
				}

				// ファイル末尾を読み込んでしまった場合に備え、から文字をセット
				if (str == null) {
					str = new String("");
				}
				
				// モードに応じて各データを読み込み、メンバーへのセット
				if (isTestDocPath) {
					fTestDocPath = str;
				} else if (isTestDocSetPattern) {
					if (str.equals("##")) {
						// パターンを1セット追加
						fTestDocSetKeys.add(key);
						fTestDocSetPatterns.add(pattern);
						// パターン用のリストを開放
						pattern = null;
					} else if (str.startsWith("#")) {
						// #始まりならパターンのキーを読み込む
						key = str;
						// パターン用のリストを作成
						pattern = new ArrayList<String>();
					} else {
						// #始まりでなければパターン内のファイル名を読み込む
						pattern.add(str);
					}
				} else if (isTestDocNameReplace) {
					fTestDocNameReplaces.add(str);
				}
			}
			br.close();
			}catch(FileNotFoundException e){
				System.out.println(e);
			}catch(IOException e){
				System.out.println(e);
			}
	}
}
