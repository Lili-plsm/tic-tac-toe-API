package ru.site.datasource.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "field")
public class FieldStore {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private int[][] matrix;

    public FieldStore() {}

    public FieldStore(int[][] matrix) { this.matrix = matrix; }

    public int[][] getField() { return matrix; }

    public void setField(int[][] matrix) { this.matrix = matrix; }
}
