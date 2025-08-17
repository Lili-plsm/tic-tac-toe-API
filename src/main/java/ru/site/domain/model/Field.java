package ru.site.domain.model;

public class Field {
    private int[][] matrix;

    public static Field createEmptyField() {
        int[][] matrix = new int[3][3];
        return new Field(matrix);
    }

    public Field() {}

    public Field(int[][] matrix) { this.matrix = matrix; }

    public int[][] getField() { return matrix; }

    public void setField(int[][] matrix) { this.matrix = matrix; }
}
