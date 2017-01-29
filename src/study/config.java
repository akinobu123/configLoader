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

	enum EReadMode {
		eNone,
		eTestDocPath,
		eTestDocSetPattern,
		eTestDocNameReplace
	}
	
	public void read(String filePath) {
		try{
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String str = null;
			EReadMode readMode = EReadMode.eNone;
			String key = new String("");
			List<String> pattern = null;
			while((str = br.readLine()) != null){
				
				// コンフィグ内の見出しを検索し、フラグを立てて各データの読み込みモードにする
				if(str.equals("[testDocPath]")) {
					readMode = EReadMode.eTestDocPath;
				} else if(str.equals("[testDocSetPattern]")) {
					readMode = EReadMode.eTestDocSetPattern;
				} else if(str.equals("[testDocNameReplace]")) {
					readMode = EReadMode.eTestDocNameReplace;
				} else if(str.equals("")) {
					// 改行のみの行で読み込みモードをリセット
					readMode = EReadMode.eNone;
				} else {

					// モードに応じて各データを読み込み、メンバーへのセット
					if (readMode == EReadMode.eTestDocPath) {
						fTestDocPath = str;
					} else if (readMode == EReadMode.eTestDocSetPattern) {
						if (str.equals("##") && pattern != null) {
							// パターンを1セット追加
							fTestDocSetKeys.add(key);
							fTestDocSetPatterns.add(pattern);
							// パターン用のリストを開放
							pattern = null;
						} else if (str.startsWith("#")) {
							// #始まりならパターンのキーを読み込む
							key = str;
							// パターン用のリストを作成（リセット）
							if (pattern == null) {
								pattern = new ArrayList<String>();
							} else {
								pattern.clear();
							}
						} else if (pattern != null){
							// #始まりでなければパターン内のファイル名を読み込む
							pattern.add(str);
						}
					} else if (readMode == EReadMode.eTestDocNameReplace) {
						fTestDocNameReplaces.add(str);
					}
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
