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
		
		// �_�~�[�̃}�g���N�X���쐬
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

		// �e�X�g�p�R���t�B�O�̓ǂݍ���
		fConfig.read("C:\\Users\\akinobu\\workspace\\study\\config.txt");
		
		// �e�e�X�g�����Z�b�g�p�^�[��������
		int testItem = 1;
		for(int patternIndex=0; 
				patternIndex<fConfig.fTestDocSetPatterns.size(); 
				patternIndex++) {
			String str = fConfig.fTestDocSetKeys.get(patternIndex);
			String[] strList = str.split("#");
			if(strList.length==3) {
				String patternFactor = strList[1];
				String patternLevel = strList[2];
				// �����w��̈��q���������݂̃e�X�g���ڂƍ��v����Ȃ�R�s�[���s
				String level = fMatrix.get(testItem, patternFactor);
				if(level != null && level.equals(patternLevel)) {
					executeCopy(testItem, patternIndex);
					break;
				}
			}
		}
	}
	
	static void executeCopy(int testItem, int patternIndex) {
		// �e�X�g�����ۊǃf�B���N�g���̎��o��
		String fileDir = fConfig.fTestDocPath;
		if (fileDir.equals("")) { System.out.println("[testDocPath] is none."); return; }
		if ( ! fileDir.endsWith("\\")) {
			fileDir = fileDir + "\\";
		}
		// �e�X�g�����Z�b�g�p�^�[�����̃��X�g�Ń��[�v
		if (fConfig.fTestDocSetPatterns.size() <= patternIndex) { return; }
		List<String> pattern = fConfig.fTestDocSetPatterns.get(patternIndex);
		for(int i=0; i<pattern.size(); i++) {
			// �t�@�C�����̎��o��
			String fileName = pattern.get(i);
			// �u��������u�������i���q�[�������j
			for(int j=0; j<fConfig.fTestDocNameReplaces.size(); j++) {
				String factor = fConfig.fTestDocNameReplaces.get(j);
				String level = fMatrix.get(testItem, factor);
				if (level == null) {
					System.out.println(
						String.format("[testDocNameReplace] Factor %s is none.", factor));
					continue;
				}
				fileName = fileName.replace("["+factor+"]", level);
			}
			// �t�@�C���p�X�̍����A�R�s�[���s
			String filePath = fileDir + fileName;
			System.out.println(filePath);
			boolean ret = copyFile(filePath, String.format(".\\img\\%03d_%s", i, fileName));
			if (!ret) {
				System.out.println(
					String.format("Test item no.%03d, Copy error : %s", testItem, filePath));
			}
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
