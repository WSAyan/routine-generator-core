package com.campunix;

public class Gene {
    private String courseCode;
    private String courseTeacher;
    private String semester;
    private boolean isLab;
    private int semesterNumber;
    private int cellNumber; // 1 - 30

    public Gene(String courseCode, String courseTeacher, String semester, int semesterNumber, boolean isLab) {
        this.courseCode = courseCode;
        this.courseTeacher = courseTeacher;
        this.semester = semester;
        this.semesterNumber = semesterNumber;
        this.isLab = isLab;
    }

    // Getters and Setters
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isLab() {
        return isLab;
    }

    public void setLab(boolean isLab) {
        this.isLab = isLab;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(int cellNumber) {
        this.cellNumber = cellNumber;
    }
}
