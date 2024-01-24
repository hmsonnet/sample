package org.example.authority;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tb_authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("아이디")
    private Long id;

    @Column(nullable = false, length = 50)
    @Comment("권한")
    private String role;

    @OneToMany(mappedBy = "authority")
    private List<UserAuthority> userAuthorities;

}
