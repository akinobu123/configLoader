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
				
				// �R���t�B�O���̌��o�����������A�t���O�𗧂ĂĊe�f�[�^�̓ǂݍ��݃��[�h�ɂ���
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
					// ���s�݂̂̍s�œǂݍ��݃��[�h�����Z�b�g
					isTestDocPath = false;
					isTestDocSetPattern = false;
					isTestDocNameReplace = false;
				}

				// �t�@�C��������ǂݍ���ł��܂����ꍇ�ɔ����A���當�����Z�b�g
				if (str == null) {
					str = new String("");
				}
				
				// ���[�h�ɉ����Ċe�f�[�^��ǂݍ��݁A�����o�[�ւ̃Z�b�g
				if (isTestDocPath) {
					fTestDocPath = str;
				} else if (isTestDocSetPattern) {
					if (str.equals("##")) {
						// �p�^�[����1�Z�b�g�ǉ�
						fTestDocSetKeys.add(key);
						fTestDocSetPatterns.add(pattern);
						// �p�^�[���p�̃��X�g���J��
						pattern = null;
					} else if (str.startsWith("#")) {
						// #�n�܂�Ȃ�p�^�[���̃L�[��ǂݍ���
						key = str;
						// �p�^�[���p�̃��X�g���쐬
						pattern = new ArrayList<String>();
					} else {
						// #�n�܂�łȂ���΃p�^�[�����̃t�@�C������ǂݍ���
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
