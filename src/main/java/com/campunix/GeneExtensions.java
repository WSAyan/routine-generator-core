package com.campunix;

public class GeneExtensions {

    public static boolean hasSameCourseCodeOf(Gene first, Gene second) {
        return first.getCourseCode().equals(second.getCourseCode());
    }

    public static boolean hasSameCourseTeacherOf(Gene first, Gene second) {
        return first.getCourseTeacher().equals(second.getCourseTeacher());
    }

    public static boolean hasSameSemesterOf(Gene first, Gene second) {
        return first.getSemester().equals(second.getSemester());
    }

    public static boolean isInSameSlotOnSameDay(Gene first, Gene second, int totalSlot, int totalSemester) {
        return isInSameDay(first, second, totalSlot, totalSemester)
                && isInSameSlotOf(first, second, totalSlot);
    }

    public static boolean isInPreviousSlotOnSameDay(Gene first, Gene second, int totalSlot, int totalSemester) {
        return isInSameDay(first, second, totalSlot, totalSemester)
                && isInPreviousSlotOf(first, second, totalSlot);
    }

    public static boolean isInNextSlotOnSameDay(Gene first, Gene second, int totalSlot, int totalSemester) {
        return isInSameDay(first, second, totalSlot, totalSemester)
                && isInNextSlotOf(first, second, totalSlot);
    }

    private static boolean isInSameSlotOf(Gene first, Gene second, int totalSlot) {
        return first.getCellNumber() % totalSlot == second.getCellNumber() % totalSlot;
    }

    private static boolean isInPreviousSlotOf(Gene first, Gene second, int totalSlot) {
        return first.getCellNumber() % totalSlot == (second.getCellNumber() - 1) % totalSlot;
    }

    private static boolean isInNextSlotOf(Gene first, Gene second, int totalSlot) {
        return first.getCellNumber() % totalSlot == (second.getCellNumber() + 1) % totalSlot;
    }

    private static boolean isInSameDay(Gene first, Gene second, int totalSlot, int totalSemester) {
        int totalCellInADay = totalSlot * totalSemester;
        int cell1Day = first.getCellNumber() / totalCellInADay;
        int cell2Day = second.getCellNumber() / totalCellInADay;
        return cell1Day == cell2Day;
    }
}

