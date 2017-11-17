package dao;

import model.FileAssignmentView;
import model.FileView;
import model.ListFileAssignmentView;

import java.util.List;

public interface FileDao {
	
	//DIR
	public List<FileView> getDirList();
	public List<FileView> getDirByParam(FileView searchDir);
	
	//FILE
	public List<FileView> getFileList();
	public List<FileView> getFileByParam(FileView searchFile);
	public Integer insertFile(FileView file);
	public void updateFile(FileView file);
	public void backupFile(FileView file);
	public void deleteFile(FileView file);

	//FILE ASSIGNMENT
	public List<FileView> getFileAssignmetList();
	public List<FileView> getFileAssignmentByParam(FileAssignmentView searchFileAssignment);
	public Integer insertFileAssignment(FileAssignmentView fileAssignment);
	public void updateFileAssignment(FileAssignmentView fileAssignment);
	public void backupFileAssignment(Integer id);
	
	
	//LIST FILE ASSIGNMENT VIEW
	public List<ListFileAssignmentView> getListFileAssignmetList();
	public List<ListFileAssignmentView> getListFileAssignmentByParam(ListFileAssignmentView searchListFileAssignment);


}
