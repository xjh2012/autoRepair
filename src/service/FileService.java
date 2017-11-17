package service;

import model.FileAssignmentView;
import model.FileView;
import model.ListFileAssignmentView;

import java.util.List;

public interface FileService {
	//DIR
	public List<FileView> getDirList();
	public List<FileView> getDirByParam(FileView searchDir);
	
	
	//FILE
	public List<FileView> getFileList();
	public List<FileView> getFileByParam(FileView searchFile);
	public Integer insertFile(FileView file);
	public void updateFile(FileView file);
	public void deleteFile(FileView file);
	public void backupFile(FileView file);
	
	//FILE ASSIGNMENT
	public Integer insertFileAssignment(FileAssignmentView fileAssignment);
	public void updateFileAssignment(FileAssignmentView fileAssignment);
	public Integer deleteCourse(FileAssignmentView fileAssignment);
	public List<FileAssignmentView> getFileAssignmentList();
	public List<FileAssignmentView> getFileAssignmentByParam(FileAssignmentView searchFileAssignment);
	
	//LIST FILE ASSIGNMENT VIEW
	public List<ListFileAssignmentView> getListFileAssignmentList();
	public List<ListFileAssignmentView> getListFileAssigmentByParam(ListFileAssignmentView searchListFileAssignment);
}
