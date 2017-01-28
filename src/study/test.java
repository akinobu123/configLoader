package study;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class test {

	static config fConfig = new config();
	static matrix fMatrix = new matrix();
	
	public static void main(String[] args) {
		
		// ダミーのマトリクスを作成
		HashMap<String,String> map1 = new HashMap<String,String>();		
		map1.put("AAA", "A1");
		map1.put("BBB", "B2");
		map1.put("CCC", "C1");
		fMatrix.add(map1);
		
		HashMap<String,String> map2 = new HashMap<String,String>();		
		map2.put("AAA", "A2");
		map2.put("BBB", "B1");
		map2.put("CCC", "C3");
		fMatrix.add(map2);

		// テスト用コンフィグの読み込み
		fConfig.read("C:\\Users\\akinobu\\workspace\\study\\config.txt");
		
		// 各テスト文書セットパターンを検索
		int testItem = 1;
		for(int patternIndex=0; 
				patternIndex<fConfig.fTestDocSetPatterns.size(); 
				patternIndex++) {
			String str = fConfig.fTestDocSetKeys.get(patternIndex);
			String[] strList = str.split("#");
			if(strList.length==3) {
				String factor = strList[1];
				String level = strList[2];
				// もし指定の因子水準が現在のテスト項目と合致するならコピー実行
				if(fMatrix.get(testItem, factor).equals(level)){
					executeCopy(testItem, patternIndex);
					break;
				}
			}
		}
	}
	
	static void executeCopy(int testItem, int patternIndex) {
		// テスト文書保管ディレクトリの取り出し
		String fileDir = fConfig.fTestDocPath;
		if ( ! fileDir.endsWith("\\")) {
			fileDir = fileDir + "\\";
		}
		// テスト文書セットパターン内のリストでループ
		List<String> pattern = fConfig.fTestDocSetPatterns.get(patternIndex);
		for(int i=0; i<pattern.size(); i++) {
			// ファイル名の取り出し
			String fileName = pattern.get(i);
			// 置換文字を置き換え（因子ー＞水準）
			for(int j=0; j<fConfig.fTestDocNameReplaces.size(); j++) {
				String factor = fConfig.fTestDocNameReplaces.get(j);
				String level = fMatrix.get(testItem, factor);
				fileName = fileName.replace("["+factor+"]", level);
			}
			// ファイルパスの合成、コピー実行
			String filePath = fileDir + fileName;
			System.out.println(filePath);
			copyFile(filePath, ".\\img\\" + fileName);
		}
	}
	
	static boolean copyFile(String srcFile, String dstFile) {
		try {
			File src = new File(srcFile); if (!src.exists()) { return false; };
			File dst = new File(dstFile);
			File dstDir = new File(dst.getPath());
			dstDir.mkdirs();
			Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
