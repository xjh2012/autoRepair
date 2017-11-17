package service;

import model.*;

import java.util.List;

public interface CourseService {
	//Course
	public List<CourseView> getCourseList();
	public List<CourseView> getCourseByParam(CourseView searchCourse);
	public Integer createCourse(CourseView course);
	public Integer deleteCourse(CourseView course);
	public List<CourseView> updateCourseByParam(CourseView course);
	public List<CourseView>  deleteCourseByParam(CourseView course);

	//Course Group
	public List<CourseGroupView> getCourseGroupList();
	public List<CourseGroupView> getCourseGroupByParam(CourseGroupView searchCourseGroup);
	public Integer createCourseGroup(CourseView courseGroup);
	public List<CourseView> updateCourseGroupByParam(CourseView courseGroup);
	public List<CourseView>  deleteCourseGroupByParam(CourseView courseGroup);
	
	//Course Assignment
	public List<AssignmentView> getAssignmentList();
	public List<AssignmentView> getAssignmentByParam(AssignmentView searchAssignment);
	public Integer createAssignment(AssignmentView assignment);
	public Integer updateAssignment(AssignmentView assignment);
	public Integer deleteAssignment(AssignmentView assignment);
	public List<AssignmentView> createAssignmentByParam(AssignmentView assignment);
	public List<AssignmentView>  deleteAssignmentByParam(AssignmentView assignment);

	//Course Enrollment
	public List<EnrollmentView> getEnrollmentList();
	public List<EnrollmentView> getEnrollmentbyParam(EnrollmentView searchEnrollment);
	public Integer createEnrollment(EnrollmentView enrollment);
	public Integer updateEnrollment(EnrollmentView enrollment);
	public Integer deleteEnrollment(EnrollmentView enrollment);
	public List<EnrollmentView> createEnrollmentByParam(EnrollmentView enrollment);
	public List<EnrollmentView>  deleteEnrollmentByParam(EnrollmentView enrollment);

	//Course User Enrollment View
	public List<UserEnrollmentView> getUserEnrollmentList();
	public List<UserEnrollmentView> getUserEnrollmentbyParam(UserEnrollmentView searchUserEnrollment);
		

	
}
