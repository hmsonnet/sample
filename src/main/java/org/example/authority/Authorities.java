package org.example.authority;


import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_user_authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Comment("권한")
    private String authority;

}
