package ru.site.web.model;

public class FieldWeb {
    private int[][] matrix;

    public FieldWeb() {}

    public FieldWeb(int[][] matrix) { this.matrix = matrix; }

    public int[][] getField() { return matrix; }

    public void setField(int[][] matrix) { this.matrix = matrix; }
}