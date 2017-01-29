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
				
				// �R���t�B�O���̌��o�����������A�t���O�𗧂ĂĊe�f�[�^�̓ǂݍ��݃��[�h�ɂ���
				if(str.equals("[testDocPath]")) {
					readMode = EReadMode.eTestDocPath;
				} else if(str.equals("[testDocSetPattern]")) {
					readMode = EReadMode.eTestDocSetPattern;
				} else if(str.equals("[testDocNameReplace]")) {
					readMode = EReadMode.eTestDocNameReplace;
				} else if(str.equals("")) {
					// ���s�݂̂̍s�œǂݍ��݃��[�h�����Z�b�g
					readMode = EReadMode.eNone;
				} else {

					// ���[�h�ɉ����Ċe�f�[�^��ǂݍ��݁A�����o�[�ւ̃Z�b�g
					if (readMode == EReadMode.eTestDocPath) {
						fTestDocPath = str;
					} else if (readMode == EReadMode.eTestDocSetPattern) {
						if (str.equals("##") && pattern != null) {
							// �p�^�[����1�Z�b�g�ǉ�
							fTestDocSetKeys.add(key);
							fTestDocSetPatterns.add(pattern);
							// �p�^�[���p�̃��X�g���J��
							pattern = null;
						} else if (str.startsWith("#")) {
							// #�n�܂�Ȃ�p�^�[���̃L�[��ǂݍ���
							key = str;
							// �p�^�[���p�̃��X�g���쐬�i���Z�b�g�j
							if (pattern == null) {
								pattern = new ArrayList<String>();
							} else {
								pattern.clear();
							}
						} else if (pattern != null){
							// #�n�܂�łȂ���΃p�^�[�����̃t�@�C������ǂݍ���
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
