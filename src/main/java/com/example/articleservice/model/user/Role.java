package com.example.articleservice.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@SQLDelete(sql = "UPDATE roles SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "roles")
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "enum('USER','ADMIN')")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RoleName name;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Role(RoleName name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
