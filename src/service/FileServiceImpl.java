package service;

import dao.FileDao;
import model.FileAssignmentView;
import model.FileView;
import model.ListFileAssignmentView;

import java.util.List;

public class FileServiceImpl implements FileService{
	

	private FileDao fileDao;
	
	//FILE
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}
	public List<FileView> getFileList() {
		// TODO Auto-generated method stub
		return fileDao.getFileList();
	}
	public List<FileView> getFileByParam(FileView searchFile) {
		// TODO Auto-generated method stub
		return fileDao.getFileByParam(searchFile);
	}
	public Integer insertFile(FileView file) {
		// TODO Auto-generated method stub
		return fileDao.insertFile(file);
	}
	public void updateFile(FileView file) {
		// TODO Auto-generated method stub
		fileDao.updateFile(file);

	}
	public void deleteFile(FileView file) {
		fileDao.deleteFile(file);
	}
	public void backupFile(FileView file) {
		// TODO Auto-generated method stub
		return;
	}

	
	
	//FILE ASSIGNMENT
	public Integer insertFileAssignment(FileAssignmentView fileAssignment) {
		return fileDao.insertFileAssignment(fileAssignment);
	}



	public void updateFileAssignment(FileAssignmentView fileAssignment) {
		// TODO Auto-generated method stub
		fileDao.updateFileAssignment(fileAssignment);
	}



	public Integer deleteCourse(FileAssignmentView fileAssignment) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<FileAssignmentView> getFileAssignmentList() {
		// TODO Auto-generated method stub
		return null;
	}



	public List<FileAssignmentView> getFileAssignmentByParam(FileAssignmentView searchFileAssignment) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//TODO LIST FILE ASSIGNMENT
	public List<ListFileAssignmentView> getListFileAssignmentList() {
		return fileDao.getListFileAssignmetList();
	}
	public List<ListFileAssignmentView> getListFileAssigmentByParam(ListFileAssignmentView searchListFileAssignment) {
		return fileDao.getListFileAssignmentByParam(searchListFileAssignment);
	}
	
	//TODO @Budi DIR
	public List<FileView> getDirList() {
		return fileDao.getDirList();
	}
	public List<FileView> getDirByParam(FileView searchDir) {
		return fileDao.getDirByParam(searchDir);
	}
	

}
